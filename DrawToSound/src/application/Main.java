package application;	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import application.GUI_Controller;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI.fxml"));
			Parent root = (Parent)loader.load();
			root.getStylesheets().add("GUI.css");
			stage.setTitle("DrawToSound");
			stage.setScene(new Scene(root));
			GUI_Controller controller = (GUI_Controller) loader.getController();
			//stage.setOnCloseRequest(e -> controller.beendeTimer());
			stage.show();
			controller.setStage(stage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}