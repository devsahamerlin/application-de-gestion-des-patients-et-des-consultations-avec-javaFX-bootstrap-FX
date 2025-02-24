package com.merlin.gestionconsultation.dao.service;

import com.merlin.gestionconsultation.dao.DBConnection;
import com.merlin.gestionconsultation.entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDaoImpl implements IPatientDao {
    @Override
    public void create(Patient patient) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO PATIENTS(NOM,PRENOM,DATE_NAISSANCE,ADRESSE,TELEPHONE, EMAIL) VALUES (?,?,?,?,?,?)");

        preparedStatement.setString(1, patient.getNom());
        preparedStatement.setString(2, patient.getPrenom());
        preparedStatement.setDate(3, patient.getDateNaissance());
        preparedStatement.setString(4, patient.getAdresse());
        preparedStatement.setString(5, patient.getTelephone());
        preparedStatement.setString(6, patient.getEmail());

        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Patient patient) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE PATIENTS SET NOM=?,PRENOM=?,DATE_NAISSANCE=?,ADRESSE=?,TELEPHONE=?,EMAIL=? WHERE ID_PATIENT=?");

        preparedStatement.setString(1, patient.getNom());
        preparedStatement.setString(2, patient.getPrenom());
        preparedStatement.setDate(3, patient.getDateNaissance());
        preparedStatement.setString(4, patient.getAdresse());
        preparedStatement.setString(5, patient.getTelephone());
        preparedStatement.setString(6, patient.getEmail());
        preparedStatement.setLong(7, patient.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Patient patient) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PATIENTS WHERE ID_PATIENT=?");
        preparedStatement.setLong(1, patient.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PATIENTS");
        preparedStatement.executeUpdate();
    }


    @Override
    public List<Patient> search(String criteria) throws SQLException {

        System.out.println("Patient search criteria: " + criteria);

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM PATIENTS WHERE NOM LIKE ?");

        preparedStatement.setString(1, "%" + criteria + "%");
        ResultSet resultSet = preparedStatement.executeQuery();


        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setId(resultSet.getLong("ID_PATIENT"));
            patient.setNom(resultSet.getString("NOM"));
            patient.setPrenom(resultSet.getString("PRENOM"));
            patient.setDateNaissance(resultSet.getDate("DATE_NAISSANCE"));
            patient.setAdresse(resultSet.getString("ADRESSE"));
            patient.setTelephone(resultSet.getString("TELEPHONE"));
            patient.setEmail(resultSet.getString("EMAIL"));
            patients.add(patient);
        }

        return patients;
    }

    @Override
    public Patient findById(Long id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM PATIENTS WHERE ID_PATIENT=?");
        preparedStatement.setLong(1, id);

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

        return patient;
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM PATIENTS");
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setId(resultSet.getLong("ID_PATIENT"));
            patient.setNom(resultSet.getString("NOM"));
            patient.setPrenom(resultSet.getString("PRENOM"));
            patient.setDateNaissance(resultSet.getDate("DATE_NAISSANCE"));
            patient.setAdresse(resultSet.getString("ADRESSE"));
            patient.setTelephone(resultSet.getString("TELEPHONE"));
            patient.setEmail(resultSet.getString("EMAIL"));
            patients.add(patient);
        }

        return patients;
    }

    @Override
    public List<Patient> searchByQuery(String query) throws SQLException {
        System.out.println("Patient search query: " + query);

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM PATIENTS WHERE NOM LIKE ? OR PRENOM LIKE ? OR DATE_NAISSANCE LIKE ?");

        preparedStatement.setString(1, "%"+query+"%");
        preparedStatement.setString(2, "%"+query+"%");
        preparedStatement.setString(3, "%"+query+"%");

        ResultSet resultSet = preparedStatement.executeQuery();

        List<Patient> patients = new ArrayList<>();

        while (resultSet.next()) {
            Patient patient = new Patient();
            patient.setId(resultSet.getLong("ID_PATIENT"));
            patient.setNom(resultSet.getString("NOM"));
            patient.setPrenom(resultSet.getString("PRENOM"));
            patient.setDateNaissance(resultSet.getDate("DATE_NAISSANCE"));
            patient.setAdresse(resultSet.getString("ADRESSE"));
            patient.setTelephone(resultSet.getString("TELEPHONE"));
            patient.setEmail(resultSet.getString("EMAIL"));
            patients.add(patient);
        }

        return patients;
    }
}
