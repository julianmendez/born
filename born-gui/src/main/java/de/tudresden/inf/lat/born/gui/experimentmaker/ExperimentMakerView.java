package de.tudresden.inf.lat.born.gui.experimentmaker;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.splitter.SplitterConfiguration;

/**
 * This is the panel to split annotations in an ontology.
 * 
 * @author Julian Mendez
 */
public class ExperimentMakerView extends JPanel {

	private static final long serialVersionUID = -7105856642163451498L;

	private JButton buttonSelectInputOntologyFile = new JButton();
	private JButton buttonSelectOutputOntologyFile = new JButton(BornIcon.SAVE_FILE);
	private JButton buttonCreateOntology = new JButton(BornIcon.RUN);
	private JTextField textInputOntologyFile = new JTextField();
	private JTextField textBayesianNetworkFile = new JTextField();
	private final SplitterConfiguration model;

	public ExperimentMakerView(SplitterConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
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

		this.buttonSelectOutputOntologyFile.addActionListener(listener);
		this.buttonSelectOutputOntologyFile.setActionCommand(actionCommand);
	}

	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonCreateOntology.addActionListener(listener);
		this.buttonCreateOntology.setActionCommand(actionCommand);
	}

	public void addTextFieldInputOntologyFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textInputOntologyFile.addActionListener(listener);
		this.textInputOntologyFile.setActionCommand(actionCommand);
	}

	public void addTextFieldBayesianNetworkFileListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.textBayesianNetworkFile.addActionListener(listener);
		this.textBayesianNetworkFile.setActionCommand(actionCommand);
	}

	void createPanel() {

		setAlignmentX(CENTER_ALIGNMENT);
		setLayout(null);

		JLabel lblInput = new JLabel("input");
		lblInput.setBounds(31, 62, 70, 15);
		add(lblInput);

		buttonSelectInputOntologyFile.setIcon(BornIcon.OPEN_FILE);
		buttonSelectInputOntologyFile.setBounds(31, 22, 50, 26);
		buttonSelectInputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSelectInputOntologyFile);

		textInputOntologyFile.setBounds(154, 24, 215, 26);
		textInputOntologyFile.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textInputOntologyFile.setAlignmentX(LEFT_ALIGNMENT);
		add(textInputOntologyFile);

		JLabel lblOutput = new JLabel("output");
		lblOutput.setBounds(31, 143, 70, 15);
		add(lblOutput);

		buttonSelectOutputOntologyFile.setIcon(BornIcon.SAVE_FILE);
		buttonSelectOutputOntologyFile.setBounds(31, 102, 50, 26);
		buttonSelectOutputOntologyFile.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonSelectOutputOntologyFile);

		textBayesianNetworkFile.setBounds(154, 102, 215, 26);
		textBayesianNetworkFile.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		textBayesianNetworkFile.setAlignmentX(LEFT_ALIGNMENT);
		add(textBayesianNetworkFile);

		buttonCreateOntology.setIcon(BornIcon.RUN);
		buttonCreateOntology.setBounds(31, 197, 50, 26);
		buttonCreateOntology.setToolTipText(Message.tooltipComputeInference);
		add(buttonCreateOntology);
	}

	public SplitterConfiguration getModel() {
		return this.model;
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonSelectInputOntologyFile.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonCreateOntology.setEnabled(b);
	}

	public void update() {
	}

}
