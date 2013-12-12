package com.example.metacritique;


import com.example.metacritique.R;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	private ImageButton searchBtn;
	private EditText searchTxt;
	private TextView txtDisplay;
	
	private TextView score;
	private TextView title;
	private TextView dev;
	private TextView devFill;
	private TextView date;
	private TextView dateFill;
	private TextView genre;
	private TextView genreFill;
	private TextView rating;
	private TextView ratingFill;
	private TextView summary;
	private TextView summaryFill;
	private TextView criticReviews;
	private TextView userReviews;
	
	BingInterface bing;
	MetaParse Meta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Find elems by their id's
		searchTxt = (EditText) findViewById(R.id.SearchBar);
		searchBtn = (ImageButton) findViewById(R.id.SearchBtn);
		txtDisplay = (TextView) findViewById(R.id.txtDisplay);
		
		score = (TextView) findViewById(R.id.score);
		title = (TextView) findViewById(R.id.title);
		dev = (TextView) findViewById(R.id.dev);
		devFill = (TextView) findViewById(R.id.devFill);
		date = (TextView) findViewById(R.id.date);
		dateFill = (TextView) findViewById(R.id.dateFill);
		genre = (TextView) findViewById(R.id.genre);
		genreFill = (TextView) findViewById(R.id.genreFill);
		rating = (TextView) findViewById(R.id.rating);
		ratingFill = (TextView) findViewById(R.id.ratingFill);
		summary = (TextView) findViewById(R.id.summary);
		summaryFill = (TextView) findViewById(R.id.summaryFill);
		criticReviews = (TextView) findViewById(R.id.criticReviews);
		userReviews = (TextView) findViewById(R.id.userReviews);
		
		//Set display of all meta information to invisible
		setMetaElemsVisibility(View.INVISIBLE);
		
		summaryFill.setMovementMethod(new ScrollingMovementMethod());
		devFill.setSelected(true);
		title.setSelected(true);
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
					bing = new BingInterface();
					
					String searchTerm = new String();
					searchTerm = searchTxt.getText().toString();
					searchTerm = bing.makeTextBingable(searchTerm);
					
					new BingText().execute(searchTerm);
					
					txtDisplay.setVisibility(View.GONE);
			}
			
		});
		
		searchTxt.setOnEditorActionListener(new OnEditorActionListener() {        
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        if(actionId==EditorInfo.IME_ACTION_DONE){
		        	bing = new BingInterface();
					
					String searchTerm = new String();
					searchTerm = searchTxt.getText().toString();
					searchTerm = bing.makeTextBingable(searchTerm);
					
					new BingText().execute(searchTerm);
					
					txtDisplay.setVisibility(View.GONE);
		        }
		    return false;
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void setMetaElemsVisibility(int vis) {
		score.setVisibility(vis);
		title.setVisibility(vis);
		dev.setVisibility(vis);
		devFill.setVisibility(vis);
		date.setVisibility(vis);
		dateFill.setVisibility(vis);
		genre.setVisibility(vis);
		genreFill.setVisibility(vis);
		rating.setVisibility(vis);
		ratingFill.setVisibility(vis);
		summary.setVisibility(vis);
		summaryFill.setVisibility(vis);
		criticReviews.setVisibility(vis);
		userReviews.setVisibility(vis);
	}
	
	//Set appropriate background color for score
	public void setScoreColor(int score) {
		if(score < 50)
			this.score.setBackgroundColor(Color.parseColor("#ff0000"));
		else if(score > 50 && score < 75)
			this.score.setBackgroundColor(Color.parseColor("#ffcc33"));
		else if(score > 75)
			this.score.setBackgroundColor(Color.parseColor("#66cc33"));
			
	}
	
	//Hide keyboard when tapped outside
	public static void hideSoftKeyboard(Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	/***** Async pull source code from bing *****/
	private class BingText extends AsyncTask<String, Void, String> {
		
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
	    	String url = bing.getMetaURL(result);
	    	
	    	new MetaInfo().execute(url);
	    }
	}
	/***** Async pull source code from metacritic *****/
	private class MetaInfo extends AsyncTask<String, Void, String> {

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
	    	Meta = new MetaParse(result);
	    	Meta.getMetaInfo();
	    	
	    	score.setText(Meta.getScore());
	    	title.setText(Meta.getTitle());
	    	devFill.setText(Meta.getDev());
	    	dateFill.setText(Meta.getDate());
	    	genreFill.setText(Meta.getGenre());
	    	ratingFill.setText(Meta.getRating());
	    	summaryFill.setText(Meta.getSummary());
	    	
	    	setMetaElemsVisibility(View.VISIBLE);
	    	
	    	Log.d("meta", Meta.getScore());
	    	Log.d("meta", Meta.getTitle());
	    	Log.d("meta", Meta.getGenre());
	    	Log.d("meta", Meta.getRating());
	    	Log.d("meta", Meta.getSummary());
	    	Log.d("meta", Meta.getDev());
	    	Log.d("meta", Meta.getDate());
	    	
	    	setScoreColor(Integer.parseInt(Meta.getScore()));
	    	
	    	criticReviews.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Meta.getCriticUrl()));
					startActivity(browserIntent);
				}
				
			});
	    	userReviews.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Meta.getUserUrl()));
					startActivity(browserIntent);
				}
				
			});
	    	
	    }
	}
}
