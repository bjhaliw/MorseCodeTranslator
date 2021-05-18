package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class DecodeAmbiguousMorseCode implements Runnable {

	String input, output;
	HashMap<String, ArrayList<String>> wordMap;
	ArrayList<String> realWords;

	
	public DecodeAmbiguousMorseCode(String input) {
		
	}
	
	@Override
	public void run() {
		
		
	}
	
	private void decodeAmbiguousMorseAux(String input, String output, ArrayList<String> list) {
		String tempInput = input;
		String tempOutput = output;
		for (MorseCode code : MorseCode.values()) {
			if (tempInput.length() == 0) {
				break;
			}

			if (tempInput.startsWith(code.getCode())) {
				output += code.getLetter();
				tempInput = tempInput.substring(code.getCode().length(), tempInput.length());
				decodeAmbiguousMorseAux(tempInput, output, list);
			}

			if (tempInput.length() == 0) {
				list.add(output);
				checkIfRealWord(output);
				System.out.println(output);
			}

			output = tempOutput;
			tempInput = input;
		}

	}

	private boolean checkIfRealWord(String input) {
		String firstLetter = input.substring(0, 1).toUpperCase();

		if (wordMap.get(firstLetter.toUpperCase()).contains(input.toLowerCase())) {
			realWords.add(input);
			return true;
		} else {
			return false;
		}
	}
	
	private int writeCombinationFile(ArrayList<String> list) {
		File file = new File(System.getProperty("user.dir") + "//results.txt");
		int counter = 0;
		try {
			PrintWriter writer = new PrintWriter(file);
			for (String string : list) {
				writer.println(string);
				counter++;
			}

			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return counter;
	}

}
