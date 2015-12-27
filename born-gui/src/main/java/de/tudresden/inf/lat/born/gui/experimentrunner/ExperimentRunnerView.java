package de.tudresden.inf.lat.born.gui.experimentrunner;

import java.awt.event.ActionListener;

import de.tudresden.inf.lat.born.gui.common.JPanelView;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;

public interface ExperimentRunnerView extends JPanelView {

	/**
	 * Adds an action listener to the button to select the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonInputOntologyListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the Bayesian network.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonBayesianNetworkListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the output directory.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonOutputDirectoryListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to update the seed.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonUpdateSeedListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to compute the inference.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonComputeInferenceListener(ActionListener listener, String actionCommand);

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	MultiProcessorConfiguration getModel();

	/**
	 * Returns the directory of the input ontologies.
	 * 
	 * @return the directory of the input ontologies
	 */
	String getInputOntologyDirectory();

	/**
	 * Sets the directory of the input ontologies.
	 * 
	 * @param directoryName
	 *            directory
	 */
	void setInputOntologyDirectory(String directoryName);

	/**
	 * Returns the directory of the Bayesian networks.
	 * 
	 * @return the directory of the Bayesian networks
	 */
	String getBayesianNetworkDirectory();

	/**
	 * Set the directory of the Bayesian networks.
	 * 
	 * @param directoryName
	 *            directory
	 */
	void setBayesianNetworkDirectory(String directoryName);

	/**
	 * Returns the output directory.
	 * 
	 * @return the output directory
	 */
	String getOutputDirectory();

	/**
	 * Sets the output directory.
	 * 
	 * @param directoryName
	 *            directory
	 */
	void setOutputDirectory(String directoryName);

	/**
	 * Changes the state of the load button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	void setButtonLoadEnabled(boolean status);

	/**
	 * Changes the state of the compute button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	void setButtonComputeInferenceEnabled(boolean status);

	/**
	 * Updates the directory of ontologies.
	 */
	void updateInputOntologyDirectory();

	/**
	 * Updates the directory of Bayesian networks.
	 */
	void updateBayesianNetworkDirectory();

	/**
	 * Updates the output directory.
	 */
	void updateOutputDirectory();

	/**
	 * Updates the seed for the pseudorandom number generator.
	 */
	void updateSeed();

	/**
	 * Updates the number of queries.
	 */
	void updateNumberOfQueries();

	/**
	 * Updates the visual components.
	 */
	void update();

	/**
	 * Makes visible or invisible the label that shows that the reasoner is
	 * running.
	 * 
	 * @param status
	 *            value to define if the label is visible
	 */
	void setComputing(boolean status);

	/**
	 * Returns the number of queries.
	 * 
	 * @return the number of queries
	 */
	String getNumebrOfQueries();

	/**
	 * Returns the seed.
	 * 
	 * @return the seed
	 */
	String getSeed();

	/**
	 * Sets the seed for the pseudorandom number generator.
	 * 
	 * @param seed
	 *            the seed
	 */
	void setSeed(String seed);

	/**
	 * Changes the state of all buttons to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the buttons are enabled
	 */
	void setButtonsEnabled(boolean status);

}
