/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.User;
import emc.brousegame.repository.UserRepository;
import emc.brousegame.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author Emerio-PC
 */
@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if(user == null){
                throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }
    
    @Override
    public User save(User user){
        String hash = encoder.encode(user.getPassword());
        user.setPassword(hash);
        return userRepository.save(user);
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
}
