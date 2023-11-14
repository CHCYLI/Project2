package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Photos extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/View/FXMLFILES/Login.fxml"));
			Scene scene = new Scene(root,640,480);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("uPhotos");
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(event -> {
				//event.consume();
				quit(stage);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quit(Stage stage) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Quit?");
		alert.setHeaderText("Quit the uPhotos app?");
		alert.setContentText(":(");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			System.out.println("uPhotos closed successfully");
			stage.close();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	//create new username called stock
	//create new album
	//add stock photos to album
}