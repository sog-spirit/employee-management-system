package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import exception.BirthdayException;
import exception.EmailException;

public class GetDataUtil {
	private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

	public static String getFullName(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {
			try {
				System.out.print("fullName: ");
				input = scanner.nextLine().trim();
				if (input.length() > 50)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		return input;
	}
	public static LocalDate getBirthday(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate tempLocalDate = null;

		do {
			try {
				System.out.print("birthday: ");
				input = scanner.nextLine().trim();
				tempLocalDate = LocalDate.parse(input, formatter);
				if (tempLocalDate.getYear() < 1900)
					throw new BirthdayException();
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
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return tempLocalDate;
	}
	public static String getPhone(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {
			try {
				System.out.print("phone: ");
				input = scanner.nextLine().trim();
				if (input.length() != 10)
					throw new DataFormatException();
				isInvalidInput = false;
			} catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static String getEmail(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {			
			try {
				System.out.print("email: ");
				input = scanner.nextLine().trim();
				if (!Pattern.compile(EMAIL_REGEX).matcher(input).matches())
					throw new EmailException();
				isInvalidInput = false;
			} catch (EmailException e) {
				System.out.println(e.getMessage());
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static int getYearOfExperience(Scanner scanner) {
		String input;
		boolean isInvalidInput;
		int year = 0;
		do {
			try {
				System.out.print("yearOfExperience: ");
				input = scanner.nextLine().trim();
				year = Integer.parseInt(input);
				if (year < 0)
					throw new NumberFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return year;
	}
	public static String getProSkill(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {
			try {				
				System.out.print("proSkill: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 128)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static LocalDate getGraduationDate(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate tempLocalDate = null;

		do {
			try {
				System.out.print("graduationDate: ");
				input = scanner.nextLine().trim();
				tempLocalDate = LocalDate.parse(input, formatter);
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return tempLocalDate;
	}
	public static String getGraduationRank(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {
			try {
				System.out.print("graduationRank: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 50)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static String getUniversityName(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;

		do {
			try {
				System.out.print("universityName: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 256)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static String getMajor(Scanner scanner) {
		String input = null;
		boolean isInvalidInput;
		
		do {
			try {
				System.out.print("major: ");
				input = scanner.nextLine().trim();
				if (input.length() < 0 || input.length() > 256)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return input;
	}
	public static int getSemester(Scanner scanner) {
		String input;
		boolean isInvalidInput;
		int semester = 0;

		do {
			try {
				System.out.print("semester: ");
				input = scanner.nextLine().trim();
				semester = Integer.parseInt(input);
				if (semester <= 0)
					throw new DataFormatException();
				isInvalidInput = false;
			}
			catch (Exception e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);
		
		return semester;
	}
}
