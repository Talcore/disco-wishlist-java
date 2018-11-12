package wishlist;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Wishlist extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		Pane root = (Pane) FXMLLoader.load(getClass().getResource("Wishlist.fxml"));
		WishlistController.preload();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Schuldisco Wunschliste");
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	//STARTED FROM Changer (->Manager)
	/*public static void main(String[] args) {
		launch(args);
	}*/
}
