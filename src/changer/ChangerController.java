package changer;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import observer.Observer;
import wishlist.Wishlist;

public class ChangerController {
	@FXML RadioButton rBclient;
	@FXML RadioButton rBserver;
	@FXML ToggleGroup startgroup;
	
	@FXML
	public void startProgram() {
		if(rBclient.isSelected()) {
			((Stage) rBclient.getScene().getWindow()).close();
			
			try {
				new Wishlist().start(new Stage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			((Stage) rBclient.getScene().getWindow()).close();
			
			try {
				new Observer().start(new Stage());
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
}
