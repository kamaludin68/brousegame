/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Emerio-PC
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//        if(null == authentication)
            return Optional.of("system");
//        return Optional.of(authentication.getName());
    }
    
}
