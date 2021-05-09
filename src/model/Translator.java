package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Translator {

	HashMap<String, ArrayList<String>> wordMap;
	HashMap<String, String> codeLetter;
	HashMap<String, String> letterCode;

	public Translator() {
		this.wordMap = new HashMap<>();
		this.codeLetter = new HashMap<>();
		this.letterCode = new HashMap<>();
		loadWordMap();

		for (MorseCode code : MorseCode.values()) {
			this.codeLetter.put(code.getCode(), code.getLetter());
			this.letterCode.put(code.getLetter(), code.getCode());
		}
	}

	public String englishToMorse(String input) {
		StringBuilder sb = new StringBuilder();

		if (!input.matches("\s*[A-Za-z\s*]+\s*")) {
			return "Improper format. Letters only";
		}

		input = input.toUpperCase();
		String[] split = input.split("");

		for (int i = 0; i < split.length; i++) {
			if (split[i].matches("[A-Za-z]+")) {
				sb.append(letterCode.get(split[i]) + " ");
			}
		}

		return sb.toString();
	}

	public String morseToEnglish(String input) {
		StringBuilder sb = new StringBuilder();

		if (!input.matches("\s*[.\\-\s*]+\s*")) {
			return "Improper format. Periods and hyphens only ( . - )";
		}

		String[] split = input.split(" ");

		for (int i = 0; i < split.length; i++) {
			if (split[i].matches("[.-]+")) {
				sb.append(codeLetter.get(split[i]) + " ");
			} else if (split[i].matches("\s+")) {
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

		for (String string : comboList) {
			if (checkIfRealWord(string)) {
				sb.append(string + "\n");
			}
		}

		sb.append("Total combination results can be found: " + System.getProperty("user.dir") + "\\results.txt");

		return sb.toString();
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
			}

			output = tempOutput;
			tempInput = input;
		}

	}

	private boolean checkIfRealWord(String input) {
		String firstLetter = input.substring(0, 1).toUpperCase();

		if (input.equals("HELLO")) {
			System.out.println("Found hello");
		}

		if (wordMap.get(firstLetter.toUpperCase()).contains(input.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	private void loadWordMap() {
		InputStream is = getClass().getResourceAsStream("/words_alpha.txt");
		Scanner scanner = new Scanner(is);

		String string;
		String firstLetter;
		while (scanner.hasNext()) {
			string = scanner.nextLine();
			firstLetter = string.substring(0, 1).toUpperCase();

			if (wordMap.containsKey(firstLetter)) {
				wordMap.get(firstLetter).add(string.toLowerCase());
			} else {
				ArrayList<String> list = new ArrayList<>();
				list.add(string.toLowerCase());
				wordMap.put(firstLetter, list);
			}
		}
		scanner.close();
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
