package u2f.service;

import com.yubico.u2f.data.messages.RegisterRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import u2f.model.RegisterData;
import u2f.repository.RegisterDataRepository;

/**
 *
 * @author ENESTORO
 */
@Service
public class RegisterDataServiceImpl implements RegisterDataService {

    @Autowired
    private RegisterDataRepository registerDataRepository;
    
    @Override
    public void save(RegisterRequestData registerRequestData) {
        RegisterData registerData = new RegisterData();
        registerData.setRegisterRequestData(registerRequestData);
        registerData.setRequestId(registerRequestData.getRequestId());
        registerDataRepository.save(registerData);
    }

    @Override
    public RegisterRequestData delete(String requestId) {
        RegisterData requestData = registerDataRepository.findByRequestId(requestId);
        if(requestData != null) {
            registerDataRepository.delete(requestData.getId());
            return requestData.getRegisterRequestData();
        }
        return null;
    }
    
}
