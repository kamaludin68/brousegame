/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame;

import emc.brousegame.domain.AppParams;
import emc.brousegame.domain.Currency;
import emc.brousegame.domain.User;
import emc.brousegame.service.AppParamsService;
import emc.brousegame.service.CurrencyService;
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
    @Autowired
    CurrencyService currencyService;

    @Override
    public void run(String... args) throws Exception {
        if(userService.listUser().size()<=0){
            User user = new User();
            user.setUsername("admin");
            user.setPassword("password");
            user.setRole("ADMIN");
            user.setFullname("administrator");
            userService.save(user);
        }
        
       if(appParamsService.list().size()<=0){
            AppParams appParams = new AppParams();
            appParams.setCode("EQUITY");
            appParams.setValue("1000");
            appParams.setDescription("digunakan untuk limit equity");
            appParamsService.save(appParams);
       }
       
       if(currencyService.findAll().size()<=0){
           Currency currency = new Currency();
           currency.setCode("USD");
           currency.setFlag("flag-icon-us");
           currency.setName("US DOLLAR");
           currencyService.save(currency);
           
           currency = new Currency();
           currency.setCode("IDR");
           currency.setFlag("flag-icon-id");
           currency.setName("RUPIAH");
           currencyService.save(currency);
           
           currency = new Currency();
           currency.setCode("SGD");
           currency.setFlag("flag-icon-sg");
           currency.setName("DOLLAR SINGAPORE");
           currencyService.save(currency);
           
       }
    }

}
