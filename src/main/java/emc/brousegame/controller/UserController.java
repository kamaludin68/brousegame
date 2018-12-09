/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.controller;

import emc.brousegame.domain.User;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.service.UserService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Emerio-PC
 */
@RestController
@RequestMapping("/api/v1/")
public class UserController {
    @Autowired
    private UserService userService;
    
    @GetMapping("user")
    public List<User> listUser(){
        List<User> users =  userService.listUser();
        for (User user : users) {
            user.setPassword(null);
        }
        return users;
    }
    
    @GetMapping("user/{id}")
    public User getUser(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        if(!user.isPresent())
            throw new ResourceNotFoundException("id:"+id);
        User res =  user.get();
        res.setPassword(null);
        return res;
    }
    
    @PostMapping("user")
    public ResponseEntity<Object> create(@RequestBody User user){
        if(null != user.getId())
            return ResponseEntity.badRequest().body("A new User cannot already have an ID");
        User res = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(res.getId()).toUri();
        return ResponseEntity.created(location).body(res);
    }
    
    @PutMapping("user")
    public User update(@RequestBody User user){
        return userService.save(user);
    }
    
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok("user "+id+" has been delete");
    }
}
