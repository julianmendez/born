package de.tudresden.inf.lat.born.gui.processor;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenInputOntologyFile)));

	private JButton buttonSelectBayesianNetworkFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconOpenInputOntologyFile)));
	private JButton buttonComputeInference = new JButton(
			new ImageIcon(this.getClass().getClassLoader().getResource(Message.iconComputeInference)));
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private JTextArea textConsoleInput = new JTextArea();
	private JScrollPane scrollConsoleInput = new JScrollPane(this.textConsoleInput);
	private JTextArea textConsoleOutput = new JTextArea();
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

		int width = 280;
		int height = 28;

		buttonComputeInference.setBounds(184, 439, 54, 28);
		add(buttonComputeInference);

		this.buttonComputeInference.setToolTipText(Message.tooltipComputeInference);
		buttonSelectInputOntologyFile.setBounds(168, 135, 54, 28);
		add(buttonSelectInputOntologyFile);

		this.buttonSelectInputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		textInputOntologyFile.setBounds(282, 43, 228, 45);
		add(textInputOntologyFile);

		this.textInputOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		this.textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);

		buttonSelectBayesianNetworkFile.setBounds(168, 48, 54, 28);
		add(buttonSelectBayesianNetworkFile);

		this.buttonSelectBayesianNetworkFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		textBayesianNetworkFile.setBounds(282, 135, 228, 45);
		add(textBayesianNetworkFile);

		this.textBayesianNetworkFile.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		this.textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);

		this.textConsoleInput.setToolTipText(Message.tooltipTextFieldListOfParents);
		this.textConsoleInput.setAlignmentX(LEFT_ALIGNMENT);
		scrollConsoleInput.setBounds(282, 228, 228, 65);
		add(scrollConsoleInput);

		this.scrollConsoleInput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		scrollConsoleOutput.setBounds(282, 345, 228, 65);
		add(scrollConsoleOutput);
		this.scrollConsoleOutput.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsoleOutput.setViewportView(textConsoleOutput);

		this.textConsoleOutput.setToolTipText(Message.tooltipTextFieldOutputFile);
		this.textConsoleOutput.setMinimumSize(new Dimension(width, height));
		this.textConsoleOutput.setAlignmentX(LEFT_ALIGNMENT);

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
