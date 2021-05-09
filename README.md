# MorseCodeTranslator

## Features
- English to Morse Code
- Morse Code to English
- Decode ambiguous Morse Code (squished together)

## Decoding Ambiguous Morse Code
Ambiguous Morse Code is created when there are no clear distinctions between Morse Code letters. 
- Example:  ...- has 7 different possible outputs: EEA, EEET, EIT, EU, IA, IET, and ST

The tool is designed to iterate through all possible combinations of the ambiguous Morse Code and then display the results in a text file for the user.
After a combination has been finished, it is then checked against a database of words to see if it is a real word. If so, this information is displayed in the GUI window.
- Example: -.....-. results in BEEN, BIN, BITE, DEER, and THEN

## Potential Uses
If you need to quickly transfer back and forth between Morse Code and English. Also if you need to decode Morse Code that has been accidentally or intentionally squished together.

## Speed
Ambiguous Morse Code decoding can be a lengthy process if the input is large. Please give time for it to fully decode.

