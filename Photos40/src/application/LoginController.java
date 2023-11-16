package application;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	Button loginButton;
	
	@FXML
	TextField usernameInput;
	
	public void switchToSubsystem(ActionEvent event) throws IOException {
		
		String enteredText = usernameInput.getText();
		
		//if == admin: Admin subsystem
		if (enteredText.equals("admin")) {
			root = FXMLLoader.load(getClass().getResource("/View/Admin.fxml"));
		}
		else if (enteredText.equals("user")) {
			root = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("User Not Found");
			alert.setContentText("Enter a valid admin/regular username");
			alert.showAndWait();
			return;
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root,640,480);
		stage.setScene(scene);
		stage.show();
	}
}