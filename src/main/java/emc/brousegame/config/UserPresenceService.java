package emc.brousegame.config;

import emc.brousegame.domain.User;
import emc.brousegame.service.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class UserPresenceService implements ChannelInterceptor{
  @Autowired 
  private UserService userService;

  @Override
  public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
    StompHeaderAccessor stompDetails = StompHeaderAccessor.wrap(message);

    if(stompDetails.getCommand() == null) { return; }

    switch(stompDetails.getCommand()) {
      case CONNECT:    
      case CONNECTED:
        toggleUserPresence(stompDetails.getUser().getName().toString(), true);
        break;
      case DISCONNECT:
        toggleUserPresence(stompDetails.getUser().getName().toString(), false);
        break;
      default:
        break;
    }
  }

  private void toggleUserPresence(String username, Boolean isPresent) {
      Optional<User> user = userService.findByUsername(username);
      if(user.isPresent())
        userService.setIsPresent(user.get(), isPresent);
  }
}