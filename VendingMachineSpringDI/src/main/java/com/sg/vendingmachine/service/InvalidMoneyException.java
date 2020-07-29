/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

/**
 *
 * @author codedchai
 */
public class InvalidMoneyException extends Exception {
    
    public InvalidMoneyException(String message) {
        super(message);
    }
    
    public InvalidMoneyException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
