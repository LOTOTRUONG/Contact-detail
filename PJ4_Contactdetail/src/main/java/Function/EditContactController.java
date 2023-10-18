package Function;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main_code.ContactController;

public class EditContactController {
    @FXML
    private TextField nomTextField, prenomTextField, dobTextField, adresseTextField, telTextField,emailTextField;

    private String originalNom, originalPrenom, originalDOB, originalAdresse, originalTel, originalEmail;
    private ContactController contactController;

    public void setContactFileController(ContactController controller) {
        this.contactController = controller;
    }

    @FXML
    private void saveChanges() {
        // Get the modified data from the input fields
        String modifiedNom = nomTextField.getText();
        String modifiedPrenom = prenomTextField.getText();
        String modifiedDOB = dobTextField.getText();
        String modifiedAdresse = adresseTextField.getText();
        String modifiedTel = telTextField.getText();
        String modifiedEmail = emailTextField.getText();

        //create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save file");
        alert.setHeaderText("Confirm save");
        alert.setContentText("Are you sure you want to save the changes");

        //Show the confirmation dialog and wait for the user's response
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            // User confirmed, save the changes

            // Update the contact information in the ContactFileSceneController
            contactController.updateContactInfo(modifiedNom,modifiedPrenom,modifiedDOB,modifiedAdresse,modifiedTel,modifiedEmail);

            // Close the Modify scene
            Stage stage = (Stage) nomTextField.getScene().getWindow();
            stage.close();
        }

    }


    public void setOriginalData(String nom, String prenom, String dob, String adresse, String tel, String email) {
        originalNom = nom;
        originalPrenom = prenom;
        originalDOB = dob;
        originalAdresse = adresse;
        originalTel = tel;
        originalEmail = email;

        // Populate the input fields with the original data
        nomTextField.setText(nom);
        prenomTextField.setText(prenom);
        dobTextField.setText(dob);
        adresseTextField.setText(adresse);
        telTextField.setText(tel);
        emailTextField.setText(email);
    }


}
