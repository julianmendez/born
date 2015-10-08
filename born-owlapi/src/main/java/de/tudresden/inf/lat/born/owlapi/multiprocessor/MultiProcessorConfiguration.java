package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.ArrayList;
import java.util.List;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.OntologyAndNetwork;

/**
 * 
 * @author Julian Mendez
 *
 */
public class MultiProcessorConfiguration {

	private List<OntologyAndNetwork> ontologyList = new ArrayList<OntologyAndNetwork>();
	private List<String> outputFileNameList;
	private String problogDirectory;
	private boolean problogNeeded = true;
	private boolean showingLog = true;
	private int seed = 1;
	private int numberOfQueries = 1;

	public List<OntologyAndNetwork> getOntologyList() {
		return ontologyList;
	}

	public void setOntologyList(List<OntologyAndNetwork> ontologyList) {
		this.ontologyList = ontologyList;
	}

	public List<String> getOutputFileNameList() {
		return outputFileNameList;
	}

	public void setOutputFileNameList(List<String> outputFileNameList) {
		this.outputFileNameList = outputFileNameList;
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

	public String getProblogDirectory() {
		return problogDirectory;
	}

	public void setProblogDirectory(String problogDirectory) {
		this.problogDirectory = problogDirectory;
	}

	public boolean isShowingLog() {
		return showingLog;
	}

	public void setShowingLog(boolean showingLog) {
		this.showingLog = showingLog;
	}

	public boolean isProblogNeeded() {
		return problogNeeded;
	}

	public void setProblogNeeded(boolean problogNeeded) {
		this.problogNeeded = problogNeeded;
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
					&& getOutputFileNameList().equals(other.getOutputFileNameList())
					&& getProblogDirectory().equals(other.getProblogDirectory())
					&& (isShowingLog() == other.isShowingLog()) && (isProblogNeeded() == other.isProblogNeeded());
		}
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public String toString() {
		return this.ontologyList.size() + " ontologies, number of queries=" + this.numberOfQueries + ", seed="
				+ this.seed + " " + this.outputFileNameList + " " + this.problogDirectory + " " + this.showingLog + " "
				+ this.problogNeeded;
	}

}
