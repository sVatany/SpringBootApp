package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Services {
	List<String> list = new ArrayList<String>();
	
	public void updateList(List<String> myList) {
		this.list = myList;
	}
	
	public void addToList(String sentence) {
		this.list.add(sentence);
	}
	
	public void deleteFromList(String sentence) {
		this.list.remove(sentence);
	}

	public List<String> createList() {
		this.list.add("Banking");
		this.list.add("Health Industry");
		this.list.add("Stock market");
		return this.list;
	}

	public List<String> getList() {
		// TODO Auto-generated method stub
		return this.list;
	}
}
