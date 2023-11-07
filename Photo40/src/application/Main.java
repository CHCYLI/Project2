package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.Parent;


public class Main extends Application {
	public static Stage STAGE;
	@Override
	public void start(Stage stage) throws IOException{
		/*try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		STAGE = stage;
		//FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
		Scene scene = new Scene(fxmlLoader.load(),400,400);
        stage.setTitle("Photos");
        stage.setScene(scene);
        stage.show();
	}
	
	 public void start() throws IOException {
	        Stage stage = new Stage();
	        start(stage);
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}
