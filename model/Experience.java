package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import util.GetDataUtil;

public class Experience extends Candidate {
	private int yearOfExperience;
	private String proSkill;
	
	public Experience() { }
	
	public Experience(int yearOfExperience, String proSkill) {
		this.yearOfExperience = yearOfExperience;
		this.proSkill = proSkill;
	}
	
	public Experience(String id, String fullName, LocalDate birthday, String phone, String email, List<Certificate> certificates,
			int yearOfExperience, String proSkill) {
		super(id, 0, fullName, birthday, phone, email, certificates);
		this.yearOfExperience = yearOfExperience;
		this.proSkill = proSkill;
	}
	
	@Override
	public void showInfo() {
		System.out.println(this);
	}
	
	@Override
	public void getInfo(Scanner scanner) {
		super.getInfo(scanner);
		this.setCandidateType(0);
		this.setYearOfExperience(GetDataUtil.getYearOfExperience(scanner));
		this.setProSkill(GetDataUtil.getProSkill(scanner));
	}
	
	@Override
	public String toString() {
		return "Experience{"
				+ "candidateID=" + candidateID + ","
				+ "fullName=" + fullName + ","
				+ "birthday=" + birthday + ","
				+ "phone=" + phone + ","
				+ "email=" + email + ","
				+ "yearOfExperience=" + yearOfExperience + ","
				+ "proSkill=" + proSkill + ","
				+ "certificates=" + certificates.toString()
				+ "}";
	}

	public int getYearOfExperience() {
		return yearOfExperience;
	}

	public void setYearOfExperience(int yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}

	public String getProSkill() {
		return proSkill;
	}

	public void setProSkill(String proSkill) {
		this.proSkill = proSkill;
	}
	
	
}
