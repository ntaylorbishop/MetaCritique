package com.example.metacritique;

import java.util.regex.Pattern;

import android.util.Log;

public class MetaParse {
	
	private final String titleSearch = "<meta name=\"og:title\" content=\"";
	private final String scoreSearch = "<span itemprop=\"ratingValue\">";
	private final String ratingSearch = "<span class=\"data\" itemprop=\"contentRating\">";
	private final String sumSearch = "<meta name=\"og:description\" content=\"";
	private final String devSearch = "<a href=\"/company/";
	private final String genreSearch = "<span class=\"data\" itemprop=\"genre\">";
	private final String dateSearch = "<span class=\"data\" itemprop=\"datePublished\">";
	private final String criticSearch = "<p class=\"see_all\"><a href=\"";
	private final String userSearch = "<div class=\"write_review\"><a class=\"write_review\" href=";
	private String src;
	
	private String MetaType;
	private String MetaTitle;
	private String MetaScore;
	private String MetaSummary;
	private String MetaDev;
	private String MetaGenre;
	private String MetaRating;
	private String MetaDate;
	
	private String CriticReviews;
	private String UserReviews;

	public MetaParse(String srcCode) {
		src = new String();
		src = srcCode;
		
		MetaTitle = "";
		MetaScore = "";
		MetaSummary = "";
		MetaDev = "";
		MetaGenre = "";
		MetaRating = "";
		MetaDate = "";
		CriticReviews = "";
		UserReviews = "";
	}
	
	public void getMetaInfo() {
		int pos = 0;
		
		//Get Title
		pos = src.indexOf(titleSearch) + titleSearch.length() - 1;
		
		boolean end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '\"') {
				MetaTitle += src.charAt(pos);
			}
			else
				end = true;
		}
		//MetaTitle.replaceAll("null","");
		
		//Get Meta Score
		pos = src.indexOf(scoreSearch) + scoreSearch.length() - 1;
		if(src.indexOf(scoreSearch) == -1) {
			MetaScore = "NA";
		}
		else {
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '<') {
					MetaScore += src.charAt(pos);
				}
				else
					end = true;
			}
		}
		
		//Get Meta Rating
		pos = src.indexOf(ratingSearch) + ratingSearch.length() - 1;
		if(src.indexOf(ratingSearch) == -1) {
			MetaRating = "NA";
		}
		else {
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '<') {
					MetaRating += src.charAt(pos);
				}
				else
					end = true;
			}
		}
		
		//Get Meta Summary
		pos = src.indexOf(sumSearch) + sumSearch.length() - 1;
		if(src.indexOf(sumSearch) == -1) {
			MetaSummary = "Not Available";
		}
		else {
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '\"') {
					MetaSummary += src.charAt(pos);
				}
				else
					end = true;
			}
			fixSummary();
			Log.d("meta", MetaSummary);
		}
		
		//Get Meta Dev
		pos = src.indexOf(devSearch) + devSearch.length() - 1;
		if(src.indexOf(devSearch) == -1) {
			MetaDev = "NA";
		}
		else {				
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '\"') {
					MetaDev += src.charAt(pos);
				}
				else
					end = true;
			}
			fixDevString();
		}
				
		//Get Meta Genre
		pos = src.indexOf(genreSearch) + genreSearch.length() - 1;
		if(src.indexOf(genreSearch) == -1) {
			MetaGenre = "NA";
		}
		else {	
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '<') {
					MetaGenre += src.charAt(pos);
				}
				else
					end = true;
			}
		}
				
		//Get Meta date
		pos = src.indexOf(dateSearch) + dateSearch.length() - 1;
		if(src.indexOf(dateSearch) == -1) {
			MetaDate = "NA";
		}
		else {				
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '<') {
					MetaDate += src.charAt(pos);
				}
				else
					end = true;
			}
			MetaDate = MetaDate.trim();
		}
		
		//Get critic reviews
		pos = src.indexOf(criticSearch) + criticSearch.length() - 1;
		CriticReviews += "http://www.metacritic.com";
		if(src.indexOf(criticSearch) == -1) {
			CriticReviews = "about:blank";
		}
		else {
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '\"') {
					CriticReviews += src.charAt(pos);
				}
				else
					end = true;
			}
		}
		
		//Get user reviews
		pos = src.indexOf(userSearch) + userSearch.length() - 1;
		UserReviews += "http://www.metacritic.com";
		if(src.indexOf(userSearch) == -1) {
			UserReviews = "about:blank";
		}
		else {
			end = false;
			while(!end) {
				pos++;
				if(src.charAt(pos) != '\"') {
					UserReviews += src.charAt(pos);
				}
				else
					end = true;
			}
		}
	}
	
	//Getters
	public String getTitle() {
		return MetaTitle;
	}
	public String getScore() {
		return MetaScore;
	}
	public String getRating() {
		return MetaRating;
	}
	public String getSummary() {
		return MetaSummary;
	}
	public String getDev() {
		return MetaDev;
	}
	public String getGenre() {
		return MetaGenre;
	}
	public String getDate() {
		return MetaDate;
	}
	public String getCriticUrl() {
		return CriticReviews;
	}
	public String getUserUrl() {
		return UserReviews;
	}
	
	//Trimming resultant strings
	public void fixDevString() {
		MetaDev = MetaDev.replace('-', ' ');

		String[] tokens = MetaDev.split("\\s"); //Strip '-' in result
		MetaDev = "";

		for(int i = 0; i < tokens.length; i++){	//Capitalize company name
		    char capLetter = Character.toUpperCase(tokens[i].charAt(0));
		    MetaDev +=  " " + capLetter + tokens[i].substring(1, tokens[i].length());
		}
	}
	public void fixSummary() { //Strips metacritic escape sequences and html char sequences
		MetaSummary = MetaSummary.replaceAll(Pattern.quote("\\"), "");
		MetaSummary = MetaSummary.replace("&quot;", "\"");
		MetaSummary = MetaSummary.replace("quot;", "\"");
		MetaSummary = MetaSummary.replace("&amp;", "");
		
	}
}
