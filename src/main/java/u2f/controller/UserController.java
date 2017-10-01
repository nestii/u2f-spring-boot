package u2f.controller;

import com.yubico.u2f.U2F;
import com.yubico.u2f.data.DeviceRegistration;
import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.AuthenticateResponse;
import com.yubico.u2f.data.messages.RegisterRequestData;
import com.yubico.u2f.data.messages.RegisterResponse;
import com.yubico.u2f.exceptions.DeviceCompromisedException;
import com.yubico.u2f.exceptions.NoEligibleDevicesException;
import com.yubico.u2f.exceptions.U2fBadInputException;
import java.security.Principal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import u2f.InMemoryDeviceRegistration;
import u2f.InMemoryRequestStorage;
import u2f.model.User;
import u2f.repository.UserRepository;
import u2f.service.UserService;

/**
 *
 * @author ENESTORO
 */
@Controller
@SessionAttributes("usernameAttr")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    U2F u2f;

    private static final String APP_ID = "https://localhost:8080";

    @Autowired
    InMemoryRequestStorage requestStorage;

    @Autowired
    InMemoryDeviceRegistration devices;

    @RequestMapping(value = {"/", "/registration"})
    public String registration(Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        RegisterRequestData registerRequestData = u2f.startRegistration(APP_ID, getRegistrations(userForm.getUsername()));
        requestStorage.save(registerRequestData);
        userForm.setRegisterRequestData(registerRequestData.toJson());
        userService.save(userForm);

        model.addAttribute("data", registerRequestData.toJson());
        model.addAttribute("usernameAttr", userForm.getUsername());

        return "redirect:/registration-u2f";
    }

    @RequestMapping(value = "/registration-u2f")
    public String registrationU2F(@ModelAttribute("usernameAttr") String username, Model model) {
        User user = userRepository.findByUsername(username);
        model.addAttribute("data", user.getRegisterRequestData());
        return "registration-u2f";
    }

    @RequestMapping(value = "/registration-u2f", method = RequestMethod.POST)
    public String registrationU2F(@ModelAttribute("usernameAttr") String username, @RequestParam("tokenResponse") String response, BindingResult bindingResult, Model model) {
        RegisterResponse registerResponse = RegisterResponse.fromJson(response);
        RegisterRequestData registerRequestData = requestStorage.deleteRegistration(registerResponse.getRequestId());
        DeviceRegistration registration = u2f.finishRegistration(registerRequestData, registerResponse);
        devices.saveRegistrationForUsername(registration, username);

        return "redirect:/registration-success";
    }

    @RequestMapping("/login")
    public String login(Model model, @RequestParam(value = "logout", required = false) String logout) {
        boolean isLoggedOut = logout != null;
        model.addAttribute("isLoggedOut", isLoggedOut);
        return "login";
    }

    @RequestMapping("/registration-success")
    public String registrationSuccess(Principal p) {
        return "registration-success";
    }

    @RequestMapping("/authenticate")
    public String authenticateForm(@ModelAttribute("usernameAttr") String username, Model model) throws U2fBadInputException, NoEligibleDevicesException {
        // Generate a challenge for each U2F device that this user has registered
        AuthenticateRequestData requestData = u2f.startAuthentication(APP_ID, getRegistrations(username));

        // Store the challenges for future reference
        requestStorage.save(requestData);

        // Return an HTML page containing the challenges
        model.addAttribute("data", requestData.toJson());
        return "authenticate";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String authenticate(@ModelAttribute("usernameAttr") String username, @RequestParam String tokenResponse)
            throws DeviceCompromisedException {
        AuthenticateResponse response = AuthenticateResponse.fromJson(tokenResponse);

        // Get the challenges that we stored when starting the authentication
        AuthenticateRequestData authenticateRequest = requestStorage.delete(response.getRequestId());

        // Verify the that the given response is valid for one of the registered
        // devices
        u2f.finishAuthentication(authenticateRequest, response, getRegistrations(username));

        return "redirect:/success";
    }

    @RequestMapping(value = {"/success"}, method = RequestMethod.GET)
    public String welcome(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "success";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    private List<DeviceRegistration> getRegistrations(String username) {
        List<DeviceRegistration> findRegistrationsByUsername = devices.findRegistrationsByUsername(username);
        return findRegistrationsByUsername;
    }

}
