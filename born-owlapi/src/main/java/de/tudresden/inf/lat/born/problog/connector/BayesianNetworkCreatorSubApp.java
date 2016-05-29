package de.tudresden.inf.lat.born.problog.connector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

import de.tudresden.inf.lat.born.core.common.ResourceUtil;
import de.tudresden.inf.lat.born.core.term.SubApp;

/**
 * An object of this class creates a Bayesian network using randomized values.
 * 
 * @see BayesianNetworkCreatorCore
 * 
 * @author Julian Mendez
 *
 */
public class BayesianNetworkCreatorSubApp implements SubApp {

	public static final String COMMAND = "link";
	public static final String HELP = "Parameters: <list of parents> <output file>"
			+ "\n\nExample of parameters: \"1,1,2,3,5,8\" network.pl"
			+ "\n\nThe example creates a Bayesian network with:" + "\n  1 independent variable (no parents),"
			+ "\n  1 variable with 1 parent," + "\n  2 variables with 2 parents," + "\n  3 variables with 3 parents,"
			+ "\n  5 variables with 4 parents," + "\n  and 8 variables with 5 parents.";

	public BayesianNetworkCreatorSubApp() {
	}

	boolean isValidInput(List<Integer> variables) {
		Objects.requireNonNull(variables);
		int accumVars = 0;
		boolean isValid = true;
		for (int parents = 0; isValid && (parents < variables.size()); parents++) {
			isValid = isValid && (parents <= accumVars);
			int current = variables.get(parents);
			accumVars += current;
		}
		return isValid;
	}

	List<Integer> parseIntegers(String listAsStr) {
		List<Integer> ret = new ArrayList<>();
		StringTokenizer stok = new StringTokenizer(listAsStr, ",");
		while (stok.hasMoreTokens()) {
			String value = stok.nextToken().trim();
			ret.add(Integer.parseInt(value));
		}
		return ret;
	}

	@Override
	public String getHelp() {
		return HELP;
	}

	@Override
	public boolean isValid(String[] args) {
		if (args.length == 2) {
			List<Integer> dependencies = parseIntegers(args[0]);
			return isValidInput(dependencies);
		} else {
			return false;
		}
	}

	@Override
	public String run(String[] args) {
		if (isValid(args)) {
			try {
				BayesianNetworkCreatorConfiguration conf = new BayesianNetworkCreatorConfigurationImpl();

				List<Integer> dependencies = parseIntegers(args[0]);
				conf.setDependencies(dependencies);

				OutputStream output = new FileOutputStream(ResourceUtil.ensurePath(args[1]));
				conf.setOutput(output);

				BayesianNetworkCreatorCore core = new BayesianNetworkCreatorCore();
				core.run(conf);

				output.close();

			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
			return "Done.";
		} else {
			return getHelp();
		}
	}

}
