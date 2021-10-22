package com.example.demo;


import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.example.dao.movieObj;
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
	
	public String getJSON(String path) throws Exception {
        Path paths = Paths.get(path);
        return new String(Files.readAllBytes(paths));
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
		this.createMockData1();
		
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

}
