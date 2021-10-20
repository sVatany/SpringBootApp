package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.example.dao.movieObj;

public class Services {
	Map<String, movieObj> list = new HashMap<String, movieObj>();
	
	public void updateList(movieObj movie) {
		this.list.put(movie.getTitle(), movie);
	}
	
	public void addToList(movieObj movie) {
		this.list.put(movie.getTitle(), movie);
	}
	
	public void deleteFromList(String title) {
		this.list.remove(title);
	}

	public Set<Entry<String, movieObj>> getList() {
		return this.list.entrySet();
	}
}
