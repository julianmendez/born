package de.tudresden.inf.lat.born.gui;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This is the model of the BORN graphical user interface.
 * 
 * @author Julian Mendez
 *
 */
public class BornModel {

	private ProcessorConfiguration processorConfiguration = new ProcessorConfiguration();
	private AnnotatorConfiguration annotatorConfiguration = new AnnotatorConfiguration();
	private MultiProcessorConfiguration multiProcessorConfiguration = new MultiProcessorConfiguration();

	/**
	 * Constructs a new BORN model.
	 */
	public BornModel() {
	}

	/**
	 * Returns the annotator configuration.
	 * 
	 * @return the annotator configuration
	 */
	public AnnotatorConfiguration getAnnotatorConfiguration() {
		return annotatorConfiguration;
	}

	/**
	 * Sets the annotator configuration.
	 * 
	 * @param annotatorConfiguration
	 *            annotator configuration
	 */
	public void setAnnotatorConfiguration(AnnotatorConfiguration annotatorConfiguration) {
		this.annotatorConfiguration = annotatorConfiguration;
	}

	/**
	 * Returns the processor configuration.
	 * 
	 * @return the processor configuration
	 */
	public ProcessorConfiguration getProcessorConfiguration() {
		return processorConfiguration;
	}

	/**
	 * Sets the processor configuration.
	 * 
	 * @param processorConfiguration
	 *            processor configuration
	 */
	public void setProcessorConfiguration(ProcessorConfiguration processorConfiguration) {
		this.processorConfiguration = processorConfiguration;
	}

	/**
	 * Returns the multi processor configuration.
	 * 
	 * @return
	 */
	public MultiProcessorConfiguration getMultiProcessorConfiguration() {
		return multiProcessorConfiguration;
	}

	/**
	 * Sets the multi processor configuration.
	 * 
	 * @param multiProcessorConfiguration
	 *            multi processor configuration
	 */
	public void setMultiProcessorConfiguration(MultiProcessorConfiguration multiProcessorConfiguration) {
		this.multiProcessorConfiguration = multiProcessorConfiguration;
	}

}
