package application;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

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

/*
 * @author Chris Li
 * @author Tony Lu
 */
public class LoginController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Scene preScene;
	private static String tempName;
	
	@FXML
	Button loginButton;
	
	@FXML
	TextField usernameInput;
	
	/*
	 * @param scene previous scene
	 */
	public void setPrescene(Scene tempScene) {
		this.preScene = tempScene;
	}
	
	@FXML
	public void switchToSubsystem(ActionEvent event) throws IOException {
		
		String enteredText = usernameInput.getText();
		boolean hasUser = false;
		
		FileInputStream file =  new FileInputStream("data/user.txt");
		
		//check if the user exist
		int ch;
		boolean firstComma = false;
		ArrayList<Character> charArrayList = new ArrayList<Character>();
	
			while ((ch = file.read()) != -1) {
				if (ch == ',' && firstComma == false) {
					firstComma = true;
					continue;
				} else if (ch == ','){
				StringBuilder builder = new StringBuilder(charArrayList.size());
				for(Character c: charArrayList) {
			        builder.append(c);
			    }
				if (builder.toString().equals(enteredText)) {
					hasUser = true;
					break;
				}
					charArrayList.clear();
				} else {
					charArrayList.add((char)ch);
				}
			}
	
	
			file.close();
	
			//for the last one
			StringBuilder builder = new StringBuilder(charArrayList.size());
			for(Character c: charArrayList) {
				builder.append(c);
			}
			if (builder.toString().equals(enteredText)) {
				hasUser = true;
			}
			charArrayList.clear();
			
			tempName = enteredText;
		
		//**********************************************//
		
		
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
		else if (hasUser) {
			//root = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
			if (preScene != null) {
				stage = (Stage) loginButton.getScene().getWindow();
				stage.setScene(preScene);
				stage.show();
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/User.fxml"));
	        scene = new Scene(fxmlLoader.load(), 640, 480);
	        UserController controller = fxmlLoader.getController();
	        controller.setUpUsername(enteredText);
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
	
	/*
	 * @return return the name of the user when logging in
	 */
	public static String getName() {
		return tempName;
	}
}