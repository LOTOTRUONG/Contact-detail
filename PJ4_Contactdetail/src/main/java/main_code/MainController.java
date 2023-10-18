package main_code;

import javafx.fxml.FXML;


public class MainController {

    private final MenubarController menubarController = new MenubarController();


    @FXML
    private void openContactFile() {
        menubarController.openContactFile();


    }
}