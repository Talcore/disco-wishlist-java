package changer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Changer extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("Changer.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Schuldisco");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
