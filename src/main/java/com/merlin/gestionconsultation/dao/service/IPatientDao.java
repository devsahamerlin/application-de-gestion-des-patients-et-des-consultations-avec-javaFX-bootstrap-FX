package com.merlin.gestionconsultation.dao.service;

import com.merlin.gestionconsultation.dao.Dao;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.SQLException;
import java.util.List;

public interface IPatientDao extends Dao<Patient, Long> {
    List<Patient> searchByQuery(String query) throws SQLException;
}
