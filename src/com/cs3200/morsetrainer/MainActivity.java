package com.cs3200.morsetrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	int mWPMSetting;
	int mHertzSetting;
	SharedPreferences mSP;
	static final String PREFS_KEY="MorseTrainerPrefs";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btnSettings = (Button) findViewById(R.id.btnSettings);	
		btnSettings.setOnClickListener(this);
		Button btnTraining = (Button) findViewById(R.id.btnTraining);	
		btnTraining.setOnClickListener(this);
		Button btnExamination = (Button) findViewById(R.id.btnExamination);	
		btnExamination.setOnClickListener(this);
		Button btnGuide = (Button) findViewById(R.id.btnGuide);
		btnGuide.setOnClickListener(this);
		
		// get Shared Prefernces
		mSP = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
		// extract from SP
		mWPMSetting = mSP.getInt("WPM", 5);
		mHertzSetting = mSP.getInt("hertz",500);
	}
	
	public void onClick(View v) {
		
	// Determine which button called us	
		if(v.getId() == R.id.btnTraining) {
			// --- Training Button ---
			startActivity(new Intent(this,TrainingActivity.class));
		}
		else if (v.getId()== R.id.btnSettings) {
			// --- Settings Button ---
			startActivity(new Intent(this,SettingsActivity.class));
		}
		else if (v.getId() == R.id.btnGuide) {
			startActivity(new Intent(this,GuideActivity.class));
		}
		else if (v.getId() == R.id.btnExamination) {
			startActivity(new Intent(this,ExamActivity.class));
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
