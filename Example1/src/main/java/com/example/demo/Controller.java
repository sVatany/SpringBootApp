package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String HelloSpring(){
        System.out.println("My First Spring Application");
        return "My First Spring Application";
    }
    
    @GetMapping("/create")
    public void create() {
    	
    }
    
    @GetMapping("/update") 
    public void update() {
    	
    }
    
    @GetMapping("/delete")
    public void delete() {
    	
    }
}