/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f.service;

import u2f.model.User;

/**
 *
 * @author ENESTORO
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
