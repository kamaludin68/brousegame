/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Emerio-PC
 */
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameAndStatus(String username,Boolean status);
    public Optional<User> findByUsername(String username);
    public List<User> findAllByUsernameNot(String username);
}
