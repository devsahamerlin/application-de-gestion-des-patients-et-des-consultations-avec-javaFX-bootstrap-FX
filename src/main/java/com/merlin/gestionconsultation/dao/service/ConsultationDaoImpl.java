package com.merlin.gestionconsultation.dao.service;

import com.merlin.gestionconsultation.dao.DBConnection;
import com.merlin.gestionconsultation.entities.Consultation;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDaoImpl implements IConsultationDao {
    @Override
    public void create(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO CONSULTATIONS(DATE_CONSULTATION,DESCRIPTION,ID_PATIENT) VALUES (?,?,?)");

        preparedStatement.setDate(1, consultation.getDateConsultation());
        preparedStatement.setString(2, consultation.getDescription());
        preparedStatement.setLong(3, consultation.getPatient().getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE CONSULTATIONS SET DATE_CONSULTATION=?,DESCRIPTION=?,ID_PATIENT=? WHERE ID_CONSULTATION=?");

        preparedStatement.setDate(1, consultation.getDateConsultation());
        preparedStatement.setString(2, consultation.getDescription());
        preparedStatement.setLong(3, consultation.getPatient().getId());
        preparedStatement.setLong(4, consultation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CONSULTATIONS WHERE ID_CONSULTATION=?");
        preparedStatement.setLong(1, consultation.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Consultation findById(Long id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CONSULTATIONS WHERE ID_CONSULTATION=?");
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        Consultation consultation = new Consultation();

        if (resultSet.next()) {
            consultation.setId(resultSet.getLong("ID_CONSULTATION"));
            consultation.setDateConsultation(resultSet.getDate("DATE_CONSULTATION"));
            consultation.setDescription(resultSet.getString("DESCRIPTION"));
            consultation.setPatient(getPatient(resultSet.getLong("ID_PATIENT")));
        }

        return consultation;
    }

    @Override
    public List<Consultation> findAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CONSULTATIONS");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Consultation> consultations = new ArrayList<>();

        while (resultSet.next()) {
            Consultation consultation = new Consultation();
            consultation.setId(resultSet.getLong("ID_CONSULTATION"));
            consultation.setDateConsultation(resultSet.getDate("DATE_CONSULTATION"));
            consultation.setDescription(resultSet.getString("DESCRIPTION"));
            consultation.setPatient(getPatient(resultSet.getLong("ID_PATIENT")));
            consultations.add(consultation);
        }

        return consultations;
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CONSULTATIONS");
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Consultation> search(String criteria) throws SQLException {
        System.out.println("Consultation search criteria: " + criteria);

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CONSULTATIONS WHERE description LIKE ?");

        preparedStatement.setString(1, "%" + criteria + "%");
        ResultSet resultSet = preparedStatement.executeQuery();


        List<Consultation> consultations = new ArrayList<>();

        while (resultSet.next()) {
            Consultation consultation = new Consultation();
            consultation.setId(resultSet.getLong("ID_CONSULTATION"));
            consultation.setDateConsultation(resultSet.getDate("DATE_CONSULTATION"));
            consultation.setDescription(resultSet.getString("DESCRIPTION"));
            consultation.setPatient(getPatient(resultSet.getLong("ID_PATIENT")));
            consultations.add(consultation);
        }

        return consultations;
    }

    @Override
    public List<Consultation> getConsultationByPatient(Long id_patient) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM CONSULTATIONS WHERE ID_PATIENT=?");

        preparedStatement.setLong(1, id_patient);
        ResultSet resultSet = preparedStatement.executeQuery();


        List<Consultation> consultations = new ArrayList<>();

        while (resultSet.next()) {
            Consultation consultation = new Consultation();
            consultation.setId(resultSet.getLong("ID_CONSULTATION"));
            consultation.setDateConsultation(resultSet.getDate("DATE_CONSULTATION"));
            consultation.setDescription(resultSet.getString("DESCRIPTION"));
            consultation.setPatient(getPatient(resultSet.getLong("ID_PATIENT")));
            consultations.add(consultation);
        }

        return consultations;
    }

    @Override
    public Patient getPatient(Long id_patient) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM PATIENTS WHERE ID_PATIENT=?");
        preparedStatement.setLong(1, id_patient);

        ResultSet resultSet = preparedStatement.executeQuery();
        Patient patient = new Patient();

        if (resultSet.next()) {
            patient.setId(resultSet.getLong("ID_PATIENT"));
            patient.setNom(resultSet.getString("NOM"));
            patient.setPrenom(resultSet.getString("PRENOM"));
            patient.setDateNaissance(resultSet.getDate("DATE_NAISSANCE"));
            patient.setAdresse(resultSet.getString("ADRESSE"));
            patient.setTelephone(resultSet.getString("TELEPHONE"));
            patient.setEmail(resultSet.getString("EMAIL"));
        }

        System.out.println("Patient : " + patient);

        return patient;
    }
}
