package de.tudresden.inf.lat.born.problog.connector;

import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

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
		Objects.requireNonNull(dependencies);
		this.dependencies = dependencies;
	}

	public OutputStream getOutput() {
		return output;
	}

	public void setOutput(OutputStream output) {
		Objects.requireNonNull(output);
		this.output = output;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof BayesianNetworkCreatorConfiguration)) {
			return false;
		} else {
			BayesianNetworkCreatorConfiguration other = (BayesianNetworkCreatorConfiguration) obj;
			return getDependencies().equals(other.getDependencies()) && getOutput().equals(other.getOutput());
		}
	}

	@Override
	public int hashCode() {
		return this.dependencies.hashCode() + 0x1F * this.output.hashCode();
	}

	@Override
	public String toString() {
		return this.dependencies.toString() + " " + this.output.toString();
	}

}
