package de.tudresden.inf.lat.born.gui;

import java.util.Date;
import java.util.Objects;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfigurationImpl;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfigurationImpl;
import de.tudresden.inf.lat.born.owlapi.processor.ProblogProcessor;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

/**
 * This is the model of the graphical user interface.
 * 
 * @author Julian Mendez
 *
 */
public class BornModel {

	private ProcessorConfigurationImpl processorConfiguration = new ProcessorConfigurationImpl();
	private AnnotatorConfiguration annotatorConfiguration = new AnnotatorConfigurationImpl();
	private MultiProcessorConfiguration multiProcessorConfiguration = new MultiProcessorConfigurationImpl();

	/**
	 * Constructs a new BORN model.
	 */
	public BornModel() {
		ProblogProcessor queryProcessor = new ProblogProcessor();
		this.processorConfiguration.setQueryProcessor(queryProcessor);
		this.multiProcessorConfiguration.setQueryProcessor(queryProcessor);
		queryProcessor.startInstallation((new Date().getTime()));
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
		Objects.requireNonNull(annotatorConfiguration);
		this.annotatorConfiguration = annotatorConfiguration;
	}

	/**
	 * Returns the processor configuration.
	 * 
	 * @return the processor configuration
	 */
	public ProcessorConfigurationImpl getProcessorConfiguration() {
		return processorConfiguration;
	}

	/**
	 * Sets the processor configuration.
	 * 
	 * @param processorConfiguration
	 *            processor configuration
	 */
	public void setProcessorConfiguration(ProcessorConfigurationImpl processorConfiguration) {
		Objects.requireNonNull(processorConfiguration);
		this.processorConfiguration = processorConfiguration;
	}

	/**
	 * Returns the multi processor configuration.
	 * 
	 * @return the multi processor configuration
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
		Objects.requireNonNull(multiProcessorConfiguration);
		this.multiProcessorConfiguration = multiProcessorConfiguration;
	}

}
