/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dtos;

import java.math.BigDecimal;

/**
 *
 * @author codedchai
 */
public class VendingItem {
    //fields
    private String name;
    private BigDecimal cost;
    private int inventory;

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    
    //
    public boolean moreExpensiveThan(BigDecimal moneyInput) {
        boolean toReturn = false;
        
        if (this.cost.compareTo(moneyInput) > 0) {
            toReturn = true;
        }
        return toReturn;
    }

    public void decreaseInventory() {
        this.inventory = this.inventory - 1;
    }
}
