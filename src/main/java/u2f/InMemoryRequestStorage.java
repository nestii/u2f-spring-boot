/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f;

import com.yubico.u2f.data.messages.AuthenticateRequestData;
import com.yubico.u2f.data.messages.RegisterRequestData;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ENESTORO
 */
public class InMemoryRequestStorage {
    private Map<String,AuthenticateRequestData> idToData = new ConcurrentHashMap<>();
	private Map<String,RegisterRequestData> idToRegisterData = new ConcurrentHashMap<>();

	public void save(RegisterRequestData request) {
		idToRegisterData.put(request.getRequestId(), request);
	}

	public RegisterRequestData deleteRegistration(String id) {
		return idToRegisterData.remove(id);
	}

	public void save(AuthenticateRequestData request) {
		idToData.put(request.getRequestId(), request);
	}

	public AuthenticateRequestData delete(String id) {
		return idToData.remove(id);
	}
}
