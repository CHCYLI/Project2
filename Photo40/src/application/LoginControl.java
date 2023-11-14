package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.Parent;


public class LoginControl extends Application {
	public static Stage STAGE;
	
	/*
	 * @param start
	 */
	public void start(Stage stage) throws IOException{
		STAGE = stage;
		//FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
		FXMLLoader fxmlLoader = new FXMLLoader(LoginControl.class.getResource("/View/FXMLFILES/Login.fxml"));
		Scene scene = new Scene(fxmlLoader.load(),400,400);
        stage.setTitle("Photos");
        stage.setScene(scene);
        stage.show();
	}
	
	
	/*
	 * @param start
	 */
	 public void start() throws IOException {
	        Stage stage = new Stage();
	        start(stage);
	    }
	 
	 
	/*
	* @param launch args 
	*/
	public static void main(String[] args) {
		launch(args);
	}
}
