/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.payload;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Emerio-PC
 */
@Getter @Setter
public class Position {
    private String currencyPair;
    private String position;
    private int rate;
    private long summmary;

    public Position(String currencyPair, String position, int rate, long summmary) {
        this.currencyPair = currencyPair;
        this.position = position;
        this.rate = rate;
        this.summmary = summmary;
    }
    
}
