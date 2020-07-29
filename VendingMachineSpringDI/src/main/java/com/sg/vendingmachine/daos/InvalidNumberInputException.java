/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.daos;

/**
 *
 * @author codedchai
 */
public class InvalidNumberInputException extends Exception {
    
    public InvalidNumberInputException(String message) {
        super(message);
    }
    
    public InvalidNumberInputException(String message, Throwable innerException) {
        super(message, innerException);
    }
}
