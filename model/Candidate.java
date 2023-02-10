package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import util.GetDataUtil;

public abstract class Candidate {
	public static int count = 0;
	protected String candidateID;
	protected int candidateType;
	protected String fullName;
	protected LocalDate birthday;
	protected String phone;
	protected String email;
	protected List<Certificate> certificates;
	
	public Candidate() { }

	public Candidate(String candidateID, int candidateType, String fullName, LocalDate birthday, String phone, String email,
			List<Certificate> certificates) {
		super();
		this.candidateID = candidateID;
		this.candidateType = candidateType;
		this.fullName = fullName;
		this.birthday = birthday;
		this.phone = phone;
		this.email = email;
		this.certificates = certificates;
	}
	
	public abstract void showInfo();
	
	public void getInfo(Scanner scanner) {

		this.setCandidateID(GetDataUtil.getCandidateID(scanner));
		this.setFullName(GetDataUtil.getFullName(scanner));
		this.setBirthday(GetDataUtil.getBirthday(scanner));
		this.setPhone(GetDataUtil.getPhone(scanner));
		this.setEmail(GetDataUtil.getEmail(scanner));
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean retValue = false;
		if (obj instanceof Candidate) {
			Candidate e = (Candidate) obj;
			retValue = fullName.equals(e.fullName); 
		}
		return retValue;
	}

	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		Candidate.count = count;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public int getCandidateType() {
		return candidateType;
	}

	public void setCandidateType(int candidateType) {
		this.candidateType = candidateType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}
}
