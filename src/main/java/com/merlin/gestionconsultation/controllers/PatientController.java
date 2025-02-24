package com.merlin.gestionconsultation.controllers;

import com.merlin.gestionconsultation.dao.service.ConsultationDaoImpl;
import com.merlin.gestionconsultation.dao.service.PatientDaoImpl;
import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;
import com.merlin.gestionconsultation.service.CabinetService;
import com.merlin.gestionconsultation.service.ICabinetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {
    @FXML
    private TextField textFieldNom, textFieldPrenom, textFieldAdresse, textFieldTelephone, textFieldEmail, textFieldRechercher;
    @FXML
    private DatePicker dateNaissancePicker;
    @FXML
    private TableView<Patient> tablePatients;
    @FXML
    private TableView<Consultation> tableConsultations;
    @FXML
    private TableColumn<Patient, Long> columnId;
    @FXML
    private TableColumn<Patient, String> columnNom, columnPrenom, columnAdresse, columnTelephone, columnEmail, columnDateNaissance;
    @FXML
    private TableColumn<Patient, String> columnConsultationId, columnDateConsultation, columnDescription;
    @FXML
    private Button ajouterBtn, modifierBtn, supprimerBtn, clearBtn, supprimerTousBtn;
    @FXML
    private Label labelSuccess;

    private ICabinetService cabinetService;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private final ObservableList<Consultation> consultations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableConsultations.setVisible(false);

        cabinetService = new CabinetService(new PatientDaoImpl(), new ConsultationDaoImpl());

        ajouterBtn.getStyleClass().add("btn-primary");
        modifierBtn.getStyleClass().add("btn-warning");
        supprimerBtn.getStyleClass().add("btn-danger");
        clearBtn.getStyleClass().add("btn-default");
        supprimerTousBtn.getStyleClass().add("btn-danger");
        tablePatients.getStyleClass().add("table-striped");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        columnPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        columnAdresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        columnTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnDateNaissance.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        columnConsultationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDateConsultation.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tablePatients.setItems(patients);
        loadPatients();

        textFieldRechercher.textProperty()
                .addListener((observableValue, oldValue, newValue) ->
                        handlesearchPatientByQuery(newValue));

        tablePatients.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                        handleSelection(newValue));
    }

    private void handlesearchPatientByQuery(String value) {
        try {
            patients.setAll(cabinetService.searchPatientByQuery(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSelection(Patient selectedPatient) {
        if (selectedPatient != null) {
            textFieldNom.setText(selectedPatient.getNom());
            textFieldPrenom.setText(selectedPatient.getPrenom());
            textFieldAdresse.setText(selectedPatient.getAdresse());
            textFieldTelephone.setText(selectedPatient.getTelephone());
            textFieldEmail.setText(selectedPatient.getEmail());

            if (selectedPatient.getDateNaissance() != null) {
                LocalDate dateNaissance = LocalDate.parse(selectedPatient.getDateNaissance().toString());
                dateNaissancePicker.setValue(dateNaissance);
            }
            tableConsultations.setItems(consultations);
            List<Consultation> list = cabinetService.getConsultationByPatient(selectedPatient.getId());
            System.out.println("Liste des consultations de "+selectedPatient.getNom() +" : " + list.size());
            consultations.setAll(cabinetService.getConsultationByPatient(selectedPatient.getId()));
            tableConsultations.setVisible(true);

        }
    }

    public void addPatient() {

        if (textFieldNom.getText() == null || textFieldPrenom.getText() == null ||
                textFieldAdresse.getText() == null || textFieldTelephone.getText() == null ||
                textFieldEmail.getText() == null || dateNaissancePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be completed.");
            return;
        }

        Patient patient = new Patient();
        patient.setNom(textFieldNom.getText());
        patient.setPrenom(textFieldPrenom.getText());
        patient.setAdresse(textFieldAdresse.getText());
        patient.setTelephone(textFieldTelephone.getText());
        patient.setEmail(textFieldEmail.getText());
        patient.setDateNaissance(Date.valueOf(LocalDate.parse(dateNaissancePicker.getValue().toString())));
        cabinetService.addPatient(patient);
        loadPatients();
        labelSuccess.setText("Patient "+patient.getNom() +" added");
        clearFormFields();
    }

    public void deletePatient() {
        Patient patient = tablePatients.getSelectionModel().getSelectedItem();
        cabinetService.deletePatient(patient);
        loadPatients();
        labelSuccess.setText("Patient "+patient.getNom() +" deleted");
        clearFormFields();
    }

    public void modifierPatient() {
        Patient selectedPatient = tablePatients.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "Select a patient", "Please select a patient to edit.");
            return;
        }

        if (textFieldNom.getText() == null || textFieldPrenom.getText() == null ||
                textFieldAdresse.getText() == null || textFieldTelephone.getText() == null ||
                textFieldEmail.getText() == null || dateNaissancePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be completed.");
            return;
        }

        selectedPatient.setNom(textFieldNom.getText());
        selectedPatient.setPrenom(textFieldPrenom.getText());
        selectedPatient.setAdresse(textFieldAdresse.getText());
        selectedPatient.setTelephone(textFieldTelephone.getText());
        selectedPatient.setEmail(textFieldEmail.getText());
        selectedPatient.setDateNaissance(Date.valueOf(LocalDate.parse(dateNaissancePicker.getValue().toString())));

        cabinetService.updatePatient(selectedPatient);
        loadPatients();
        labelSuccess.setText("Patient "+selectedPatient.getNom() +" modified");
        clearFormFields();
    }

    @FXML
    private void handleSupprimerTousPatients() {
        cabinetService.deleteAllPatients();
        loadPatients();
        labelSuccess.setText("All patients deleted in database");
    }

    @FXML
    private void handleRechercher() {
        if (textFieldRechercher.getText()==null || textFieldRechercher.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Search field should not be empty.");
            return;
        }
        tablePatients.setItems(patients);
        patients.setAll(cabinetService.searchPatient(textFieldRechercher.getText()));
    }

    @FXML
    private void clearFormFields() {
        textFieldNom.clear();
        textFieldPrenom.clear();
        dateNaissancePicker.setValue(null);
        textFieldEmail.clear();
        textFieldTelephone.clear();
        textFieldAdresse.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadPatients() {
        patients.setAll(cabinetService.getAllPatients());
    }


}
