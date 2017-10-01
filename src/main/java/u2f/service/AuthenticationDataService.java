/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.service;

import com.yubico.u2f.data.messages.AuthenticateRequestData;

/**
 *
 * @author ENESTORO
 */
public interface AuthenticationDataService {
    void save(AuthenticateRequestData authenticateRequestData);
    AuthenticateRequestData delete(String requestId);
}
