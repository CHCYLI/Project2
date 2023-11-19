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
	private Scene preScene;
	
	@FXML
	Button loginButton;
	
	@FXML
	TextField usernameInput;
	
	public void setPrescene(Scene tempScene) {
		this.preScene = tempScene;
	}
	
	public void switchToSubsystem(ActionEvent event) throws IOException {
		
		String enteredText = usernameInput.getText();
		
		//if == admin: Admin subsystem
		if (enteredText.equals("admin")) {
			//root = FXMLLoader.load(getClass().getResource("/View/Admin.fxml"));
			if (preScene != null) {
				stage = (Stage) loginButton.getScene().getWindow();
				stage.setScene(preScene);
				stage.show();
			}
			
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Admin.fxml"));
	        scene = new Scene(fxmlLoader.load(), 640, 480);
	        AdminController controller = fxmlLoader.getController();
			controller.setPrescene(loginButton.getScene());
			
		}
		else if (enteredText.equals("user")) {
			//root = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
			if (preScene != null) {
				stage = (Stage) loginButton.getScene().getWindow();
				stage.setScene(preScene);
				stage.show();
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
	        scene = new Scene(fxmlLoader.load(), 640, 480);
	        UserController controller = fxmlLoader.getController();
			controller.setPrescene(loginButton.getScene());
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("User Not Found");
			alert.setContentText("Enter a valid admin/regular username");
			alert.showAndWait();
			return;
		}
		
		
		stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);
		stage.show();
		
		//stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		//scene = new Scene(root,640,480);
		
	}
}