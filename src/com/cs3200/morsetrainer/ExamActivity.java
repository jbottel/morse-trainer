package com.cs3200.morsetrainer;

import java.util.Random;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("DefaultLocale")
public class ExamActivity extends Activity implements OnClickListener {

	SharedPreferences mSP;
	static final String PREFS_KEY="MorseTrainerPrefs";
	Random mRandomGenerator;
	boolean mNumbersIncluded;
	int mWPMSetting;
	int mHertzSetting;
	String mMessage;
	TextView mCopyText;
	TextView mDescText;
	EditText mCopiedText;
	boolean mDone;
	Button mDoneButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exam);
		
		// get Shared Preferences
		mSP = getSharedPreferences(PREFS_KEY, MODE_PRIVATE);
		// extract from SP
		mWPMSetting = mSP.getInt("WPM", 5);
		mHertzSetting = mSP.getInt("hertz",500);
		mDoneButton = (Button) findViewById(R.id.doneButton);
		Button rptMessage = (Button) findViewById(R.id.rptMessage);
		mDoneButton.setOnClickListener(this); rptMessage.setOnClickListener(this);
	
		mRandomGenerator = new Random();
		String[] messages = getResources().getStringArray(R.array.messages);
		mMessage = messages[mRandomGenerator.nextInt(messages.length)]; 

		MorsePlayer mp = new MorsePlayer(mWPMSetting, mHertzSetting);
		mp.playMessage("  " + mMessage);
		
		mCopyText = (TextView) findViewById(R.id.listenText);
		mDescText = (TextView) findViewById(R.id.descText);
		mCopiedText = (EditText) findViewById(R.id.copiedText);
		mDone = false;
		
		mCopyText.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);


	}
	
	@Override 
	public void onClick(View v) {
		if (v.getId() == R.id.rptMessage) {
			MorsePlayer mp = new MorsePlayer(mWPMSetting, mHertzSetting);
			mp.playMessage(mMessage);
		}
		if (v.getId() == R.id.doneButton) {
			if (mDone) { 
				mCopyText.setText("Copy The Message");
				mDescText.setText("Copy the message as you hear it.");
				onCreate(new Bundle());
			}
			else {
			int lDistance = getLevenshteinDistance(mMessage.toLowerCase(), mCopiedText.getText().toString().toLowerCase());
			int score = 100 - (lDistance*2);
			if (score<0) score = 0;
			Log.d("Score", "Score:"+score );
			mDescText.setText("Sent Message: " + mMessage);	
			mCopyText.setText("Your Score: " + score + "%");
			mDone = true;
			mDoneButton.setText("New Exam");
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exam, menu);
		return true;
	}

	 public static int getLevenshteinDistance(String s, String t) {
		 // implementation of Levenshtein Distance in Java
		 // from the Apache Commons project (stringUtils)
		 
	        if (s == null || t == null) {
	            throw new IllegalArgumentException("Strings must not be null");
	        }

	        /*
	           The difference between this impl. and the previous is that, rather 
	           than creating and retaining a matrix of size s.length()+1 by t.length()+1, 
	           we maintain two single-dimensional arrays of length s.length()+1.  The first, d,
	           is the 'current working' distance array that maintains the newest distance cost
	           counts as we iterate through the characters of String s.  Each time we increment
	           the index of String t we are comparing, d is copied to p, the second int[].  Doing so
	           allows us to retain the previous cost counts as required by the algorithm (taking 
	           the minimum of the cost count to the left, up one, and diagonally up and to the left
	           of the current cost count being calculated).  (Note that the arrays aren't really 
	           copied anymore, just switched...this is clearly much better than cloning an array 
	           or doing a System.arraycopy() each time  through the outer loop.)

	           Effectively, the difference between the two implementations is this one does not 
	           cause an out of memory condition when calculating the LD over two very large strings.
	         */

	        int n = s.length(); // length of s
	        int m = t.length(); // length of t

	        if (n == 0) {
	            return m;
	        } else if (m == 0) {
	            return n;
	        }

	        int p[] = new int[n+1]; //'previous' cost array, horizontally
	        int d[] = new int[n+1]; // cost array, horizontally
	        int _d[]; //placeholder to assist in swapping p and d

	        // indexes into strings s and t
	        int i; // iterates through s
	        int j; // iterates through t

	        char t_j; // jth character of t

	        int cost; // cost

	        for (i = 0; i<=n; i++) {
	            p[i] = i;
	        }

	        for (j = 1; j<=m; j++) {
	            t_j = t.charAt(j-1);
	            d[0] = j;

	            for (i=1; i<=n; i++) {
	                cost = s.charAt(i-1)==t_j ? 0 : 1;
	                // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
	                d[i] = Math.min(Math.min(d[i-1]+1, p[i]+1),  p[i-1]+cost);
	            }

	            // copy current distance counts to 'previous row' distance counts
	            _d = p;
	            p = d;
	            d = _d;
	        }

	        // our last action in the above loop was to switch d and p, so p now 
	        // actually has the most recent cost counts
	        return p[n];
	    }
}
