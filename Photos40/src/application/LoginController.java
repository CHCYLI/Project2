package application;

import javafx.application.Application;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	//private Button loginButton;
	
	public void switchToSubsystem(ActionEvent event) throws IOException {
		//if == admin: Admin subsystem
		root = FXMLLoader.load(getClass().getResource("/View/FXMLFILES/User.fxml"));
		//else if == user: regular User subsystem
		//else: error msg
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root,640,480);
		stage.setScene(scene);
		stage.show();
	}
	
}
