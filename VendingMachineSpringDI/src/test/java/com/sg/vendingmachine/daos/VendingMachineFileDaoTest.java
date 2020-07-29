/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.daos;

import com.sg.vendingmachine.dtos.Change;
import com.sg.vendingmachine.dtos.VendingItem;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class VendingMachineFileDaoTest {
    
    AnnotationConfigApplicationContext ctx;
    VendingMachineFileDao toTest;

    public VendingMachineFileDaoTest() throws IOException {
        ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().getPropertySources().addFirst
                (new ResourcePropertySource(new ClassPathResource("application.properties")));
        //tells where to start looking (which folder/package)
        ctx.scan("com.sg.vendingmachine");
        //loads up the beans
        ctx.refresh();
        toTest = ctx.getBean(VendingMachineFileDao.class);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        //define the paths to the files
        Path testPath = Paths.get("test.txt");
        Path seedPath = Paths.get("seed.txt");
        //delete the test file and replace with the seed file
        //resets the test file
        Files.deleteIfExists(testPath);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetItemsGoldenPath() {
        List<VendingItem> test = toTest.getItems();

        assertEquals("Swedish Fish", test.get(0).getName());
        assertEquals(new BigDecimal("1.25"), test.get(0).getCost());
        assertEquals(5, test.get(0).getInventory());
    }
    
    @Test
    public void testGetItemsMissingFile() throws IOException {
        
        AnnotationConfigApplicationContext ctxBad = new AnnotationConfigApplicationContext();
        ctxBad.getEnvironment().getPropertySources().addFirst
                (new ResourcePropertySource(new ClassPathResource("badsettings.properties")));
        //tells where to start looking (which folder/package)
        ctxBad.scan("com.sg.vendingmachine");
        //loads up the beans
        ctxBad.refresh();
       VendingMachineFileDao toTestBad = ctxBad.getBean(VendingMachineFileDao.class);
        
        assertTrue(toTestBad.getItems().isEmpty());
    }

    @Test
    public void testUpdateQuantityGoldenPath() {
        List<VendingItem> test = toTest.getItems();
        VendingItem item = test.get(0);

        try {
            toTest.updateQuantity(item);
            assertEquals(4, item.getInventory());
        } catch (VendingMachineDaoException ex) {
            fail();
        }
    }
    

}
