package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	Services service = new Services();
	
    @GetMapping("/")
    public String HelloSpring(){
        System.out.println("My First Spring Application");
        return "My First Spring Application";
    }
    
    @PutMapping("/create")
    public List<String> create() {
    	return service.createList();
    }
    
    @PostMapping("/update") 
    public void update(@RequestBody String sens) {
    	service.addToList(sens);
    }
    
    @DeleteMapping("/delete/{listItem}")
    public void delete(@PathVariable("listItem") String sens) {
    	service.deleteFromList(sens);
    }
    
    @GetMapping("/viewList")
    public List<String> viewList() {
    	return service.getList();
    }
 
}