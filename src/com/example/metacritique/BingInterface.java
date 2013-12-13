package com.example.metacritique;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

public class BingInterface {
	
	//Convert search term into a valid bing URL
	public String makeTextBingable(String str) {
		String bingableText = new String();
		String[] toks = str.split(" ");
		
		bingableText += "http://www.bing.com/search?q=";
		for(int i = 0; i < toks.length; i++) {
			bingableText += toks[i] + "+";
		}
		
		bingableText += "metacritic";
		
		return bingableText;
	}
	
	//Convert bing results into a relevant metacritic URL
	public String getMetaURL(String src) {
		
		String url = "http://www.metacritic.com/";
		int pos = src.indexOf(url) + url.length() - 1;
		
		boolean end = false;
		while(!end) {
			pos++;
			if(src.charAt(pos) != '\"') {
				url += src.charAt(pos);
			}
			else
				end = true;
		}
		
		//Strip to base URL if need be
		int pos1 = url.indexOf("critic-reviews");
		int pos2 = url.indexOf("user-reviews");
		if(pos1 != -1) {
			url = url.substring(0, pos1);
		}
		if(pos2 != -1) {
			url = url.substring(0, pos2);
		}
		
		Log.d("url", url);
		return url;
	}
	
	//Error handling
	public int checkForProperResult(String URL) {
		Log.d("meta", URL);
		if(URL.indexOf("www.metacritic.com/=") != -1) //Does the Bing interface not pull a metacritic result?
			return 1;
		else if(URL.indexOf("www.metacritic.com/=") == -1 && (URL.indexOf("www.metacritic.com/game/") == -1 && URL.indexOf("www.metacritic.com/movie/") == -1)) //Or does it pull a 
			return 2;
		else
			return 0;
	}
}
