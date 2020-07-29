/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.view;

import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
@Component
public class UserIO {

    Scanner scn = new Scanner(System.in);

    public void print(String message) {
        System.out.print(message);
    }

    public String readString(String prompt) {
        print(prompt);
        String userInput = scn.nextLine();
        return userInput;
    }
    
    public int readInt(String prompt) {
        int toReturn = 0;

        boolean valid = false;
        while (!valid) {
            String userInput = readString(prompt);

            try {
                toReturn = Integer.parseInt(userInput);
                valid = true;
            } catch (NumberFormatException ex) {

            }
        }
        return toReturn;
    }

    int readInt(String prompt, int incMin, int incMax) {
        int toReturn = 0;

        boolean valid = false;
        while (!valid) {
            toReturn = readInt(prompt);
            valid = toReturn <= incMax && toReturn >= incMin;
        }
        return toReturn;
    }

}
