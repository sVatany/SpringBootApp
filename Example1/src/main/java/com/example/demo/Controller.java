package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.movieObj;

@RestController
public class Controller {

	Services service = new Services();
	movieObj movie = new movieObj();
	
    @GetMapping("/")
    public String HelloSpring(){
        System.out.println("My First Spring Application");
        return "My First Spring Application";
    }
    
    
    @PostMapping("/update") 
    public movieObj update(@RequestBody movieObj movie) {
    	return service.addToList(movie);
    }
    
    @DeleteMapping("/delete/{title}")
    public void delete(@PathVariable("title") String title) {
    	service.deleteFromList(title);
    }
    
    @GetMapping("/viewList")
    public List<movieObj> viewList() {
    	return service.getList();
    }
 
}