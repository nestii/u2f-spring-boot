package u2f.service;

import u2f.model.User;

/**
 *
 * @author ENESTORO
 */
public interface UserService {
    void save(User user);
    void update(User user);
    User findByUsername(String username);
}
