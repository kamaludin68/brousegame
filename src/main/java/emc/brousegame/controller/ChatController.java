package emc.brousegame.controller;

import emc.brousegame.domain.ChatMessage;
import emc.brousegame.service.ChatService;
import emc.brousegame.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {
  @Autowired
  private ChatService chatService;

  @Autowired
    private UserService userService;

    @MessageMapping("/private.chat.{channelId}")
    @SendTo("/topic/private.chat.{channelId}")
    public ChatMessage chatMessage(@DestinationVariable String channelId, ChatMessage message){
      chatService.submitMessage(message);

      return message;
    }

    @RequestMapping(value="/api/private-chat/channel", method=RequestMethod.PUT, produces="application/json", consumes="application/json")
    public ResponseEntity<String> establishChatChannel(@RequestParam Long userIdOne, @RequestParam Long userIdTwo){ 
      String channelUuid = chatService.establishChatSession(userIdOne, userIdTwo);
      return ResponseEntity.ok(channelUuid);
    }
    
    @RequestMapping(value="/api/private-chat/channel/{channelUuid}", method=RequestMethod.GET, produces="application/json")
    public ResponseEntity<?> getExistingChatMessages(@PathVariable("channelUuid") String channelUuid) {
      List<ChatMessage> messages = chatService.getExistingChatMessages(channelUuid);
      return ResponseEntity.ok(messages);
    }
}