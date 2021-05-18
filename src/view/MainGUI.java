package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.MorseCode;
import model.Translator;

@SuppressWarnings("restriction")
public class MainGUI extends Application {

	private Translator translator;
	private CustomizationScreen customScreen;

	public MainGUI() {
		this.translator = new Translator();
		this.customScreen = new CustomizationScreen();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox(10);
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setAlignment(Pos.TOP_CENTER);

		Label title = new Label("Morse Code Translation Tool");
		title.setFont(Font.font(30));
		title.setUnderline(true);

		TextField inputField = new TextField();
		inputField.setAlignment(Pos.CENTER);
		inputField.setFont(Font.font(20));

		RadioButton englishToMorse = new RadioButton("English to Morse Code");
		englishToMorse.setSelected(true);
		RadioButton morseToEnglish = new RadioButton("Morse Code to English");
		RadioButton ambiguousMorse = new RadioButton("Decode Ambiguous Morse Code");

		HBox tgBox = new HBox(10);
		tgBox.setAlignment(Pos.CENTER);
		tgBox.getChildren().addAll(englishToMorse, morseToEnglish, ambiguousMorse);

		ToggleGroup tg = new ToggleGroup();
		tg.getToggles().addAll(englishToMorse, morseToEnglish, ambiguousMorse);

		HBox buttonBox = new HBox(10); // Will hold the Buttons to use translation services
		buttonBox.setAlignment(Pos.CENTER); // Set alignment of box to be centered

		Button goButton = new Button("Translate"); // Button to start the translation
		goButton.setPrefWidth(125); // Set preferred width of Button to 125 pixels
		Button clearButton = new Button("Clear Areas"); // Button to clear TextField and TextArea
		clearButton.setPrefWidth(125); // Set preferred width of Button to 125 pixels
		Button spacesButton = new Button("Remove Spaces"); // Button to remove spaces from TextField
		spacesButton.setPrefWidth(125); // Set preferred width of Button to 125 pixels

		buttonBox.getChildren().addAll(goButton, clearButton, spacesButton);

		TextArea resultArea = new TextArea(); // Where results of translation will show
		resultArea.setFont(Font.font(20)); // Increase font size of TextArea
		resultArea.setEditable(false); // Don't allow user to manipulate the TextArea
		resultArea.setWrapText(true); // Allow TextArea to word wrap so user doesn't have to scroll

		goButton.setOnAction(e -> { // Initiate translation
			RadioButton selectedButton = (RadioButton) tg.getSelectedToggle();
			if (selectedButton.getText().equals("English to Morse Code")) {
				resultArea.setText(translator.englishToMorse(inputField.getText()));
			} else if (selectedButton.getText().equals("Morse Code to English")) {
				resultArea.setText(translator.morseToEnglish(inputField.getText()));
			} else if (selectedButton.getText().equals("Decode Ambiguous Morse Code")) {
				resultArea.setText(translator.decodeAmbiguousMorse(inputField.getText().replaceAll(" ", "")));
			}
		});

		clearButton.setOnAction(e -> { // Clears the TextField and the TextArea
			inputField.setText("");
			resultArea.setText("");
		});

		spacesButton.setOnAction(e -> { // Button click to remove spaces from TextField
			inputField.setText(inputField.getText().replace(" ", "")); // Replaces spaces with blank characters
		});

		HBox customBox = new HBox(10);
		customBox.setAlignment(Pos.CENTER_RIGHT);
		Button customButton = new Button("Customize");
		customBox.getChildren().addAll(customButton);

		root.getChildren().addAll(title, createButtonPane(inputField), inputField, tgBox, buttonBox, resultArea,
				customBox); // Add all child Nodes to the main VBox
		Scene scene = new Scene(root); // Create the scene by loading the main VBox

		customButton.setOnAction(e -> { // Selecting the Customize Button at the bottom of the screen
			customScreen.launchCustomizationScreen(scene); // Launch customization screen
		});

		primaryStage.setTitle("Morse Code Translator"); // Create the title for the window
		primaryStage.setScene(scene); // Setting the screen for the window
		primaryStage.show(); // Show the window on the screen
		primaryStage.setOnCloseRequest(e -> System.exit(0)); // Terminate if main window is closed
	}

	/**
	 * Create the Buttons for the upper portion of the GUI. Allows the user to
	 * select Morse Code characters by hitting each Button. Text will be displayed
	 * in the supplied TextField
	 * 
	 * @param entry - TextField to display Morse Code characters
	 * @return GridPane containing Buttons for Morse Code
	 */
	private GridPane createButtonPane(TextField entry) {
		GridPane pane = new GridPane(); // Create GridPane to hold Morse Code buttons
		pane.setAlignment(Pos.CENTER); // Center the GridPAne
		pane.setHgap(10); // Horizontal gap of 10 pixels
		pane.setVgap(10); // Vertical gap of 10 pixels

		ArrayList<Button> buttons = new ArrayList<>(); // List containing all buttons A-Z

		for (MorseCode code : MorseCode.values()) {
			Button button = new Button(code.getLetter() + ":  " + code.getCode()); // Create button
			button.setAlignment(Pos.CENTER); // Center text of button
			button.setPrefSize(100, 100); // Set preferred size of button to be 100 x 100 pixels
			button.setFont(Font.font(20)); // Increase font size of button to 20 pixels
			button.setOnAction(e -> { // If button is pressed, display its Morse Code to TextField
				entry.setText(entry.getText() + " " + code.getCode());
			});

			buttons.add(button); // Add the button to the list of all buttons
		}

		int row = 0; // Keep track of which row we're on
		int col = 0; // Keep track of which column we're on
		int counter = 0; // Get the desired button from button list

		while (counter < 26) {
			pane.add(buttons.get(counter), col, row);
			counter++;
			col++;
			if (col == 7) {
				col = 0;
				row++;
			}
		}

		return pane; // Return the finished GridPane containing all buttons
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
