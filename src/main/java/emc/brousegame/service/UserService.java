/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.Notification;
import emc.brousegame.domain.User;
import emc.brousegame.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Emerio-PC
 */
public interface UserService {
    public User save(User user);
    public List<User> listUser();
    public Optional<User> findById(Long id);
    public void delete(Long id);
    public void notifyUser(User recipientUser, Notification notification);
    public Optional<User> findByUsername(String username);
    public void setIsPresent(User user, Boolean stat);
    public void update(User user) throws ResourceNotFoundException;
    public List<User> friendList (String username);
}
