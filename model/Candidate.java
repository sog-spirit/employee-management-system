package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import exception.BirthdayException;
import exception.EmailException;

public abstract class Candidate {
	public static int count = 0;
	protected String candidateID;
	protected int candidateType;
	protected String fullName;
	protected LocalDate birthday;
	protected String phone;
	protected String email;
	protected List<Certificate> certificates;
	
	private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
	
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String input;
		boolean isInvalidInput;
		
		do {
			try {
				System.out.print("candidateID: ");
				input = scanner.nextLine().trim();
				if (input.length() != 5)
					throw new DataFormatException();
				isInvalidInput = false;
				this.setCandidateID(input);
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("fullName: ");
				input = scanner.nextLine().trim();
				if (input.length() > 50)
					throw new DataFormatException();
				isInvalidInput = false;
				this.setFullName(input);
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("birthday: ");
				input = scanner.nextLine().trim();
				LocalDate tempLocalDate = LocalDate.parse(input, formatter);
				if (tempLocalDate.getYear() < 1900)
					throw new BirthdayException();
				this.setBirthday(tempLocalDate);
				isInvalidInput = false;
			} catch (DateTimeParseException e) {
				isInvalidInput = true;
				try {
					throw new BirthdayException();
				}
				catch (BirthdayException birthdayException) {
					System.out.println(birthdayException.getMessage());
				}
			} catch (BirthdayException birthdayException) {
				System.out.println(birthdayException.getMessage());
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("phone: ");
				input = scanner.nextLine().trim();
				if (input.length() != 10)
					throw new DataFormatException();
				this.setPhone(input);
				isInvalidInput = false;
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {			
			try {
				System.out.print("email: ");
				input = scanner.nextLine().trim();
				if (!Pattern.compile(EMAIL_REGEX).matcher(input).matches())
					throw new EmailException();
				isInvalidInput = false;
				this.setEmail(input);
			} catch (EmailException e) {
				System.out.println(e.getMessage());
				isInvalidInput = true;
			}
		} while (isInvalidInput);
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
