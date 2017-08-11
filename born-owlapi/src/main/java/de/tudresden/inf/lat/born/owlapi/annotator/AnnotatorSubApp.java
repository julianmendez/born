package de.tudresden.inf.lat.born.owlapi.annotator;

import de.tudresden.inf.lat.born.core.common.ResourceUtil;
import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

import java.io.*;
import java.util.Objects;
import java.util.Set;

/**
 * An object of this class add annotations with variables to an OWL ontology.
 * These variables are of the form x0, x1, x2, ... .
 *
 * @author Julian Mendez
 * @see AnnotatorCore
 */
public class AnnotatorSubApp implements SubApp {

	public static final String COLON_COLON = "::";
	public static final String POINT = ".";
	public static final String HELP = "Parameters: <input ontology> <output ontology> [<threshold> <input Bayesian network>]";
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
		String result = "";
		if (isValid(args)) {
			AnnotatorConfiguration conf = new AnnotatorConfigurationImpl();

			double threshold = 1;
			if (args.length >= 3) {
				threshold = Double.parseDouble(args[2]);
			}
			conf.setThreshold(threshold);

			try {
				InputStream in = new FileInputStream(args[0]);
				conf.setInputOntology(in);

				Set<String> bayesianNetworkVariables = AnnotationCreator
						.extractVariables(ProcessorConfigurationImpl.read(new FileReader(args[3])));
				conf.setInputBayesianNetworkVariables(bayesianNetworkVariables);

				OutputStream outOnt = new FileOutputStream(ResourceUtil.ensurePath(args[1]));
				conf.setOutputOntology(outOnt);

				AnnotatorCore instance = new AnnotatorCore();
				instance.run(conf);

				in.close();
				outOnt.close();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			result = "Done.";
		} else {
			result = getHelp();
		}

		return result;
	}

}
