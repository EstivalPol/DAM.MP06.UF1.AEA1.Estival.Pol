package Activitat1;

import java.io.File;
import java.util.Date;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Application {

	private String personalInformation[] = new String[2];
	private Scanner sc = new Scanner(System.in);

	/**
	 * Run the first phase of the program.
	 * @param file the {@code File} object of which we want to obtain the information
	 * @param ui the {@code UserInterface} object with which we will display the graphical interface
	 */
	private void firstPhase(FileSystem file, UserInterface ui) {

		System.out.print("- Introdueix el teu nom: ");
		personalInformation[1] = sc.nextLine();
		System.out.print("- Introdueix el teu cognom: ");
		personalInformation[0] = sc.nextLine();

		System.out.printf("%s\n%33s\n%s\n", ui.getLine(), "FASE 1", ui.getLine());

		// Check if the "MP06_Projecte" folder exists in the desktop
		if (!file.getCurrentFile().exists()) {
			file.getCurrentFile().mkdir();
		}

		for (int i = 0; i < personalInformation.length; i++) {
			file.setCurrentFile(file.getCurrentFile(), personalInformation[i]);
			file.createFolder(file.getCurrentFile());
			ui.setLine('-', 62);
			ui.printLine();
		}

		String additionalPath;
		File currentFile = file.getCurrentFile();

		for (int i = 0; i < 13; i++) {
			if (i < 9) {
				additionalPath = String.format("MP0%d", i + 1);
			} else {
				additionalPath = String.format("MP%d", i + 1);
			}
			file.setCurrentFile(currentFile, additionalPath);
			file.createFolder(file.getCurrentFile());
			if (i != 12) {
				ui.printLine();
			}
		}

	} // End of the firstPhase method

	/**
	 * Run the second phase of the program.
	 * @param file the {@code File} object of which we want to obtain the information
	 * @param ui the {@code UserInterface} object with which we will display the graphical interface
	 */
	private void secondPhase(FileSystem file, UserInterface ui) {

		Date birthday = null;

		ui.setLine('=', 62);
		System.out.printf("%s\n%33s\n%s\n", ui.getLine(), "FASE 2", ui.getLine());
		ui.setLine('-', 62);

		System.out.print("- Escriu la teva data de naixement (dd/mm/yyyy): ");
		String customDate = sc.nextLine();

		try {
			birthday = new SimpleDateFormat("dd/MM/yyyy").parse(customDate);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthday);

		int day = calendar.get(Calendar.DAY_OF_MONTH);

		// Return to the "MP06_Projecte" folder
		file.returnToWorkDirectory();

		for (int i = 0; i < personalInformation.length; i++) {
			file.setCurrentFile(file.getCurrentFile(), personalInformation[i]);
		}

		file.renameFolder(file.getCurrentFile(), String.format("%s%d", personalInformation[1], day));

	} // End of the secondPhase method

	/**
	 * Run the third phase of the program.
	 * @param file the {@code File} object of which we want to obtain the information
	 * @param ui the {@code UserInterface} object with which we will display the graphical interface
	 */
	private void thirdPhase(FileSystem file, UserInterface ui) {

		ui.setLine('=', 62);
		System.out.printf("%s\n%33s\n%s\n", ui.getLine(), "FASE 3", ui.getLine());
		ui.setLine('-', 62);

		file.listFiles(file.getCurrentFile());

		File[] fileList = file.getCurrentFile().listFiles();

		if (fileList != null) {
			for (File document : fileList) {
				if (Integer.parseInt(document.getName().substring(document.getName().length() - 1)) % 2 == 0) {
					ui.printLine();
					file.deleteFolder(document);
				}
			}
		}

		ui.setLine('=', 62);
		ui.printLine();

	} // End of the thirdPhase method

	public static void main(String[] args) {

		FileSystem file = new FileSystem();
		UserInterface ui = new UserInterface();
		Application app = new Application();

		ui.showTitle("MANTENIMENT D'UN SISTEMA DE FITXERS");

		app.firstPhase(file, ui);
		app.secondPhase(file, ui);
		app.thirdPhase(file, ui);

	} // End of the main method

} // End of the Application class
