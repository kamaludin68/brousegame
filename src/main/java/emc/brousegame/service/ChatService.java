/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.service;

import emc.brousegame.domain.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Emerio-PC
 */
public interface ChatService {
    public String establishChatSession(Long userIdOne, Long userIdTwo);
    void submitMessage(ChatMessage chatMessage);
    public List<ChatMessage> getExistingChatMessages(String channelUuid);
}
