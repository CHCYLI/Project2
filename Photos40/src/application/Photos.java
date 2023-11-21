package application;
	
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/*
 * @author Chris Li
 * @author Tony Lu
 */
public class Photos extends Application {
	@Override
	public void start(Stage stage) {
		String dataDir = System.getProperty("user.dir");
		//System.out.println(dataDir);
		dataDir = dataDir.replace("\\","/");
		dataDir = dataDir + "/data";
		System.out.println(dataDir);
		
		try {
			//START INITIALIZING STOCK
			PrintWriter writer = new PrintWriter("data/stockalbum.txt");
			writer.print(",stockAlbum");
			writer.close();
			
			PrintWriter writer2 = new PrintWriter("data/stockstockAlbumphoto.txt");
			String path1 = ",file:/" + dataDir + "/Bliss.jpg";
			String path2 = ",file:/" + dataDir + "/Hills.jpg";
			String path3 = ",file:/" + dataDir + "/Lilies.jpg";
			String path4 = ",file:/" + dataDir + "/Sunset.jpg";
			String path5 = ",file:/" + dataDir + "/Winter.jpg";
			writer2.print(path1 + path2 + path3 + path4 + path5);
			writer2.close();
			//FINISH INITIALIZING STOCK
			
			Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
			Scene scene = new Scene(root,640,480);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("uPhotos");
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(event -> {
				event.consume();
				quit(stage);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @param quitting the program
	 */
	public void quit(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit?");
		alert.setHeaderText("Quit the uPhotos app?");
		alert.setContentText(":(");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("uPhotos closed successfully");
			Platform.exit();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	//create new/reset username called stock
	//create new album
	//add stock photos to album
}