package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class CustomizationScreen {

	private ComboBox<String> cb;

	public CustomizationScreen() {
		this.cb = new ComboBox<>();
		loadInitialComboBox();
	}

	public void launchCustomizationScreen(Scene scene) {
		Stage stage = new Stage();

		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(10, 10, 10, 10));

		Label title = new Label("Customization Screen");
		title.setFont(Font.font(30));
		title.setUnderline(true);

		Label colorFieldLabel = new Label("Type in desired background color");
		colorFieldLabel.setFont(Font.font(20));
		TextField colorField = new TextField();
		colorField.setFont(Font.font(15));
		colorField.setAlignment(Pos.CENTER);

		Separator sep = new Separator();

		Label colorBoxLabel = new Label("Select desired background color");
		colorBoxLabel.setFont(Font.font(20));

		colorField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				startCustomization(scene, colorField);
			}
		});

		cb.setOnAction(e -> {
			String selected = cb.getSelectionModel().getSelectedItem();

			if (selected.equals("Default")) {
				scene.getRoot().setStyle("");
				colorField.setText("");
			} else {
				scene.getRoot().setStyle("-fx-base: " + selected);
				colorField.setText(selected);
			}

		});

		box.getChildren().addAll(title, colorFieldLabel, colorField, sep, colorBoxLabel, this.cb);

		stage.setScene(new Scene(box));
		stage.setTitle("Customization Screen");
		stage.show();

	}

	private void startCustomization(Scene scene, TextField colorField) {
		String text = colorField.getText().replaceAll(" ", "");; // Trim and have only one space

		if (text.equals("") || text.equalsIgnoreCase("default")) {
			scene.getRoot().setStyle("");
			this.cb.getSelectionModel().select("Default");
		} else {
			text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length());
			try {
				Color color = Color.valueOf(text); // Check if a color exists
				if (!checkColorLoaded(text)) {
					this.cb.getItems().add(text);
				}
				
				this.cb.getSelectionModel().select(text);
				
				scene.getRoot().setStyle("-fx-base: " + text);
			} catch (IllegalArgumentException e) {
				System.out.println("Not a valid color");
			}

		}

	}
	
	private boolean checkColorLoaded(String string) {
		for(String curr : cb.getItems()) {
			if (curr.equalsIgnoreCase(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The initial load of the ComboBox. First attempts to open and read the File
	 * object that is in the user's directory if it exists. If it doesn't, then
	 * loads the default list located in the source folder.
	 * 
	 * @param cb
	 */
	private void loadInitialComboBox() {
		File file = new File(System.getProperty("user.dir") + "//colors.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String color = scanner.nextLine();
				this.cb.getItems().add(color);

			}
		} catch (FileNotFoundException e) {
			InputStream is = getClass().getResourceAsStream("/colors.txt");
			scanner = new Scanner(is);
			while (scanner.hasNextLine()) {
				String color = scanner.nextLine();
				this.cb.getItems().add(color);
			}
		}

		scanner.close();
	}
}
