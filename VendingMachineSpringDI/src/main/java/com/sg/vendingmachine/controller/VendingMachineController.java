/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.daos.InvalidNumberInputException;
import com.sg.vendingmachine.daos.VendingMachineDaoException;
import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoInventoryException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.InvalidMoneyException;
import com.sg.vendingmachine.service.VendingMachineService;
import com.sg.vendingmachine.view.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
@Component
public class VendingMachineController {

    //fields
    VendingMachineService service;
    VendingMachineView view;

    //controller
    @Autowired
    public VendingMachineController(VendingMachineService service,
            VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    //runs the vending machine program
    public void run() {

        boolean exitProgram = false;
        boolean displayItems = true;
        String userInput = "";

        while (!exitProgram) {
            try {
                if (displayItems) {
                    //retrieves and displays all items to the user
                    getAndDisplayItems();
                    //gets either a dollar amount or "exit"
                    userInput = view.menuOptions();
                } else {
                    userInput = view.getAdditionalMoney();
                    if(userInput.equalsIgnoreCase("exit")) {
                        returnAllChange();
                    }
                }
                
                //exits the program if the user types "exit"
                if (userInput.equalsIgnoreCase("exit")) {
                    exitProgram = true;
                } else {
                    try {
                        //check that the input is indeed "money"
                        service.setUserMoney(validateInput(userInput).add(service.getUserMoney()));
                        //gets item selection from the user
                        VendingItem userSelection = getUserSelection();
                        purchase(userSelection);
                        displayItems = true;
                    } catch (VendingMachineDaoException | InvalidNumberInputException
                            | InvalidMoneyException ex) {
                        view.displayErrorMessage(ex.getMessage());
                    } catch (InsufficientFundsException ex) {
                        view.displayErrorMessage(ex.getMessage());
                        displayItems = false;
                        
                    } catch (NoItemInventoryException ex) {
                        view.displayErrorMessage(ex.getMessage());
                        returnAllChange();
                    }
                }
            } catch (NoInventoryException ex) {
                view.displayErrorMessage(ex.getMessage());
                exitProgram = true;
            }
        }
    }

    private void getAndDisplayItems() throws NoInventoryException {
        List<VendingItem> items = service.getItems();
        view.displayItems(items);
    }

    private void purchase(VendingItem userSelection) throws
            VendingMachineDaoException, InsufficientFundsException, NoItemInventoryException {
        //throws NoItemInventoryException if item not in stock
        service.validateInventory(userSelection);
        //gets the change to give back to the user
        //throws InsufficientFundsException if not enough money 
        Change change = service.getChange(userSelection);
        //decreases inventory of the selected item
        service.updateQuantity(userSelection);
        //displays change back to the user
        view.displayChange(change);
    }

    private BigDecimal validateInput(String userInput) throws InvalidNumberInputException,
            InvalidMoneyException {
        return service.stringToBigDecimal(userInput);
    }

    private VendingItem getUserSelection() throws NoInventoryException {
        //get all items available for purchase
        List<VendingItem> items = service.getItems();
        //get the user's item selection
        return view.getItem(items);
    }

    private void returnAllChange() {
        
        Change change = service.getChange();
        //displays change back to the user
        view.displayChange(change);
    }
}
