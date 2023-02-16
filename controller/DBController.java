package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.Candidate;
import model.Certificate;
import model.Experience;
import model.Fresher;
import model.Intern;

public class DBController {
	
	private static final String SELECT_CANDIDATE_FULLNAME = "SELECT fullName FROM CANDIDATE";
	
	private static final String SELECT_CANDIDATE = "SELECT * FROM CANDIDATE";
	
	private static final String SELECT_CANDIDATE_IF_EXIST = "SELECT * FROM CANDIDATE WHERE candidateID = ?";
	
	private static final String SELECT_CERTIFICATE = "SELECT * FROM CERTIFICATE";
	
	private static final String sELECT_CERTIFICATE_FROM_CANDIDATE = "SELECT * FROM CERTIFICATE WHERE candidateID = ?";
	
	
	public List<Certificate> getCertificatesFromCandidate(String candidateID) {
		Connection connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		List<Certificate> certificates = new ArrayList<>();
		
		try {
			statement = connection.prepareStatement(sELECT_CERTIFICATE_FROM_CANDIDATE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Certificate certificate = new Certificate();
				certificate.setCertificateID(resultSet.getString("certificateID"));
				certificate.setCertificateName(resultSet.getString("certificateName"));
				certificate.setCertificateRank(resultSet.getString("certificateRank"));
				certificate.setCertificateDate(resultSet.getDate("certificateDate").toLocalDate());
				certificate.setCandidateID(resultSet.getString("candidateID"));
				certificates.add(certificate);
			}
		} catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		} finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
		return certificates;
	}

	/**
	 * Insert a candidate object to database
	 * @param candidate Candidate
	 * @return true if insert successfully, else return false
	 */
	public boolean insertCandidate(Candidate candidate) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		boolean check = false;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery();
			resultSet.moveToInsertRow();
			resultSet.updateString("candidateID", candidate.getCandidateID());
			resultSet.updateString("fullName", candidate.getFullName());
			resultSet.updateDate("birthday", Date.valueOf(candidate.getBirthday()));
			resultSet.updateString("phone", candidate.getPhone());
			resultSet.updateString("email", candidate.getEmail());
			
			if (candidate instanceof Experience) {
				Experience experience = (Experience) candidate;
				resultSet.updateInt("candidateType", experience.getCandidateType());
				resultSet.updateInt("yearOfExperience", experience.getYearOfExperience());
				resultSet.updateString("proSkill", experience.getProSkill());
				resultSet.updateString("graduationDate", null);
				resultSet.updateString("graduationRank", null);
				resultSet.updateString("universityName", null);
				resultSet.updateString("major", null);
				resultSet.updateString("semester", null);
			}
			else if (candidate instanceof Fresher) {
				Fresher fresher = (Fresher) candidate;
				resultSet.updateInt("candidateType", fresher.getCandidateType());
				resultSet.updateString("yearOfExperience", null);
				resultSet.updateString("proSkill", null);
				resultSet.updateDate("graduationDate", Date.valueOf(fresher.getGraduationDate()));
				resultSet.updateString("graduationRank", fresher.getGraduationRank());
				resultSet.updateString("universityName", fresher.getUniversityName());
				resultSet.updateString("major", null);
				resultSet.updateString("semester", null);
			}
			else if (candidate instanceof Intern) {
				Intern intern = (Intern) candidate;
				resultSet.updateInt("candidateType", intern.getCandidateType());
				resultSet.updateString("yearOfExperience", null);
				resultSet.updateString("proSkill", null);
				resultSet.updateString("graduationDate", null);
				resultSet.updateString("graduationRank", null);
				resultSet.updateString("universityName", intern.getUniversityName());
				resultSet.updateString("major", intern.getMajor());
				resultSet.updateInt("semester", intern.getSemester());
			}
			resultSet.insertRow();
			check = true;
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			check = false;
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
		return check;
	}
	
	/**
	 * Insert a certificate object to database
	 * @param certificate Certificate
	 * @return true if insert successfully, else return false
	 */
	public boolean insertCertificate(Certificate certificate) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		boolean isSuccess;
		try {
			statement = connection.prepareStatement(SELECT_CERTIFICATE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = statement.executeQuery();
			resultSet.moveToInsertRow();
			resultSet.updateString("certificateID", certificate.getCertificateID());
			resultSet.updateString("certificateName", certificate.getCertificateName());
			resultSet.updateString("certificateRank", certificate.getCertificateRank());
			resultSet.updateDate("certificateDate", Date.valueOf(certificate.getCertificateDate()));
			resultSet.updateString("candidateID", certificate.getCandidateID());
			resultSet.insertRow();
			isSuccess = true;
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			isSuccess = false;
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (SQLException e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
		return isSuccess;
	}
	
	public void showCandidateFullnameListWithString() {
		String candidateFullnameListResult = "";
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_FULLNAME);
			ResultSet candidateFullnameList = statement.executeQuery();
			while (candidateFullnameList.next()) {
				candidateFullnameListResult += candidateFullnameList.getString("fullName");
				candidateFullnameListResult += ",";
			}
			if (candidateFullnameListResult.isEmpty()) {
				System.out.println("Empty");
			} else {
				int endIndex = candidateFullnameListResult.length(); 
				candidateFullnameListResult = candidateFullnameListResult.substring(0, endIndex - 1);
				System.out.println(candidateFullnameListResult);
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	
	public void showCandidateFullnameListWithStringBuffer() {
		StringBuffer candidateFullnameListResult = new StringBuffer();
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_FULLNAME);
			ResultSet candidateFullnameList = statement.executeQuery();
			while (candidateFullnameList.next()) {
				candidateFullnameListResult.append(candidateFullnameList.getString("fullName"));
				candidateFullnameListResult.append(",");
			}
			if (candidateFullnameListResult.isEmpty()) {
				System.out.println("Empty");
			} else {
				int endIndex = candidateFullnameListResult.length(); 
				candidateFullnameListResult.replace(endIndex - 1, endIndex, "");
				System.out.println(candidateFullnameListResult.toString());
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	
	public void showCandidateInfo() {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE);
			ResultSet candidateInfoResultSet = statement.executeQuery();
			while (candidateInfoResultSet.next()) {
				int candidateType = candidateInfoResultSet.getInt("candidateType");
				if (candidateType == 0) {
					Candidate candidate = new Experience();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Experience) candidate).setYearOfExperience(candidateInfoResultSet.getInt("yearOfExperience"));
					((Experience) candidate).setProSkill(candidateInfoResultSet.getString("proSkill"));
					((Experience) candidate).showInfo();
				}
				else if (candidateType == 1) {
					Candidate candidate = new Fresher();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Fresher) candidate).setGraduationDate(candidateInfoResultSet.getDate("graduationDate").toLocalDate());
					((Fresher) candidate).setGraduationRank(candidateInfoResultSet.getString("graduationRank"));
					((Fresher) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
					candidate.showInfo();
				}
				else if (candidateType == 2) {
					Candidate candidate = new Intern();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Intern) candidate).setMajor(candidateInfoResultSet.getString("major"));
					((Intern) candidate).setSemester(candidateInfoResultSet.getInt("semester"));
					((Intern) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
					candidate.showInfo();
				}		
			}
		} catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	
	public void showNonDuplicateNameCandidateList() {
		Set<Candidate> candidates = new HashSet<>();
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE);
			ResultSet candidateInfoResultSet = statement.executeQuery();
			while (candidateInfoResultSet.next()) {
				Candidate candidate;
				int candidateType = candidateInfoResultSet.getInt("candidateType");
				if (candidateType == 0) {
					candidate = new Experience();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Experience) candidate).setYearOfExperience(candidateInfoResultSet.getInt("yearOfExperience"));
					((Experience) candidate).setProSkill(candidateInfoResultSet.getString("proSkill"));
				}
				else if (candidateType == 1) {
					candidate = new Fresher();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Fresher) candidate).setGraduationDate(candidateInfoResultSet.getDate("graduationDate").toLocalDate());
					((Fresher) candidate).setGraduationRank(candidateInfoResultSet.getString("graduationRank"));
					((Fresher) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
				}
				else {
					candidate = new Intern();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Intern) candidate).setMajor(candidateInfoResultSet.getString("major"));
					((Intern) candidate).setSemester(candidateInfoResultSet.getInt("semester"));
					((Intern) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
				}
				candidates.add(candidate);
			}
			candidates.stream().forEach((candidate) -> candidate.showInfo());
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	
	public void sortCandidateListByCandidateTypeAndBirthYear() {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		List<Candidate> candidates = new LinkedList<>();
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE);
			ResultSet candidateInfoResultSet = statement.executeQuery();
			while (candidateInfoResultSet.next()) {
				Candidate candidate;
				int candidateType = candidateInfoResultSet.getInt("candidateType");
				if (candidateType == 0) {
					candidate = new Experience();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Experience) candidate).setYearOfExperience(candidateInfoResultSet.getInt("yearOfExperience"));
					((Experience) candidate).setProSkill(candidateInfoResultSet.getString("proSkill"));
				}
				else if (candidateType == 1) {
					candidate = new Fresher();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Fresher) candidate).setGraduationDate(candidateInfoResultSet.getDate("graduationDate").toLocalDate());
					((Fresher) candidate).setGraduationRank(candidateInfoResultSet.getString("graduationRank"));
					((Fresher) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
				}
				else {
					candidate = new Intern();
					candidate.setCandidateID(candidateInfoResultSet.getString("candidateID"));
					candidate.setCandidateType(candidateType);
					candidate.setFullName(candidateInfoResultSet.getString("fullName"));
					candidate.setBirthday(candidateInfoResultSet.getDate("birthday").toLocalDate());
					candidate.setPhone(candidateInfoResultSet.getString("phone"));
					candidate.setEmail(candidateInfoResultSet.getString("email"));
					candidate.setCertificates(getCertificatesFromCandidate(candidate.getCandidateID()));
					((Intern) candidate).setMajor(candidateInfoResultSet.getString("major"));
					((Intern) candidate).setSemester(candidateInfoResultSet.getInt("semester"));
					((Intern) candidate).setUniversityName(candidateInfoResultSet.getString("universityName"));
				}
				candidates.add(candidate);
			}
			candidates = candidates.stream()
					.sorted((o1, o2) -> {
						if (o1.getCandidateType() == o2.getCandidateType()) {
							return o2.getBirthday().getYear() - o1.getBirthday().getYear();
						}
						else {
							return o1.getCandidateType() - o2.getCandidateType();
						}
					})
					.collect(Collectors.toList());
			candidates.stream().forEach(o -> o.showInfo());
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	
	public int candidateIDIsExist(String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("candidateType");
			}
			else {
				return -1;
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
		return -1;
	}
	
	public void updateFullName(String newFullName, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("fullName", newFullName);
				resultSet.updateRow();
				System.out.println("Update fullName successfully");
			}
		} catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			e.printStackTrace();
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
		
	}
	public void updateBirthday(LocalDate newBirthDate, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateDate("birthday", Date.valueOf(newBirthDate));
				resultSet.updateRow();
				System.out.println("birthday update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updatePhone(String newPhone, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("phone", newPhone);
				resultSet.updateRow();
				System.out.println("phone update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateEmail(String newEmail, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("email", newEmail);
				resultSet.updateRow();
				System.out.println("email update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateYearOfExperience(int newYear, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateInt("yearOfExperience", newYear);
				resultSet.updateRow();
				System.out.println("yearOfExperience update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateProSkill(String newProSkill, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("proSkill", newProSkill);
				resultSet.updateRow();
				System.out.println("email update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateGraduationDate(LocalDate newGraduationDate, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateDate("graduationDate", Date.valueOf(newGraduationDate));
				resultSet.updateRow();
				System.out.println("graduationDate update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateGraduationRank(String newGraduationRank, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("graduationRank", newGraduationRank);
				resultSet.updateRow();
				System.out.println("graduationRank update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateUniversityName(String newUniversityName, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("universityName", newUniversityName);
				resultSet.updateRow();
				System.out.println("universityName update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateMajor(String newMajor, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateString("major", newMajor);
				resultSet.updateRow();
				System.out.println("email update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
	public void updateSemester(int newSemester, String candidateID) {
		Connection connection;
		connection = DBConnector.getConnection();
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(SELECT_CANDIDATE_IF_EXIST, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			statement.setString(1, candidateID);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				resultSet.updateInt("semester", newSemester);
				resultSet.updateRow();
				System.out.println("email update successfully");
			}
		}
		catch (Exception e) {
			System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
		}
		finally {
			try {
				if (statement != null)
					statement.close();
				if (connection != null)
					connection.close();
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
			}
		}
	}
}
