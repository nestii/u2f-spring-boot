package u2f.service;

import com.yubico.u2f.data.messages.RegisterRequestData;

/**
 *
 * @author ENESTORO
 */
public interface RegisterDataService {
    void save(RegisterRequestData registerRequestData);
    RegisterRequestData delete(String requestId);
}
