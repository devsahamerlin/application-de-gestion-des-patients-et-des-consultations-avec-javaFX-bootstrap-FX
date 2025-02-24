package com.merlin.gestionconsultation.dao.service;

import com.merlin.gestionconsultation.dao.Dao;
import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IConsultationDao extends Dao<Consultation, Long> {
    List<Consultation> getConsultationByPatient(Long id_patient) throws SQLException;
    Patient getPatient(Long id_patient) throws SQLException;
}
