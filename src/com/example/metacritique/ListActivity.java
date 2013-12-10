package com.example.metacritique;

import com.example.metacritique.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity {

	private ImageButton searchBtn;
	String[] wordlist = new String[] { "a", "b", "c" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		searchBtn = (ImageButton) findViewById(R.id.SearchBtn); //Set the onClick listener
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Add list changes here
			}
		});
		
		/*Future<HttpResponse<JsonNode>> request = Unirest.post("https://byroredux-metacritic.p.mashape.com/search/game")
				.header("X-Mashape-Authorization", "GbicItHfuQQ5oS73hIhKWOCBohXyhnF9")
				.field("title", "The Elder Scrolls V: Skyrim")
				.field("max_pages", "1")
				.field("platform", "3")
				.asJsonAsync(new Callback<JsonNode>() {

				    public void failed(UnirestException e) {
				    	Log.d("MC", "The request has failed");
				    }

				    public void completed(HttpResponse<JsonNode> response) {
				         int code = response.getCode();
				         Map<String, String> headers = response.getHeaders();
				         JsonNode body = response.getBody();
				         InputStream rawBody = response.getRawBody();
				         
				         Context context = getApplicationContext();
				         CharSequence text = "Hello toast!";
				         int duration = Toast.LENGTH_LONG;

				         Toast toast = Toast.makeText(context, body.toString(), duration);
				         toast.show();
				    }

				    public void cancelled() {
				    	Log.d("MC", "The request has been cancelled");
				    }

				});*/
		
		

		
	    /*final ListView listview = (ListView) findViewById(R.id.srcList);
	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
	        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
	        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
	        "Android", "iPhone", "WindowsMobile" };
	    final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) { list.add(values[i]); }
	    final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);

	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view,
	          int position, long id) {
	        final String item = (String) parent.getItemAtPosition(position);
	        view.animate().setDuration(2000).alpha(0).withEndAction( new Runnable() {
	              @Override
	              public void run() {
	                  list.remove(item);
	                  adapter.notifyDataSetChanged();
	                  view.setAlpha(1);
	              }
	        });
	     }

	    });*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
			super(context, textViewResourceId, objects);
		    for (int i = 0; i < objects.size(); ++i) {
		    	mIdMap.put(objects.get(i), i);
		    }
		}

		@Override
		public long getItemId(int position) {
		    String item = getItem(position);
		    return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
		    return true;
		}

	} 
}