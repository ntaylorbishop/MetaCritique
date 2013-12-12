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
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ImageButton searchBtn;
	private EditText searchTxt;
	/*private RadioButton selectGame;
	private RadioButton selectMusic;
	private RadioButton selectMovie;
	private RadioButton selectTV;*/
	
	private int typeSelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		typeSelect = 0;
		
		searchTxt = (EditText) findViewById(R.id.SearchBar);
		searchBtn = (ImageButton) findViewById(R.id.SearchBtn);
		/*selectGame = (RadioButton) findViewById(R.id.game);
		selectMusic = (RadioButton) findViewById(R.id.music);
		selectMovie = (RadioButton) findViewById(R.id.movie);
		selectTV = (RadioButton) findViewById(R.id.tv);*/
		
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(typeSelect == 0) {
					CharSequence text = "Please select a category";
					Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
					toast.show();
				}
				else
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
	
	public void onRadioButtonClicked(View view) {
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    switch(view.getId()) {
	        case R.id.game:
	            if (checked)
	            	typeSelect = 1;
	            break;
	        case R.id.music:
	            if (checked)
	            	typeSelect = 2;
	            break;
	        case R.id.movie:
	            if (checked)
	            	typeSelect = 3;
	            break;
	        case R.id.tv:
	            if (checked)
	            	typeSelect = 4;
	            break;
	    }
	}
	
	
	/***** Async pull source code *****/
	private class DownloadSource extends AsyncTask<String, Void, String> {
		
		BingInterface bing;

	    protected String doInBackground(String... urls) {
	    	
	    	//Pull source code
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
	    	bing = new BingInterface(result);
	    	
	        MetaParse parse = new MetaParse(result);
	        parse.getScore();
	    }
	}
}
