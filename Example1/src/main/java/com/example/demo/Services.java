package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.example.dao.movieObj;

public class Services {
	List<movieObj> list = new ArrayList<movieObj>();
	
	public void updateList(movieObj movie) {
		this.list.add(movie);
	}
	
	public movieObj addToList(movieObj movie) {
		this.list.add(movie);
		return movie;
	}
	
	public void deleteFromList(String movieTitle) {
		for(movieObj movie: this.list) {
			if (movie.getTitle().equals(movieTitle)) {
				this.list.remove(movie);
			}
		}
	}

	public List<movieObj> getList() {
		return this.list;
	}
}
