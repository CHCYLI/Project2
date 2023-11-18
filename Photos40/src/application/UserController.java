package application;


import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import Model.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Pair;

public class UserController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	final User user = new User("user"); //temporary name
	@FXML
	MenuBar myMenuBar;
	
	@FXML
    private ListView<String> listOfAlbums;
	
	public void showUsername(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Username");
		alert.setHeaderText("Username");
		alert.setContentText("Your username is" + user.getUsername());
		alert.showAndWait();
	}
	
	public void logout(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout?");
		alert.setHeaderText("Logout of your account?");
		alert.setContentText("Changes will be saved.");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			stage = (Stage) myMenuBar.getScene().getWindow();
			scene = new Scene(root,640,480);
			stage.setScene(scene);
			stage.show();
		}
	}
	
	public void help(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About This Page");
		alert.setHeaderText("User Overview Page");
		alert.setContentText("Here, you can create new albums, rename existing ones, or delete albums altogether. Click on an album to view its contents.");
		alert.showAndWait();
	}
	
	public void createAlbum(ActionEvent event) throws IOException {
		
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("New Album");
		inputDialog.setHeaderText("New Album");
		inputDialog.setContentText("Enter name for new album...");
		//inputDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        Optional<String> nameInput = inputDialog.showAndWait();
        
        if(!nameInput.isPresent()) {
			return;
        }
        
        String name = nameInput.get();
        
        
		
        if (name.isEmpty()){
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot initialize an album with no name!");
			alert.showAndWait();
			return;
		}
        
        if (!user.createAlbum(name)  ){
			//System.out.println("name is empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Duplicate Name Entry");
			alert.setContentText("Please try different name.");
			alert.showAndWait();
			return;
		}
		else {
			//System.out.println(nameInput.get());
			//add new album to user's library
			listOfAlbums.getItems().add(name);
		}
	}
	
	public void deleteAlbum(ActionEvent event) throws IOException {
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Delete Album");
		inputDialog.setHeaderText("Delete Album");
		inputDialog.setContentText("Enter name of the album...");
		
        Optional<String> nameInput = inputDialog.showAndWait();
        
        if(!nameInput.isPresent()) {
			return;
        }
        
        String name = nameInput.get();
        
        if (name.isEmpty()){
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot delete an album with an empty name!");
			alert.showAndWait();
			return;
		}
        
        int indexOfTargetAlbum = user.getAlbumIndex(name);
        if (indexOfTargetAlbum == -1) {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("Invalid Name Entry");
			alert.setContentText("No album of that name exists!");
			alert.showAndWait();
			return;
        }
        
        if (user.deleteAlbum(name)) {
        	listOfAlbums.getItems().remove(indexOfTargetAlbum);
        	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Delete Album");
			alert.setHeaderText("Success");
			alert.setContentText("Album has been deleted successfully.");
			alert.showAndWait();
			return;
        }
        
        
        
	}
	
	public void renameAlbum(ActionEvent event) throws IOException {
		//if name match: ask for new name
		//else if nothing entered/nomatch: errormsg
		Dialog<Pair<String, String>> renameDialog = new Dialog<>();
		renameDialog.setTitle("Rename Album");
		renameDialog.setHeaderText("Enter the name of the album you want to change:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		renameDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane renameGrid = new GridPane();
		renameGrid.setHgap(20);
		renameGrid.setVgap(20);
		renameGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField oldAlbumName = new TextField();
	    TextField newAlbumName = new TextField();
	    
	    renameGrid.add(oldAlbumName, 0, 1);
	    renameGrid.add(new Label("Original Name"), 0, 0);
	    renameGrid.add(newAlbumName, 1, 1);
	    renameGrid.add(new Label("New Name"), 1, 0);
	    
	    renameDialog.getDialogPane().setContent(renameGrid);
	    
	    renameDialog.showAndWait();
		
		String oldName = oldAlbumName.getText().strip();
		String newName = newAlbumName.getText().strip();
		
		if (oldName.isEmpty() || newName.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Rename Album");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot rename an album to an empty name!");
			alert.showAndWait();
			return;
		}
		
		int albumIndex = user.getAlbumIndex(oldName);
		if (user.renameAlbum(oldName, newName)) {
			listOfAlbums.getItems().set(albumIndex, newName);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Rename Album");
			alert.setHeaderText("Error");
			alert.setContentText("Please check if the original album name is entered correctly!");
			alert.showAndWait();
			return;
		}
	}
	
	//TO BE DELETED
	public void test_goToAlbum(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/View/Album.fxml"));
		stage = (Stage) myMenuBar.getScene().getWindow();
		scene = new Scene(root,640,480);
		stage.setScene(scene);
		stage.show();
	}
}