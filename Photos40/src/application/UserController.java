package application;


import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.UnaryOperator;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
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
	public Scene scene;
	private Parent root;
	public static User user = new User("user"); //temporary name
	public static String goToAlbumName;
	private Scene preScene;
	@FXML
	MenuBar myMenuBar;
	
	@FXML
    private ListView<String> listOfAlbums = new ListView<String>(user.getAlbumNameList());
	
	public void showUsername(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Username");
		alert.setHeaderText("Username");
		alert.setContentText("Your username is" + user.getUsername());
		alert.showAndWait();
	}
	
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
			//scene = new Scene(root,640,480);
			//stage.setScene(scene);
			//stage.show();
			//stage = (Stage) myMenuBar.getScene().getWindow();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            scene = new Scene(fxmlLoader.load(), 640, 480);
            LoginController controller = fxmlLoader.getController();
    		controller.setPrescene(myMenuBar.getScene());
            //stage.setScene(scene);
    		//stage.show();
    		
    		
			stage = (Stage) myMenuBar.getScene().getWindow();
			stage.setScene(preScene);
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
			alert.setTitle("New Album");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot initialize an album with no name!");
			alert.showAndWait();
			return;
		}
        
        if (!user.createAlbum(name)){
			//System.out.println("name is empty");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("New Album");
			alert.setHeaderText("Error");
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
			alert.setTitle("Delete Album");
			alert.setHeaderText("Empty Name Entry");
			alert.setContentText("Cannot delete an album with an empty name!");
			alert.showAndWait();
			return;
		}
        
        int indexOfTargetAlbum = user.getAlbumIndex(name);
        if (indexOfTargetAlbum == -1) {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Delete Album");
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
	    
	    Button ok = (Button) renameDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String oldName = oldAlbumName.getText().strip();
	        String newName = newAlbumName.getText().strip();
	        
	        if (oldName.isEmpty() || newName.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Rename Album");
	            alert.setHeaderText("Empty Name Entry");
	            alert.setContentText("Cannot rename an album to an empty name!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
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
	    });
	    
	    renameDialog.showAndWait();
	}
	
	//TO BE DELETED
	public void goToAlbum(ActionEvent event) throws IOException {
		//get album name, then go to specific album
		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Choose Album");
		inputDialog.setHeaderText("Album Opener");
		inputDialog.setContentText("Enter name of the album you want to access...");
		//inputDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        Optional<String> nameInput = inputDialog.showAndWait();
        
        if(!nameInput.isPresent()) {
			return;
        }
        
        goToAlbumName = nameInput.get();
        //ListView<String> tempList = listOfAlbums;
        if (user.getAlbumByName(goToAlbumName) == null) {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Enter Album");
			alert.setHeaderText("Error");
			alert.setContentText("Please check if the album name is entered correctly!");
			alert.showAndWait();
			return;
        } else {
    		stage = (Stage) myMenuBar.getScene().getWindow();
    		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Album.fxml"));
            scene = new Scene(fxmlLoader.load(), 640, 480);
            AlbumController controller = fxmlLoader.getController();
    		controller.setPrescene(myMenuBar.getScene());
            stage.setScene(scene);
    		stage.show();
    		
        }
        //listOfAlbums = tempList;
        
	}
	
	public void searchByDate(ActionEvent event) throws IOException {
		Dialog<Pair<String, String>> searchDialog = new Dialog<>();
		searchDialog.setTitle("Search by Date");
		searchDialog.setHeaderText("Enter the date range of your search:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		searchDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane searchGrid = new GridPane();
		searchGrid.setHgap(20);
		searchGrid.setVgap(20);
		searchGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField fromYear = new TextField();
	    TextField toYear = new TextField();
	    TextField fromMonth = new TextField();
	    TextField toMonth = new TextField();
	    TextField fromDay = new TextField();
	    TextField toDay = new TextField();
	    
	    //code to filter text
    	UnaryOperator<TextFormatter.Change> numFilter = change -> {
    	    String text = change.getText();

    	    if (text.matches("[0-9]*")) {
    	        return change; // Allow only numeric text
    	    }

    	    return null; // Reject non-numeric text
    	};

    	fromYear.setTextFormatter(new TextFormatter<>(numFilter));
    	fromMonth.setTextFormatter(new TextFormatter<>(numFilter));
    	fromDay.setTextFormatter(new TextFormatter<>(numFilter));
    	toYear.setTextFormatter(new TextFormatter<>(numFilter));
    	toMonth.setTextFormatter(new TextFormatter<>(numFilter));
    	toDay.setTextFormatter(new TextFormatter<>(numFilter));
	    
	    searchGrid.add(fromYear, 0, 1);
	    searchGrid.add(new Label("Start year"), 0, 0);
	    searchGrid.add(toYear, 1, 1);
	    searchGrid.add(new Label("End year"), 1, 0);
	    searchGrid.add(fromMonth, 0, 3);
	    searchGrid.add(new Label("Start month"), 0, 2);
	    searchGrid.add(toMonth, 1, 3);
	    searchGrid.add(new Label("End month"), 1, 2);
	    searchGrid.add(fromDay, 0, 5);
	    searchGrid.add(new Label("Start day"), 0, 4);
	    searchGrid.add(toDay, 1, 5);
	    searchGrid.add(new Label("End day"), 1, 4);
	    
	    searchDialog.getDialogPane().setContent(searchGrid);
	    
	    Button ok = (Button) searchDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredFY = fromYear.getText().strip();
	        String enteredFM = fromMonth.getText().strip();
	        String enteredFD = fromDay.getText().strip();
	        String enteredTY = toYear.getText().strip();
	        String enteredTM = toMonth.getText().strip();
	        String enteredTD = toDay.getText().strip();
	        
	        if (enteredFY.isEmpty() || enteredFM.isEmpty() || enteredFD.isEmpty()
	        		|| enteredTY.isEmpty() || enteredTM.isEmpty() || enteredTD.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Search by Date");
	            alert.setHeaderText("Empty Date Entry");
	            alert.setContentText("One or more of the required fields are left empty!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	        	if (enteredFY.length()!=4 || enteredFM.length()!=2 || enteredFD.length()!=2
		        		|| enteredFY.length()!=4 || enteredFM.length()!=2 || enteredFD.length()!=2) {
	        		Alert alert = new Alert(AlertType.ERROR);
		            alert.setTitle("Search by Date");
		            alert.setHeaderText("Invalid Date Entry");
		            alert.setContentText("Please try again.");
		            alert.showAndWait();
		            return;
	        	}
	        	//implement way to convert date -> calendar
	        	//if no match found: errormsg, no match found, no new window
	    		//implement way to create new temporary list of photos to be shown in album.fxml
	    		try {
					root = FXMLLoader.load(getClass().getResource("/View/Search.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		Stage newStage = new Stage();
	    		newStage.setTitle("uPhotos");
	    		scene = new Scene(root,640,480);
	    		newStage.setScene(scene);
	    		newStage.show();
	        }
	    });
	    
	    searchDialog.showAndWait();
	}
	
	public void searchByTag(ActionEvent event) throws IOException {
		Dialog<Pair<String, String>> searchDialog = new Dialog<>();
		searchDialog.setTitle("Search by Tag");
		searchDialog.setHeaderText("Enter the tag pair you want to search for:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		searchDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane searchGrid = new GridPane();
		searchGrid.setHgap(20);
		searchGrid.setVgap(20);
		searchGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tagType = new TextField();
	    TextField tagName = new TextField();
	    
	    searchGrid.add(tagType, 0, 1);
	    searchGrid.add(new Label("Type"), 0, 0);
	    searchGrid.add(tagName, 1, 1);
	    searchGrid.add(new Label("Name"), 1, 0);
	    searchGrid.add(tagType, 0, 1);
	    
	    searchDialog.getDialogPane().setContent(searchGrid);
	    
	    Button ok = (Button) searchDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredType = tagType.getText().strip();
	        String enteredName = tagName.getText().strip();
	        
	        if (enteredType.isEmpty() || enteredName.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Search by Tag");
	            alert.setHeaderText("Empty Type or Name Entry");
	            alert.setContentText("Cannot have empty tags!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	        	//if no match found: errormsg, no match found, no new window
	    		//implement way to create new temporary list of photos to be shown in album.fxml
	        	
	    		try {
					root = FXMLLoader.load(getClass().getResource("/View/Search.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		Stage newStage = new Stage();
	    		newStage.setTitle("uPhotos");
	    		scene = new Scene(root,640,480);
	    		newStage.setScene(scene);
	    		newStage.show();
	        }
	    });
	    
	    searchDialog.showAndWait();
	}
	
}