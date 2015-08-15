package de.tudresden.inf.lat.born.problog.connector;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import de.tudresden.inf.lat.born.core.term.ProbClause;
import de.tudresden.inf.lat.born.core.term.ProbClauseImpl;
import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.born.core.term.Term;
import de.tudresden.inf.lat.born.core.term.TermImpl;

/**
 * An object of this class creates a Bayesian network using randomized values.
 * 
 * @author Julian Mendez
 *
 */
public class BayesianNetworkCreator implements SubApp {

	public static final String VARIABLE_PREFIX = "x";
	public static final String NEGATION_PREFIX = "\\+";
	public static final int PRECISION = 2;
	public static final int PRECISION_PLUS_2 = PRECISION + 2;
	public static final String HELP = "Parameters: <list of parents> <output file>"
			+ "\n\nExample of parameters: \"1,1,2,3,5,8\" network.pl"
			+ "\n\nThe example creates a Bayesian network with:"
			+ "\n  1 independent variable (no parents),"
			+ "\n  1 variable with 1 parent,"
			+ "\n  2 variables with 2 parents,"
			+ "\n  3 variables with 3 parents,"
			+ "\n  5 variables with 4 parents,"
			+ "\n  and 8 variables with 5 parents.";

	public BayesianNetworkCreator() {
	}

	Term newTerm(int variableIndex, boolean isNegative) {
		return new TermImpl((isNegative ? NEGATION_PREFIX : "")
				+ VARIABLE_PREFIX + variableIndex);
	}

	List<Integer> chooseDependencies(int variableIndex, int parents) {
		if ((variableIndex < 0) || (parents < 0) || (parents > variableIndex)) {
			throw new IllegalArgumentException(
					"It is not possible to create dependencies for "
							+ VARIABLE_PREFIX + variableIndex + " with "
							+ parents + " parents.");
		}

		Set<Integer> chosen = new TreeSet<Integer>();
		for (int index = 0; index < parents; index++) {
			double dependency = Math.random() * variableIndex;
			chosen.add((int) dependency);
		}

		for (int index = 0; (chosen.size() < parents) && (index < parents); index++) {
			chosen.add(index);
		}

		List<Integer> ret = new ArrayList<Integer>();
		ret.addAll(chosen);
		return ret;
	}

	ProbClause renderClause(int variableIndex, List<Integer> dependencies,
			List<Boolean> permutation, double probability) {
		if (dependencies.size() != permutation.size()) {
			throw new IllegalArgumentException(
					"It is not possible to create a probabilistic clause with these values:"
							+ " variable index '" + variableIndex
							+ "' with dependencies '" + dependencies
							+ "' with state '" + permutation + "'.");
		}

		Term head = newTerm(variableIndex, false);
		List<Term> body = new ArrayList<Term>();
		for (int index = 0; (index < dependencies.size()); index++) {
			int dependencyIndex = dependencies.get(index);
			boolean isNegative = permutation.get(index);
			body.add(newTerm(dependencyIndex, isNegative));
		}

		ProbClause ret = new ProbClauseImpl(head, body,
				asAnnotation(probability));
		return ret;
	}

	String asAnnotation(double probability) {
		String ret = "" + probability;
		if (ret.length() > PRECISION_PLUS_2) {
			ret = ret.substring(0, PRECISION_PLUS_2);
		}
		return ret;
	}

	List<ProbClause> computeDependencies(int variableIndex, int parents) {
		List<ProbClause> ret = new ArrayList<ProbClause>();
		List<Integer> dependencies = chooseDependencies(variableIndex, parents);
		boolean valid = true;
		for (Permutation permutation = new Permutation(parents); valid; valid = permutation
				.computeNextPermutation()) {
			double probability = Math.random();
			ret.add(renderClause(variableIndex, dependencies,
					permutation.getPermutation(), probability));
		}
		return ret;
	}

	public List<ProbClause> createNetwork(List<Integer> variables) {
		List<ProbClause> ret = new ArrayList<ProbClause>();
		int variableIndex = 0;
		for (int parents = 0; parents < variables.size(); parents++) {
			int n = variables.get(parents);
			for (int currVar = 0; currVar < n; currVar++) {
				List<ProbClause> current = computeDependencies(variableIndex,
						parents);
				ret.addAll(current);
				variableIndex++;
			}
		}
		return ret;
	}

	public void write(Writer writer, List<ProbClause> network)
			throws IOException {
		BufferedWriter output = new BufferedWriter(writer);
		for (ProbClause clause : network) {
			output.append(clause.asString());
			output.newLine();
		}
		output.flush();
	}

	boolean isValidInput(List<Integer> variables) {
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
		List<Integer> ret = new ArrayList<Integer>();
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

	public void run(BayesianNetworkCreatorConfiguration conf) {
		try {
			List<ProbClause> network = createNetwork(conf.getDependencies());
			write(new OutputStreamWriter(conf.getOutput()), network);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String run(String[] args) {
		if (isValid(args)) {
			try {
				BayesianNetworkCreatorConfiguration conf = new BayesianNetworkCreatorConfiguration();

				List<Integer> dependencies = parseIntegers(args[0]);
				conf.setDependencies(dependencies);

				OutputStream output = new FileOutputStream(args[1]);
				conf.setOutput(output);

				run(conf);

				output.close();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return "Done.";
		} else {
			return getHelp();
		}
	}

}
