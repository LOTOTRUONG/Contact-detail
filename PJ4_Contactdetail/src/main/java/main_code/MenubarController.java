package main_code;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MenubarController {

    @FXML
    private Menu Recentfiles;

    @FXML
    private AnchorPane mainPane;

    private List<File> recentContactFiles = new ArrayList<>();


    @FXML
    public void openContactFile() {
        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open .contact File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Contact Files", "*.contact"));

        // Set the initial directory to the folder where your .contact files are located
        fileChooser.setInitialDirectory(new File("src/Contact file"));

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {
            // Process the selected .contact file
            openContactFileScene(selectedFile);

            // Add the path of the selected file to the recentFiles list
            addRecentContactFile(selectedFile);
        }
    }


    private void addRecentContactFile(File contactFile) {
        // Add a recent .contact file to the list (limit the number of recent files as needed)
        recentContactFiles.add(contactFile);
        updateRecentFilesMenu();
    }

    private void updateRecentFilesMenu() {
        // Clear the menu items
        Recentfiles.getItems().clear();

        // Add recent .contact file menu items
        for (File contactFile : recentContactFiles) {
            MenuItem menuItem = new MenuItem(contactFile.getName());
            menuItem.setOnAction(this::openRecentContactFile);
            Recentfiles.getItems().add(menuItem);
        }
    }

    private void openRecentContactFile(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String filename = menuItem.getText();
        File selectedContactFile = null;

        // Find the corresponding File object for the selected recent .contact file
        for (File contactFile : recentContactFiles) {
            if (contactFile.getName().equals(filename)) {
                selectedContactFile = contactFile;
                break;
            }
        }

        if (selectedContactFile != null) {
            // Create a new scene to display the selected contact information
            openContactFileScene(selectedContactFile);
        }
    }

    private void openContactFileScene(File contactFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("contact-view.fxml"));
            Parent root = loader.load();

            //Acess the controler of the loader FXML
            ContactController contactController = loader.getController();

            //Pass the selected contact file to the controller of the new scene
            contactController.setContactFile(contactFile);

            //create a new stage for the scene
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.DECORATED);
            newStage.setTitle("Contact Information");

            //set the scene with the loader FXML
            Scene scene = new Scene(root);
            newStage.setScene(scene);

            //show the new Scene in the separate window
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            //Handle any errors that occur while loading the scene
        }
    }

    @FXML
    private void exitApplication() {
        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        }
        else {
            // Handle the case where the stage reference is not available
            showErrorAlert("Unable to exit application", "The main stage reference is not available.");
        }
    }
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }

}