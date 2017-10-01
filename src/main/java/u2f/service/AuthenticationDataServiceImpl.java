/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.service;

import com.yubico.u2f.data.messages.AuthenticateRequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import u2f.model.AuthenticationData;
import u2f.repository.AuthenticationDataRepository;

/**
 *
 * @author ENESTORO
 */
@Service
public class AuthenticationDataServiceImpl implements AuthenticationDataService {

    @Autowired
    private AuthenticationDataRepository authenticationDataRepository;
    
    @Override
    public void save(AuthenticateRequestData authenticateRequestData) {
        AuthenticationData authenticationData = new AuthenticationData();
        authenticationData.setAuthenticateRequestData(authenticateRequestData);
        authenticationData.setRequestId(authenticateRequestData.getRequestId());
        authenticationDataRepository.save(authenticationData);
    }

    @Override
    public AuthenticateRequestData delete(String requestId) {
        AuthenticationData authenticationData = authenticationDataRepository.findByRequestId(requestId);
        if(authenticationData != null) {
            authenticationDataRepository.delete(authenticationData.getId());
            return authenticationData.getAuthenticateRequestData();
        }
        return null;
    }
    
}
