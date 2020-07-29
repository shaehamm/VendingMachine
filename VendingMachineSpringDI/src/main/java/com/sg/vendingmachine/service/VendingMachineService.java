/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.daos.InvalidNumberInputException;
import com.sg.vendingmachine.daos.VendingMachineDao;
import com.sg.vendingmachine.daos.VendingMachineDaoException;
import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
@Component
public class VendingMachineService {

    VendingMachineDao dao;
    private BigDecimal userMoney = new BigDecimal("0");

    @Autowired
    
    public VendingMachineService(VendingMachineDao dao) {
        this.dao = dao;
    }

    public BigDecimal getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(BigDecimal userMoney) {
        this.userMoney = userMoney;
    }
    
    public List<VendingItem> getItems() throws NoInventoryException {
        List<VendingItem> allItems = dao.getItems();
        if (allItems.isEmpty()
                || allItems.stream().allMatch(item -> item.getInventory() == 0)) {
            throw new NoInventoryException("There is currently nothing in stock"
                    + ".\nPlease try again later!\n");
        }
        return allItems;
    }

    public void updateQuantity(VendingItem userSelection) throws VendingMachineDaoException {
        dao.updateQuantity(userSelection);
    }

    public BigDecimal stringToBigDecimal(String userInput) throws InvalidNumberInputException, InvalidMoneyException {
        
        BigDecimal toConvert = new BigDecimal("0");
        try {
            toConvert = new BigDecimal(userInput);
        } catch (NumberFormatException ex) {
            throw new InvalidNumberInputException("Please enter a valid money " +
                    "amount or type \"Exit\".\n");
        }
        if (toConvert.compareTo(new BigDecimal("0")) < 1) {
            throw new InvalidMoneyException("Please enter a valid dollar and "
                    + "coin amount (cannot enter zero dollars).\n");
        }
        return toConvert;
    
    }

    public void validateInventory(VendingItem userSelection) throws NoItemInventoryException {
        if (userSelection.getInventory() < 1) {
            throw new NoItemInventoryException("The item you selected ("
                    + userSelection.getName() + ") is currently out of stock.\n");
        }
    }

    public Change getChange() {
        Change toReturn = bigDecimalToChange(userMoney);
        setUserMoney(new BigDecimal("0"));
        return toReturn;
    }

    public Change getChange(VendingItem userSelection) throws InsufficientFundsException {
        if (userSelection.moreExpensiveThan(userMoney)) {
            throw new InsufficientFundsException("The amount you entered ($"
                    + userMoney + ") is not sufficient to purchase "
                    + userSelection.getName() + ".\n");
        }
        //get the difference of user money and item cost
        BigDecimal change = userMoney.subtract(userSelection.getCost());
        //converts BigDecimal to a Change object
        Change toReturn = bigDecimalToChange(change);
        setUserMoney(new BigDecimal("0"));
        return toReturn;
    }

    private Change bigDecimalToChange(BigDecimal change) {

        Change toReturn = new Change();

        int quarterCount = 0;
        int dimeCount = 0;
        int nickelCount = 0;
        int pennieCount = 0;

        while (change.compareTo(toReturn.getQuarter()) > -1) {
            quarterCount += 1;
            change = change.subtract(toReturn.getQuarter());
        }
        while (change.compareTo(toReturn.getDime()) > -1) {
            dimeCount += 1;
            change = change.subtract(toReturn.getDime());
        }
        while (change.compareTo(toReturn.getNickel()) > -1) {
            nickelCount += 1;
            change = change.subtract(toReturn.getNickel());
        }
        while (change.compareTo(toReturn.getPenny()) > -1) {
            pennieCount += 1;
            change = change.subtract(toReturn.getPenny());
        }

        toReturn.setQuarterQuantity(quarterCount);
        toReturn.setDimeQuantity(dimeCount);
        toReturn.setNickelQuantity(nickelCount);
        toReturn.setPennyQuantity(pennieCount);

        return toReturn;
    }
}
