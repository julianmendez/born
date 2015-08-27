package de.tudresden.inf.lat.born.main;

import de.tudresden.inf.lat.born.owlapi.main.BornMain;

/**
 * 
 * @author Julian Mendez
 *
 */
public class Main {

	public Main() {
	}

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

	public static void main(String[] args) {
		Main instance = new Main();
		System.out.println(instance.run(args));
	}

}
