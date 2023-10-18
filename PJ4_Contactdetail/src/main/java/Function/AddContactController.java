package Function;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main_code.ContactController;

import java.util.Optional;

public class AddContactController {
    @FXML
    private TextField nomTextField, prenomTextField, dobTextField, adresseTextField, telTextField, emailTextField;

    private ContactController contactController;

    public void setContactFileController(ContactController controller) {
        this.contactController = controller;
    }

    @FXML
    private void saveAdd() {
        // Get the modified data from the input fields
        String addNom = nomTextField.getText();
        String addPrenom = prenomTextField.getText();
        String addDOB = dobTextField.getText();
        String addAdresse = adresseTextField.getText();
        String addTel = telTextField.getText();
        String addEmail = emailTextField.getText();

        if (!addNom.isEmpty() && !addPrenom.isEmpty() && !addDOB.isEmpty() && !addAdresse.isEmpty() && !addTel.isEmpty() && !addEmail.isEmpty()) {
            // Create a new contact
            Contact newContact = new Contact(addNom, addPrenom, addDOB, addAdresse, addTel, addEmail); // You can add tel and email here

            // Display a confirmation dialog
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Confirm Addition");
            confirmation.setContentText("Are you sure you want to add this contact?");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                // Add the new contact to the table view
                contactController.getTableStudent().getItems().add(newContact);

                // Clear the input fields
                nomTextField.clear();
                prenomTextField.clear();
                dobTextField.clear();
                adresseTextField.clear();
                telTextField.clear();
                emailTextField.clear();

                // Close the add contact scene
                Stage stage = (Stage) nomTextField.getScene().getWindow();
                stage.close();

                // Save the updated contact list to the file (You can call your save method here)
                contactController.saveContactInformation();

            }
        }

    }
}

