
package Activitat1;

import java.io.File;
import java.time.ZoneId;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.nio.file.attribute.FileTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystem {

	private File currentFile;
	private final String WORK_DIRECTORY;
	private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	/**
	 * Constructor of <code>MantenimentSistemaFitxers</code> class that initializes the
	 * instance variables <code>ROOT_PATH</code> and <code>currentPath</code>.
	 */
	public FileSystem() {
		String os = System.getProperty("os.name").toLowerCase(),
			homeDirectory;

		if (os.matches("windows\\s\\d+")) {
			homeDirectory = System.getenv("USERPROFILE");
		} else {
			homeDirectory = System.getenv("HOME");
		}

		WORK_DIRECTORY = String.valueOf(Paths.get(homeDirectory, "D:/PRAT_EDUCACIO/DAM2/MP06 - Accés a dades"));
		currentFile = new File(WORK_DIRECTORY);
	} // End of the constructor

	/**
	 * Get the current file.
	 * @return the {@code File} object that represents the current file
	 */
	public File getCurrentFile() {
		return currentFile;
	} // End of the getCurrentPath method

	/**
	 * Set the current file as the work directory.
	 */
	public void returnToWorkDirectory() {
		currentFile = new File(WORK_DIRECTORY);
	} // End of the returnToWorkDirectory

	/**
	 * Set the new path of the current file.
	 * @param file the {@code File} object that represents the current file
	 */
	public void setCurrentFile(File file) {
		if (file.getAbsolutePath().substring(0, WORK_DIRECTORY.length()).equals(WORK_DIRECTORY)) {
			currentFile = new File(file.getAbsolutePath());
		}
	} // End of the setCurrentPath method

	/**
	 * Set the new path, starting from the current file path.
	 * @param file the {@code File} object that represents the current file
	 * @param additionalPath the {@code String} object that represents the new path that will
	 * be added to the current file path
	 */
	public void setCurrentFile(File file, String additionalPath) {
		if (file.getAbsolutePath().substring(0, WORK_DIRECTORY.length()).equals(WORK_DIRECTORY)) {
			currentFile = new File(file.getAbsolutePath(), additionalPath);
		}
	} // End of the setCurrentPath method

	/**
	 * Create the specified folder by the current file path.
	 * @param file the {@code File} object that represents the current file
	 */
	public void createFolder(File file) {
		if (!file.exists()) {
			file.mkdir();
			System.out.printf("- La carpeta (%s) s'ha creat correctament\n", file.getName());
		} else {
			System.out.printf("- La carpeta (%s) ja existeix\n", file.getName());
		}
		System.out.printf("- Ruta completa: %s\n", file.getAbsolutePath());
	} // End of the createFolder method

	/**
	 * Delete the folder specified by the current file path.
	 * @param file the {@code File} object that represents the current file
	 */
	public void deleteFolder(File file) {
		if (file.exists()) {
			file.delete();
			System.out.printf("- La carpeta (%s) s'ha eliminat correctament\n", file.getName());
		} else {
			System.out.printf("- La carpeta (%s) no s'ha pogut eliminar\n", file.getName());
		}
	} // End of the deleteFolder method

	/**
	 * Rename the current file.
	 * @param file the {@code File} object that represents the current file
	 * @param name the {@code Strinf} object that represents the new name to be assigned to the current file
	 */
	public void renameFolder(File file, String name) {
		String path = file.getAbsolutePath();

		while (!path.substring(path.length() - 1).equals(File.separator)) {
			path = path.substring(0, path.length() - 1);
		}

		File destinationFile = new File(path + name);

		if (file.renameTo(destinationFile)) {
			System.out.printf("- La carpeta (%s) s'ha canviat de nom a: (%s)\n", file.getName(), name);
			setCurrentFile(destinationFile);
		} else {
			System.out.printf("- No s'ha pogut canviar el nom de la carpeta (%s) a (%s)\n", file.getName(), name);
		}
	} // End of the renameFolder method

	/**
	 * Show all the contents of the current directory.
	 * @param file the {@code File} object that represents the current file
	 */
	public void listFiles(File file) {

		File[] fileList = file.listFiles();

		if (fileList != null) {
			for (File document : fileList) {
				System.out.printf("- Nom del fitxer: (%s)\n", document.getName());
				System.out.printf("- Ruta completa: %s\n", document.getAbsolutePath());
				showDateInformation(document);
				showPermissions(document);

				if (fileList[fileList.length - 1] != document) {
					System.out.println();
				}
			}
		}
	} // End of the listFiles method

	/**
	 * Converts a boolean value to a String object and returns it in uppercase.
	 * @param condition The boolean condition to check
	 * @return The {@code String} object, converted to uppercase
	 */
	private String toUpperCase(boolean condition) {
		return String.valueOf(condition).toUpperCase();
	} // End of the toUpperCase method

	/**
	 * Show the permissions belonging to the current file.
	 * @param file the {@code File} object that represents the current file
	 */
	public void showPermissions(File file) {
		System.out.printf("- Permisos de lectura: %s\n", toUpperCase(file.canRead()));
		System.out.printf("- Permisos d'escriptura: %s\n", toUpperCase(file.canWrite()));
		System.out.printf("- Permisos d'execució: %s\n", toUpperCase(file.canExecute()));
	} // End of the showPermissions method

	/**
	 * Shows the date of creation, last modification and last access of the current file.
	 * @param file the {@code File} object that represents the current file
	 */
	public void showDateInformation(File file) {
		try {
			Path path = Paths.get(file.getAbsolutePath());

			BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

			FileTime creationDate = attributes.creationTime();
			FileTime lastAccessDate = attributes.lastAccessTime();
			FileTime lastModifiedDate = attributes.lastModifiedTime();

			System.out.printf("- Data de creació: %s\n", formatedDateTime(creationDate));
			System.out.printf("- Data de l'últim accés: %s\n", formatedDateTime(lastAccessDate));
			System.out.printf("- Data de l'última modificació: %s\n", formatedDateTime(lastModifiedDate));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} // End of the showDateInformation method

	/**
	 * Set the appropriate date format for the current file's timestamp attributes.
	 * @param fileTime represents the value of a file's time stamp attribute. For example, it may represent
	 * the time that the file was last modified, accessed, or created
	 * @return the {@code String} object that represents the selected attribute formatted with the specified date
	 */
	private String formatedDateTime(FileTime fileTime) {
		LocalDateTime localDateTime = fileTime
			.toInstant()
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
		return localDateTime.format(DATE_FORMATTER);
	} // End of the formatedDateTime method

} // End of the FileSystem class
