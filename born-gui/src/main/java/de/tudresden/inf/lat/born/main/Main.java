package de.tudresden.inf.lat.born.main;

import de.tudresden.inf.lat.born.owlapi.main.BornMain;

/**
 * This is the main class. An object of this class starts the application with
 * the given parameters. This can start both: the command line application, and
 * the visual application.
 * 
 * @author Julian Mendez
 *
 */
public class Main {

	/**
	 * Constructs a new instance.
	 */
	public Main() {
	}

	/**
	 * Starts the application with the given command line parameters. If no
	 * parameters are given, this method starts the graphical visual interface.
	 * This method returns the result of the execution, if the command line was
	 * run, or an empty string, if the graphical user interface was run
	 * 
	 * @param args
	 *            parameters
	 * @return the result of the execution, if the command line was run, or an
	 *         empty string, if the graphical user interface was run
	 */
	public String run(String[] args) {
		if (args.length == 0) {
			BornStandalone standalone = new BornStandalone();
			standalone.run();
			return "";
		} else {
			BornMain consoleMain = new BornMain();
			return consoleMain.run(args);
		}
	}

	/**
	 * Starts the application from the command line, and outputs the result, if
	 * there is any result.
	 * 
	 * @param args
	 *            parameters
	 */
	public static void main(String[] args) {
		Main instance = new Main();
		System.out.println(instance.run(args));
	}

}
