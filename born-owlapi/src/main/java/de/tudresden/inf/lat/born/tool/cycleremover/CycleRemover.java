package de.tudresden.inf.lat.born.tool.cycleremover;

import java.util.Objects;

/**
 * This is an executable class to remove cycles from an ontology.
 * 
 * @author Julian Mendez
 *
 */
public class CycleRemover {

	public static final String HELP = "Parameters: \n" + //
			"  <ontology file> <output file>: removes the cycles in a given ontology \n";

	public CycleRemover() {
	}

	public static void main(String[] args) {
		Objects.requireNonNull(args);
		if (args.length == 2) {

		} else {
			System.out.println(HELP);

		}
	}

}
