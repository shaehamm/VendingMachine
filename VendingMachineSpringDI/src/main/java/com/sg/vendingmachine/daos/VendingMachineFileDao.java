/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.daos;

import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author codedchai
 */
@Component
public class VendingMachineFileDao implements VendingMachineDao {

    @Value("${dao.path}")
    String path;

    @Override
    public List<VendingItem> getItems() {
        List<VendingItem> toReturn = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(path)));
            //read in items from a file
            while (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                VendingItem toAdd = convertLineToVendingItem(row);
                toReturn.add(toAdd);
            }
        } catch (FileNotFoundException ex) {
        }
        //return the list of all items in the file
        return toReturn;
    }

    private VendingItem convertLineToVendingItem(String row) {
        //separate the elements by the delimiter
        String[] splitRow = row.split("::");
        //convert the string to the correct types
        String name = splitRow[0];
        BigDecimal cost = new BigDecimal(splitRow[1]);
        int quantity = Integer.parseInt(splitRow[2]);
        //set the item's fields        
        VendingItem toReturn = new VendingItem();
        toReturn.setName(name);
        toReturn.setCost(cost);
        toReturn.setInventory(quantity);

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
        writeToFile(toWrite);
    }

    private void writeToFile(List<VendingItem> toWrite) throws VendingMachineDaoException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(path));

            for (VendingItem v : toWrite) {
                String line = convertVendingItemToLine(v);
                writer.println(line);
            }
            writer.flush();
            writer.close();

        } catch (IOException ex) {
            //exception "translation"
            //wrap specific exception with more general exception
            throw new VendingMachineDaoException("Could not open file: " + path
                    + "\n", ex);
        }
    
    }

    private String convertVendingItemToLine(VendingItem item) {
        return item.getName() + "::" + item.getCost() + "::" + 
                item.getInventory();
    }

}
