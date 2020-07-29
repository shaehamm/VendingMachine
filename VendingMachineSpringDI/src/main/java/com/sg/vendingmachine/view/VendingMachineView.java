/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.view;

import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
@Component
public class VendingMachineView {

    UserIO io = new UserIO();

    public String menuOptions() {
        return io.readString("\nEnter money or type \"Exit\" to leave: ");
    }

    public void displayItems(List<VendingItem> items) {
        for (int i = 0; i < items.size(); i++) {
            int number = i + 1;
            VendingItem toPrint = items.get(i);
            if (toPrint.getInventory() > 0) {
                io.print(number + ". " + toPrint.getName() + " - $" + toPrint.getCost()
                        + " (" + toPrint.getInventory() + " in stock)\n");
            }
        }
    }

    public VendingItem getItem(List<VendingItem> items) {
        int selection = io.readInt("Please enter the corresponding number for "
                + "the item you'd like to purchase: ", 1, items.size());
        return items.get(selection - 1);
    }

    public void displayChange(Change usersChange) {
        io.print("Quarters returned: " + usersChange.getQuarterQuantity()
                + "\nDimes returned: " + usersChange.getDimeQuantity()
                + "\nNickels returned: " + usersChange.getNickelQuantity()
                + "\nPennies returned: " + usersChange.getPennyQuantity() + "\n");
    }

    public void displayErrorMessage(String message) {
        io.print(message);
    }

    public String getAdditionalMoney() {
        return io.readString("\nPlease enter additional money or type \"Exit\" to leave: ");
    }
}
