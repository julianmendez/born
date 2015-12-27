package de.tudresden.inf.lat.born.gui;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

public interface BornModel {

	/**
	 * Returns the annotator configuration.
	 * 
	 * @return the annotator configuration
	 */
	AnnotatorConfiguration getAnnotatorConfiguration();

	/**
	 * Sets the annotator configuration.
	 * 
	 * @param annotatorConfiguration
	 *            annotator configuration
	 */
	void setAnnotatorConfiguration(AnnotatorConfiguration annotatorConfiguration);

	/**
	 * Returns the processor configuration.
	 * 
	 * @return the processor configuration
	 */
	ProcessorConfiguration getProcessorConfiguration();

	/**
	 * Sets the processor configuration.
	 * 
	 * @param processorConfiguration
	 *            processor configuration
	 */
	void setProcessorConfiguration(ProcessorConfiguration processorConfiguration);

	/**
	 * Returns the multi processor configuration.
	 * 
	 * @return the multi processor configuration
	 */
	MultiProcessorConfiguration getMultiProcessorConfiguration();

	/**
	 * Sets the multi processor configuration.
	 * 
	 * @param multiProcessorConfiguration
	 *            multi processor configuration
	 */
	void setMultiProcessorConfiguration(MultiProcessorConfiguration multiProcessorConfiguration);

}