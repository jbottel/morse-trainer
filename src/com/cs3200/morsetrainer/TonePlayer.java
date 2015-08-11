package com.cs3200.morsetrainer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class TonePlayer {
	
	private int mWPM;
	private int mToneFreq;
	static final int sampleRate = 8000;
	static final int sampleMilli = 8; //For generating tones in the milliseconds.
	static final String PREFS_KEY="MorseTrainerPrefs";
	
	TonePlayer(int wpm, int tone) {
		mWPM = wpm;
		mToneFreq = tone;
	}
	
	public void playMorseSequence(String seq) {
		ByteArrayOutputStream sequencePCM = new ByteArrayOutputStream();
		char[] chars = seq.toCharArray();
		for (int i = 0, n = chars.length; i < n; i++) {
		    char c = chars[i];
		    byte[] thisArray = getMorseTone(c);
		    byte[] spacing = getMorseTone('~');
		    try {
				sequencePCM.write(thisArray);
				sequencePCM.write(spacing);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		playSound(sequencePCM.toByteArray());
	}
	
	public byte[] getMorseTone(Character c) {
		double CPS = (1.2 / mWPM); // Get characters per second
		Double fam = (CPS * 100) * sampleMilli; // Set a unit time in terms of PCM sample frame.
		int keyTime = fam.intValue();
		boolean keyed = false;
		if (c=='.') {
			//keyTime remains the same
			keyed = true;
		}
		else if (c=='-') { 
			keyTime = keyTime * 3;
			keyed = true;
		}
		else if (c=='_') {
			// This is intra-character wait time
			keyed = false;
		}
		else if (c==' ') {
			// This is a space (7x wait time)
			keyed = false;
			keyTime = keyTime * 6; // This is only used in a sequence, so sequence will add another time unit.
		}
		
        byte[] result = new byte[keyTime];
        if (keyed) {
	        result = genMilliTone(keyTime, mToneFreq);
        }
        else {
        	result = genMilliTone(keyTime, 1);
        }
        return result;
		}
		
	
	

	void playSound(byte[] snd) {
    	int minBuf = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    	if (snd.length < minBuf) { Log.d("TonePlayer","Buffer size not sufficient.");}
    	Log.d("TonePlayer", "playSound: Min Buffer Size: " + minBuf);
    	Log.d("TonePlayer","playSound: Current Buffer Size: " + snd.length);
    	// Frame size for 16-bit PCM is 4
    	// Therefore, our length has to be a multiple of 4
    	// Get the closest multiple of 4 (Use floor because we don't have more bytes)
    	Double trueLength = (Math.floor(snd.length/4) * 4);
    	int lengthToUse = trueLength.intValue();
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
               AudioFormat.ENCODING_PCM_16BIT, lengthToUse,
              AudioTrack.MODE_STATIC);
        audioTrack.write(snd, 0, lengthToUse);
		audioTrack.play();
    }
	
	
	public byte[] genMilliTone(int duration, int freqOfTone) {
		// Raw PCM Audio Tone Generation
		// Based on code written by Paul Reeves - http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
		// Adjusted to play tones in milliseconds rather than milliseconds
		// Ramping additions contributed by GitHub user SuspendedPhan (https://gist.github.com/SuspendedPhan/7596139)
		
		int numSamples = duration * 8;
		double sample[] = new double[numSamples];
		byte generatedSnd[] = new byte[2 * numSamples];
	
		// fill out the array
	    for (int i = 0; i < numSamples; ++i) {
	        sample[i] = Math.sin((2 * Math.PI - .001) * i / (sampleRate/freqOfTone));
	    }
	 
	    // convert to 16 bit pcm sound array
	    // assumes the sample buffer is normalized.
	    int idx = 0;
	    int ramp = numSamples / 20;
	    for (int i = 0; i < ramp; i++) {
	        // scale to maximum amplitude
	        final short val = (short) ((sample[i] * 32767) * i / ramp);
	        // in 16 bit wav PCM, first byte is the low order byte
	        generatedSnd[idx++] = (byte) (val & 0x00ff);
	        generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
	    }
	 
	    for (int i = ramp; i < numSamples - ramp; i++) {
	        // scale to maximum amplitude
	        final short val = (short) ((sample[i] * 32767));
	        // in 16 bit wav PCM, first byte is the low order byte
	        generatedSnd[idx++] = (byte) (val & 0x00ff);
	        generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
	    }
	 
	    for (int i = numSamples - ramp; i < numSamples; i++) {
	        // scale to maximum amplitude
	        final short val = (short) ((sample[i] * 32767) * (numSamples - i) / ramp);
	        // in 16 bit wav PCM, first byte is the low order byte
	        generatedSnd[idx++] = (byte) (val & 0x00ff);
	        generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
	    }
	    
	    return generatedSnd;
}

}
