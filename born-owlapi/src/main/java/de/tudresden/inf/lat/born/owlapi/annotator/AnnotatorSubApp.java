package de.tudresden.inf.lat.born.owlapi.annotator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.term.SubApp;

/**
 * An object of this class add annotations with variables to an OWL ontology.
 * These variables are of the form x0, x1, x2, ... .
 * 
 * @see AnnotatorCore
 * 
 * @author Julian Mendez
 *
 */
public class AnnotatorSubApp implements SubApp {

	public static final String COLON_COLON = "::";
	public static final String POINT = ".";
	public static final String HELP = "Parameters: <input ontology> <output ontology> [<threshold> [<number of variables>] ]";
	public static final String COMMAND = "put";

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public boolean isValid(String[] args) {
		Objects.requireNonNull(args);
		return (2 <= args.length) && (args.length <= 4);
	}

	@Override
	public String run(String args[]) {
		Objects.requireNonNull(args);
		if (isValid(args)) {
			AnnotatorConfigurationImpl conf = new AnnotatorConfigurationImpl();

			double threshold = 1;
			if (args.length >= 3) {
				threshold = Double.parseDouble(args[2]);
			}
			conf.setThreshold(threshold);

			int maxNumberOfVars = Integer.MAX_VALUE;
			if (args.length >= 4) {
				maxNumberOfVars = Integer.parseInt(args[3]);
			}
			conf.setMaxNumberOfVars(maxNumberOfVars);

			try {
				InputStream in = new FileInputStream(args[0]);
				conf.setInputOntology(in);

				OutputStream outOnt = new FileOutputStream(args[1]);
				conf.setOutputOntology(outOnt);

				AnnotatorCore instance = new AnnotatorCore();
				instance.run(conf);

				in.close();
				outOnt.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return "Done.";
		} else {
			return getHelp();
		}
	}

}
