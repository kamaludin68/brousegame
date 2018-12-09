/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Emerio-PC
 */
@Entity
@NoArgsConstructor
@Setter @Getter
@ToString @EqualsAndHashCode
public class ChatChannel {
  @Id
  @NotNull
  private String uuid;
  @OneToOne
  private User userOne;
  @OneToOne
  private User userTwo;
  
  public ChatChannel(User userOne, User userTwo) {
    this.uuid = UUID.randomUUID().toString();
    this.userOne = userOne;
    this.userTwo = userTwo;
  }
}
