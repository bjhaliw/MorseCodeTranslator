package model;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Translator translator = new Translator();
		String userInput = "", result = "", string = "";
		final String exit = "0";

		System.out.println("Morse Code Translation Tool");

		while (!userInput.equals(exit)) {
			System.out.println("Please select from one of the following options.");
			System.out.println("1. English to Morse Code\n2. Morse Code to English\n3. Decode Ambiguous Morse Code");
			userInput = scanner.nextLine();

			switch (userInput) {
			case ("1"):
				System.out.println("Please enter your English word/sentence");
				string = scanner.nextLine();
				result = translator.englishToMorse(string);
				System.out.println(string + ": " + result);
				break;
			case ("2"):
				System.out.println("Please enter your Morse Code with spaces between distinct letters");
				string = scanner.nextLine();
				result = translator.morseToEnglish(string);
				System.out.println(string + ": " + result);
				break;
			case ("3"):
				System.out.println("Please enter your Morse Code with no spaces");
				string = scanner.nextLine();
				result = translator.decodeAmbiguousMorse(string);
				System.out.println(string + ": " + result);
				break;
			case ("0"):
				break;
			default:
				System.out.println("Not a valid selection");
			}

		}

	}
}
