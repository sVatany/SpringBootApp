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
	
	public movieObj addToList(movieObj movie) {
		this.list.put(movie.getTitle(), movie);
		return movie;
	}
	
	public void deleteFromList(String title) {
		this.list.remove(title);
	}

	public Set<Entry<String, movieObj>> getList() {
		return this.list.entrySet();
	}
}
