package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.zip.DataFormatException;

import controller.DBController;
import model.Candidate;
import model.Certificate;
import model.Experience;
import model.Fresher;
import model.Intern;
import util.GetDataUtil;

public class Program {
	private static DBController dbController = new DBController();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		menu(scanner);
		scanner.close();
	}
	
	private static void menu(Scanner scanner) {
		int choice;
		
		do {
			System.out.println("--Program--");
			System.out.println("1. Insert candidates");
			System.out.println("2. Update candidate");
			System.out.println("3. View non-duplicate name candidate list");
			System.out.println("4. View candidate info");
			System.out.println("5. View candidate name list with String implementation");
			System.out.println("6. View candidate name list with StringBuffer implementation");
			System.out.println("7. Sort candidate list ascending by candidate_type, if candidate_type equals sort descending by birth year");
			System.out.println("8. Exit program");
			choice = getMenuChoice(scanner);
			
			switch (choice) {
				case 1:
					insert(scanner);
					break;
				case 2:
					update(scanner);
					break;
				case 3:
					dbController.showNonDuplicateNameCandidateList();
					break;
				case 4:
					dbController.showCandidateInfo();
					break;
				case 5:
					dbController.showCandidateFullnameListWithString();
					break;
				case 6:
					dbController.showCandidateFullnameListWithStringBuffer();
					break;
				case 7:
					dbController.sortCandidateListByCandidateTypeAndBirthYear();
					break;
				case 8:
					break;
			}
		} while (choice != 8);
	}
	
	private static int getMenuChoice(Scanner scanner) {
		boolean isInvalidInput;
		String input;
		do {
			System.out.print("Your choice: ");
			input = scanner.nextLine().trim();
			try {
				int choice = Integer.parseInt(input);
				switch (choice) {
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						isInvalidInput = false;
						break;
					default:
						throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		return Integer.parseInt(input);
	}
	
	private static void insert(Scanner scanner) {
		int choice;
		int currentCandidateCount = Candidate.count;
		do {
			System.out.println("Choose candidate:");
			System.out.println("1. Experience");
			System.out.println("2. Fresher");
			System.out.println("3. Intern");
			System.out.println("4. Stop insert");
			choice = getCandidateChoice(scanner);
			
			switch (choice) {
			case 1:
				Candidate experience = new Experience();
				experience.getInfo(scanner);
				if (!dbController.insertCandidate(experience))
					break;
				System.out.println("Insert candidate successfully");
				Candidate.setCount(++Candidate.count);
				experience.showInfo();
				getCertificates(scanner, experience.getCandidateID());
				break;
			case 2:
				Candidate fresher = new Fresher();
				fresher.getInfo(scanner);
				if (!dbController.insertCandidate(fresher))
					break;
				System.out.println("Insert candidate successfully");
				Candidate.setCount(++Candidate.count);
				fresher.showInfo();
				getCertificates(scanner, fresher.getCandidateID());
				break;
			case 3:
				Candidate intern = new Intern();
				intern.getInfo(scanner);
				if (!dbController.insertCandidate(intern))
					break;
				System.out.println("Insert candidate successfully");
				Candidate.setCount(++Candidate.count);
				intern.showInfo();
				getCertificates(scanner, intern.getCandidateID());
				break;
			}
		} while (choice != 4);
		System.out.println("You have inserted " + (Candidate.getCount() - currentCandidateCount) + " candidate(s)");
	}
	
	private static int getCandidateChoice(Scanner scanner) {
		boolean isInvalidInput;
		String input;
		do {
			System.out.print("Your choice: ");
			input = scanner.nextLine().trim();
			try {
				int choice = Integer.parseInt(input);
				switch (choice) {
					case 1:
					case 2:
					case 3:
					case 4:
						isInvalidInput = false;
						break;
					default:
						throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
				isInvalidInput = true;
			}
		} while (isInvalidInput);

		return Integer.parseInt(input);
	}
	
	private static void getCertificates(Scanner scanner, String candidateID) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String choice, input;
		boolean isInvalidInput;

		do {
			Certificate certificate = new Certificate();
			certificate.setCandidateID(candidateID);
			choice = getConfirmationChoice(scanner, candidateID);
			if (choice.equals("n") || choice.equals("N"))
				return;
			
			do {
				try {
					System.out.print("certificateID: ");
					input = scanner.nextLine().trim();
					if (input.length() != 5)
						throw new DataFormatException();
					certificate.setCertificateID(input);
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
					certificate.setCertificateName(input);
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
					certificate.setCertificateRank(input);
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
					certificate.setCertificateDate(tempLocalDate);
				} catch (Exception e) {
					System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
					isInvalidInput = true;
				}
			} while (isInvalidInput);
			
			certificate.showInfo();
			if (dbController.insertCertificate(certificate)) {
				System.out.println("Insert certificate successfully");
			}

		} while (choice.equals("Y") || choice.equals("y"));
	}
	
	private static String getConfirmationChoice(Scanner scanner, String candidateID) {
		String input;
		boolean isInvalidInput;
		do {
			System.out.print("Do you want to insert certificate for " + candidateID + "? [Y/n]: ");
			input = scanner.nextLine().trim();
			if (input.equals("y") || input.equals("Y") ||
					input.equals("N") || input.equals("n")) {
				return input;
			}
			isInvalidInput = true;
		} while (isInvalidInput);

		return "n";
	}
	
	private static void update(Scanner scanner) {
		String input;
		System.out.print("Enter candidateID: ");
		input = scanner.nextLine().trim();
		int candidateType = dbController.candidateIDIsExist(input);
		if (candidateType >= 0) {
			if (candidateType == 0) {
				int choice;
				do {
					System.out.println("Select field you want to update");
					System.out.println("1. fullName");
					System.out.println("2. birthday");
					System.out.println("3. phone");
					System.out.println("4. email");
					System.out.println("5. yearOfExperience");
					System.out.println("6. proSkill");
					System.out.println("7. exit");
					choice = getUpdateChoice(scanner, candidateType);
					
					switch (choice) {
					case 1:
						String newFullName = GetDataUtil.getFullName(scanner);
						dbController.updateFullName(newFullName, input);
						break;
					case 2:
						LocalDate newBirthday = GetDataUtil.getBirthday(scanner);
						dbController.updateBirthday(newBirthday, input);
						break;
					case 3:
						String newPhone = GetDataUtil.getPhone(scanner);
						dbController.updatePhone(newPhone, input);
						break;
					case 4:
						String newEmail = GetDataUtil.getEmail(scanner);
						dbController.updateEmail(newEmail, input);
						break;
					case 5:
						int newYear = GetDataUtil.getYearOfExperience(scanner);
						dbController.updateYearOfExperience(newYear, input);
						break;
					case 6:
						String newProSkill = GetDataUtil.getProSkill(scanner);
						dbController.updateProSkill(newProSkill, input);
						break;
					case 7:
						break;
					}
				} while (choice != 7);
			} else if (candidateType == 1) {
				int choice;
				do {
					System.out.println("Select field you want to update");
					System.out.println("1. fullName");
					System.out.println("2. birthday");
					System.out.println("3. phone");
					System.out.println("4. email");
					System.out.println("5. graduationDate");
					System.out.println("6. graduationRank");
					System.out.println("7. universityName");
					System.out.println("8. exit");
					choice = getUpdateChoice(scanner, candidateType);
					
					switch (choice) {
					case 1:
						String newFullName = GetDataUtil.getFullName(scanner);
						dbController.updateFullName(newFullName, input);
						break;
					case 2:
						LocalDate newBirthday = GetDataUtil.getBirthday(scanner);
						dbController.updateBirthday(newBirthday, input);
						break;
					case 3:
						String newPhone = GetDataUtil.getPhone(scanner);
						dbController.updatePhone(newPhone, input);
						break;
					case 4:
						String newEmail = GetDataUtil.getEmail(scanner);
						dbController.updateEmail(newEmail, input);
						break;
					case 5:
						LocalDate newGraduationDate = GetDataUtil.getGraduationDate(scanner);
						dbController.updateGraduationDate(newGraduationDate, input);
						break;
					case 6:
						String newGraduationRank = GetDataUtil.getGraduationRank(scanner);
						dbController.updateProSkill(newGraduationRank, input);
						break;
					case 7:
						String newUniversityName = GetDataUtil.getUniversityName(scanner);
						dbController.updateUniversityName(newUniversityName, input);
						break;
					case 8:
						break;
					}
				} while (choice != 8);
			} else {
				int choice;
				do {
					System.out.println("Select field you want to update");
					System.out.println("1. fullName");
					System.out.println("2. birthday");
					System.out.println("3. phone");
					System.out.println("4. email");
					System.out.println("5. major");
					System.out.println("6. semester");
					System.out.println("7. universityName");
					System.out.println("8. exit");
					choice = getUpdateChoice(scanner, candidateType);
					
					switch (choice) {
					case 1:
						String newFullName = GetDataUtil.getFullName(scanner);
						dbController.updateFullName(newFullName, input);
						break;
					case 2:
						LocalDate newBirthday = GetDataUtil.getBirthday(scanner);
						dbController.updateBirthday(newBirthday, input);
						break;
					case 3:
						String newPhone = GetDataUtil.getPhone(scanner);
						dbController.updatePhone(newPhone, input);
						break;
					case 4:
						String newEmail = GetDataUtil.getEmail(scanner);
						dbController.updateEmail(newEmail, input);
						break;
					case 5:
						String newMajor = GetDataUtil.getMajor(scanner);
						dbController.updateMajor(newMajor, input);
						break;
					case 6:
						int newSemester = GetDataUtil.getSemester(scanner);
						dbController.updateSemester(newSemester, input);
						break;
					case 7:
						String newUniversityName = GetDataUtil.getUniversityName(scanner);
						dbController.updateUniversityName(newUniversityName, input);
						break;
					case 8:
						break;
					}
				} while (choice != 8);
			}
		}
		else {
			System.out.println("Candidate ID does not exist");
		}
	}
	private static int getUpdateChoice(Scanner scanner, int candidateType) {
		boolean isInvalidInput;
		String input;
		int choice = 0;
		if (candidateType == 0) {
			do {
				try {
					System.out.print("Your choice: ");
					input = scanner.nextLine().trim();
					choice = Integer.parseInt(input);
					
					switch (choice) {
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
							isInvalidInput = false;
							break;
						default:
							throw new NumberFormatException();
					}
				} catch (Exception e) {
					System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
					isInvalidInput = true;
				}
			} while (isInvalidInput);
		}
		else {
			do {
				try {
					System.out.print("Your choice: ");
					input = scanner.nextLine().trim();
					choice = Integer.parseInt(input);
					
					switch (choice) {
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
							isInvalidInput = false;
							break;
						default:
							throw new NumberFormatException();
					}
				} catch (Exception e) {
					System.out.println("The system has encountered an unexpected problem, sincerely sorry !!!");
					isInvalidInput = true;
				}
			} while (isInvalidInput);
		}
		return choice;
	} 
}
