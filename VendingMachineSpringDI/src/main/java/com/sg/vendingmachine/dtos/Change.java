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
public class Change {
    //fields
    private BigDecimal penny = new BigDecimal("0.01");
    private BigDecimal nickel = new BigDecimal("0.05");
    private BigDecimal dime = new BigDecimal("0.10");
    private BigDecimal quarter = new BigDecimal("0.25");
    private int pennyQuantity;
    private int nickelQuantity;
    private int dimeQuantity;
    private int quarterQuantity;
    //getters and setters
    public BigDecimal getPenny() {
        return penny;
    }

    public BigDecimal getNickel() {
        return nickel;
    }

    public BigDecimal getDime() {
        return dime;
    }

    public BigDecimal getQuarter() {
        return quarter;
    }

    public int getPennyQuantity() {
        return pennyQuantity;
    }

    public void setPennyQuantity(int pennyQuantity) {
        this.pennyQuantity = pennyQuantity;
    }

    public int getNickelQuantity() {
        return nickelQuantity;
    }

    public void setNickelQuantity(int nickelQuantity) {
        this.nickelQuantity = nickelQuantity;
    }

    public int getDimeQuantity() {
        return dimeQuantity;
    }

    public void setDimeQuantity(int dimeQuantity) {
        this.dimeQuantity = dimeQuantity;
    }

    public int getQuarterQuantity() {
        return quarterQuantity;
    }

    public void setQuarterQuantity(int quarterQuantity) {
        this.quarterQuantity = quarterQuantity;
    }
    
    
}
