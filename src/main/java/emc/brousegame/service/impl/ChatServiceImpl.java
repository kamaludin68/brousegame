/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service.impl;

import emc.brousegame.domain.ChatChannel;
import emc.brousegame.domain.ChatMessage;
import emc.brousegame.domain.Notification;
import emc.brousegame.domain.User;
import emc.brousegame.exception.BadRequestException;
import emc.brousegame.exception.ResourceNotFoundException;
import emc.brousegame.repository.ChatChannelRepository;
import emc.brousegame.repository.ChatMessageRepository;
import emc.brousegame.service.ChatService;
import emc.brousegame.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Emerio-PC
 */
@Service
public class ChatServiceImpl implements ChatService{
    
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatChannelRepository chatChannelRepository;
    @Autowired
    private UserService userService;
    
    private String getExistingChannel(Long userIdOne, Long userIdTwo) {
    List<ChatChannel> channel = chatChannelRepository
      .findExistingChannelById(userIdOne,userIdTwo);
    
    return (channel != null && !channel.isEmpty()) ? channel.get(0).getUuid() : null;
  }
    
    private String getExistingChannel(String usernameOne, String usernameTwo) {
    List<ChatChannel> channel = chatChannelRepository
      .findExistingChannelByUsername(usernameOne,usernameTwo);
    
    return (channel != null && !channel.isEmpty()) ? channel.get(0).getUuid() : null;
  }

  private String newChatSession(Long userIdOne, Long userIdTwo){
    User user1 = userService.findById(userIdOne).map(user -> user).orElseThrow(()->new ResourceNotFoundException("User id one not found"));
    User user2 = userService.findById(userIdTwo).map(user ->user).orElseThrow(()->new ResourceNotFoundException("User id two not found"));
    ChatChannel channel = new ChatChannel(user1, user2);
    chatChannelRepository.save(channel);

    return channel.getUuid();
  }
  
  private String newChatSession(String usernameOne, String usernameTwo){
    User user1 = userService.findByUsername(usernameOne).map(user -> user).orElseThrow(()->new ResourceNotFoundException("User id one not found"));
    User user2 = userService.findByUsername(usernameTwo).map(user ->user).orElseThrow(()->new ResourceNotFoundException("User id two not found"));
    ChatChannel channel = new ChatChannel(user1, user2);
    chatChannelRepository.save(channel);

    return channel.getUuid();
  }

  public String establishChatSession(Long userIdOne, Long userIdTwo){
    if (userIdOne == userIdTwo) {
      throw new BadRequestException("userId one equal to user id two");
    }

    String uuid = getExistingChannel(userIdOne,userIdTwo);

    // If channel doesn't already exist, create a new one
    return (uuid != null) ? uuid : newChatSession(userIdOne,userIdTwo);
  }
  
  public void submitMessage(ChatMessage chatMessage){
    chatMessageRepository.save(chatMessage);
//    Notification notification = new Notification();
//    notification.setContents(chatMessage.getWho().getUsername() + " has sent you a message");
//    notification.setFromUserId(chatMessage.getAuthor().getId());
//    notification.setType("ChatMessageNotification");
//    userService.notifyUser(chatMessage.getRecipient(),notification);
  }
 
  public List<ChatMessage> getExistingChatMessages(String channelUuid){
    return chatMessageRepository.findAllByChannelId(channelUuid);
    // TODO: fix this
    //List<ChatMessage> messagesByLatest = Lists.reverse(chatMessages); 

  }

    @Override
    public String establishChatSession(String usernameOne, String usernameTwo) {
        if (usernameOne == usernameTwo) {
            throw new BadRequestException("userId one equal to user id two");
         }

    String uuid = getExistingChannel(usernameOne,usernameTwo);

    // If channel doesn't already exist, create a new one
    return (uuid != null) ? uuid : newChatSession(usernameOne,usernameTwo);
    }
    
}
