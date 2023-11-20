package application;


import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import Model.Album;
import Model.Photo;
import Model.User;

public class AlbumController {
	
	private Stage stage;
	private Scene scene;
	private Scene preScene;
	private Parent root;
	private User albumUser = UserController.user;
	private Album currAlbum; //contains index of selectedPhoto, and arraylist albumPhoto
	private Photo selectedPhoto; //contains name, cal, caption, tags
	private String selectedPath = "";
	
	@FXML
	ImageView imageView;
	
	@FXML
	MenuBar myMenuBar;
	
	//@FXML
	//Label debug;
	
	@FXML
	Label filenameDisplay;
	
	@FXML
	Label captionDisplay;
	
	@FXML
    //private ListView<String> photoList = new ListView<String>(albumUser.getPhotoNameList(UserController.goToAlbumName));
	private ListView<String> photoList;
	
	public void setPrescene(Scene tempScene) {
		this.preScene = tempScene;
	}
	
	@FXML
	public void initialize() throws IOException {
		albumUser = new User(LoginController.getName());
		photoList.setItems(currAlbum.getPhotoNameListByFile());
	}
	
	public void displaySelected() { //done
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
	
	public void getInfo(ActionEvent event) throws IOException {
		if (photoList.getSelectionModel().getSelectedItem().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR");
			alert.setHeaderText("No Photo Selected");
			alert.setContentText("Select a photo and try again.");
			alert.showAndWait();
			return;
		}
		
		//else
		selectedPath = photoList.getSelectionModel().getSelectedItem();
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Current Photo Information");
		alert.setHeaderText(selectedPath.substring(selectedPath.lastIndexOf("/")+1));
		alert.setContentText("(the name date caption and tags)");
		alert.showAndWait();
		//display name, date, caption, tags
	}
	
	public void returnToUser(ActionEvent event) throws IOException { //done
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Return?");
		alert.setHeaderText("Return to Album Selection?");
		alert.setContentText("Changes will be saved.");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			root = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
			stage = (Stage) myMenuBar.getScene().getWindow();
			scene = new Scene(root,640,480);
			stage.setScene(preScene);
			stage.show();
		}
	}
	
	public void help(ActionEvent event) { //done
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About This Page");
		alert.setHeaderText("Album Page");
		alert.setContentText("Here, you can add, tag, caption or delete photos, search for a specific photo, view a photo in a new window, or return to the previous page.");
		alert.showAndWait();
	}

	public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
         
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
                  
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            String path = file.toURI().toString();
            //System.out.println(path);
            
            //**commented out for debug**
            //Photo tempPhoto = new Photo(path.substring(path.lastIndexOf("/")+1), Calendar.getInstance(), "No caption", null);
            //UserController.user.addPhoto(currAlbum.getAlbumName(), tempPhoto);
            
            photoList.getItems().add(path);
            imageView.setImage(image);
            
            String selectedFileName = path.substring(path.lastIndexOf("/")+1);
            //System.out.println(path.lastIndexOf("/"));
            //System.out.println(selectedFileName);
	        //String selectedCaption = ...
	        
	        filenameDisplay.setText(selectedFileName);
	        //captionDisplay.setText...
        }
    }
	
	public void delFile(ActionEvent event) {
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Delete Photo");
		inputDialog.setHeaderText("Input Name");
		inputDialog.setContentText("Enter a caption for this photo:");
		Optional<String> nameInput = inputDialog.showAndWait();
		
		if(!nameInput.isPresent()) {
			return;
        }
		
		String name = nameInput.get();
		
        if (name.isEmpty()){
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delete Photo");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot delete a photo with no name!");
			alert.showAndWait();
			return;
		}
        
        if (!albumUser.createAlbum(name)){
			//System.out.println("name is empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delete Photo");
			alert.setHeaderText("Error");
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		}
		else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Photo");
			alert.setHeaderText("Confirm Deletion");
			alert.setContentText("Are you sure you want to delete this photo?");
			
			if(alert.showAndWait().get() == ButtonType.OK) {
				//delete photo in listview
				photoList.lookup(name); //...
				
				//delete photo from album
			}
		}
	}
	
	public void renameFile(ActionEvent event) {
		//...
	}
	
	public void caption(ActionEvent event) {
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Caption");
		inputDialog.setHeaderText("Add or Change Caption");
		inputDialog.setContentText("Enter a caption for this photo:");
		Optional<String> nameInput = inputDialog.showAndWait();
		
		if(!nameInput.isPresent()) {
			return;
        }
		
		//else
		String captionText = nameInput.get();
		selectedPhoto.setCaption(captionText);
		//captionDisplay.setText...
	}
	
	public void addTag(ActionEvent event) {
		Dialog<Pair<String, String>> tagDialog = new Dialog<>();
		tagDialog.setTitle("Add Tag");
		tagDialog.setHeaderText("Enter the type and content of the tag you'd like to add:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		tagDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane tagGrid = new GridPane();
		tagGrid.setHgap(20);
		tagGrid.setVgap(20);
		tagGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tagType = new TextField();
	    TextField tagName = new TextField();
	    
	    tagGrid.add(tagType, 0, 1);
	    tagGrid.add(new Label("Type"), 0, 0);
	    tagGrid.add(tagName, 1, 1);
	    tagGrid.add(new Label("Name"), 1, 0);
	    
	    tagDialog.getDialogPane().setContent(tagGrid);
	    
	    Button ok = (Button) tagDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredType = tagType.getText().strip();
	        String enteredName = tagName.getText().strip();
	        
	        if (enteredType.isEmpty() || enteredName.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("New Tag");
	            alert.setHeaderText("Empty Type or Name Entry");
	            alert.setContentText("Cannot have empty tags!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	        	selectedPhoto.addTag(enteredType,enteredName);
	        }
	    });
	    
	    tagDialog.showAndWait();
	}
	
	public void delTag(ActionEvent event) {
		//for loop to look for tag
		//if not present: errmsg
		//else: delete
		Dialog<Pair<String, String>> tagDialog = new Dialog<>();
		tagDialog.setTitle("Add Tag");
		tagDialog.setHeaderText("Enter the type and content of the tag you'd like to add:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		tagDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane tagGrid = new GridPane();
		tagGrid.setHgap(20);
		tagGrid.setVgap(20);
		tagGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tagType = new TextField();
	    TextField tagName = new TextField();
	    
	    tagGrid.add(tagType, 0, 1);
	    tagGrid.add(new Label("Type:"), 0, 0);
	    tagGrid.add(tagName, 1, 1);
	    tagGrid.add(new Label("Name:"), 1, 0);
	    
	    tagDialog.getDialogPane().setContent(tagGrid);
	    
	    Button ok = (Button) tagDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredType = tagType.getText().strip();
	        String enteredName = tagName.getText().strip();
	        
	        if (enteredType.isEmpty() || enteredName.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("New Tag");
	            alert.setHeaderText("Empty Type or Name Entry");
	            alert.setContentText("Cannot have empty tags!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	        	//look for tag
	        	
	    			//if not present: errmsg
	    		//else: 
	        		//selectedPhoto.deleteTag(enteredType,enteredName);
	        }
	    });
	    
	    tagDialog.showAndWait();
	}
	
	public void copy(ActionEvent event) { //menubar copy function
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Copy Photo");
		inputDialog.setHeaderText("Copy Photo");
		inputDialog.setContentText("Enter name for photo to copy...");
        
        Optional<String> nameInput = inputDialog.showAndWait();
        
        if(!nameInput.isPresent()) {
			return;
        }
        
        String name = nameInput.get();
		
        if (name.isEmpty()){
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Copy Photo");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot initialize an album with no name!");
			alert.showAndWait();
			return;
		}
        
        if (!albumUser.createAlbum(name)){
			//System.out.println("name is empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Copy Photo");
			alert.setHeaderText("Error");
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		}
		else {
			//implement method to copy photo (temp object?)
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Copy Photo");
			alert.setHeaderText("Success");
			alert.setContentText("Photo has been copied successfully.");
			alert.showAndWait();
			return;
		}
	}
	
	public void paste(ActionEvent event) {
		//check userPhotos.txt for duplicate
		if (true) { //add statement for check
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Paste");
			alert.setHeaderText("Error");
			alert.setContentText("Please check if the original album name is entered correctly!");
			alert.showAndWait();
			return;
		}
		//if not:paste path another album to userPhotos.txt
	}
	/*
	 * functionalities:
	 * context menu: del, caption curr photo, add/del tag of curr photo, copy/paste between albums
	 */
	
}