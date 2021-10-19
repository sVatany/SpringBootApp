package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;



import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	
	@Autowired
    MockMvc mvc;
	
	@Autowired
	RestTemplate request;

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
	void testGetList() throws Exception {
		  request.put("http://localhost:8081/create", null);
		  
		  List<String> results;
		  
		  results = request.getForObject("http://localhost:8081/viewList", List.class);
		  
		  assertTrue (results.size() == 3);
	}
	
	@Test
	@Rollback
	void testAddToList() throws Exception {
		request.postForObject("http://localhost:8081/update", "Xbox Series X", List.class);
		
		/*this.mvc.perform(get("/viewList")).andExpect(status().isOk()).andExpect(content().json("[\r\n"
				+ "    \"Banking\",\r\n"
				+ "    \"Health Industry\",\r\n"
				+ "    \"Stock market\",\r\n"
				+ "    \"Xbox Series X\"\r\n"
				+ "]"));*/
		List<String> results;
		results = request.getForObject("http://localhost:8081/viewList", List.class);
		assertTrue (results.size() == 4);
	}

}
