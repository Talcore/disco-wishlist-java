package observer;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Observer extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException, URISyntaxException {
		FXMLLoader fxmlloader = new FXMLLoader();
		Pane root = (Pane) fxmlloader.load(getClass().getResource("Observer.fxml").openStream());
		ObserverController.preload();
		ObserverController controller = fxmlloader.getController();
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Schuldisco Wunschliste");
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		Timeline updateTimer = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {

		    @Override
		    public void handle(ActionEvent event) {
		        controller.updateWishList();
		    }
		}));
		updateTimer.setCycleCount(Timeline.INDEFINITE);
		updateTimer.play();
	}

	//STARTED FROM Changer (->Manager)
	/*public static void main(String[] args) {
		launch(args);
	}*/
}
