package com.merlin.gestionconsultation.service;

import com.merlin.gestionconsultation.dao.service.IConsultationDao;
import com.merlin.gestionconsultation.dao.service.IPatientDao;
import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CabinetService implements ICabinetService{
    private static Logger logger = Logger.getLogger(CabinetService.class.getName());
    private IPatientDao patientDao;
    private IConsultationDao consultationDao;

    public CabinetService(IPatientDao patientDao, IConsultationDao consultationDao) {
        this.patientDao = patientDao;
        this.consultationDao = consultationDao;
    }

    @Override
    public void addPatient(Patient patient) {
        try {
            patientDao.create(patient);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updatePatient(Patient patient) {
        try {
            patientDao.update(patient);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void deletePatient(Patient patient) {
        try {
            patientDao.delete(patient);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients;
        try {
            patients = patientDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patients;
    }

    @Override
    public Patient getPatientById(Long id) {
        Patient patient = null;
        try {
            patient = patientDao.findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return patient;
    }

    @Override
    public void deleteAllPatients() {
        try {
            patientDao.deleteAll();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Patient> searchPatient(String search) {
        List<Patient> patients = List.of();
        try {
            patients = patientDao.search(search);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return patients;
    }

    @Override
    public List<Patient> searchPatientByQuery(String query) {
        List<Patient> patients = List.of();
        try {
            patients = patientDao.searchByQuery(query);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return patients;
    }

    @Override
    public void addConsultation(Consultation consultation) {
        try {
            consultationDao.create(consultation);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateConsultation(Consultation consultation) {
        try {
            consultationDao.update(consultation);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void deleteConsultation(Consultation consultation) {
        try {
            consultationDao.delete(consultation);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Consultation> getAllConsultations() {
        List<Consultation> consultations;
        try {
            consultations = consultationDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return consultations;
    }

    @Override
    public Consultation getConsultationById(Long id) {
        Consultation consultation = null;
        try {
            consultation = consultationDao.findById(id);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return consultation;
    }

    @Override
    public List<Consultation> getConsultationByPatient(Long id) {
        List<Consultation> consultations;
        try {
            consultations = consultationDao.getConsultationByPatient(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consultations;
    }
}
