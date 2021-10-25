package com.example.dao;

public class movieRating {
	
	private String movieTitle;
	private int rating;
	private String reviews;
	private String userName;
	
	public String getMovieTitle() {
		return movieTitle;
	}
	
	public void setMovieName(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public String getReviews() {
		return reviews;
	}
	
	public void setReview(String reviews) {
		this.reviews = reviews;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
