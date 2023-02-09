package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import javax.print.DocFlavor.INPUT_STREAM;

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
		String input;
		boolean isInvalidInput;

		do {
			try {
				System.out.print("yearOfExperience: ");
				input = scanner.nextLine().trim();
				int year = Integer.parseInt(input);
				if (year < 0)
					throw new NumberFormatException();
				this.setYearOfExperience(year);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		do {
			try {				
				System.out.print("proSkill: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 128)
					throw new DataFormatException();
				this.setProSkill(input);
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
		return "Experience{"
				+ "candidateID=" + candidateID + ","
				+ "fullName=" + fullName + ","
				+ "birthday=" + birthday + ","
				+ "phone=" + phone + ","
				+ "email=" + email + ","
				+ "yearOfExperience=" + yearOfExperience + ","
				+ "proSkill=" + proSkill
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
