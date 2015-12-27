package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;

/**
 * 
 * This interface models the test maker view.
 * 
 * @author Julian Mendez
 *
 */
public interface TestMakerView {

	/**
	 * Adds an action listener to the button to select the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonSelectInputOntologyFileListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the output ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonSelectOutputOntologyFileListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to save the Bayesian network.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonSaveBayesianNetworkListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the text field of the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addTextFieldInputOntologyFileListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the text field of the list of parents.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addTextFieldListOfParentsListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the text field of the threshold.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addTextFieldThresholdListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the text field of the maximum number of
	 * variables.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addTextFieldMaxNumberOfVarListener(ActionListener listener, String actionCommand);

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	AnnotatorConfiguration getModel();

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
	 * Updates the ontology file.
	 */
	void updateOntologyFile();

	/**
	 * Updates the threshold.
	 */
	void updateThreshold();

	/**
	 * Updates the maximum number of variables.
	 */
	void updateMaxNumberOfVars();

	/**
	 * Returns the input ontology file.
	 * 
	 * @return the input ontology file
	 */
	String getInputOntologyFile();

	/**
	 * Sets the input ontology file.
	 * 
	 * @param fileName
	 *            file name
	 */
	void setInputOntologyFile(String fileName);

	/**
	 * Returns the threshold.
	 * 
	 * @return the threshold
	 */
	String getThreshold();

	/**
	 * Sets the threshold.
	 * 
	 * @param threshold
	 *            the threshold
	 */
	void setThreshold(String threshold);

	/**
	 * Returns the maximum number of variables.
	 * 
	 * @return the maximum number of variables
	 */
	String getMaxNumberOfVar();

	/**
	 * Sets the maximum number of variables.
	 * 
	 * @param maxNumberOfVars
	 *            the maximum number of variables
	 */
	void setMaxNumberOfVar(String maxNumberOfVars);

	/**
	 * Returns the list of parents.
	 * 
	 * @return the list of parents
	 */
	String getListOfParents();

	/**
	 * Sets the list of parents.
	 * 
	 * @param listOfParents
	 *            the list of parents
	 */
	void setListOfParents(String listOfParents);

	/**
	 * Returns the panel.
	 * 
	 * @return the panel
	 */
	JPanel getPanel();

}