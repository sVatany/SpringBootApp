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
	
	private void createMockData() throws Exception {
        String movieStr = getJSON("resources/oneMovie.json");
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

}
