package Activitat1;

public class UserInterface {

	private String repeatedCharacters;

	/**
	 * Establish a line composed of repeated characters.
	 * @param e the character that will be repeated
	 * @param x the number of times that the selected character will be repeated
	 */
	public void setLine(char e, int x) {
		char character = e;
		String str = Character.toString(character);
		this.repeatedCharacters = str.repeat(x);
	} // End of the setLine method

	/**
	 * Get a line composed of repeated characters.
	 * @return the {@code String} object that represents a line composed of
	 * repeated characters.
	 */
	public String getLine() {
		return repeatedCharacters;
	} // End of the getLine method

	/**
	 * Print a line composed of repeated characters in the terminal.
	 */
	public void printLine() {
		System.out.println(getLine());
	} // End of the printLine method

	/**
	 * Show the program title.
	 * @param title the {@code String} object that represents the title that will
	 * be displayed in the terminal.
	 */
	public void showTitle(String title) {
		String format = "%s\n%s\n%s\n";

		if (title.length() < 14) {
			setLine('=', title.length());
		} else {
			setLine('=', title.length() * 2 - 8);
			format = "%s\n" + "%" + ((title.length() + title.length() / 2) - 4) + "s\n%s\n";
		}

		System.out.printf(format, getLine(), title, getLine());
	} // End of the showTitle method

} // End of the UserInterface class
