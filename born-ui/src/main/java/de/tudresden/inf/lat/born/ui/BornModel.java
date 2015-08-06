package de.tudresden.inf.lat.born.ui;

import java.io.File;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BornModel {

	private OWLOntology inputOntology = null;
	private File inputOntologyFile = null;
	private String bayesianNetworkFileName = null;
	private String query = null;
	private String output = null;

	public File getInputOntologyFile() {
		return this.inputOntologyFile;
	}

	public void setInputOntologyFile(File ontologyFileName) {
		this.inputOntologyFile = ontologyFileName;
	}

	public OWLOntology getInputOntology() {
		return this.inputOntology;
	}

	public void setInputOntology(OWLOntology ontology) {
		this.inputOntology = ontology;
	}

	public String getBayesianNetworkFileName() {
		return this.bayesianNetworkFileName;
	}

	public void setBayesianNetworkFileName(String bayesianNetworkFileName) {
		this.bayesianNetworkFileName = bayesianNetworkFileName;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
