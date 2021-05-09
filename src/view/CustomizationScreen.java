package view;

import java.io.File;
import java.io.FileNotFoundException;
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

	public static void launchCustomizationScreen(Scene scene) {
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

		ComboBox<String> cb = new ComboBox<>();

		colorField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				startCustomization(scene, cb, colorField);
			}
		});

		box.getChildren().addAll(title, colorFieldLabel, colorField, sep, colorBoxLabel, cb);

		stage.setScene(new Scene(box));
		stage.setTitle("Customization Screen");
		stage.show();

	}

	private static void startCustomization(Scene scene, ComboBox<String> cb, TextField colorField) {

		String text = colorField.getText().replaceAll(" ", "");
		text = text.substring(0, 1).toUpperCase() + text.substring(1, text.length());

		if (text.equalsIgnoreCase("default")) {
			scene.getRoot().setStyle("");
		} else {

			try {
				Color color = Color.valueOf(text); // Check if a color exists
				scene.getRoot().setStyle("-fx-base: " + text);
				if (!cb.getItems().contains(text)) {
					cb.getItems().add(text);
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Not a valid color");
			}

		}

	}
	
	private static void loadComboBox(ComboBox<String> cb) {
		File file = new File(System.getProperty("user.dir") + "//colors.txt");
		try {
			Scanner scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void loadInitialComboBox(ComboBox<String> cb) {
		
	}

}
