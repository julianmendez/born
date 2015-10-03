package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.InputStream;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorConfiguration {

	private InputStream ontologyInputStream;
	private InputStream bayesianNetworkInputStream;
	private String query;
	private String outputFileName;
	private String problogDirectory;
	private boolean problogNeeded = true;
	private boolean showingLog = true;

	public InputStream getOntologyInputStream() {
		return ontologyInputStream;
	}

	public void setOntologyInputStream(InputStream ontologyInputStream) {
		this.ontologyInputStream = ontologyInputStream;
	}

	public InputStream getBayesianNetworkInputStream() {
		return bayesianNetworkInputStream;
	}

	public void setBayesianNetworkInputStream(InputStream bayesianNetworkInputStream) {
		this.bayesianNetworkInputStream = bayesianNetworkInputStream;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
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
			ProcessorConfiguration other = (ProcessorConfiguration) obj;
			return getOntologyInputStream().equals(other.getOntologyInputStream())
					&& getBayesianNetworkInputStream().equals(other.getBayesianNetworkInputStream())
					&& getQuery().equals(other.getQuery())
					&& getOutputFileName().equals(other.getOutputFileName())
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
		return this.ontologyInputStream + " " + this.bayesianNetworkInputStream + " " + this.query + " "
				+ this.outputFileName + " " + this.problogDirectory + " " + this.showingLog + " " + this.problogNeeded;
	}

}
