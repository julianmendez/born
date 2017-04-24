package de.tudresden.inf.lat.born.problog.connector;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import de.tudresden.inf.lat.born.core.term.ProbClause;
import de.tudresden.inf.lat.born.core.term.ProbClauseImpl;
import de.tudresden.inf.lat.born.core.term.Term;
import de.tudresden.inf.lat.born.core.term.TermImpl;

/**
 * An object of this class creates a Bayesian network using randomized values.
 * 
 * @author Julian Mendez
 *
 */
public class BayesianNetworkCreatorCore {

	public static final String VARIABLE_PREFIX = "x";
	public static final String NEGATION_PREFIX = "\\+";
	public static final int PRECISION = 2;
	public static final int PRECISION_PLUS_2 = PRECISION + 2;

	/**
	 * Constructs a Bayesian network creator.
	 */
	public BayesianNetworkCreatorCore() {
	}

	Term newTerm(int variableIndex, boolean isNegative) {
		return new TermImpl((isNegative ? NEGATION_PREFIX : "") + VARIABLE_PREFIX + variableIndex);
	}

	List<Integer> chooseDependencies(int variableIndex, int parents) {
		if ((variableIndex < 0) || (parents < 0) || (parents > variableIndex)) {
			throw new IllegalArgumentException("It is not possible to create dependencies for " + VARIABLE_PREFIX
					+ variableIndex + " with " + parents + " parents.");
		}

		List<Integer> chosen = IntStream.range(0, parents).mapToObj(x -> ((int) (Math.random() * variableIndex)))
				.distinct().collect(Collectors.toList());
		List<Integer> remaining = IntStream.range(chosen.size(), parents).mapToObj(x -> x).collect(Collectors.toList());
		List<Integer> ret = Stream.concat(chosen.stream(), remaining.stream()).collect(Collectors.toList());
		return ret;
	}

	ProbClause renderClause(int variableIndex, List<Integer> dependencies, List<Boolean> permutation,
			double probability) {
		if (dependencies.size() != permutation.size()) {
			throw new IllegalArgumentException("It is not possible to create a probabilistic clause with these values:"
					+ " variable index '" + variableIndex + "' with dependencies '" + dependencies + "' with state '"
					+ permutation + "'.");
		}

		Term head = newTerm(variableIndex, false);
		List<Term> body = IntStream.range(0, dependencies.size())
				.mapToObj(x -> newTerm(dependencies.get(x), permutation.get(x))).collect(Collectors.toList());

		ProbClause ret = new ProbClauseImpl(head, body, asAnnotation(probability));
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
		List<ProbClause> ret = new ArrayList<>();
		List<Integer> dependencies = chooseDependencies(variableIndex, parents);
		boolean valid = true;
		for (Permutation permutation = new Permutation(parents); valid; valid = permutation.computeNextPermutation()) {
			double probability = Math.random();
			ret.add(renderClause(variableIndex, dependencies, permutation.getPermutation(), probability));
		}
		return ret;
	}

	/**
	 * Returns a Bayesian network using the given variables.
	 * 
	 * @param variables
	 *            variables
	 * @return a Bayesian network using the given variables
	 */
	public List<ProbClause> createNetwork(List<Integer> variables) {
		Objects.requireNonNull(variables);
		List<ProbClause> ret = new ArrayList<>();
		int variableIndex = 0;
		for (int parents = 0; parents < variables.size(); parents++) {
			int n = variables.get(parents);
			for (int currVar = 0; currVar < n; currVar++) {
				List<ProbClause> current = computeDependencies(variableIndex, parents);
				ret.addAll(current);
				variableIndex++;
			}
		}
		return ret;
	}

	/**
	 * Writes a Bayesian network on the given writer.
	 * 
	 * @param writer
	 *            writer
	 * @param network
	 *            Bayesian network
	 * @throws IOException
	 *             if something went wrong with the I/O
	 */
	public void write(Writer writer, List<ProbClause> network) throws IOException {
		Objects.requireNonNull(writer);
		Objects.requireNonNull(network);
		BufferedWriter output = new BufferedWriter(writer);
		for (ProbClause clause : network) {
			output.append(clause.asString());
			output.newLine();
		}
		output.flush();
	}

	/**
	 * Runs the Bayesian network creator.
	 * 
	 * @param conf
	 *            configuration
	 */
	public void run(BayesianNetworkCreatorConfiguration conf) {
		Objects.requireNonNull(conf);
		try {
			List<ProbClause> network = createNetwork(conf.getDependencies());
			write(new OutputStreamWriter(conf.getOutput()), network);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
