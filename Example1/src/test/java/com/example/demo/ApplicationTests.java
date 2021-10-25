package com.example.demo;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.dao.User;
import com.example.dao.movieObj;
import com.example.dao.movieRating;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	
	@Autowired
    MockMvc mvc;
	
	@Autowired
	RestTemplate request;
	
	ObjectMapper mapper = new ObjectMapper();
	
	movieObj moiveEx = new movieObj();
	List<movieObj> listOfMovies = new ArrayList<movieObj>();
	
	public String getJSON(String path) throws Exception {
        Path paths = Paths.get(path);
        return new String(Files.readAllBytes(paths));
    }
	
	private void createMockData() throws Exception {
        String movieStr = getJSON("resources/oneMovie.json");
        movieObj movie = mapper.readValue(movieStr, movieObj.class);
        this.moiveEx = movie;
        RequestBuilder request = post("/update").contentType(MediaType.APPLICATION_JSON).content(movieStr);
        this.mvc.perform(request);

    }
	
	private void createMockManyMovies() throws Exception {
        String movieStr = getJSON("resources/manyMovies.json");
        TypeReference<List<movieObj>> movies = new TypeReference<List<movieObj>>() { };

        this.listOfMovies = mapper.readValue(movieStr, movies);
        RequestBuilder request = post("/addList").contentType(MediaType.APPLICATION_JSON).content(movieStr);
        this.mvc.perform(request);
    }
	
	private void createMockData1() throws Exception {
        String movieStr = getJSON("resources/oneMovie.json");
        movieObj movie = mapper.readValue(movieStr, movieObj.class);
        this.moiveEx = movie;
        RequestBuilder request = post("/update").contentType(MediaType.APPLICATION_JSON).content(movieStr);
        this.mvc.perform(request);
    }

	private void createMockData2() throws Exception {
		String movieStr = getJSON("resources/oneMovie2.json");
        movieObj movie = mapper.readValue(movieStr, movieObj.class);
        this.moiveEx = movie;
        RequestBuilder request = post("/update").contentType(MediaType.APPLICATION_JSON).content(movieStr);
        this.mvc.perform(request);
	}

	@Test
	void contextLoads() {
	}
	
	@Test
	void testGetEmptyList() throws Exception {
			this.mvc.perform(get("/viewList")).andExpect(status().isOk()).andExpect(content().json("[]"));
	}
	
	@Test
	//@Transactional
	@Rollback
	void testAddToList() throws Exception {
		String movieStr = getJSON("resources/oneMovie.json");
		System.out.println(movieStr);
		
		movieObj movie = mapper.readValue(movieStr, movieObj.class);
		
		RequestBuilder request = post("/update").contentType(MediaType.APPLICATION_JSON).content(movieStr);
		
		this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(movie.getTitle())))
        .andExpect(jsonPath("$.director", is(movie.getDirector())))
        .andExpect(jsonPath("$.actors", is(movie.getActors())))
        .andExpect(jsonPath("$.release", is(movie.getRelease())))
        .andExpect(jsonPath("$.description", is(movie.getDescription())));
	}
	
	@Test
	//@Transactional
	@Rollback
	void testCheckList() throws Exception {
		this.createMockData();
		
		RequestBuilder request = get("/viewList");
		
		movieObj movie = this.moiveEx;
		
		this.mvc.perform(request)
		.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is(movie.getTitle())))
        .andExpect(jsonPath("$[0].director", is(movie.getDirector())))
        .andExpect(jsonPath("$[0].actors", is(movie.getActors())))
        .andExpect(jsonPath("$[0].release", is(movie.getRelease())))
        .andExpect(jsonPath("$[0].description", is(movie.getDescription())));
	}
	
	@Test
	//@Transactional
	@Rollback
	public void testGetManyMovies() throws Exception {

        createMockManyMovies();


        List<movieObj> moviesList =  this.listOfMovies;
        movieObj movie = moviesList.get(0);
        movieObj movie2 = moviesList.get(1);

        this.mvc.perform(get("/viewList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is(movie.getTitle())))
                .andExpect(jsonPath("$[0].director", is(movie.getDirector())))
                .andExpect(jsonPath("$[0].actors", is(movie.getActors())))
                .andExpect(jsonPath("$[0].release", is(movie.getRelease())))
                .andExpect(jsonPath("$[0].description", is(movie.getDescription())))
                .andExpect(jsonPath("$[1].title", is(movie2.getTitle())))
                .andExpect(jsonPath("$[1].director", is(movie2.getDirector())))
                .andExpect(jsonPath("$[1].actors", is(movie2.getActors())))
                .andExpect(jsonPath("$[1].release", is(movie2.getRelease())))
                .andExpect(jsonPath("$[1].description", is(movie2.getDescription())));
    }
	
	@Test
    //@Transactional
    @Rollback
    public void testGetExistingMovieDetails() throws Exception {

        createMockManyMovies();
        List<movieObj> moviesList = this.listOfMovies;
        movieObj movie = moviesList.get(0);
        
        //need help setting up param, had to hard code request 
        RequestBuilder request = get("/getMovie/The Avengers");
//                .param("title", movie.getTitle());

        this.mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(movie.getTitle())))
        .andExpect(jsonPath("$.director", is(movie.getDirector())))
        .andExpect(jsonPath("$.actors", is(movie.getActors())))
        .andExpect(jsonPath("$.release", is(movie.getRelease())))
        .andExpect(jsonPath("$.description", is(movie.getDescription())));
    }
	
	@Test
	//@Transactional
	@Rollback
	public void testGetNonExistingMovieDetails() throws Exception {
		fail("finish test case");
	}
	
	@Test
	//@Transactional
	@Rollback
	public void testSubmitRating() throws Exception {
		createMockManyMovies();
        List<movieObj> moviesList = this.listOfMovies;
        movieObj movie = moviesList.get(0);

        User user = new User("nicky");
        //this.userRepository.save(user);

        String movieRatingStr = getJSON("resources/userRating.json");

        movieRating movieRating = mapper.readValue(movieRatingStr, movieRating.class);

        RequestBuilder request = post("/user/userRating")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieRatingStr);

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.data.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.data.actors", is(movie.getActors())))
                .andExpect(jsonPath("$.data.release", is(movie.getRelease())))
                .andExpect(jsonPath("$.data.description", is(movie.getDescription())))
                .andExpect(jsonPath("$.data.rating", is((double) movieRating.getRating())));
	}
	
	@Test
	@Rollback
	void testDeleteFromList() throws Exception {
		this.createMockData1();
		movieObj movie1 = this.moiveEx;
		
		RequestBuilder viewList = get("/viewList");
		
		this.createMockData2();
		movieObj movie2 = this.moiveEx;
		
		
		this.mvc.perform(viewList)
		.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is(movie1.getTitle())))
        .andExpect(jsonPath("$[0].director", is(movie1.getDirector())))
        .andExpect(jsonPath("$[0].actors", is(movie1.getActors())))
        .andExpect(jsonPath("$[0].release", is(movie1.getRelease())))
        .andExpect(jsonPath("$[0].description", is(movie1.getDescription())))
        .andExpect(jsonPath("$[1].title", is(movie2.getTitle())))
		.andExpect(jsonPath("$[1].director", is(movie2.getDirector())))
        .andExpect(jsonPath("$[1].actors", is(movie2.getActors())))
        .andExpect(jsonPath("$[1].release", is(movie2.getRelease())))
        .andExpect(jsonPath("$[1].description", is(movie2.getDescription())));
		
		
		RequestBuilder delete = MockMvcRequestBuilders.delete("/delete/" + movie2.getTitle());
		
		this.mvc.perform(delete)
		.andExpect(status().isOk())
        .andExpect(jsonPath("$[0].title", is(movie1.getTitle())))
        .andExpect(jsonPath("$[0].director", is(movie1.getDirector())))
        .andExpect(jsonPath("$[0].actors", is(movie1.getActors())))
        .andExpect(jsonPath("$[0].release", is(movie1.getRelease())))
        .andExpect(jsonPath("$[0].description", is(movie1.getDescription())));
	}
	
//	@Test
//	@Rollback
//	void testDeleteFromList2() throws Exception {
//		this.createMockData1();
//		movieObj movie1 = this.moiveEx;
//		
//		RequestBuilder viewList = get("/viewList");
//		
//		this.createMockData2();
//		movieObj movie2 = this.moiveEx;
//		
//		
////		this.mvc.perform(viewList)
////		.andExpect(status().isOk())
////        .andExpect(jsonPath("$[0].title", is(movie1.getTitle())))
////        .andExpect(jsonPath("$[0].director", is(movie1.getDirector())))
////        .andExpect(jsonPath("$[0].actors", is(movie1.getActors())))
////        .andExpect(jsonPath("$[0].release", is(movie1.getRelease())))
////        .andExpect(jsonPath("$[0].description", is(movie1.getDescription())))
////        .andExpect(jsonPath("$[1].title", is(movie2.getTitle())))
////		.andExpect(jsonPath("$[1].director", is(movie2.getDirector())))
////        .andExpect(jsonPath("$[1].actors", is(movie2.getActors())))
////        .andExpect(jsonPath("$[1].release", is(movie2.getRelease())))
////        .andExpect(jsonPath("$[1].description", is(movie2.getDescription())));
//		
//		
//		RequestBuilder delete = delete("/delete/" + movie1.getTitle());
//		this.mvc.perform(delete).andExpect(status().isOk());
//		
//		
//		this.mvc.perform(viewList)
//		.andExpect(status().isOk())
//        .andExpect(jsonPath("$[0].title", is(movie2.getTitle())))
//        .andExpect(jsonPath("$[0].director", is(movie2.getDirector())))
//        .andExpect(jsonPath("$[0].actors", is(movie2.getActors())))
//        .andExpect(jsonPath("$[0].release", is(movie2.getRelease())))
//        .andExpect(jsonPath("$[0].description", is(movie2.getDescription())));
//	}

}
