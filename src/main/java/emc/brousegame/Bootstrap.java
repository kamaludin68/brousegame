/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame;

import emc.brousegame.domain.AppParams;
import emc.brousegame.domain.User;
import emc.brousegame.service.AppParamsService;
import emc.brousegame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Emerio-PC
 */
@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    AppParamsService appParamsService;

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.setStatus("ACTIVE");
        user.setRole("ADMIN");
        userService.save(user);
        
        AppParams appParams = new AppParams();
        appParams.setCode("EQUITY");
        appParams.setValue("1000");
        appParams.setDescription("digunakan untuk limit equity");
        appParamsService.save(appParams);
    }

}
