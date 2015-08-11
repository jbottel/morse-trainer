package com.cs3200.morsetrainer;

import java.util.Random;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TrainingActivity extends Activity implements OnClickListener {

	SharedPreferences mSP;
	SharedPreferences.Editor mSPe;
	static final String PREFS_KEY="MorseTrainerPrefs";
	Alphabet mAlphabet;
	Random mRandomGenerator;
	boolean mNumbersIncluded;
	int mWPMSetting;
	int mHertzSetting;
	int mScore;
	int mHighScore;
	int mCorrectIndex;
	char mCurrentChar;
	char[] mButtonChars;
	
	// Buttons
	Button mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;
	
	// TextViews
	TextView mListenText;
	TextView mScoreText;
	TextView mHighScoreText;
	
	
	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
		
		// Inflate Buttons
		mButton1 = (Button) findViewById(R.id.doneButton);
		mButton2 = (Button) findViewById(R.id.rptMessage);
		mButton3 = (Button) findViewById(R.id.button3);
		mButton4 = (Button) findViewById(R.id.button4);
		mButton5 = (Button) findViewById(R.id.button5);
		mButton6 = (Button) findViewById(R.id.button6);
		
		// Inflate Text
		mListenText = (TextView) findViewById(R.id.listenText);
		mScoreText = (TextView) findViewById(R.id.score);
		mHighScoreText = (TextView) findViewById(R.id.highScore);
		
		// Set up Listener
		mButton1.setOnClickListener(this); mButton2.setOnClickListener(this); mButton3.setOnClickListener(this);
		mButton4.setOnClickListener(this); mButton5.setOnClickListener(this); mButton6.setOnClickListener(this);
		
		Button repeatButton = (Button) findViewById(R.id.repeatButton); repeatButton.setOnClickListener(this);
		
		// get Shared Preferences
		mSP = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
		mSPe = mSP.edit();
		// extract from SP
		mWPMSetting = mSP.getInt("WPM", 5);
		mHertzSetting = mSP.getInt("hertz",500);
		mHighScore = mSP.getInt("high_score",0);
		mNumbersIncluded = mSP.getBoolean("numbers_enabled", true);

		// set Defaults
		mAlphabet = new Alphabet();
		mScore = 0;
		mRandomGenerator = new Random();

		newGame();
	}
	
	@Override
	public void onClick(View v) {
		int buttonSelected = 0;
		if (v.getId() == R.id.repeatButton) {
			MorsePlayer mp = new MorsePlayer(mWPMSetting, mHertzSetting);
			mp.playMessage(String.valueOf(mButtonChars[mCorrectIndex]));
			return;
		}
		if (v.getId() == R.id.doneButton) buttonSelected = 1; if (v.getId() == R.id.rptMessage) buttonSelected = 2;
		if (v.getId() == R.id.button3) buttonSelected = 3; if (v.getId() == R.id.button4) buttonSelected = 4;
		if (v.getId() == R.id.button5) buttonSelected = 5; if (v.getId() == R.id.button6) buttonSelected = 6;
		
		if (buttonSelected-1 == mCorrectIndex) {
			Log.d("TrainingActivity","OnClick: Correct Answer.");
			mScore++;
			if (mScore > mHighScore) {
				mSPe.putInt("high_score", mScore);
				mSPe.commit();
				mHighScore = mScore;
			}
			newGame();
		}
		else { 
			mScore = 0;
			newGame();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.training, menu);
		return true;
	}
	
	public char getRandomLetter() {
		String selection;
		if (mNumbersIncluded) selection = mAlphabet.getFullAlphabetString();
		else selection = mAlphabet.getLimitedAlphabetString();
		return selection.charAt(mRandomGenerator.nextInt(selection.length()));
	}
	
	public void newGame() {
		// set Score
		mScoreText.setText(String.valueOf(mScore));
		mHighScoreText.setText(String.valueOf(mHighScore));

		
		// set up Buttons and State
		char mCurrentChar =  getRandomLetter();
		mButtonChars = new char[6];
		// fill out the array of chars
		for (int i = 0; i < 6; i++) {
			boolean good = false;
			char candidate = ' ';
			while (!good) {
				candidate = getRandomLetter();
				if (candidate != mCurrentChar) good = true; 
				for(int j = 0; j < i; j++) { if (candidate == mButtonChars[j]) good=false; } // no repeats
			}
			mButtonChars[i] = candidate;
		}

		// set correct button
		mCorrectIndex = mRandomGenerator.nextInt(5);
		mButtonChars[mCorrectIndex] = mCurrentChar;

		// Set button values
		mButton1.setText(String.valueOf(mButtonChars[0])); mButton2.setText(String.valueOf(mButtonChars[1])); mButton3.setText(String.valueOf(mButtonChars[2]));
		mButton4.setText(String.valueOf(mButtonChars[3])); mButton5.setText(String.valueOf(mButtonChars[4])); mButton6.setText(String.valueOf(mButtonChars[5]));
		
		
		// Play the morse
		MorsePlayer mp = new MorsePlayer(mWPMSetting, mHertzSetting);
		mp.playMessage(" " +String.valueOf(mButtonChars[mCorrectIndex]));
		
		
	}
	}
