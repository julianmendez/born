package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This is the panel to compute inference.
 * 
 * @author Julian Mendez
 */
public class ProcessorView extends JPanel {

	private static final long serialVersionUID = -3489883631448640992L;

	public static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private JButton buttonOntologyFile = new JButton();
	private JButton buttonBayesianNetworkFile = new JButton();
	private JButton buttonConsoleInput = new JButton();
	private JButton buttonConsoleOutput = new JButton();
	private JButton buttonComputeInference = new JButton();
	private JButton buttonUpdateExample = new JButton();
	private JLabel labelProgress = new JLabel("computing ...");
	private JTextField textOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private JTextArea textConsoleInput = new JTextArea();
	private JTextArea textConsoleOutput = new JTextArea();
	private JScrollPane scrollConsoleInput = new JScrollPane();
	private JScrollPane scrollConsoleOutput = new JScrollPane();
	private JComboBox comboBoxExample = new JComboBox();
	private final ProcessorConfiguration model;

	public ProcessorView(ProcessorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		setLayout(null);
		createPanel();
	}

	public void addButtonOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonOntologyFile.addActionListener(listener);
		buttonOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonBayesianNetworkFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonBayesianNetworkFile.addActionListener(listener);
		buttonBayesianNetworkFile.setActionCommand(actionCommand);
	}

	public void addButtonConsoleInputListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonConsoleInput.addActionListener(listener);
		buttonConsoleInput.setActionCommand(actionCommand);
	}

	public void addButtonConsoleOutputListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonConsoleOutput.addActionListener(listener);
		buttonConsoleOutput.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonComputeInference.addActionListener(listener);
		buttonComputeInference.setActionCommand(actionCommand);
	}

	public void addComboBoxExampleListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		comboBoxExample.addActionListener(listener);
		comboBoxExample.setActionCommand(actionCommand);
	}

	public void addButtonUpdateExampleListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		buttonUpdateExample.addActionListener(listener);
		buttonUpdateExample.setActionCommand(actionCommand);
	}

	void createPanel() {

		JLabel lblNewLabel = new JLabel("ontology");
		lblNewLabel.setBounds(282, 83, 70, 15);
		add(lblNewLabel);

		buttonOntologyFile.setIcon(BornIcon.OPEN_FILE);
		buttonOntologyFile.setBounds(213, 43, 54, 28);
		add(buttonOntologyFile);
		buttonOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textOntologyFile.setBounds(282, 43, 688, 28);
		add(textOntologyFile);
		textOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textOntologyFile.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblNewLabel_1 = new JLabel("Bayesian network");
		lblNewLabel_1.setBounds(282, 175, 128, 15);
		add(lblNewLabel_1);

		buttonBayesianNetworkFile.setIcon(BornIcon.OPEN_FILE);
		buttonBayesianNetworkFile.setBounds(213, 135, 54, 28);
		add(buttonBayesianNetworkFile);
		buttonBayesianNetworkFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textBayesianNetworkFile.setBounds(282, 135, 688, 28);
		add(textBayesianNetworkFile);
		textBayesianNetworkFile.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblInput = new JLabel("input");
		lblInput.setBounds(282, 323, 70, 15);
		add(lblInput);

		buttonConsoleInput.setIcon(BornIcon.OPEN_FILE);
		buttonConsoleInput.setBounds(213, 254, 54, 28);
		add(buttonConsoleInput);
		buttonConsoleInput.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textBayesianNetworkFile.setBounds(282, 135, 688, 28);
		add(textBayesianNetworkFile);

		textConsoleInput.setToolTipText(Message.tooltipTextFieldListOfParents);
		textConsoleInput.setAlignmentX(LEFT_ALIGNMENT);

		scrollConsoleInput.setBounds(282, 246, 688, 65);
		add(scrollConsoleInput);
		scrollConsoleInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsoleInput.setViewportView(textConsoleInput);

		JLabel lblOutput = new JLabel("output");
		lblOutput.setBounds(282, 444, 70, 15);
		add(lblOutput);

		textConsoleOutput.setToolTipText(Message.tooltipTextFieldOutputFile);
		textConsoleOutput.setAlignmentX(LEFT_ALIGNMENT);
		scrollConsoleOutput.setViewportView(textConsoleOutput);

		buttonConsoleOutput.setIcon(BornIcon.SAVE_FILE);
		buttonConsoleOutput.setBounds(213, 361, 54, 28);
		add(buttonConsoleOutput);
		buttonConsoleOutput.setToolTipText(Message.tooltipOpenInputOntologyFile);

		scrollConsoleOutput.setBounds(282, 355, 688, 65);
		add(scrollConsoleOutput);
		scrollConsoleOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		buttonComputeInference.setIcon(BornIcon.RUN);
		buttonComputeInference.setBounds(213, 476, 54, 28);
		add(buttonComputeInference);
		buttonComputeInference.setToolTipText(Message.tooltipComputeInference);

		labelProgress.setBounds(223, 517, 99, 15);
		labelProgress.setVisible(false);
		add(labelProgress);

		comboBoxExample.setBounds(702, 476, 268, 28);
		comboBoxExample.setToolTipText(Message.tooltipComboBoxExample);
		add(comboBoxExample);

		buttonUpdateExample.setIcon(BornIcon.REFRESH);
		buttonUpdateExample.setBounds(620, 476, 54, 28);
		buttonUpdateExample.setToolTipText(Message.tooltipButtonUpdateExample);
		add(buttonUpdateExample);

	}

	public ProcessorConfiguration getModel() {
		return model;
	}

	public String getOntologyFile() {
		return textOntologyFile.getText();
	}

	public void setOntologyFile(String fileName) {
		textOntologyFile.setText(fileName);
	}

	public String getBayesianNetworkFile() {
		return textBayesianNetworkFile.getText();
	}

	public void setBayesianNetworkFile(String fileName) {
		textBayesianNetworkFile.setText(fileName);
	}

	public String getConsoleInput() {
		return textConsoleInput.getText();
	}

	public void setConsoleInput(String text) {
		textConsoleInput.setText(text);
	}

	public void readConsoleInput(String consoleInputFile) {
		if (consoleInputFile != null && !consoleInputFile.trim().isEmpty()) {
			try {
				String text = ProcessorConfiguration.read(new FileReader(consoleInputFile));
				this.textConsoleInput.setText(text);
				updateQuery();
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	public void writeConsoleOutput(String consoleOutputFile) {
		if (consoleOutputFile != null && !consoleOutputFile.trim().isEmpty()) {
			try {
				String text = this.textConsoleOutput.getText();
				ProcessorConfiguration.write(new StringReader(text), new FileWriter(consoleOutputFile));
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	public String getConsoleOutput() {
		return textConsoleOutput.getText();
	}

	public void setConsoleOutput(String fileName) {
		textConsoleOutput.setText(fileName);
	}

	public void setButtonLoadEnabled(boolean b) {
		buttonOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		buttonComputeInference.setEnabled(b);
	}

	public void setComboBoxExampleEnabled(boolean b) {
		comboBoxExample.setEnabled(b);
	}

	public int getComboBoxExampleIndex() {
		return comboBoxExample.getSelectedIndex();
	}

	void updateOntologyFile() {
		String inputOntologyFile = getOntologyFile();
		if (inputOntologyFile != null && !inputOntologyFile.trim().isEmpty()) {
			try {
				getModel().setOntology(ProcessorConfiguration.readOntology(new FileInputStream(inputOntologyFile)));
			} catch (IOException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			} catch (OWLOntologyCreationException e) {
				setOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateBayesianNetworkFile() {
		String bayesianNetworkFile = getBayesianNetworkFile();
		if (bayesianNetworkFile != null && !bayesianNetworkFile.trim().isEmpty()) {
			try {
				getModel().setBayesianNetwork(ProcessorConfiguration.read(new FileReader(bayesianNetworkFile)));
			} catch (IOException e) {
				setBayesianNetworkFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateQuery() {
		String query = textConsoleInput.getText();
		if (query != null && !query.trim().isEmpty()) {
			getModel().setQuery(query);
		}
	}

	public void update() {
		updateOntologyFile();
		updateBayesianNetworkFile();
		updateQuery();
	}

	public void setResult(String result) {
		textConsoleOutput.setText(result);
	}

	public void setComputing(boolean status) {
		labelProgress.setVisible(status);
	}

	public void setButtonsEnabled(boolean status) {
		buttonOntologyFile.setEnabled(status);
		buttonBayesianNetworkFile.setEnabled(status);
		buttonConsoleInput.setEnabled(status);
		buttonConsoleOutput.setEnabled(status);
		buttonComputeInference.setEnabled(status);
		buttonUpdateExample.setEnabled(status);
		comboBoxExample.setEnabled(status);
	}

	public void addExamples(Collection<ExampleConfiguration> examples) {
		for (ExampleConfiguration configuration : examples) {
			comboBoxExample.addItem(configuration.getOntologyName());
		}
	}

}
