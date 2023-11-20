package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.UnaryOperator;

import Model.Album;
import Model.Photo;
import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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
	private Scene preScene;
	private Parent root;
	public static User user;
	private Album selectedAlbum;
	private String username;
	
	@FXML
	MenuBar myMenuBar;
	
	@FXML
    private ListView<String> listOfAlbums;
	
	
	
	/*
	 * @param name name of user
	 */
	public void setUpUsername(String name) {
		//user = new User(name);
		username = name;
	}
	
	@FXML
	public void initialize() throws IOException {
		//System.out.println("Hello "+LoginController.getName());
		//System.out.println("Initialize " + user.getUsername());
		user = new User(LoginController.getName());
		listOfAlbums.setItems(user.getAlbumNameListByFile());
		//user = new User(User.getUsername());
	}
	
	/*
	 * @param event of showing one's username
	 */
	public void showUsername(ActionEvent e) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Username");
		alert.setHeaderText("Your Username");
		alert.setContentText("You are logged in as: " + username);
		alert.showAndWait();
	}
	
	/*
	 * @param previous scene
	 */
	public void setPrescene(Scene tempScene) {
		this.preScene = tempScene;
	}
	
	/*
	 * @param event of logging out
	 * @throws throws IOException
	 */
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
	
	/*
	 * @param event of using help
	 * @throws throws IOException
	 */
	public void help(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("About This Page");
		alert.setHeaderText("User Overview Page");
		alert.setContentText("Here, you can manage your albums, view the contents within one, or search for photos by date or tag.");
		alert.showAndWait();
	}
	
	/*
	 * @param event of creating album
	 * @throws throws IOException
	 */
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
			alert.setContentText("Please try a different name.");
			alert.showAndWait();
			return;
		}
		else {
			File f = new File("data/"+ username +"album.txt");
			if(!f.exists() && !f.isDirectory()) { 
				FileOutputStream createfile = new FileOutputStream("data/"+ username +"album.txt");
				createfile.close();
			}
			
			
			FileInputStream file = new FileInputStream("data/"+ username +"album.txt");
			int ch;
			
			FileOutputStream tempfile = new FileOutputStream("data/tempalbum.txt");
			while ((ch = file.read()) != -1) {
				tempfile.write(ch);
			}
			
			
			char[] tempArray = name.toCharArray();
			tempfile.write(',');
			for (int i = 0; i < tempArray.length; i++) {
				tempfile.write(tempArray[i]);
			}
			
			tempfile.close();
			file.close();
			
			File oldFile = new File("data/"+ username +"album.txt");
			oldFile.delete();
			
			FileInputStream tempUserFile = new FileInputStream("data/tempalbum.txt");
			FileOutputStream newfile = new FileOutputStream("data/"+ username +"album.txt");
			while ((ch = tempUserFile.read()) != -1) {
				newfile.write(ch);
			}
			
			tempUserFile.close();
			newfile.close();
			File ofile = new File ("data/tempalbum.txt");
			ofile.delete();
			initialize();
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
        	//String deletedName = listOfAlbums.getItems().remove(indexOfTargetAlbum);
        	
		 	FileInputStream file = new FileInputStream("data/album.txt");
			//String readText = file.read();
			int ch;
			int commaCount = 0;
			FileOutputStream tempfile = new FileOutputStream("data/tempalbum.txt");
			//listofnames.getItems().add(admin.usernameList().size());
			while ((ch = file.read()) != -1) {
				if (ch == ',') commaCount++;
				if (commaCount-1 != indexOfTargetAlbum)
				tempfile.write(ch);
			}
			
			tempfile.close();
			file.close();
			
			File oldFile = new File("data/album.txt");
			oldFile.delete();
			
			FileInputStream tempUserFile = new FileInputStream("data/tempalbum.txt");
			FileOutputStream newfile = new FileOutputStream("data/album.txt");
			while ((ch = tempUserFile.read()) != -1) {
				newfile.write(ch);
			}
			
			tempUserFile.close();
			newfile.close();
			File ofile = new File ("data/tempalbum.txt");
			ofile.delete();
			initialize();
        	
        	
        	
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
        
        String goToAlbumName = nameInput.get();
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
    		//TBI: navigate to correct album
        }
        //listOfAlbums = tempList;
        
	}
	
	public void searchByDate(ActionEvent event) throws IOException {
		Dialog<Pair<String, String>> searchDialog = new Dialog<>();
		searchDialog.setTitle("Search by Date");
		searchDialog.setHeaderText("Enter the date range of your search, formatted in YYYY/MM/DD:");
		
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
	        	//
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
	        	String fromDateStringForm = enteredFY + "/" + enteredFM + "/" + enteredFD;
	        	String toDateStringForm = enteredTY + "/" + enteredTM + "/" + enteredTD;
	        	//ObservableList<Photo> 
	        	
	        	//if no match found: errormsg, no match found, no new window
	    		//implement way to create new temporary list of photos to be shown in album.fxml
	    		try {
	    			ObservableList<Photo> newSearchList = user.searchByCal(fromDateStringForm,toDateStringForm);
	    			root = FXMLLoader.load(getClass().getResource("/View/Search.fxml"));
					
	    			//null exception
					//SearchController newSearchController = (new FXMLLoader(getClass().getResource("/View/Search.fxml"))).getController();
					//newSearchController.searchMatches = newSearchList;
					
					Stage newStage = new Stage();
		    		newStage.setTitle("uPhotos");
		    		scene = new Scene(root,640,480);
		    		newStage.setScene(scene);
		    		newStage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    
	    searchDialog.showAndWait();
	}
	
	public void searchByTag(ActionEvent event) throws IOException { //single tag
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
	    
	    searchDialog.getDialogPane().setContent(searchGrid);
	    
	    Button ok = (Button) searchDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredType = tagType.getText().strip();
	        String enteredName = tagName.getText().strip();
	        
	        if (enteredType.isEmpty() || enteredName.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Search by Tag");
	            alert.setHeaderText("Empty Type or Name Entry");
	            alert.setContentText("One or more of the required fields are left empty!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	    		try {
	    			ObservableList<Photo> newSearchList = user.searchByTags(enteredType, enteredName);
	    			
					root = FXMLLoader.load(getClass().getResource("/View/Search.fxml"));
					
					SearchController newSearchController = (new FXMLLoader(getClass().getResource("/View/Search.fxml"))).getController();
					newSearchController.searchMatches = newSearchList;
					
					Stage newStage = new Stage();
		    		newStage.setTitle("uPhotos");
		    		scene = new Scene(root,640,480);
		    		newStage.setScene(scene);
		    		newStage.show();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    
	    searchDialog.showAndWait();
	}
	
	public void searchByTagMultiple(ActionEvent event) throws IOException {
		Dialog<Pair<String, String>> searchDialog = new Dialog<>();
		searchDialog.setTitle("Search by Tag");
		searchDialog.setHeaderText("Enter the tag pair you want to search for:");
		
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		searchDialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);
		
		GridPane searchGrid = new GridPane();
		searchGrid.setHgap(20);
		searchGrid.setVgap(20);
		searchGrid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField tag1Type = new TextField();
	    TextField tag1Name = new TextField();
	    TextField tag2Type = new TextField();
	    TextField tag2Name = new TextField();
	    String andOr[] = {"AND", "OR"};
	    ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(andOr));
	    comboBox.setValue("AND");
	    
	    searchGrid.add(tag1Type, 0, 1);
	    searchGrid.add(new Label("Type"), 0, 0);
	    searchGrid.add(tag1Name, 1, 1);
	    searchGrid.add(new Label("Name"), 1, 0);
	    searchGrid.add(tag2Type, 0, 3);
	    searchGrid.add(new Label("Type"), 0, 2);
	    searchGrid.add(tag2Name, 1, 3);
	    searchGrid.add(new Label("Name"), 1, 2);
	    searchGrid.add(comboBox, 2, 1);
	    
	    searchDialog.getDialogPane().setContent(searchGrid);
	    
	    Button ok = (Button) searchDialog.getDialogPane().lookupButton(okButton);
	    ok.addEventFilter(ActionEvent.ACTION, mouseClickEvent -> {
	        String enteredType1 = tag1Type.getText().strip();
	        String enteredName1 = tag1Name.getText().strip();
	        String enteredType2 = tag2Type.getText().strip();
	        String enteredName2 = tag2Name.getText().strip();
	        String junctionType = (String) comboBox.getValue();
	        System.out.println(junctionType);
	        
	        if (enteredType1.isEmpty() || enteredName1.isEmpty()
	        		|| enteredType2.isEmpty() || enteredName2.isEmpty()) {
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Search by Tag");
	            alert.setHeaderText("Empty Type or Name Entry");
	            alert.setContentText("One or more of the required fields are left empty!");
	            alert.showAndWait();
	            event.consume(); // Prevent dialog from closing
	        }
	        else {
	    		try {
	    			ObservableList<Photo> newSearchList;
	    			if (junctionType.equals("AND")) {
	    				newSearchList = user.searchByTags(enteredType1, enteredName1, enteredType2, enteredName2, "AND");
	    			}
	    			else { //or
	    				newSearchList = user.searchByTags(enteredType1, enteredName1, enteredType2, enteredName2, "OR");
	    			}
	    			
					root = FXMLLoader.load(getClass().getResource("/View/Search.fxml"));
					
					//null exception
					//SearchController newSearchController = (new FXMLLoader(getClass().getResource("/View/Search.fxml"))).getController();
					//newSearchController.searchMatches = newSearchList;
					
					Stage newStage = new Stage();
		    		newStage.setTitle("uPhotos");
		    		scene = new Scene(root,640,480);
		    		newStage.setScene(scene);
		    		newStage.show();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
	    
	    searchDialog.showAndWait();
	}
	
}