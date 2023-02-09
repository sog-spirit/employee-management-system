package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Fresher extends Candidate {
	private LocalDate graduationDate;
	private String graduationRank;
	private String universityName;
	
	public Fresher() { }
	
	public Fresher(LocalDate graduationDate, String graduationRank, String universityName) {
		this.graduationDate = graduationDate;
		this.graduationRank = graduationRank;
		this.universityName = universityName;
	}
	
	public Fresher(String id, String fullName, LocalDate birthday, String phone, String email, List<Certificate> certificates,
			LocalDate graduationDate, String graduationRank, String universityName) {
		super(id, 1, fullName, birthday, phone, email, certificates);
		this.graduationDate = graduationDate;
		this.graduationRank = graduationRank;
		this.universityName = universityName;
	}
	
	@Override
	public void getInfo(Scanner scanner) {
		super.getInfo(scanner);
		this.setCandidateType(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String input;
		boolean isInvalidInput;
		
		do {
			try {
				System.out.print("graduationDate: ");
				input = scanner.nextLine().trim();
				LocalDate tempLocalDate = LocalDate.parse(input, formatter);
				this.setGraduationDate(tempLocalDate);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		do {
			try {
				System.out.print("graduationRank: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 50)
					throw new DataFormatException();
				this.setGraduationRank(input);
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
	public void showInfo() {
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		return "Fresher{"
				+ "candidateID=" + candidateID + ","
				+ "fullName=" + fullName + ","
				+ "birthday=" + birthday + ","
				+ "phone=" + phone + ","
				+ "email=" + email + ","
				+ "graduationDate=" + graduationDate + ","
				+ "graduationRank=" + graduationRank + ","
				+ "universityName=" + universityName
				+ "}";
	}

	public LocalDate getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(LocalDate graduationDate) {
		this.graduationDate = graduationDate;
	}

	public String getGraduationRank() {
		return graduationRank;
	}

	public void setGraduationRank(String graduationRank) {
		this.graduationRank = graduationRank;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
}
