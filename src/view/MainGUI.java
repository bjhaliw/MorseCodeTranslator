package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.*;
import model.MorseCode;
import model.Translator;

@SuppressWarnings("restriction")
public class MainGUI extends Application {

	Translator translator;

	public MainGUI() {
		this.translator = new Translator();
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

		HBox buttonBox = new HBox(10);
		buttonBox.setAlignment(Pos.CENTER);

		Button goButton = new Button("Go!");
		Button clearButton = new Button("Clear");
		Button spacesButton = new Button("Remove Spaces");

		buttonBox.getChildren().addAll(goButton, clearButton, spacesButton);

		TextArea resultArea = new TextArea();
		resultArea.setFont(Font.font(20));
		resultArea.setEditable(false);
		resultArea.setWrapText(true);

		goButton.setOnAction(e -> {
			RadioButton selectedButton = (RadioButton) tg.getSelectedToggle();
			if (selectedButton.getText().equals("English to Morse Code")) {
				resultArea.setText(translator.englishToMorse(inputField.getText()));
			} else if (selectedButton.getText().equals("Morse Code to English")) {
				resultArea.setText(translator.morseToEnglish(inputField.getText()));
			} else if (selectedButton.getText().equals("Decode Ambiguous Morse Code")) {
				resultArea.setText(translator.decodeAmbiguousMorse(inputField.getText().replaceAll(" ", "")));
			}
		});

		clearButton.setOnAction(e -> {
			inputField.setText("");
			resultArea.setText("");
		});

		spacesButton.setOnAction(e -> {
			inputField.setText(inputField.getText().replace(" ", ""));
		});

		HBox customBox = new HBox(10);
		customBox.setAlignment(Pos.CENTER_RIGHT);
		Button customButton = new Button("Customize");
		customBox.getChildren().addAll(customButton);

		root.getChildren().addAll(title, createButtonPane(inputField), inputField, tgBox, buttonBox, resultArea,
				customBox);
		Scene scene = new Scene(root);

		customButton.setOnAction(e -> {
			CustomizationScreen.launchCustomizationScreen(scene);
		});

		primaryStage.setTitle("Morse Code Translator");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> System.exit(0));
	}

	private GridPane createButtonPane(TextField entry) {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(10);
		pane.setVgap(10);

		ArrayList<Button> buttons = new ArrayList<>();

		for (MorseCode code : MorseCode.values()) {
			Button button = new Button(code.getLetter() + ":  " + code.getCode());
			button.setAlignment(Pos.CENTER);
			button.setPrefSize(100, 100);
			button.setFont(Font.font(20));
			button.setOnAction(e -> {
				entry.setText(entry.getText() + " " + code.getCode());
			});

			buttons.add(button);
		}

		int row = 0;
		int col = 0;
		int counter = 0;

		while (counter < 26) {
			pane.add(buttons.get(counter), col, row);
			counter++;
			col++;
			if (col == 7) {
				col = 0;
				row++;
			}
		}

		return pane;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
