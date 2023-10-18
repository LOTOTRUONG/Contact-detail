package main_code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primarystage) throws IOException {
           try {

               //load the FXML file
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            //Load the roat layout from the fxml file
               Parent root = myLoader.load();
               //set controller for the fxml file
               MainController controller = myLoader.getController();

               //Configure and display the primary stage
               Scene scene = new Scene(root);
               primarystage.setTitle("Contact Detail!");
               primarystage.setScene(scene);
               primarystage.show();

           } catch (IOException e) {
               e.printStackTrace();
           }
    }


    public static void main(String[] args) {
        launch();
    }
}