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

	private JButton buttonSelectInputOntologyFile = new JButton(new ImageIcon(
			this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));

	private JButton buttonSelectBayesianNetworkFile = new JButton(
			new ImageIcon(this.getClass().getClassLoader()
					.getResource(Message.iconOpenInputOntologyFile)));
	private JButton buttonComputeInference = new JButton(new ImageIcon(this
			.getClass().getClassLoader()
			.getResource(Message.iconComputeInference)));
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private JTextArea textConsoleInput = new JTextArea();
	private JScrollPane scrollConsoleInput = new JScrollPane(
			this.textConsoleInput);
	private JTextArea textConsoleOutput = new JTextArea();
	private JScrollPane scrollConsoleOutput = new JScrollPane(
			this.textConsoleOutput);
	private final ProcessorConfiguration model;

	public ProcessorView(ProcessorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		add(createPanel());
	}

	public void addButtonSelectInputOntologyFileListener(
			ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectInputOntologyFile.addActionListener(listener);
		this.buttonSelectInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonSelectBayesianNetworkFileListener(
			ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonSelectBayesianNetworkFile.addActionListener(listener);
		this.buttonSelectBayesianNetworkFile.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener,
			String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonComputeInference.addActionListener(listener);
		this.buttonComputeInference.setActionCommand(actionCommand);
	}

	JTextField newLabel(String text) {
		JTextField ret = new JTextField(text);
		ret.setEditable(false);
		ret.setBackground(null);
		ret.setBorder(null);
		ret.setAlignmentX(LEFT_ALIGNMENT);
		return ret;
	}

	private JPanel createPanel() {

		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.Y_AXIS));

		int width = 280;
		int height = 28;
		int gap = 4;

		JPanel smallPanel = new JPanel();
		smallPanel.setAlignmentX(CENTER_ALIGNMENT);
		this.buttonComputeInference
				.setToolTipText(Message.tooltipComputeInference);
		smallPanel.add(this.buttonComputeInference);
		ret.add(smallPanel);

		ret.add(Box.createVerticalStrut(gap));

		JPanel largePanel = new JPanel();
		largePanel.setAlignmentX(CENTER_ALIGNMENT);
		largePanel.setLayout(new BoxLayout(largePanel, BoxLayout.Y_AXIS));

		largePanel.add(newLabel(Message.textInputOntologyFile));

		this.buttonSelectInputOntologyFile
				.setToolTipText(Message.tooltipOpenInputOntologyFile);
		largePanel.add(this.buttonSelectInputOntologyFile);

		this.textInputOntologyFile
				.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		this.textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textInputOntologyFile);

		largePanel.add(Box.createVerticalStrut(gap));

		largePanel.add(newLabel(Message.textBayesianNetworkFile));

		this.buttonSelectBayesianNetworkFile
				.setToolTipText(Message.tooltipOpenInputOntologyFile);

		largePanel.add(this.buttonSelectBayesianNetworkFile);

		this.textBayesianNetworkFile
				.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		this.textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);
		largePanel.add(this.textBayesianNetworkFile);

		largePanel.add(Box.createVerticalStrut(gap));

		largePanel.add(newLabel(Message.textQuery));

		this.textConsoleInput
				.setToolTipText(Message.tooltipTextFieldListOfParents);
		this.textConsoleInput.setAlignmentX(LEFT_ALIGNMENT);

		this.scrollConsoleInput
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		largePanel.add(this.scrollConsoleInput);

		largePanel.add(Box.createVerticalStrut(gap));

		largePanel.add(newLabel(Message.textOutput));

		this.textConsoleOutput
				.setToolTipText(Message.tooltipTextFieldOutputFile);
		this.textConsoleOutput.setMinimumSize(new Dimension(width, height));
		this.textConsoleOutput.setAlignmentX(LEFT_ALIGNMENT);
		this.scrollConsoleOutput
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		largePanel.add(this.scrollConsoleOutput);

		ret.add(largePanel);

		return ret;
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
				getModel().setOntologyInputStream(
						new FileInputStream(inputOntologyFile));
			} catch (IOException e) {
				setInputOntologyFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateBayesianNetworkFile() {
		String bayesianNetworkFile = getBayesianNetworkFile();
		if (bayesianNetworkFile != null
				&& !bayesianNetworkFile.trim().isEmpty()) {
			try {
				getModel().setBayesianNetworkInputStream(
						new FileInputStream(bayesianNetworkFile));
			} catch (IOException e) {
				setBayesianNetworkFile(WRONG_FILE_NAME_ERROR_MESSAGE);
			}
		}
	}

	void updateQuery() {
		String query = this.textConsoleInput.getText();
		if (query != null && !query.trim().isEmpty()) {
			getModel().setQueryInputStream(
					new ByteArrayInputStream(query.getBytes()));
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
