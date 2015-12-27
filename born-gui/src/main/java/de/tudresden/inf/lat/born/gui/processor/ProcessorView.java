package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JPanel;

import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;

/**
 * View of processor.
 * 
 * @author Julian Mendez
 *
 */
public interface ProcessorView {

	void addButtonOntologyFileListener(ActionListener listener, String actionCommand);

	void addButtonBayesianNetworkFileListener(ActionListener listener, String actionCommand);

	void addButtonConsoleInputListener(ActionListener listener, String actionCommand);

	void addButtonConsoleOutputListener(ActionListener listener, String actionCommand);

	void addButtonComputeInferenceListener(ActionListener listener, String actionCommand);

	void addComboBoxExampleListener(ActionListener listener, String actionCommand);

	void addButtonUpdateExampleListener(ActionListener listener, String actionCommand);

	ProcessorConfigurationImpl getModel();

	String getOntologyFile();

	void setOntologyFile(String fileName);

	String getBayesianNetworkFile();

	void setBayesianNetworkFile(String fileName);

	String getConsoleInput();

	void setConsoleInput(String text);

	void readConsoleInput(String consoleInputFile);

	void writeConsoleOutput(String consoleOutputFile);

	String getConsoleOutput();

	void setConsoleOutput(String fileName);

	void setButtonLoadEnabled(boolean b);

	void setButtonComputeInferenceEnabled(boolean b);

	void setComboBoxExampleEnabled(boolean b);

	int getComboBoxExampleIndex();

	void updateOntologyFile();

	void updateBayesianNetworkFile();

	void updateQuery();

	void setResult(String result);

	void setComputing(boolean status);

	void setButtonsEnabled(boolean status);

	void addExamples(Collection<ExampleConfiguration> examples);

	JPanel getPanel();

}
