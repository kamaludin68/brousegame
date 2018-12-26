/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(notes = "generated uuid from establish session")
    private String channelId;
    @ApiModelProperty(notes = "username login")
    private String createdBy;
    @ApiModelProperty(notes = "chat message content")
    private String message;
    @ApiModelProperty(notes = "timeSent default current datetime from system")
    private LocalDateTime timeSent = LocalDateTime.now();
    
}
