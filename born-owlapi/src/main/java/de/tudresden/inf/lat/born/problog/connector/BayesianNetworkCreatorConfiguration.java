package de.tudresden.inf.lat.born.problog.connector;

import java.io.OutputStream;
import java.util.List;

/**
 * This interface models the configuration of a Bayesian network creator.
 * 
 * @author Julian Mendez
 *
 */
public interface BayesianNetworkCreatorConfiguration {

	/**
	 * Returns the dependencies.
	 * 
	 * @return the dependencies
	 */
	List<Integer> getDependencies();

	/**
	 * Sets the dependencies.
	 * 
	 * @param dependencies
	 *            dependencies
	 */
	void setDependencies(List<Integer> dependencies);

	/**
	 * Returns the output.
	 * 
	 * @return the output
	 */
	OutputStream getOutput();

	/**
	 * Sets the output.
	 * 
	 * @param output
	 *            output
	 */
	void setOutput(OutputStream output);

}
