/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Emerio-PC
 */ 
@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {
    @Query(value="select * FROM"
        + "    Chat_Message m"
        + "  WHERE"
        + "    m.authorUser.id IN (:userIdOne, :userIdTwo)"
        + "  AND"
        + "    m.recipientUser.id IN (:userIdOne, :userIdTwo)"
        + "  ORDER BY"
        + "    m.timeSent"
        + "  DESC"
    ,nativeQuery = true)
    public List<ChatMessage> getExistingChatMessages(
        @Param("userIdOne") long userIdOne, @Param("userIdTwo") long userIdTwo);
}
