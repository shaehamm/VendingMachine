/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 *
 * @author codedchai
 */
public class App {
    
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext appContext = 
                new AnnotationConfigApplicationContext();
        appContext.getEnvironment().getPropertySources().addFirst
                (new ResourcePropertySource(new ClassPathResource("application.properties")));
        appContext.scan("com.sg.vendingmachine");
        appContext.refresh();
        
        VendingMachineController controller = 
                appContext.getBean("vendingMachineController", 
                        VendingMachineController.class);
        //runs the program
        controller.run();
        
    }
    
}
