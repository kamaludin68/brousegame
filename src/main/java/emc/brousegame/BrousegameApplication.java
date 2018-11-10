package emc.brousegame;

import emc.brousegame.domain.User;
import emc.brousegame.repository.UserRepository;
import emc.brousegame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BrousegameApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(BrousegameApplication.class, args);
               
	}
}
