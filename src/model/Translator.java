package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {

	HashMap<String, String> wordMap;
	HashMap<String, String> codeLetter;
	HashMap<String, String> letterCode;
	ArrayList<String> realWords;

	public Translator() {
		this.wordMap = new HashMap<>();
		this.codeLetter = new HashMap<>();
		this.letterCode = new HashMap<>();
		realWords = new ArrayList<>();
		loadWordMap();

		for (MorseCode code : MorseCode.values()) {
			this.codeLetter.put(code.getCode(), code.getLetter());
			this.letterCode.put(code.getLetter(), code.getCode());
		}
	}

	/**
	 * Convert English letters to Morse Code
	 * 
	 * @param input - English letters to be converted
	 * @return String containing Morse Code characters
	 */
	public String englishToMorse(String input) {
		StringBuilder sb = new StringBuilder();

		// Check if format is correct
		if (!input.matches("\s*[A-Za-z\s*]+\s*")) {
			return "Improper format. Letters only";
		}

		input = input.toUpperCase();
		String[] split = input.split(""); // Split string by each character

		for (int i = 0; i < split.length; i++) {
			if (split[i].matches("[A-Za-z]+")) { // If the current character is a letter
				sb.append(letterCode.get(split[i]) + " "); // Append Morse Code
			}
		}

		return sb.toString();
	}

	/**
	 * Convert Morse Code characters to English Letters
	 * 
	 * @param input - Morse Code string containing spaces to split characters
	 * @return String containing English letters for the translation
	 */
	public String morseToEnglish(String input) {
		StringBuilder sb = new StringBuilder();

		// Check to see if format is correct
		if (!input.matches("\s*[.\\-\s*]+\s*")) {
			return "Improper format. Periods and hyphens only ( . - )";
		}

		String[] split = input.split(" "); // Split string by any spaces

		for (int i = 0; i < split.length; i++) {
			if (split[i].matches("[.-]+")) { // If a Morse Code character
				sb.append(codeLetter.get(split[i]) + " "); // Replace with letter
			} else if (split[i].matches("\s+")) { // If it is a space
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	public String decodeAmbiguousMorse(String input) {
		StringBuilder sb = new StringBuilder();
		String temp = input;
		String output = "";
		ArrayList<String> comboList = new ArrayList<>();
		realWords = new ArrayList<>();

		long start = System.currentTimeMillis();
		for (MorseCode code : MorseCode.values()) {
			if (input.startsWith(code.getCode())) {
				output += code.getLetter();
				temp = input.substring(code.getCode().length(), input.length());
				decodeAmbiguousMorseAux(temp, output, comboList);
			}
			output = "";
		}

		int num = writeCombinationFile(comboList);

		sb.append("Number of combinations: " + num + "\n");

		for (String string : realWords) {
			sb.append(string + "\n");
		}

		sb.append("Total combination results can be found: " + System.getProperty("user.dir") + "\\results.txt");

		long end = System.currentTimeMillis();

		System.out.println("Time: " + (end - start));

		return sb.toString();
	}

	/**
	 * Auxiliary method for the decodeAmbiguousMorse method. Recursive and iterative
	 * loop to try all possible combinations of the Morse Code.
	 * 
	 * @param input  - The current String input of Morse Code
	 * @param output - The current String output of English letters
	 * @param list   - An ArrayList<String> containing all possible combinations of
	 *               the Morse Code String
	 */
	private void decodeAmbiguousMorseAux(String input, String output, ArrayList<String> list) {
		String tempInput = input;
		String tempOutput = output;
		for (MorseCode code : MorseCode.values()) {
			// Check to see if we've ran out of Morse Code for this loop
			if (tempInput.length() == 0) {
				list.add(output); // Add combination to running total
				checkIfRealWord(output); // Check if combination is real or not
				System.out.println(output);
				break;
			}

			if (tempInput.startsWith(code.getCode())) { // Is this a possible starting point?
				output += code.getLetter(); // Convert Morse Code to English Letter for output
				tempInput = tempInput.substring(code.getCode().length(), tempInput.length()); // Remove Morse Code
				decodeAmbiguousMorseAux(tempInput, output, list); // Enter new recursive loop
			}

			output = tempOutput; // Reassign the output to be back to the beginning
			tempInput = input; // Reassign the input to be back to the beginning
		}

	}

	/**
	 * Checks to see if the given string is a real word
	 * 
	 * @param input - The combination resulting from the ambiguous Morse Code
	 * @return true if real word, false if not
	 */
	private boolean checkIfRealWord(String input) {
		if (wordMap.get(input) != null) { // Does HashMap contain the input?
			realWords.add(input); // Was a real word, add to real word list
			return true;
		} else {
			return false; // Was not a real word
		}
	}

	/**
	 * Initializes the instance variable HashMap<String, String> wordMap which
	 * contains real words
	 */
	private void loadWordMap() {
		InputStream is = getClass().getResourceAsStream("/words_alpha.txt");
		Scanner scanner = new Scanner(is);

		String string;

		while (scanner.hasNext()) { // Loop through word text file and add words to hashmap
			string = scanner.nextLine();
			wordMap.put(string.toUpperCase(), string); // Convert to upper case letters
		}
		scanner.close();
	}

	/**
	 * Writes the results to a text file titled "results.txt" which will be found in
	 * the same location as the GUI file.
	 * 
	 * @param list - ArrayList<String> containing the combinations to be written to
	 *             the file
	 * @return number of combinations that were loaded into the file
	 */
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
