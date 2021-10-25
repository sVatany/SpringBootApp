package com.example.util;

import com.example.dao.movieObj;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailResponse {
    public MovieDetailResponse(int value, String success, movieObj movie) {
		// TODO Auto-generated constructor stub
	}
	private int status;
    private String message;
    private movieObj data;
}
