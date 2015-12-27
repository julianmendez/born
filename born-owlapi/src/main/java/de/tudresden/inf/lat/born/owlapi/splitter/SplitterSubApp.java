package de.tudresden.inf.lat.born.owlapi.splitter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.term.SubApp;

/**
 * An object of this class splits a probabilistic OWL ontology in two parts: an
 * OWL ontology with variables, and a Bayesian network.
 * 
 * @see SplitterCore
 * 
 * @author Julian Mendez
 *
 */
public class SplitterSubApp implements SubApp {

	public static final String HELP = "Parameters: <input ontology> <output ontology> <Bayesian network>";
	public static final String COMMAND = "split";

	/**
	 * Constructs a new splitter.
	 */
	public SplitterSubApp() {
	}

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public boolean isValid(String args[]) {
		Objects.requireNonNull(args);
		return (args.length == 3);
	}

	@Override
	public String run(String args[]) {
		Objects.requireNonNull(args);
		if (isValid(args)) {
			try {
				SplitterConfigurationImpl conf = new SplitterConfigurationImpl();

				InputStream in = new FileInputStream(args[0]);
				conf.setInputOntology(in);

				OutputStream outOnt = new FileOutputStream(args[1]);
				conf.setOutputOntology(outOnt);

				OutputStream outNet = new FileOutputStream(args[2]);
				conf.setBayesianNetwork(outNet);

				SplitterCore core = new SplitterCore();
				core.run(conf);

				in.close();
				outNet.close();
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
