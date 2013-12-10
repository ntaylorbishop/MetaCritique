package com.example.metacritique;

import com.example.metacritique.R;

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
		
		final Intent i = new Intent(this, ListActivity.class);
		
		searchTxt = (EditText) findViewById(R.id.SearchBar);
		searchBtn = (ImageButton) findViewById(R.id.SearchBtn); //Set the onClick listener
		searchBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//i.putExtra("SearchTerm", searchTxt.getText().toString());
				startActivity(i); 
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
