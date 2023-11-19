package application;


import java.io.File;
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

public class SearchController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private User albumUser = UserController.user;
	
	@FXML
	ImageView imageView;
	
	@FXML
	MenuBar myMenuBar;
	
	@FXML
    //private ListView<String> photoList = new ListView<String>(albumUser.getPhotoNameList(UserController.goToAlbumName));
	private ListView<String> photoList;
	
	public void help(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		//String albumName = xxx.get();
		alert.setTitle("About This Page");
		alert.setHeaderText("Search Results Page");
		alert.setContentText("Here are the results of your search query. You can choose to create a new album with them.");
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
            Photo tempPhoto = new Photo(path.substring(path.lastIndexOf("/")+1), Calendar.getInstance(), null, null);
            //temp calendar
            UserController.user.addPhoto(UserController.goToAlbumName, tempPhoto);
            photoList.getItems().add(path.substring(path.lastIndexOf("/")+1));
            //System.out.println(path.substring(path.lastIndexOf("/")+1));
            imageView.setImage(image);
        }
    }
}