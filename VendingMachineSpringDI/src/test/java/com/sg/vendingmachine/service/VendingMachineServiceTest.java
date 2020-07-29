/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.daos.InvalidNumberInputException;
import com.sg.vendingmachine.daos.VendingMachineDaoException;
import com.sg.vendingmachine.daos.VendingMachineInMemDao;
import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 *
 * @author codedchai
 */
//@ContextConfiguration(classes = VendingMachineInMemDao.class)
public class VendingMachineServiceTest {
    
    AnnotationConfigApplicationContext ctx;
    VendingMachineService toTest;

    public VendingMachineServiceTest() {
        ctx = new AnnotationConfigApplicationContext();
        //tells where to start looking (which folder/package)
        ctx.scan("com.sg.vendingmachine");
        //loads up the beans
        ctx.refresh();
        toTest = ctx.getBean(VendingMachineService.class, VendingMachineInMemDao.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
        
    @Test
    public void testGetItemsEmpty() {      
        try {
            List<VendingItem> test = toTest.getItems();
            fail();
        } catch (NoInventoryException ex) {     
        }
    }

    @Test
    public void testGetChangeGoldenPath() {
        
        toTest.setUserMoney(new BigDecimal("2.99"));
        VendingItem item = new VendingItem();
        //set the item's parameters
        item.setName("Swedish Fish");
        item.setCost(new BigDecimal("1.25"));
        item.setInventory(7);
        
        try {
            Change returned = toTest.getChange(item);
            
            assertEquals(6, returned.getQuarterQuantity());
            assertEquals(2, returned.getDimeQuantity());
            assertEquals(0, returned.getNickelQuantity());
            assertEquals(4, returned.getPennyQuantity());
            
        } catch (InsufficientFundsException ex) {
            fail();
        }
    }
    
    @Test
    public void testGetChangeInsufficientFunds() {
        
        toTest.setUserMoney(new BigDecimal("1.24"));
        VendingItem item = new VendingItem();
        //set the item's parameters
        item.setName("Swedish Fish");
        item.setCost(new BigDecimal("1.25"));
        item.setInventory(7);
        
        try {
            Change returned = toTest.getChange(item);
            fail();
            
        } catch (InsufficientFundsException ex) {
        }
        
    }

    @Test
    public void testUpdateQuantityGoldenPath() {
        
        VendingItem item = new VendingItem();
        //set the item's parameters
        item.setName("Swedish Fish");
        item.setCost(new BigDecimal("1.25"));
        item.setInventory(7);
        
        try {
            toTest.updateQuantity(item);
            
            assertEquals(6, item.getInventory());
            
        } catch (VendingMachineDaoException ex) {
            fail();
        }
    }
    
    @Test
    public void testStringToBigDecimalGoldenPath() {
        
        try {
            BigDecimal test = toTest.stringToBigDecimal("2.50");
            
            assertEquals(new BigDecimal("2.50"), test);
            
        } catch (InvalidNumberInputException ex) {
            fail();
        } catch (InvalidMoneyException ex) {
            fail();
        }
    }
    
    @Test
    public void testStringToBigDecimalNegativeInput() {
        
        try {
            BigDecimal test = toTest.stringToBigDecimal("-2.50");
            fail();
        } catch (InvalidNumberInputException ex) {
            fail();
        } catch (InvalidMoneyException ex) {
            
        }
    }

    @Test
    public void testStringToBigDecimalBlank() {
        try {
            BigDecimal test = toTest.stringToBigDecimal("");
            fail();
        } catch (InvalidNumberInputException ex) {
            
        } catch (InvalidMoneyException ex) {
            fail();
        }
    }

    @Test
    public void testValidateInventoryGoldenPath() {
        
        VendingItem item = new VendingItem();
        //set the item's parameters
        item.setName("Swedish Fish");
        item.setCost(new BigDecimal("1.25"));
        item.setInventory(1);
        
        try {
            toTest.validateInventory(item);
        } catch (NoItemInventoryException ex) {
            fail();
        }
    }
    
    @Test
    public void testValidateInventoryOutOfStock() {
        
        VendingItem item = new VendingItem();
        //set the item's parameters
        item.setName("Swedish Fish");
        item.setCost(new BigDecimal("1.25"));
        item.setInventory(0);
        
        try {
            toTest.validateInventory(item);
            fail();
        } catch (NoItemInventoryException ex) {
        }
    }
    
}
