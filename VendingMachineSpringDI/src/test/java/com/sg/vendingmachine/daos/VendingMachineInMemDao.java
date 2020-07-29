/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.daos;

import com.sg.vendingmachine.dtos.VendingItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
public class VendingMachineInMemDao implements VendingMachineDao {

    @Override
    public List<VendingItem> getItems() {
        List<VendingItem> toReturn = new ArrayList<>();
        return toReturn;
    }

    @Override
    public void updateQuantity(VendingItem userSelection) throws VendingMachineDaoException {
        List<VendingItem> toWrite = getItems();
        userSelection.decreaseInventory();
        for (int i = 0; i < toWrite.size(); i++) {
            if (userSelection.getName().equals(toWrite.get(i).getName())) {
                toWrite.set(i, userSelection);
                break;
            }
        }
    }

}
