package com.cs3200.morsetrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity implements OnClickListener, OnSeekBarChangeListener, OnCheckedChangeListener {
	
	int mWPMSetting;
	int mHertzSetting;
	boolean mNumbersEnabled;
	TextView mWpmText;
	TextView mFreqText;
	SharedPreferences mSP;
	SharedPreferences.Editor mSPe;
	static final String PREFS_KEY="MorseTrainerPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//initalize
		Button btnReturn = (Button) findViewById(R.id.btnReturn);
		ToggleButton btnNumbers = (ToggleButton) findViewById(R.id.numberToggle);
		SeekBar wpmSeekBar = (SeekBar) findViewById(R.id.wpmSeekBar);
		SeekBar freqSeekBar = (SeekBar) findViewById(R.id.freqSeekBar);
		mWpmText = (TextView) findViewById(R.id.wpmText);
		mFreqText = (TextView) findViewById(R.id.freqText);
		mSP = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
		mSPe = mSP.edit();
		
		
		
		
		wpmSeekBar.setOnSeekBarChangeListener(this);
		freqSeekBar.setOnSeekBarChangeListener(this);
		btnReturn.setOnClickListener(this);
		btnNumbers.setOnCheckedChangeListener(this);
		
		
		// Fix up Hertz Seekbar
		freqSeekBar.setMax(15);
		
		// Seekbar values are the current settings
		int freqValue = (mSP.getInt("hertz",5)- 500)/100;
		freqSeekBar.setProgress(freqValue);
		int wpmValue = (mSP.getInt("WPM", 5))*2;
		wpmSeekBar.setProgress(wpmValue);
		mNumbersEnabled = mSP.getBoolean("numbers_enabled", true);
		btnNumbers.setChecked(mNumbersEnabled);
		
	}
	
	
	public void onClick(View v) {
		
		// Determine which button called us	
			if (v.getId()== R.id.btnReturn) {
				mSPe.putInt("WPM",mWPMSetting);
				mSPe.putInt("hertz", mHertzSetting);
				mSPe.putBoolean("numbers_enabled", mNumbersEnabled);
				mSPe.commit();
				startActivity(new Intent(this,MainActivity.class));
			}
	}
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	if (isChecked)	{
		mNumbersEnabled = true;
	}
	else {
		mNumbersEnabled = false;
	}
	}
	
	public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
		if (sb.getId() == R.id.wpmSeekBar){
			progress = progress / 2;
			if (progress < 5) progress = 5;
			mWpmText.setText("" + progress + " WPM");
			mWPMSetting = progress;
		}
		else if (sb.getId() == R.id.freqSeekBar) {
			int hertz = (progress * 100) + 500;
			mFreqText.setText("" + hertz + " Hz");
			mHertzSetting = hertz;
		}
	}
	public void onStartTrackingTouch(SeekBar sb) {  }
	public void onStopTrackingTouch(SeekBar sb) {  }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
