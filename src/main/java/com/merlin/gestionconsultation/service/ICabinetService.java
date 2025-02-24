package com.merlin.gestionconsultation.service;

import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;

import java.util.List;

public interface ICabinetService {
    void addPatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatient(Patient patient);
    List<Patient> getAllPatients();
    Patient getPatientById(Long id);
    void deleteAllPatients();
    List<Patient> searchPatient(String search);
    List<Patient> searchPatientByQuery(String query);


    void addConsultation(Consultation consultation);
    void updateConsultation(Consultation consultation);
    void deleteConsultation(Consultation consultation);
    List<Consultation> getAllConsultations();
    Consultation getConsultationById(Long id);
    List<Consultation> getConsultationByPatient(Long id);
}
