/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.brousegame.exception;

/**
 *
 * @author Emerio-PC
 */
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
