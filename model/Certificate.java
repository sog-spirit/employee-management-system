package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Certificate {
	private String certificateID;
	private String certificateName;
	private String certificateRank;
	private LocalDate certificateDate;
	private String candidateID;
	
	public Certificate() { }

	public Certificate(String certificateID, String certificateName, String certificateRank,
			LocalDate certificateDate, String candidateID) {
		super();
		this.certificateID = certificateID;
		this.certificateName = certificateName;
		this.certificateRank = certificateRank;
		this.certificateDate = certificateDate;
		this.candidateID = candidateID;
	}
	
	public void showInfo() {
		System.out.println(this);
	}

	public void getInfo(Scanner scanner) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String input;
		boolean isInvalidInput;
			
		do {
			try {
				System.out.print("certificateID: ");
				input = scanner.nextLine().trim();
				if (input.length() != 5)
					throw new DataFormatException();
				this.setCertificateID(input);
				isInvalidInput = false;
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("certificateName: ");
				input = scanner.nextLine().trim();
				if (input.length() == 0 || input.length() > 256 )
					throw new DataFormatException();
				isInvalidInput = false;
				this.setCertificateName(input);
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("certificateRank: ");
				input = scanner.nextLine().trim();
				if (input.length() == 0 || input.length() > 256 )
					throw new DataFormatException();
				isInvalidInput = false;
				this.setCertificateRank(input);
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		do {
			try {
				System.out.print("certificateDate: ");
				input = scanner.nextLine().trim();
				LocalDate tempLocalDate = LocalDate.parse(input, formatter);
				this.setCertificateDate(tempLocalDate);
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
	}
	
	@Override
	public String toString() {
		return "Certificate{"
				+ "certificateID=" + this.certificateID + ", "
				+ "certificateName=" + this.certificateName + ", "
				+ "certificateRank=" + this.certificateRank + ", "
				+ "certificateDate=" + this.certificateDate + ", "
				+ "candidateID=" + this.candidateID + "}";
	}
	
	public String getCertificateID() {
		return certificateID;
	}

	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateRank() {
		return certificateRank;
	}

	public void setCertificateRank(String certificateRank) {
		this.certificateRank = certificateRank;
	}

	public LocalDate getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(LocalDate certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}
}
