package com.example.demo;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.movieObj;
import com.example.dao.movieRating;

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
    public List<movieObj> delete(@PathVariable("title") String title) {
    	return service.deleteFromList(title);
    }
    
    @GetMapping("/viewList")
    public List<movieObj> viewList() {
    	return service.getList();
    }
    
    @PostMapping("/addList")
    public List<movieObj> addManyMovies(@RequestBody List<movieObj> movies) {
    	return service.updateList(movies);
    }
    
    @GetMapping("/getMovie/{title}")
    public movieObj getMovie(@PathVariable("title") String title) {
    	return service.getMovieByTitle(title);
    }
    
    @PostMapping("/user/addAUserRating")
    public movieRating addARating(@RequestBody movieRating rating) {
    	return service.addAMovieRating(rating);
    }
    
    
 
}