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
		
		end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '<') {
				MetaScore += src.charAt(pos);
			}
			else
				end = true;
		}
		//MetaScore.replaceAll("\\s+","");
		//Log.d("meta", MetaScore);
		
		//Get Meta Rating
		pos = src.indexOf(ratingSearch) + ratingSearch.length() - 1;
		
		end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '<') {
				MetaRating += src.charAt(pos);
			}
			else
				end = true;
		}
		
		//Get Meta Summary
		pos = src.indexOf(sumSearch) + sumSearch.length() - 1;
				
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
		
		//Get Meta Dev
		pos = src.indexOf(devSearch) + devSearch.length() - 1;
						
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
		//Log.d("meta", MetaDev);		
				
		//Get Meta Genre
		pos = src.indexOf(genreSearch) + genreSearch.length() - 1;
						
		end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '<') {
				MetaGenre += src.charAt(pos);
			}
			else
				end = true;
		}
		//Log.d("meta", MetaGenre);	
				
		//Get Meta date
		pos = src.indexOf(dateSearch) + dateSearch.length() - 1;
						
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
		Log.d("meta", MetaDate);	
		
		//Get critic reviews
		pos = src.indexOf(criticSearch) + criticSearch.length() - 1;
		CriticReviews += "http://www.metacritic.com";
		
		end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '\"') {
				CriticReviews += src.charAt(pos);
			}
			else
				end = true;
		}
		Log.d("meta", CriticReviews);
		
		//Get user reviews
		pos = src.indexOf(userSearch) + userSearch.length() - 1;
		UserReviews += "http://www.metacritic.com";
		
		end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '\"') {
				UserReviews += src.charAt(pos);
			}
			else
				end = true;
		}
		Log.d("meta", UserReviews);
	}
	
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
	
	public void fixDevString() {
		MetaDev = MetaDev.replace('-', ' ');

		String[] tokens = MetaDev.split("\\s");
		MetaDev = "";

		for(int i = 0; i < tokens.length; i++){
		    char capLetter = Character.toUpperCase(tokens[i].charAt(0));
		    MetaDev +=  " " + capLetter + tokens[i].substring(1, tokens[i].length());
		}
	}
	public void fixSummary() {
		MetaSummary = MetaSummary.replaceAll(Pattern.quote("\\"), "");
		MetaSummary = MetaSummary.replace("&quot;", "\"");
	}
}
