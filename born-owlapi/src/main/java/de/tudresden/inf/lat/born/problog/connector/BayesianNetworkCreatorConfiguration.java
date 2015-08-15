package de.tudresden.inf.lat.born.problog.connector;

import java.io.OutputStream;
import java.util.List;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BayesianNetworkCreatorConfiguration {

	private List<Integer> dependencies;
	private OutputStream output;

	public List<Integer> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<Integer> dependencies) {
		this.dependencies = dependencies;
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		this.output = output;
	}


}
