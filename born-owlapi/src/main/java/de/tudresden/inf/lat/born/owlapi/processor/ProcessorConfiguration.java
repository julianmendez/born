package de.tudresden.inf.lat.born.owlapi.processor;

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

}
