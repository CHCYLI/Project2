package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Model.Admin;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.stage.Stage;

public class AdminController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Scene preScene;
	
	@FXML
	MenuBar myMenuBar;
	
	@FXML
	TextField username;
	
    @FXML
    private ListView<String> listofnames;
    
    final static Admin admin = new Admin("admin");
    
    public void setPrescene(Scene tempScene) {
    	this.preScene = tempScene;
    }
	
	public void logout(ActionEvent event) throws IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout?");
		alert.setHeaderText("Logout of your account?");
		alert.setContentText("Changes will be saved.");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			//root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			//stage = (Stage) myMenuBar.getScene().getWindow();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            scene = new Scene(fxmlLoader.load(), 640, 480);
            LoginController controller = fxmlLoader.getController();
    		controller.setPrescene(myMenuBar.getScene());
			
			
			stage = (Stage) myMenuBar.getScene().getWindow();
			stage.setScene(preScene);
			stage.show();
			//scene = new Scene(root);
			//stage.setScene(scene);
			//stage.show();
		}
	}
	
	public void help(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About This Page");
		alert.setHeaderText("Administrator Page");
		alert.setContentText("You can add/delete an user or list all users. To view albums, logout and login with a different account.");
		alert.showAndWait();
	}
	
	@FXML
	void createUser(MouseEvent event) throws IOException {
		String name = username.getText().strip();
		
		if (name.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot initialize a user with no name!");
			alert.showAndWait();
			return;
		}
		
		if (!admin.createUser(name)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Duplicate Name Entry");
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		} else {
			//System.out.println(admin.usernameList().size());
			listofnames.getItems().add(name);
			FileOutputStream file = new FileOutputStream("data/" + name+ ".txt");
			//listofnames.getItems().add(admin.usernameList().size());
			char[] tempArray = name.toCharArray();
			for (int i = 0; i < tempArray.length; i++) {
				file.write(tempArray[i]);
			}
			file.close();
		}
	}
	
	 @FXML
	    void deleteUser(MouseEvent event)throws Exception {
		 	int selectedUser = listofnames.getSelectionModel().getSelectedIndex();
		 	
		 	if (selectedUser == -1) {
		 		Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("No User Selected");
				alert.setContentText("Please Try Again.");
				alert.showAndWait();
				return;
		 	}
		 	//System.out.println(selectedUser);
		 	admin.deleteUser(selectedUser);
		 	
		 	String name = listofnames.getItems().remove(selectedUser);
		 	File file = new File("data/" + name + ".txt");
		 	file.delete();
	    }
	
	
	
}