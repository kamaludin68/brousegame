package emc.brousegame.controller;

import emc.brousegame.domain.ChatMessage;
import emc.brousegame.service.ChatService;
import emc.brousegame.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ChatController {
  @Autowired
  private ChatService chatService;

  @Autowired
    private UserService userService;

    @MessageMapping("/chat.{channelId}")
    @SendTo("/topic/chat.{channelId}")
    public ChatMessage chatMessage(@DestinationVariable String channelId, ChatMessage message){
      chatService.submitMessage(message);
      return message;
    }
    
    @PostMapping("chat/channel/{channelid}")
    public ChatMessage submitMessage(@PathVariable String channelId, ChatMessage message){
      chatService.submitMessage(message);
      return message;
    }

    @PostMapping("chat/channel")
    public ResponseEntity<?> createChatChannel(@RequestParam String usernameOne, @RequestParam String usernameTwo){ 
      String channelUuid = chatService.establishChatSession(usernameOne, usernameTwo);
      Map<String,Object> res = new HashMap<>();
      res.put("channelid", channelUuid);
      return ResponseEntity.ok(res);
    }
    
    @GetMapping("chat/channel/{channelid}")
    public ResponseEntity<?> getExistingChatMessages(@PathVariable String channelUuid) {
      List<ChatMessage> messages = chatService.getExistingChatMessages(channelUuid);
      return ResponseEntity.ok(messages);
    }
}