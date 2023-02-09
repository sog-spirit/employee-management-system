package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	private final String INSERT_CADIDATE = "INSERT INTO CANDIDATE "
			+ "(candidateID, candidateType, fullName, birthday, phone, email,"
			+ "yearOfExperience, proSkill,"
			+ "graduationDate, graduationRank, universityName,"
			+ "major, semester) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private final String SELECT_CANDIDATE_FULLNAME = "SELECT fullName FROM CANDIDATE";
	
	private final String SELECT_CANDIDATE = "SELECT * FROM CANDIDATE";
	
	private final String INSERT_CERTIFICATE = "INSERT INTO CERTIFICATE "
			+ "(certificateID, certificateName, certificateRank, certificateDate, candidateID) "
			+ "VALUES (?,?,?,?,?)";
	
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
			statement = connection.prepareStatement(INSERT_CADIDATE);
			statement.setString(1, candidate.getCandidateID());
			statement.setString(3, candidate.getFullName());
			statement.setDate(4, Date.valueOf(candidate.getBirthday()));
			statement.setString(5, candidate.getPhone());
			statement.setString(6, candidate.getEmail());
			
			if (candidate instanceof Experience) {
				Experience experience = (Experience) candidate;
				statement.setInt(2, experience.getCandidateType());
				statement.setInt(7, experience.getYearOfExperience());
				statement.setString(8, experience.getProSkill());
				statement.setString(9, null);
				statement.setString(10, null);
				statement.setString(11, null);
				statement.setString(12, null);
				statement.setString(13, null);
			}
			else if (candidate instanceof Fresher) {
				Fresher fresher = (Fresher) candidate;
				statement.setInt(2, fresher.getCandidateType());
				statement.setString(7, null);
				statement.setString(8, null);
				statement.setDate(9, Date.valueOf(fresher.getGraduationDate()));
				statement.setString(10, fresher.getGraduationRank());
				statement.setString(11, fresher.getUniversityName());
				statement.setString(12, null);
				statement.setString(13, null);
			}
			else if (candidate instanceof Intern) {
				Intern intern = (Intern) candidate;
				statement.setInt(2, intern.getCandidateType());
				statement.setString(7, null);
				statement.setString(8, null);
				statement.setString(9, null);
				statement.setString(10, null);
				statement.setString(11, intern.getUniversityName());
				statement.setString(12, intern.getMajor());
				statement.setInt(13, intern.getSemester());
			}
			statement.executeUpdate();
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
			statement = connection.prepareStatement(INSERT_CERTIFICATE);
			statement.setString(1, certificate.getCertificateID());
			statement.setString(2, certificate.getCertificateName());
			statement.setString(3, certificate.getCertificateRank());
			statement.setDate(4, Date.valueOf(certificate.getCertificateDate()));
			statement.setString(5, certificate.getCandidateID());
			statement.executeUpdate();
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
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet candidateInfoResultSet = statement.executeQuery(SELECT_CANDIDATE);
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
		Statement statement = null;
		List<Candidate> candidates = new LinkedList<>();
		try {
			statement = connection.createStatement();
			ResultSet candidateInfoResultSet = statement.executeQuery(SELECT_CANDIDATE);
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
}
