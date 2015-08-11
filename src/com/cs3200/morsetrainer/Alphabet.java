package com.cs3200.morsetrainer;

import java.util.HashMap;
import java.util.Map;

public class Alphabet {

	private Map <Character,String> charToMorseMap;
	private Map <String,Character> morseToCharMap;
	private String fullAlphabetString;
	private String limitedAlphabetString;
	
	
	public Character getCharFromMorse(String morse) {
		Character ch;
		ch = morseToCharMap.get(morse);
		if (ch == null) ch = '?';
		return ch;
	}
	
	public String getMorseFromChar(Character ch) {
		String s;
		s = charToMorseMap.get(ch);
		if (s == null) s= " ";
		return s;
	}
	
	public String getFullAlphabetString() { return fullAlphabetString; }
	public String getLimitedAlphabetString() { return limitedAlphabetString; }
	
	Alphabet() {
	fullAlphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	limitedAlphabetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
	charToMorseMap = new HashMap<Character,String>();	
	charToMorseMap.put('A',".-");
	charToMorseMap.put('B',"-...");
	charToMorseMap.put('C',"-.-.");
	charToMorseMap.put('D',"-..");
	charToMorseMap.put('E',".");
	charToMorseMap.put('F',"..-.");
	charToMorseMap.put('G',"--.");
	charToMorseMap.put('H',"....");
	charToMorseMap.put('I',"..");
	charToMorseMap.put('J',".---");
	charToMorseMap.put('K',"-.-");
	charToMorseMap.put('L',".-..");
	charToMorseMap.put('M',"--");
	charToMorseMap.put('N',"-.");
	charToMorseMap.put('O',"---");
	charToMorseMap.put('P',".--.");
	charToMorseMap.put('Q',"--.-");
	charToMorseMap.put('R',".-.");
	charToMorseMap.put('S',"...");
	charToMorseMap.put('T',"-");
	charToMorseMap.put('U',"..-");
	charToMorseMap.put('V',"...-");
	charToMorseMap.put('W',".--");
	charToMorseMap.put('X',"-..-");
	charToMorseMap.put('Y',"-.--");
	charToMorseMap.put('Z',"--..");
	charToMorseMap.put('1',".----");
	charToMorseMap.put('2',"..---");
	charToMorseMap.put('3',"...--");
	charToMorseMap.put('4',"....-");
	charToMorseMap.put('5',".....");
	charToMorseMap.put('6',"-....");
	charToMorseMap.put('7',"--...");
	charToMorseMap.put('8',"---..");
	charToMorseMap.put('9',"----.");
	charToMorseMap.put('0',"-----");
	
	// others
	charToMorseMap.put(' ', " ");
	
	morseToCharMap = new HashMap<String,Character>();	
	morseToCharMap.put(".-",'A');
	morseToCharMap.put("-...",'B');
	morseToCharMap.put("-.-.",'C');
	morseToCharMap.put("-..",'D');
	morseToCharMap.put(".",'E');
	morseToCharMap.put("..-.",'F');
	morseToCharMap.put("--.",'G');
	morseToCharMap.put("....",'H');
	morseToCharMap.put("..",'I');
	morseToCharMap.put(".---",'J');
	morseToCharMap.put("-.-",'K');
	morseToCharMap.put(".-..",'L');
	morseToCharMap.put("--",'M');
	morseToCharMap.put("-.",'N');
	morseToCharMap.put("---",'O');
	morseToCharMap.put(".--.",'P');
	morseToCharMap.put("--.-",'Q');
	morseToCharMap.put(".-.",'R');
	morseToCharMap.put("...",'S');
	morseToCharMap.put("-",'T');
	morseToCharMap.put("..-",'U');
	morseToCharMap.put("...-",'V');
	morseToCharMap.put(".--",'W');
	morseToCharMap.put("-..-",'X');
	morseToCharMap.put("-.--",'Y');
	morseToCharMap.put("--..",'Z');
	morseToCharMap.put(".----",'1');
	morseToCharMap.put("..---",'2');
	morseToCharMap.put("...--",'3');
	morseToCharMap.put("....-",'4');
	morseToCharMap.put(".....",'5');
	morseToCharMap.put("-....",'6');
	morseToCharMap.put("--...",'7');
	morseToCharMap.put("---..",'8');
	morseToCharMap.put("----.",'9');
	morseToCharMap.put("-----",'0');
	}
	
}
