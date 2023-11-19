package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Model.Photo;
import Model.User;

public class AlbumController {
	
	private Stage stage;
	private Scene albumScene;
	private Parent root;
	private User user = UserController.user;
	private Scene preScene;
	
	@FXML
	ImageView imageView;
	
	@FXML
	MenuBar myMenuBar;
	
	@FXML
    //private ListView<String> photoList = new ListView<String>(albumUser.getPhotoNameList(UserController.goToAlbumName));
	private ListView<String> photoList;
	
	public void setPrescene(Scene tempScene) {
		this.preScene = tempScene; 
	}
	
	
	public void returnToUser(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Return?");
		alert.setHeaderText("Return to Album Selection?");
		alert.setContentText("Changes will be saved.");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			//root = FXMLLoader.load(getClass().getResource("/View/User.fxml"));
			stage = (Stage) myMenuBar.getScene().getWindow();
			//scene = new Scene(root,640,480);
			stage.setScene(preScene);
			stage.show();
		}
	}
	
	public void help(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		//String albumName = xxx.get();
		alert.setTitle("About This Page");
		alert.setHeaderText("Album Page");
		alert.setContentText("Here, you can add, rename or delete photos, search for a specific photo, ");
		alert.showAndWait();
	}
	
	
	public void openFile(ActionEvent event) throws FileNotFoundException {
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
            Photo tempPhoto = new Photo(file.getPath(), Calendar.getInstance(), null, null);
            //temp calendar
            user.addPhoto(UserController.goToAlbumName, tempPhoto);
            photoList.getItems().add(file.getPath());
            //photoList.getItems().add(path.substring(path.lastIndexOf("/")+1));
            //System.out.println(path.substring(path.lastIndexOf("/")+1));
            //File imageDir = new File("D:\\images\\");
            //FileInputStream in = new FileInputStream(path);
            //FileOutputStream out = new FileOutputStream("../data/" + path);
            if (!user.addPhoto(UserController.goToAlbumName, tempPhoto)) {
            	Alert alert = new Alert(AlertType.INFORMATION);
            	alert.setTitle("Error?");
        		alert.setHeaderText("Photo already exists");
        		alert.setContentText("Plase choose other pictures.");
            }
            imageView.setImage(image);
        }
    }
	
	
	
}