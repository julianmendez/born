package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.QueryProcessor;

/**
 * 
 * @author Julian Mendez
 *
 */
public class MultiProcessorConfiguration {

	private List<OntologyAndNetwork> ontologyList = new ArrayList<>();
	private String outputDirectory;
	private QueryProcessor queryProcessor;
	private boolean showingLog = true;
	private int seed = 1;
	private int numberOfQueries = 1;

	public List<OntologyAndNetwork> getOntologyList() {
		return ontologyList;
	}

	public void setOntologyList(List<OntologyAndNetwork> ontologyList) {
		Objects.requireNonNull(ontologyList);
		this.ontologyList = ontologyList;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(String outputDirectory) {
		Objects.requireNonNull(outputDirectory);
		this.outputDirectory = outputDirectory;
	}

	public int getNumberOfQueries() {
		return numberOfQueries;
	}

	public void setNumberOfQueries(int numberOfQueries) {
		this.numberOfQueries = numberOfQueries;
	}

	public int getSeed() {
		return seed;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public QueryProcessor getQueryProcessor() {
		return queryProcessor;
	}

	public void setQueryProcessor(QueryProcessor queryProcessor) {
		Objects.requireNonNull(queryProcessor);
		this.queryProcessor = queryProcessor;
	}

	public boolean isShowingLog() {
		return showingLog;
	}

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
