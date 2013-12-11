package com.example.metacritique;

import android.util.Log;

public class MetaParse {
	
	private final String scoreSearch = "<span itemprop=\"ratingValue\">";
	
	private String src;
	
	private String MetaScore;
	private String MetaSummary;
	private String MetaDev;
	private String MetaGenres;
	private String MetaRating;

	public MetaParse(String srcCode) {
		src = new String();
		src = srcCode;
		
		MetaScore = new String();
		MetaSummary = new String();
		MetaDev = new String();
		MetaGenres = new String();
		MetaRating = new String();
	}
	
	public String getScore() {
		String score = new String();
		int pos = 0;
		
		pos = src.indexOf(scoreSearch) + scoreSearch.length() - 1;
		
		boolean end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '<') {
				score += src.charAt(pos);
			}
			else
				end = true;
		}
		
		Log.d("meta", score);
		return score;
	}
	
}
