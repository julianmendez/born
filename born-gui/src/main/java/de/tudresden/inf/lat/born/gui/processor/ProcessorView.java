package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This is the panel to compute inference.
 * 
 * @author Julian Mendez
 */
public class ProcessorView extends JPanel {

	private static final long serialVersionUID = -3489883631448640992L;

	public static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private JButton buttonSelectInputOntologyFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenFile)));
	private JButton buttonSelectBayesianNetworkFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenFile)));
	private JButton buttonReadConsoleInputFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenFile)));
	private JButton buttonWriteConsoleOutputFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconSaveFile)));
	private JButton buttonComputeInference = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconComputeInference)));
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private JTextArea textConsoleInput = new JTextArea();
	private JTextArea textConsoleOutput = new JTextArea();
	private JScrollPane scrollConsoleInput = new JScrollPane();
	private JScrollPane scrollConsoleOutput = new JScrollPane();
	private final ProcessorConfiguration model;

	public ProcessorView(ProcessorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		setLayout(null);
		createPanel();
	}

	public void addButtonSelectInputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectInputOntologyFile.addActionListener(listener);
		this.buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSelectBayesianNetworkFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectBayesianNetworkFile.addActionListener(listener);
		this.buttonSelectBayesianNetworkFile.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonComputeInference.addActionListener(listener);
		this.buttonComputeInference.setActionCommand(actionCommand);
	}

	private void createPanel() {

		JLabel lblNewLabel = new JLabel("ontology");
		lblNewLabel.setBounds(168, 83, 70, 15);
		add(lblNewLabel);

		buttonSelectInputOntologyFile.setBounds(168, 43, 54, 28);
		add(buttonSelectInputOntologyFile);
		buttonSelectInputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textInputOntologyFile.setBounds(282, 43, 228, 45);
		add(textInputOntologyFile);
		textInputOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblNewLabel_1 = new JLabel("Bayesian network");
		lblNewLabel_1.setBounds(168, 184, 128, 15);
		add(lblNewLabel_1);

		buttonSelectBayesianNetworkFile.setBounds(168, 135, 54, 28);
		add(buttonSelectBayesianNetworkFile);
		buttonSelectBayesianNetworkFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textBayesianNetworkFile.setBounds(299, 135, 228, 45);
		add(textBayesianNetworkFile);
		textBayesianNetworkFile.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblInput = new JLabel("input");
		lblInput.setBounds(168, 283, 70, 15);
		add(lblInput);

		buttonReadConsoleInputFile.setBounds(168, 246, 54, 28);
		add(buttonReadConsoleInputFile);
		buttonReadConsoleInputFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		textBayesianNetworkFile.setBounds(282, 135, 228, 45);
		add(textBayesianNetworkFile);

		textConsoleInput.setToolTipText(Message.tooltipTextFieldListOfParents);
		textConsoleInput.setAlignmentX(LEFT_ALIGNMENT);

		scrollConsoleInput.setBounds(282, 246, 228, 65);
		add(scrollConsoleInput);
		scrollConsoleInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsoleInput.setViewportView(textConsoleInput);

		JLabel lblOutput = new JLabel("output");
		lblOutput.setBounds(168, 395, 70, 15);
		add(lblOutput);

		textConsoleOutput.setToolTipText(Message.tooltipTextFieldOutputFile);
		textConsoleOutput.setAlignmentX(LEFT_ALIGNMENT);
		scrollConsoleOutput.setViewportView(textConsoleOutput);

		buttonWriteConsoleOutputFile.setBounds(168, 355, 54, 28);
		add(buttonWriteConsoleOutputFile);
		buttonWriteConsoleOutputFile.setToolTipText(Message.tooltipOpenInputOntologyFile);

		scrollConsoleOutput.setBounds(282, 355, 228, 65);
		add(scrollConsoleOutput);
		scrollConsoleOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		buttonComputeInference.setBounds(168, 476, 54, 28);
		add(buttonComputeInference);
		buttonComputeInference.setToolTipText(Message.tooltipComputeInference);

	}

	public ProcessorConfiguration getModel() {
		return this.model;
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonComputeInference.setEnabled(b);
	}

	public String getInputOntologyFile() {
		return this.textInputOntologyFile.getText();
	}

	public void setInputOntologyFile(String fileName) {
		this.textInputOntologyFile.setText(fileName);
	}

	public String getBayesianNetworkFile() {
		return this.textBayesianNetworkFile.getText();
	}

	public void setBayesianNetworkFile(String fileName) {
		this.textBayesianNetworkFile.setText(fileName);
	}

	void updateInputOntologyFile() {
		String inputOntologyFile = getInputOntologyFile();
		if (inputOntologyFile != null && !inputOntologyFile.trim().isEmpty()) {
			try {
				getModel().setOntologyInputStream(new FileInputStream(inputOntologyFile));
			} catch (IOException e) {
				setInputOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateBayesianNetworkFile() {
		String bayesianNetworkFile = getBayesianNetworkFile();
		if (bayesianNetworkFile != null && !bayesianNetworkFile.trim().isEmpty()) {
			try {
				getModel().setBayesianNetworkInputStream(new FileInputStream(bayesianNetworkFile));
			} catch (IOException e) {
				setBayesianNetworkFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateQuery() {
		String query = this.textConsoleInput.getText();
		if (query != null && !query.trim().isEmpty()) {
			getModel().setQueryInputStream(new ByteArrayInputStream(query.getBytes()));
		}
	}

	public void update() {
		updateInputOntologyFile();
		updateBayesianNetworkFile();
		updateQuery();
	}

	public void setResult(String result) {
		this.textConsoleOutput.setText(result);
	}
}
