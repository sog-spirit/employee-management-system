package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Intern extends Candidate {
	private String major;
	private int semester;
	private String universityName;
	
	public Intern() { }

	public Intern(String major, int semester, String universityName) {
		this.major = major;
		this.semester = semester;
		this.universityName = universityName;
	}
	
	public Intern(String id, String fullName, LocalDate birthday, String phone, String email, List<Certificate> certificates,
			String major, int semester, String universityName) {
		super(id, 2, fullName, birthday, phone, email, certificates);
		this.major = major;
		this.semester = semester;
		this.universityName = universityName;
	}
	
	@Override
	public void showInfo() {
		System.out.println(this);
	}
	
	@Override
	public void getInfo(Scanner scanner) {
		super.getInfo(scanner);
		this.setCandidateType(2);
		String input;
		boolean isInvalidInput;
		
		do {
			try {
				System.out.print("major: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 256)
					throw new DataFormatException();
				this.setMajor(input);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		do {
			try {
				System.out.print("semester: ");
				input = scanner.nextLine().trim();
				int semester = Integer.parseInt(input);
				if (semester <= 0)
					throw new DataFormatException();
				this.setSemester(semester);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		do {
			try {
				System.out.print("universityName: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 256)
					throw new DataFormatException();
				this.setUniversityName(input);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
	}
	
	@Override
	public String toString() {
		return "Intern{"
				+ "candidateID=" + candidateID + ","
				+ "fullName=" + fullName + ","
				+ "birthday=" + birthday + ","
				+ "phone=" + phone + ","
				+ "email=" + email + ","
				+ "major=" + major + ","
				+ "semester=" + semester + ","
				+ "universityName=" + universityName
				+ "}";
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
}