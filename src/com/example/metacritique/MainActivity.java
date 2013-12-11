package com.example.metacritique;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.metacritique.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private ImageButton searchBtn;
	private EditText searchTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		searchTxt = (EditText) findViewById(R.id.SearchBar);
		searchBtn = (ImageButton) findViewById(R.id.SearchBtn); //Set the onClick listener
		
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				new DownloadSource().execute("http://www.metacritic.com/game/xbox-360/halo-combat-evolved-anniversary");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class DownloadSource extends AsyncTask<String, Void, String> {

	    protected String doInBackground(String... urls) {
	    	HttpClient client = new DefaultHttpClient();
	    	HttpGet get = new HttpGet(urls[0]);
	    	String result = null;
	    	try {
		    	HttpResponse response = client.execute(get);
		    	HttpEntity entity = response.getEntity();
		    	if (null != entity)
			    	result = EntityUtils.toString(entity);
		    	else
		    		return null;
	    	} catch (ClientProtocolException e) {
		    	e.printStackTrace();
	    	} catch (IOException e) {
		    	e.printStackTrace();
	    	}
	    	return result;
	    }
	    
	    protected void onPostExecute(String result) {
	        MetaParse parse = new MetaParse(result);
	        parse.getScore();
	    }
	}
}
