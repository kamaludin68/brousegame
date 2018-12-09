/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Emerio-PC
 */
@Getter @Setter
@NoArgsConstructor
public class Notification {
    private String type;
    private String contents;
    private Long fromUserId;
}
