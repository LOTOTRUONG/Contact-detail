package main_code;

import Function.AddContactController;
import Function.Contact;
import Function.EditContactController;
import Function.PDFPrinter;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Optional;

public class ContactController {
    @FXML
    private TableView<Contact> tableStudent;
    @FXML
    private TableColumn<Contact,String> Nom;
    @FXML
    private TableColumn<Contact,String> Prenom;
    @FXML
    private TableColumn<Contact,String> DOB;
    @FXML
    private TextField searchFull;
    @FXML
    private Button printButton;

    @FXML
    private TextField nom1, prenom1, DOB1, Adresse1, Tel1, Email1;


    private File contactFile;
    private Contact selectedContact;
    public TableView<Contact> getTableStudent() {
        return tableStudent;
    }

    public void setContactFile(File contactFile) {
        this.contactFile = contactFile;
        // Load and display the contact information from the selected .contact file
        loadContactInformation();
    }
    private void loadContactInformation() {
        initialize();
        if (contactFile !=null) {
            try(BufferedReader reader = new BufferedReader(new FileReader(contactFile))) {
                String line;
                while ((line = reader.readLine()) !=null) {
                    String[] parts = line.split(",");
                    if(parts.length == 6) {
                        String nom = parts[0].trim();
                        String prenom = parts[1].trim();
                        String dob = parts[2].trim();
                        String adresse = parts[3].trim();
                        String tel = parts[4].trim();
                        String email = parts[5].trim();

                //create a new contact object and add it to the tableview
                        Contact contact = new Contact(nom, prenom, dob, adresse,tel,email);
                        tableStudent.getItems().add(contact);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                //handle any error that occur during while reading the file
            }
        }
    }

    private void initialize() {
        // Initialize TableView columns
        Nom.setCellValueFactory(new PropertyValueFactory<Contact, String>("Nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<Contact, String>("Prenom"));
        DOB.setCellValueFactory(new PropertyValueFactory<Contact, String>("DOB"));
        tableStudent.setOnMouseClicked(this::getItem); //Setup a mouse event handler to capture the selected item

        // Add an event handler to the "Print" button
        printButton.setOnAction(event -> {printTableToPDF();});
    }

    @FXML
    void getItem(MouseEvent event) {
        if (event.getClickCount() == 1) {
             selectedContact = tableStudent.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                // Set values from the selected Contact to the TextFields
                nom1.setText(selectedContact.getNom());
                prenom1.setText(selectedContact.getPrenom());
                DOB1.setText(selectedContact.getDOB());
                Adresse1.setText(selectedContact.getAdresse());
                Tel1.setText(selectedContact.getTel());
                Email1.setText(selectedContact.getEmail());
         }
     }
    }
    @FXML
    private void handleSearch() {
        //Wrap the obervablelist in the filteredlist (initially display all data)
        FilteredList<Contact> filteredData = new FilteredList<>(tableStudent.getItems(), p -> true);

        searchFull.textProperty().addListener((observable, oldValue, searchValue) -> {
        //Set the filter Predicate whenever the search text changes
        filteredData.setPredicate(contact -> {
            //if the search field is empty, display all contacts
            if (searchValue == null | searchValue.isEmpty()) {
                return true; }
            //convert the search value to lowercase to case-insensitive search
            String lowerCaseSearch = searchValue.toLowerCase();
            //check if the contact Nom or Prenom contain the search text
            if (contact.getNom().toLowerCase().contains(lowerCaseSearch)) {
                return true; // Filter matches nom.
            } else if (contact.getPrenom().toLowerCase().contains(lowerCaseSearch)) {
                return true; // Filter matches prenom.
            }
            return false; // Does not match.

            });
        });


        //wrap the filtered data in the Sortedlist
        SortedList<Contact> sortedData = new SortedList<>(filteredData);
        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(tableStudent.comparatorProperty());
        // Set the sorted and filtered data to the TableView
        tableStudent.setItems(sortedData);
    }

    private void printTableToPDF() {
        // Check if the TableView is empty
        if (tableStudent.getItems().isEmpty()) {
            Alert emptyTableAlert = new Alert(Alert.AlertType.WARNING);
            emptyTableAlert.setTitle("Print");
            emptyTableAlert.setHeaderText("Table is empty");
            emptyTableAlert.setContentText("There are no contacts to print.");
            emptyTableAlert.showAndWait();
            return;
        }

        // Prompt the user for the PDF file location
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialDirectory(new File("src/Print file"));
        File pdfFile = fileChooser.showSaveDialog(printButton.getScene().getWindow());

        if (pdfFile != null) {
            PDFPrinter.createPDF(tableStudent, pdfFile);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Print");
            successAlert.setHeaderText("Print Successful");
            successAlert.setContentText("The table has been printed to a PDF file.");
            successAlert.showAndWait();
        }

    }

    @FXML
    private void addContact() {
        // Open a dialog or scene for editing
        try {
            FXMLLoader addloader = new FXMLLoader(getClass().getResource("addContact.fxml"));
            Scene addScene = new Scene(addloader.load());
            AddContactController addContactController = addloader.getController();
            addContactController.setContactFileController(this);
            Stage editStage = new Stage();
            editStage.setTitle("Add New Contact");
            editStage.setScene(addScene);
            editStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Modify scene");
        }
    }

    @FXML
    private void modifyContact() {
            // Open a dialog or scene for editing
            try {
                //load modify scene
                FXMLLoader editloader = new FXMLLoader(getClass().getResource("editContact.fxml"));
                Scene editScene = new Scene(editloader.load());

                //get the controller of the modify scene
                EditContactController editContactController = editloader.getController();

                //pass the original data to the modify scene
                editContactController.setContactFileController(this);

                //set the original data in editcontactcontroller
                editContactController.setOriginalData(nom1.getText(), prenom1.getText(), DOB1.getText(), Adresse1.getText(), Tel1.getText(), Email1.getText());

                //create a new stage for the modify scene
                Stage editStage = new Stage();
                editStage.setTitle("Modify Contact Information");
                editStage.setScene(editScene);

                //show the modify scene
                editStage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading Modify scene");
        }
    }
    @FXML
    private void deleteContact() {
        if (selectedContact != null) {
            Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmation.setTitle("Delete");
            deleteConfirmation.setHeaderText("Confirmation the delete");
            deleteConfirmation.setContentText("Are you sure you want to delete this information?");

            Optional<ButtonType> result = deleteConfirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Clear the text fields
                nom1.clear();
                prenom1.clear();
                DOB1.clear();
                Adresse1.clear();
                Tel1.clear();
                Email1.clear();
                // Remove the selected contact from the table view
                tableStudent.getItems().remove(selectedContact);
                // Save the updated contact list to the file
                saveContactInformation();

            }
        }
    }
    public void updateContactInfo(String modifiedNom, String modifiedPrenom, String modifiedDOB, String modifiedAdresse, String modifiedTel, String modifiedEmail) {
        // Update the contact information in this scene
        nom1.setText(modifiedNom);
        prenom1.setText(modifiedPrenom);
        DOB1.setText(modifiedDOB);
        Adresse1.setText(modifiedAdresse);
        Tel1.setText(modifiedTel);
        Email1.setText(modifiedEmail);

        //update contact information for the table
       selectedContact = tableStudent.getSelectionModel().getSelectedItem();

        if(selectedContact != null) {
            //update selected contact with the modified values
            selectedContact.setNom(modifiedNom);
            selectedContact.setPrenom(modifiedPrenom);
            selectedContact.setDOB(modifiedDOB);
            // Refresh the TableView to reflect the changes
            tableStudent.refresh();
            // Save the changes to the original file
            saveContactInformation();
        }
    }

    public void saveContactInformation() {
        if (contactFile != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(contactFile))) {
                for (Contact contact : tableStudent.getItems()) {
                    // Write each contact to the file in the desired format
                    writer.write(contact.getNom() + "," + contact.getPrenom() + "," + contact.getDOB() + "," + contact.getAdresse() + "," + contact.getTel() + "," + contact.getEmail());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any errors that occur during file writing
            }
        }
    }



    //MENU BAR

 private void returnMain() {



 }

}
