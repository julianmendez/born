package de.tudresden.inf.lat.born.problog.connector;

import java.io.OutputStream;
import java.util.List;

public interface BayesianNetworkCreatorConfiguration {

	List<Integer> getDependencies();

	void setDependencies(List<Integer> dependencies);

	OutputStream getOutput();

	void setOutput(OutputStream output);

}
