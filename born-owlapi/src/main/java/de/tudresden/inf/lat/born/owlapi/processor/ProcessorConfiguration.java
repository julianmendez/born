package de.tudresden.inf.lat.born.owlapi.processor;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class ProcessorConfiguration {

	private String ontologyFileName;
	private String bayesianNetworkFileName;
	private String queryFileName;
	private String outputFileName;
	private String problogDirectory;
	private boolean problogNeeded = true;
	private boolean showingLog = true;

	public String getOntologyFileName() {
		return ontologyFileName;
	}

	public void setOntologyFileName(String ontologyFileName) {
		this.ontologyFileName = ontologyFileName;
	}

	public String getBayesianNetworkFileName() {
		return bayesianNetworkFileName;
	}

	public void setBayesianNetworkFileName(String bayesianNetworkFileName) {
		this.bayesianNetworkFileName = bayesianNetworkFileName;
	}

	public String getQueryFileName() {
		return queryFileName;
	}

	public void setQueryFileName(String queryFileName) {
		this.queryFileName = queryFileName;
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
			return getOntologyFileName().equals(other.getOntologyFileName())
					&& getBayesianNetworkFileName().equals(
							other.getBayesianNetworkFileName())
					&& getQueryFileName().equals(other.getQueryFileName())
					&& getOutputFileName().equals(other.getOutputFileName())
					&& getProblogDirectory()
							.equals(other.getProblogDirectory())
					&& (isShowingLog() == other.isShowingLog())
					&& (isProblogNeeded() == other.isProblogNeeded());
		}
	}

	@Override
	public int hashCode() {
		return this.ontologyFileName.hashCode()
				+ 0x1F
				* (this.bayesianNetworkFileName.hashCode() + 0x1F * (this.queryFileName
						.hashCode() + 0x1F * (this.ontologyFileName.hashCode() + 0x1F * (this.problogDirectory
						.hashCode()))));
	}

	@Override
	public String toString() {
		return this.ontologyFileName.toString() + " "
				+ this.bayesianNetworkFileName.toString() + " "
				+ this.queryFileName.toString() + " "
				+ this.outputFileName.toString() + " "
				+ this.problogDirectory.toString() + " " + this.showingLog
				+ " " + this.problogNeeded;
	}

}
