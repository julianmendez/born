package de.tudresden.inf.lat.born.gui;

import org.semanticweb.owlapi.model.OWLOntology;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.splitter.SplitterConfiguration;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorConfiguration;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BornModel {

	private AnnotatorConfiguration annotatorConfiguration = new AnnotatorConfiguration();
	private ProcessorConfiguration processorConfiguration = new ProcessorConfiguration();
	private SplitterConfiguration splitterConfiguration = new SplitterConfiguration();
	private BayesianNetworkCreatorConfiguration bayesianNetworkCreatorConfiguration = new BayesianNetworkCreatorConfiguration();
	private MultiProcessorConfiguration multiProcessorConfiguration = new MultiProcessorConfiguration();

	private OWLOntology inputOntology = null;
	private String output = null;

	public AnnotatorConfiguration getAnnotatorConfiguration() {
		return annotatorConfiguration;
	}

	public void setAnnotatorConfiguration(AnnotatorConfiguration annotatorConfiguration) {
		this.annotatorConfiguration = annotatorConfiguration;
	}

	public ProcessorConfiguration getProcessorConfiguration() {
		return processorConfiguration;
	}

	public void setProcessorConfiguration(ProcessorConfiguration processorConfiguration) {
		this.processorConfiguration = processorConfiguration;
	}

	public SplitterConfiguration getSplitterConfiguration() {
		return splitterConfiguration;
	}

	public void setSplitterConfiguration(SplitterConfiguration splitterConfiguration) {
		this.splitterConfiguration = splitterConfiguration;
	}

	public BayesianNetworkCreatorConfiguration getBayesianNetworkCreatorConfiguration() {
		return bayesianNetworkCreatorConfiguration;
	}

	public void setBayesianNetworkCreatorConfiguration(
			BayesianNetworkCreatorConfiguration bayesianNetworkCreatorConfiguration) {
		this.bayesianNetworkCreatorConfiguration = bayesianNetworkCreatorConfiguration;
	}

	public MultiProcessorConfiguration getMultiProcessorConfiguration() {
		return multiProcessorConfiguration;
	}

	public void setMultiProcessorConfiguration(MultiProcessorConfiguration multiProcessorConfiguration) {
		this.multiProcessorConfiguration = multiProcessorConfiguration;
	}

	public OWLOntology getInputOntology() {
		return this.inputOntology;
	}

	public void setInputOntology(OWLOntology ontology) {
		this.inputOntology = ontology;
	}

	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
