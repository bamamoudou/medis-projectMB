package com.mediscreen.mediscreenpatient.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.mediscreen.mediscreenpatient.config.DatabaseConfigInterface;
import com.mediscreen.mediscreenpatient.dao.PatientDAO;
import com.mediscreen.mediscreenpatient.model.Country;
import com.mediscreen.mediscreenpatient.model.Patient;

public class PatientDAOImpl extends DaoManager implements PatientDAO {

	/**
     * Constructor
     */
    public PatientDAOImpl(DatabaseConfigInterface databaseConfig) {
        super(databaseConfig, "PatientDao");
    }

	/**
	 * @see PatientDaoInterface {@link #getPatientById(Integer)}
	 */
	@Override
	public Patient getPatientById(Integer id) {
		Patient result = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT firstname, lastname, sexe, birthday, address, email, phone, country_code");
		sql.append(" FROM patient");
		sql.append(" WHERE id = ?");

		try {
			con = databaseConfig.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = new Patient();
				result.setId(id);
				result.setFirstname(rs.getString("firstname"));
				result.setLastname(rs.getString("lastname"));
				result.setSexe(rs.getString("sexe"));
				result.setBirthday(rs.getDate("birthday").toLocalDate());
				result.setAddress(rs.getString("address"));
				result.setEmail(rs.getString("email"));
				result.setPhone(rs.getString("phone"));
				result.setCountry(new Country(rs.getString("country_code")));
			}
		} catch (Exception e) {
			super.logger.error("PatientDao.getPatientById() -> Error fetching patient", e);
		} finally {
			databaseConfig.closeSQLTransaction(con, ps, rs);
		}

		if (result != null) {
			super.logger.error("PatientDao.getPatientById() -> Profile get for patient : " + id);
		} else {
			super.logger.info("PatientDao.getPatientById() -> No patient profile for id : " + id);
		}

		return result;
	}

	/**
	 * @see PatientDaoInterface {@link #getAllPatient()}
	 */
	@Override
	public List<Patient> getAllPatient() {
		List<Patient> result = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, firstname, lastname, sexe, birthday, address, email, phone, country_code");
		sql.append(" FROM patient");

		try {
			con = databaseConfig.getConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setId(rs.getInt("id"));
				patient.setFirstname(rs.getString("firstname"));
				patient.setLastname(rs.getString("lastname"));
				patient.setSexe(rs.getString("sexe"));
				patient.setBirthday(rs.getDate("birthday").toLocalDate());
				patient.setAddress(rs.getString("address"));
				patient.setEmail(rs.getString("email"));
				patient.setPhone(rs.getString("phone"));
				patient.setCountry(new Country(rs.getString("country_code")));
				result.add(patient);
			}
			if (result.size() <= 0) {
				result = null;
			}
		} catch (Exception e) {
			super.logger.error("PatientDao.getAllPatient() -> Error fetching patients", e);
		} finally {
			databaseConfig.closeSQLTransaction(con, ps, rs);
		}
		return result;
	}

	/**
	 * @see PatientDaoInterface {@link #updatePatient(Patient)}
	 */
	@Override
	public Patient updatePatient(Patient patient) {
		Connection con = null;
		PreparedStatement ps = null;

		StringBuilder sql = new StringBuilder();
		sql.append(
				"UPDATE patient SET firstname = ?, lastname = ?, sexe = ?, birthday = ?, address = ?, email = ?, phone = ?, country_code = ?");
		sql.append(" WHERE id = ?");

		try {
			con = databaseConfig.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, patient.getFirstname());
			ps.setString(2, patient.getLastname());
			ps.setString(3, patient.getSexe());
			ps.setDate(4, Date.valueOf(patient.getBirthday()));
			ps.setString(5, patient.getAddress());
			ps.setString(6, patient.getEmail());
			ps.setString(7, patient.getPhone());
			ps.setString(8, patient.getCountry().getCode());
			ps.setInt(9, patient.getId());
			ps.execute();
			super.logger.info("PatientDao.updatePatient() -> Profile updated for patient : " + patient.getId());
			return this.getPatientById(patient.getId());
		} catch (Exception ex) {
			super.logger.error("PatientDao.updatePatient() -> Error update patient", ex);
		} finally {
			databaseConfig.closeSQLTransaction(con, ps, null);
		}

		return null;
	}

	/**
	 * @see PatientDaoInterface {@link #createPatient(Patient)}
	 */
	@Override
	public Patient createPatient(Patient patient) {
		Connection con = null;
		PreparedStatement ps = null;

		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO patient (firstname, lastname, sexe, birthday, address, email, phone, country_code)");
		sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		try {
			con = databaseConfig.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, patient.getFirstname());
			ps.setString(2, patient.getLastname());
			ps.setString(3, patient.getSexe());
			ps.setDate(4, Date.valueOf(patient.getBirthday()));
			ps.setString(5, patient.getAddress());
			ps.setString(6, patient.getEmail());
			ps.setString(7, patient.getPhone());
			ps.setString(8, patient.getCountry().getCode());
			ps.execute();
			super.logger.info("PatientDao.createPatient() -> Profile created");
			return this.getPatientById(super.getMaxId("patient"));
		} catch (Exception ex) {
			super.logger.error("PatientDao.createPatient() -> Error create new user", ex);
		} finally {
			databaseConfig.closeSQLTransaction(con, ps, null);
		}
		return null;
	}

	/**
	 * @see PatientDaoInterface {@link #searchPatients(String)}
	 */
	@Override
	public List<Patient> searchPatients(String search) {
		List<Patient> result = null;
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		search += "%";

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, firstname, lastname, email");
		sql.append(" FROM user");
		sql.append(" WHERE (firstname LIKE(?)");
		sql.append(" OR lastname LIKE (?)");
		sql.append(" OR email LIKE(?))");

		try {
			con = databaseConfig.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, search);
			ps.setString(2, search);
			ps.setString(3, search);
			rs = ps.executeQuery();
			result = new ArrayList<>();
			while (rs.next()) {
				Patient patient = new Patient();
				patient.setId(rs.getInt("id"));
				patient.setFirstname(rs.getString("firstname"));
				patient.setLastname(rs.getString("lastname"));
				patient.setEmail(rs.getString("email"));
				result.add(patient);
			}
			if (result.size() <= 0) {
				result = null;
			}
		} catch (Exception e) {
			super.logger.error("PatientDao.searchPatients() -> Error fetching patients", e);
		} finally {
			databaseConfig.closeSQLTransaction(con, ps, rs);
		}
		return result;
	}

	/**
	 * @see PatientDaoInterface {@link #deletePatientById(Integer)}
	 */
	@Override
	public void deletePatientById(Integer id) {
		super.deleteById("patient", id);
	}
}