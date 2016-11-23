package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class MultiProcessorConfigurationImpl implements MultiProcessorConfiguration {

	private List<OntologyAndNetwork> ontologyList = new ArrayList<>();
	private String outputDirectory;
	private Function<String, String> queryProcessor;
	private boolean showingLog = true;
	private int seed = 1;
	private int numberOfQueries = 1;

	@Override
	public List<OntologyAndNetwork> getOntologyList() {
		return ontologyList;
	}

	@Override
	public void setOntologyList(List<OntologyAndNetwork> ontologyList) {
		this.ontologyList = Objects.requireNonNull(ontologyList);
	}

	@Override
	public String getOutputDirectory() {
		return outputDirectory;
	}

	@Override
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = Objects.requireNonNull(outputDirectory);
	}

	@Override
	public int getNumberOfQueries() {
		return numberOfQueries;
	}

	@Override
	public void setNumberOfQueries(int numberOfQueries) {
		this.numberOfQueries = numberOfQueries;
	}

	@Override
	public int getSeed() {
		return seed;
	}

	@Override
	public void setSeed(int seed) {
		this.seed = seed;
	}

	@Override
	public Function<String, String> getQueryProcessor() {
		return queryProcessor;
	}

	@Override
	public void setQueryProcessor(Function<String, String> queryProcessor) {
		this.queryProcessor = Objects.requireNonNull(queryProcessor);
	}

	@Override
	public boolean isShowingLog() {
		return showingLog;
	}

	@Override
	public void setShowingLog(boolean showingLog) {
		this.showingLog = showingLog;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof AnnotatorConfiguration)) {
			return false;
		} else {
			MultiProcessorConfiguration other = (MultiProcessorConfiguration) obj;
			return getOntologyList().equals(other.getOntologyList())
					&& (getNumberOfQueries() == other.getNumberOfQueries()) && (getSeed() == other.getSeed())
					&& getOutputDirectory().equals(other.getOutputDirectory())
					&& getQueryProcessor().equals(other.getQueryProcessor())
					&& (isShowingLog() == other.isShowingLog());
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.ontologyList.size() + " ontologies, number of queries=" + this.numberOfQueries + ", seed="
				+ this.seed + " " + this.outputDirectory + " " + this.queryProcessor + " " + this.showingLog;
	}

}
