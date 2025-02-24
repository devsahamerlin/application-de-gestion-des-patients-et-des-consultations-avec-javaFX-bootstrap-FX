package com.merlin.gestionconsultation.controllers;

import com.merlin.gestionconsultation.dao.service.ConsultationDaoImpl;
import com.merlin.gestionconsultation.dao.service.PatientDaoImpl;
import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;
import com.merlin.gestionconsultation.service.CabinetService;
import com.merlin.gestionconsultation.service.ICabinetService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ConsultationController implements Initializable {
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private DatePicker dateConsultationPicker;
    @FXML
    private ComboBox<Patient> comboboxPatient;
    @FXML
    private Button ajouterBtn, supprimerBtn;
    @FXML
    private TableColumn<Consultation, String> columnId, columnDateConsultation, columnDescription, columnPatient;
    @FXML
    private TableView<Consultation> tableConsultations;
    @FXML
    private Label labelSuccess;
    private ICabinetService cabinetService;
    private final ObservableList<Patient> patients = FXCollections.observableArrayList();
    private final ObservableList<Consultation> consultations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ajouterBtn.getStyleClass().add("btn-primary");
        supprimerBtn.getStyleClass().add("btn-danger");

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnDateConsultation.setCellValueFactory(new PropertyValueFactory<>("dateConsultation"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnPatient.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPatient())));

        cabinetService = new CabinetService(new PatientDaoImpl(), new ConsultationDaoImpl());
        comboboxPatient.setItems(patients);
        patients.setAll(cabinetService.getAllPatients());

        tableConsultations.setItems(consultations);
        loadConsultations();

        tableConsultations.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelection(newValue)
        );
    }

    private void handleSelection(Consultation selectedConsultation) {
        if (selectedConsultation != null) {
            textAreaDescription.setText(selectedConsultation.getDescription());
            comboboxPatient.setValue(selectedConsultation.getPatient());
            if (selectedConsultation.getDateConsultation() != null) {
                LocalDate dateConsultaion = LocalDate.parse(selectedConsultation.getDateConsultation().toString());
                dateConsultationPicker.setValue(dateConsultaion);
            }
        }
    }

    public void addConsultation() {

        if (textAreaDescription.getText() == null || comboboxPatient.getSelectionModel().getSelectedItem() == null
        || dateConsultationPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be completed.");
            return;
        }

        Consultation consultation = new Consultation();
        consultation.setDescription(textAreaDescription.getText());
        consultation.setPatient(comboboxPatient.getSelectionModel().getSelectedItem());
        consultation.setDateConsultation(Date.valueOf(LocalDate.parse(dateConsultationPicker.getValue().toString())));
        cabinetService.addConsultation(consultation);
        loadConsultations();
        labelSuccess.setText("Consultation added");
    }

    public void deletePatient() {
        Consultation consultation = tableConsultations.getSelectionModel().getSelectedItem();
        cabinetService.deleteConsultation(consultation);
        loadConsultations();
        labelSuccess.setText(consultation.getPatient() +" consultation deleted");
        clearFormFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadConsultations() {
        consultations.setAll(cabinetService.getAllConsultations());
    }

    @FXML
    private void clearFormFields() {
        textAreaDescription.clear();
        dateConsultationPicker.setValue(null);
        comboboxPatient.setValue(null);
    }
}
