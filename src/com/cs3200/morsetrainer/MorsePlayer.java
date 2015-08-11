package com.cs3200.morsetrainer;

import java.util.Locale;

import android.util.Log;

public class MorsePlayer {
	
	private int mWPM, mToneFreq;
	
	MorsePlayer(int wpm, int tone) {
		mWPM = wpm;
		mToneFreq = tone;
	}

	static String messageToMorse(String message)
	{ 
    StringBuilder morseString = new StringBuilder();
	Alphabet alphabet = new Alphabet();
	String lc = message.toUpperCase(Locale.US);
	char[] chars = lc.toCharArray();
	for (int i = 0; i < chars.length; i++) {
		morseString.append(alphabet.getMorseFromChar(chars[i]));
		morseString.append("_"); // spacing
	}
	Log.d("MorsePlayer", "messageToMorse: "+morseString.toString());
	return morseString.toString();
	}
	
	
	void playMessage(String message) {
		TonePlayer tp = new TonePlayer(mWPM,mToneFreq);
		tp.playMorseSequence(messageToMorse(message));
	}
}
