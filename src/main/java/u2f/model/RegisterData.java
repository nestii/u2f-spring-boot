/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.model;

import com.yubico.u2f.data.messages.RegisterRequestData;
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
@Table(name="register_data")
public class RegisterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String requestId;

    @Column(nullable = false)
    private RegisterRequestData registerRequestData;

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
    

    public RegisterRequestData getRegisterRequestData() {
        return registerRequestData;
    }

    public void setRegisterRequestData(RegisterRequestData registerRequestData) {
        this.registerRequestData = registerRequestData;
    }
    
    
}
