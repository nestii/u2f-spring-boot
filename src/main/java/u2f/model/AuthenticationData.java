/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.model;

import com.yubico.u2f.data.messages.AuthenticateRequestData;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ENESTORO
 */
@Entity
@Table(name = "authentication_data")
public class AuthenticationData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private AuthenticateRequestData authenticateRequestData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    
    public AuthenticateRequestData getAuthenticateRequestData() {
        return authenticateRequestData;
    }

    public void setAuthenticateRequestData(AuthenticateRequestData authenticateRequestData) {
        this.authenticateRequestData = authenticateRequestData;
    }
    
    
}
