/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.repository;

import emc.brousegame.domain.ChatChannel;
import java.util.List;
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
public interface ChatChannelRepository extends CrudRepository<ChatChannel, String> {
     @Query(" FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.userOne.id IN (:userOneId, :userTwoId) "
      + "  AND"
      + "    c.userTwo.id IN (:userOneId, :userTwoId)")
  public List<ChatChannel> findExistingChannelById(
      @Param("userOneId") long userOneId, @Param("userTwoId") long userTwoId);
  
  @Query(" FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.userOne.username IN (:userOne, :userTwo) "
      + "  AND"
      + "    c.userTwo.username IN (:userOne, :userTwo)")
  public List<ChatChannel> findExistingChannelByUsername(
      @Param("userOne") String userOne, @Param("userTwo") String userTwo);
  
  @Query(" SELECT"
      + "    uuid"
      + "  FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.userOne.id IN (:userIdOne, :userIdTwo)"
      + "  AND"
      + "    c.userTwo.id IN (:userIdOne, :userIdTwo)")
  public String getChannelUuid(
      @Param("userIdOne") long userIdOne, @Param("userIdTwo") long userIdTwo);

  @Query(" FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.uuid IS :uuid")
  public ChatChannel getChannelDetails(@Param("uuid") String uuid);
}
