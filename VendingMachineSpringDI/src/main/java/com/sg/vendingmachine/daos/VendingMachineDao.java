/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.daos;

import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author codedchai
 */
public interface VendingMachineDao {
    
    
    public List<VendingItem> getItems();

    public void updateQuantity(VendingItem userSelection) throws VendingMachineDaoException;

}
