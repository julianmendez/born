package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionListener;
import java.util.Collection;

import de.tudresden.inf.lat.born.gui.common.JPanelView;
import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This interface models the processor view.
 * 
 * @author Julian Mendez
 *
 */
public interface ProcessorView extends JPanelView {

	/**
	 * Adds an action listener to the button to select the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonOntologyFileListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the Bayesian network.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonBayesianNetworkFileListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to reset the completion rules.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonResetCompletionRulesListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to go to the previous completion
	 * rules.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonGoToPreviousCompletionRulesListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the console input.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonConsoleInputListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to select the console output.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonConsoleOutputListener(ActionListener listener, String actionCommand);

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
	 * Adds an action listener to the combo box to select the example.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addComboBoxExampleListener(ActionListener listener, String actionCommand);

	/**
	 * Adds an action listener to the button to update the selected example.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	void addButtonUpdateExampleListener(ActionListener listener, String actionCommand);

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	ProcessorConfiguration getModel();

	/**
	 * Returns the ontology file.
	 * 
	 * @return the ontology file
	 */
	String getOntologyFile();

	/**
	 * Sets the ontology file
	 * 
	 * @param fileName
	 *            file name
	 */
	void setOntologyFile(String fileName);

	/**
	 * Returns the Bayesian network file.
	 * 
	 * @return the Bayesian network file
	 */
	String getBayesianNetworkFile();

	/**
	 * Sets the Bayesian network file
	 * 
	 * @param fileName
	 *            file name
	 */
	void setBayesianNetworkFile(String fileName);

	/**
	 * Returns the completion rules.
	 * 
	 * @return the completion rules
	 */
	String getCompletionRules();

	/**
	 * Sets the completion rules.
	 * 
	 * @param text
	 *            text containing completion rules in ProbLog
	 */
	void setCompletionRules(String text);

	/**
	 * Returns the console input.
	 * 
	 * @return the console input
	 */
	String getConsoleInput();

	/**
	 * Sets the console input.
	 * 
	 * @param text
	 *            text
	 */
	void setConsoleInput(String text);

	/**
	 * Reads the console input file.
	 * 
	 * @param consoleInputFile
	 *            console input file
	 */
	void readConsoleInput(String consoleInputFile);

	/**
	 * Writes the console output fle.
	 * 
	 * @param consoleOutputFile
	 *            console output file
	 */
	void writeConsoleOutput(String consoleOutputFile);

	/**
	 * Returns the console oputput.
	 * 
	 * @return the console output
	 */
	String getConsoleOutput();

	/**
	 * Sets the console output.
	 * 
	 * @param text
	 *            text
	 */
	void setConsoleOutput(String text);

	/**
	 * Changes the state of the button to load the ontology between enabled and
	 * disabled.
	 * 
	 * @param status
	 *            status (<code>true</code> if and only if the button is
	 *            enabled)
	 */
	void setButtonLoadEnabled(boolean status);

	/**
	 * Changes the state of the button to compute inference between enabled and
	 * disabled.
	 * 
	 * @param status
	 *            status (<code>true</code> if and only if the button is
	 *            enabled)
	 */
	void setButtonComputeInferenceEnabled(boolean status);

	/**
	 * Changes the state of the combo box to select the examples between enabled
	 * and disabled.
	 * 
	 * @param status
	 *            status (<code>true</code> if and only if the button is
	 *            enabled)
	 */
	void setComboBoxExampleEnabled(boolean status);

	/**
	 * Returns the index of the selected example.
	 * 
	 * @return the index of the selected example
	 */
	int getComboBoxExampleIndex();

	/**
	 * Updates the completion rules.
	 */
	void updateCompletionRules();

	/**
	 * Updates the ontology file.
	 */
	void updateOntologyFile();

	/**
	 * Updates the Bayesian network file.
	 */
	void updateBayesianNetworkFile();

	/**
	 * Updates the query.
	 */
	void updateQuery();

	/**
	 * Sets the result.
	 * 
	 * @param result
	 *            result
	 */
	void setResult(String result);

	/**
	 * Changes the status, which says whether the processor is running.
	 * 
	 * @param status
	 *            status
	 */
	void setComputing(boolean status);

	/**
	 * Changes the state of all buttons between enabled and disabled.
	 * 
	 * @param status
	 *            status (<code>true</code> if and only if the button is
	 *            enabled)
	 */
	void setButtonsEnabled(boolean status);

	/**
	 * Changes the state of the go-to-previous-completion-rules button between
	 * enabled and disabled.
	 * 
	 * @param status
	 *            status (<code>true</code> if and only if the button is
	 *            enabled)
	 */
	void setButtonGoToPreviousCompletionRulesEnabled(boolean status);

	/**
	 * Adds the given example configurations.
	 * 
	 * @param examples
	 *            example configurations
	 */
	void addExamples(Collection<ExampleConfiguration> examples);

}
