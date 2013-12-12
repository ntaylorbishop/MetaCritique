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
	
	//Convert search term into a valid URL
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
		return url;
	}
	
	public int checkForProperResult(String URL) {
		Log.d("meta", URL);
		if(URL.indexOf("www.metacritic.com/=") != -1)
			return 1;
		else if(URL.indexOf("www.metacritic.com") != -1 && (URL.indexOf("www.metacritic.com/game/") == -1 && URL.indexOf("www.metacritic.com/movie/") == -1))
			return 2;
		else
			return 0;
	}
}
