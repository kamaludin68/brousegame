/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.Notification;
import emc.brousegame.domain.User;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.repository.UserRepository;
import emc.brousegame.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Emerio-PC
 */
@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsernameAndStatus(username,true);
        if(user == null){
                throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }
    
    @Override
    public User save(User user){
        String hash = encoder.encode(user.getPassword());
        user.setStatus(true);
        user.setPassword(hash);
        return userRepository.save(user);
    }
    
    @Override
    public void update(User user) throws ResourceNotFoundException{
        Optional<User> result = userRepository.findById(user.getId());
        if(result.isPresent()){
            User currentUser = result.get();
            currentUser.setRole(user.getRole());
            currentUser.setFullname(user.getFullname());
            currentUser.setUsername(user.getUsername());
            currentUser.setStatus(user.getStatus());
            userRepository.save(currentUser);
        }else{
            throw new ResourceNotFoundException("resource not found with id "+user.getId());
        }
    }
    
    @Override
    public void delete(Long id){
        userRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<User> listUser(){
        return userRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    
    
    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    
    public void notifyUser(User recipientUser, Notification notification) {
    if (recipientUser.getIsOnline()) {
      simpMessagingTemplate
        .convertAndSend("/topic/user.notification." + recipientUser.getId(), notification);
    } else {
      System.out.println("sending email notification to " + recipientUser.getUsername());
      // TODO: send email
    }
  }
    
    public void setIsPresent(User user, Boolean stat) {
        user.setIsOnline(stat);
        userRepository.save(user);
  }
    
  public List<User> friendList (String username){
      return userRepository.findAllByUsernameNot(username);
  }
}
