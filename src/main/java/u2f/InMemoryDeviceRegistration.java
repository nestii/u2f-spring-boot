/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f;

import com.yubico.u2f.data.DeviceRegistration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ENESTORO
 */
public class InMemoryDeviceRegistration {
    private Map<String,List<DeviceRegistration>> usernameToRegistration = new ConcurrentHashMap<>();

	public void saveRegistrationForUsername(DeviceRegistration registration, String username) {
		List<DeviceRegistration> registrations = findRegistrationsByUsername(username);
		registrations.add(registration);
		usernameToRegistration.put(username, registrations);
	}

	public List<DeviceRegistration> findRegistrationsByUsername(String username) {
		List<DeviceRegistration> result = usernameToRegistration.get(username);
		if(result == null) {
			result = new ArrayList<>();
		}
		return result;
	}
}
