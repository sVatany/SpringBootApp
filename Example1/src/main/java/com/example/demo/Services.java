package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.example.dao.User;
import com.example.dao.movieObj;
import com.example.dao.movieRating;

public class Services {
	List<movieObj> list = new ArrayList<movieObj>();
	List<movieRating> listOfMovieRating = new ArrayList<movieRating>();
	List<User> listOfUsers = new ArrayList<User>();
	
	//create a method that takes in a entire list
	public List<movieObj> updateList(List<movieObj> movies) {
		for (movieObj movie: movies) {
			this.list.add(movie);
		}
		return movies;
	}
	
	public movieObj addToList(movieObj movie) {
		this.list.add(movie);
		return movie;
	}
	
	public List<movieObj> deleteFromList(String movieTitle) {
		for(movieObj movie: this.list) {
			if (movie.getTitle().equals(movieTitle)) {
				this.list.remove(movie);
			}
		}
		return this.list;
	}

	public List<movieObj> getList() {
		return this.list;
	}

	public movieObj getMovieByTitle(String title) {
		// TODO Auto-generated method stub
		for(movieObj movie: this.list) {
			if (movie.getTitle().equals(title))
				return movie;
		}
		return null;
	}
	
	public movieRating addAMovieRating(movieRating movieReview) {
		this.listOfMovieRating.add(movieReview);
		this.updateRating(movieReview);
		return movieReview;
	}
	
	private void updateRating(movieRating movieReview) {
		movieObj movie = this.getMovieByTitle(movieReview.getMovieTitle());
		int allRatings = 0;
		int numOfReviews = 0;
		for (movieRating rating: this.listOfMovieRating) {
			if (rating.getMovieTitle().equals(movie.getTitle())) {
				allRatings += rating.getRating();
				numOfReviews++;
			}
		}
		this.list
		.get(this.list.indexOf(movie))
		.setRating((double) allRatings / numOfReviews);
	}
	 
}
