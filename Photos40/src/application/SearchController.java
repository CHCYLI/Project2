package application;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Model.Album;
import Model.Photo;
import Model.User;

public class SearchController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private User albumUser = UserController.user;
	private String selectedPath = "";
	
	ObservableList<Photo> searchMatches;
	
	@FXML
	ImageView imageView;
	
	@FXML
	MenuBar myMenuBar;
	
	@FXML
	Label filenameDisplay;
	
	@FXML
	Label captionDisplay;
	
	@FXML
    //private ListView<String> photoList = new ListView<String>(albumUser.getPhotoNameList(UserController.goToAlbumName));
	private ListView<String> photoList;
	
	public void displaySelected() { //done?
		photoList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	selectedPath = photoList.getSelectionModel().getSelectedItem();
		        Image image = new Image(selectedPath);
		        imageView.setImage(image);
		        
		        String selectedFileName = selectedPath.substring(selectedPath.lastIndexOf("/")+1);
		        //String selected Caption = ...
		        
		        filenameDisplay.setText(selectedFileName);
		        //captionDisplay.setText...
		    }
		});
	}
	
	public void help(ActionEvent event) throws IOException { //done
		Alert alert = new Alert(AlertType.INFORMATION);
		//String albumName = xxx.get();
		alert.setTitle("About This Page");
		alert.setHeaderText("Search Results Page");
		alert.setContentText("Here are the results of your search query. You can choose to create a new album with them.");
		alert.showAndWait();
	}
	
	public void addAlbum(ActionEvent event) { //NEEDS WORK
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
			alert.setTitle("New Album");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot initialize an album with no name!");
			alert.showAndWait();
			return;
		}
        
        if (!albumUser.createAlbum(name)){
			//System.out.println("name is empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("New Album");
			alert.setHeaderText("Error");
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		}
		else {
			Album newSearchAlbum = new Album(name, albumUser.getUsername());
			//for loop: add photos in list
			//ArrayList<Album> albums in User
			//ArrayList<Photo> albumPhoto in Album
		}
	}
}