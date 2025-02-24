package com.merlin.gestionconsultation.service;

import com.merlin.gestionconsultation.dao.service.ConsultationDaoImpl;
import com.merlin.gestionconsultation.dao.service.PatientDaoImpl;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.Date;
import java.util.List;

public class ServiceTest {
    public static void main(String[] args) {
        ICabinetService service = new CabinetService(new PatientDaoImpl(), new ConsultationDaoImpl());

//        Patient patient = new Patient();
//        patient.setNom("Jhon");
//        patient.setPrenom("Doe");
//        patient.setAdresse("CA, AVE");
//        patient.setTelephone("0123456789");
//        patient.setEmail("jhon@gmail.com");
//        patient.setDateNaissance(new Date(System.currentTimeMillis()));
//        service.addPatient(patient);

        Patient patient = service.getPatientById(1L);

        System.out.println(patient.toString());

        patient.setTelephone("0723456789");
        service.updatePatient(patient);

        List<Patient> patients = service.getAllPatients();
        System.out.println(patients.toString());
    }
}
