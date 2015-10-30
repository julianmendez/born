package de.tudresden.inf.lat.born.gui.experimentrunner;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorCore;

/**
 * This is the panel for the experiment runner.
 * 
 * @author Julian Mendez
 */
public class ExperimentRunnerView extends JPanel {

	private static final long serialVersionUID = 8987374313881883318L;

	public static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private JButton buttonInputOntologyDirectory = new JButton();
	private JButton buttonBayesianNetworkDirectory = new JButton();
	private JButton buttonOutputDirectory = new JButton();
	private JButton buttonUpdateSeed = new JButton();
	private JButton buttonComputeInference = new JButton();
	private JLabel labelProgress = new JLabel("computing ...");
	private JTextField textInputOntologyDirectory = new JTextField();
	private JTextField textBayesianNetworkDirectory = new JTextField();
	private JTextField textNumberOfQueries = new JTextField();
	private JTextField textOutputDirectory = new JTextField();
	private JTextField textSeed = new JTextField();
	private final MultiProcessorConfiguration model;

	/**
	 * Constructs a new panel for the experiment runner.
	 * 
	 * @param model
	 *            model
	 */
	public ExperimentRunnerView(MultiProcessorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		setLayout(null);
		createPanel();
	}

	/**
	 * Adds an action listener to the button to select the input ontology.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	public void addButtonInputOntologyListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonInputOntologyDirectory.addActionListener(listener);
		this.buttonInputOntologyDirectory.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to select the Bayesian network.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	public void addButtonBayesianNetworkListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonBayesianNetworkDirectory.addActionListener(listener);
		this.buttonBayesianNetworkDirectory.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to select the output directory.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	public void addButtonOutputDirectoryListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonOutputDirectory.addActionListener(listener);
		this.buttonOutputDirectory.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to update the seed.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
	public void addButtonUpdateSeedListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonUpdateSeed.addActionListener(listener);
		this.buttonUpdateSeed.setActionCommand(actionCommand);
	}

	/**
	 * Adds an action listener to the button to compute the inference.
	 * 
	 * @param listener
	 *            listener
	 * @param actionCommand
	 *            action command
	 */
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

	/**
	 * Creates the panel.
	 */
	void createPanel() {

		JLabel lblInputOntologyDirectory = new JLabel("ontology directory");
		lblInputOntologyDirectory.setBounds(292, 83, 178, 15);
		add(lblInputOntologyDirectory);

		buttonInputOntologyDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonInputOntologyDirectory.setBounds(216, 43, 54, 28);
		buttonInputOntologyDirectory.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonInputOntologyDirectory);

		textInputOntologyDirectory.setBounds(282, 43, 688, 28);
		textInputOntologyDirectory.setToolTipText(Message.tooltipTextFieldInputOntologyFile);
		textInputOntologyDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textInputOntologyDirectory.setEditable(false);
		add(textInputOntologyDirectory);

		JLabel lblBayesianNetworkDirectory = new JLabel("Bayesian network directory");
		lblBayesianNetworkDirectory.setBounds(292, 186, 220, 15);
		add(lblBayesianNetworkDirectory);

		buttonBayesianNetworkDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonBayesianNetworkDirectory.setBounds(216, 135, 54, 28);
		buttonBayesianNetworkDirectory.setToolTipText(Message.tooltipOpenInputOntologyFile);
		add(buttonBayesianNetworkDirectory);

		textBayesianNetworkDirectory.setBounds(282, 135, 688, 28);
		textBayesianNetworkDirectory.setToolTipText(Message.tooltipTextFieldBayesianNetworkFile);
		textBayesianNetworkDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textBayesianNetworkDirectory.setEditable(false);
		add(textBayesianNetworkDirectory);

		JLabel lblInput = new JLabel("output directory");
		lblInput.setBounds(292, 283, 128, 15);
		add(lblInput);

		buttonOutputDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonOutputDirectory.setBounds(216, 243, 54, 28);
		buttonOutputDirectory.setToolTipText(Message.tooltipButtonOutputDirectory);
		add(buttonOutputDirectory);

		textOutputDirectory = new JTextField();
		textOutputDirectory.setBounds(282, 243, 688, 28);
		textOutputDirectory.setToolTipText(Message.tooltipTextFieldOutputDirectory);
		textOutputDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textOutputDirectory.setEditable(false);
		add(textOutputDirectory);

		JLabel lblNumberOfQueries = new JLabel(Message.textNumberOfQueries);
		lblNumberOfQueries.setBounds(292, 381, 140, 15);
		add(lblNumberOfQueries);

		textNumberOfQueries.setBounds(290, 341, 106, 28);
		textNumberOfQueries.setToolTipText(Message.tooltipTextFieldNumberOfQueries);
		textNumberOfQueries.setAlignmentX(LEFT_ALIGNMENT);
		add(textNumberOfQueries);

		JLabel lblSeed = new JLabel(Message.textSeed);
		lblSeed.setBounds(550, 381, 70, 15);
		add(lblSeed);

		textSeed.setBounds(540, 341, 89, 28);
		textSeed.setToolTipText(Message.tooltipTextFieldSeed);
		textSeed.setAlignmentX(LEFT_ALIGNMENT);
		add(textSeed);

		buttonUpdateSeed.setIcon(BornIcon.REFRESH);
		buttonUpdateSeed.setBounds(474, 341, 54, 28);
		buttonUpdateSeed.setToolTipText(Message.tooltipButtonUpdateSeed);
		add(buttonUpdateSeed);

		buttonComputeInference.setIcon(BornIcon.RUN);
		buttonComputeInference.setBounds(227, 429, 54, 28);
		buttonComputeInference.setToolTipText(Message.tooltipComputeInference);
		add(buttonComputeInference);

		labelProgress.setBounds(237, 478, 99, 15);
		labelProgress.setVisible(false);
		add(labelProgress);

	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public MultiProcessorConfiguration getModel() {
		return this.model;
	}

	/**
	 * Returns the directory of the input ontologies.
	 * 
	 * @return the directory of the input ontologies
	 */
	public String getInputOntologyDirectory() {
		return this.textInputOntologyDirectory.getText();
	}

	/**
	 * Sets the directory of the input ontologies.
	 * 
	 * @param directoryName
	 *            directory
	 */
	public void setInputOntologyDirectory(String directoryName) {
		this.textInputOntologyDirectory.setText(directoryName);
		updateInputOntologyDirectory();
	}

	/**
	 * Returns the directory of the Bayesian networks.
	 * 
	 * @return the directory of the Bayesian networks
	 */
	public String getBayesianNetworkDirectory() {
		return this.textBayesianNetworkDirectory.getText();
	}

	/**
	 * Set the directory of the Bayesian networks.
	 * 
	 * @param directoryName
	 *            directory
	 */
	public void setBayesianNetworkDirectory(String directoryName) {
		this.textBayesianNetworkDirectory.setText(directoryName);
		updateBayesianNetworkDirectory();
	}

	/**
	 * Returns the output directory.
	 * 
	 * @return the output directory
	 */
	public String getOutputDirectory() {
		return this.textOutputDirectory.getText();
	}

	/**
	 * Sets the output directory.
	 * 
	 * @param directoryName
	 *            directory
	 */
	public void setOutputDirectory(String directoryName) {
		this.textOutputDirectory.setText(directoryName);
		updateOutputDirectory();
	}

	/**
	 * Changes the state of the load button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	public void setButtonLoadEnabled(boolean status) {
		this.buttonInputOntologyDirectory.setEnabled(status);
	}

	/**
	 * Changes the state of the compute button to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the button is enabled
	 */
	public void setButtonComputeInferenceEnabled(boolean status) {
		this.buttonComputeInference.setEnabled(status);
	}

	/**
	 * Updates the directory of ontologies.
	 */
	void updateInputOntologyDirectory() {
		String inputOntologyDirectory = getInputOntologyDirectory();
		if (inputOntologyDirectory != null && !inputOntologyDirectory.trim().isEmpty()) {
			getModel().setOntologyList(MultiProcessorCore.getOntologyAndNetworkList(getInputOntologyDirectory(),
					getBayesianNetworkDirectory()));
		}
	}

	/**
	 * Updates the directory of Bayesian networks.
	 */
	void updateBayesianNetworkDirectory() {
		String bayesianNetworkDirectory = getBayesianNetworkDirectory();
		if (bayesianNetworkDirectory != null && !bayesianNetworkDirectory.trim().isEmpty()) {
			getModel().setOntologyList(MultiProcessorCore.getOntologyAndNetworkList(getInputOntologyDirectory(),
					getBayesianNetworkDirectory()));
		}
	}

	/**
	 * Updates the output directory.
	 */
	void updateOutputDirectory() {
		String outputDirectory = getOutputDirectory();
		if (outputDirectory != null && !outputDirectory.trim().isEmpty()) {
			getModel().setOutputDirectory(outputDirectory);
		}
	}

	/**
	 * Updates the seed for the pseudorandom number generator.
	 */
	void updateSeed() {
		String seedStr = this.textSeed.getText();
		int seed = 0;
		try {
			seed = Integer.parseInt(seedStr);
		} catch (NumberFormatException e) {
		}
		this.textSeed.setText("" + seed);
		this.model.setSeed(seed);
	}

	/**
	 * Updates the number of queries.
	 */
	void updateNumberOfQueries() {
		String numberOfQueriesStr = this.textNumberOfQueries.getText();
		int numberOfQueries = 0;
		try {
			numberOfQueries = Integer.parseInt(numberOfQueriesStr);
		} catch (NumberFormatException e) {
		}
		this.textNumberOfQueries.setText("" + numberOfQueries);
		this.model.setNumberOfQueries(numberOfQueries);
	}

	/**
	 * Updates the visual components.
	 */
	public void update() {
		updateInputOntologyDirectory();
		updateBayesianNetworkDirectory();
		updateOutputDirectory();
		updateSeed();
		updateNumberOfQueries();
	}

	/**
	 * Makes visible or invisible the label that shows that the reasoner is
	 * running.
	 * 
	 * @param status
	 *            value to define if the label is visible
	 */
	public void setComputing(boolean status) {
		labelProgress.setVisible(status);
	}

	/**
	 * Returns the number of queries.
	 * 
	 * @return the number of queries
	 */
	public String getNumebrOfQueries() {
		return this.textNumberOfQueries.getText();
	}

	/**
	 * Returns the seed.
	 * 
	 * @return the seed
	 */
	public String getSeed() {
		return this.textSeed.getText();
	}

	/**
	 * Sets the seed for the pseudorandom number generator.
	 * 
	 * @param seed
	 *            the seed
	 */
	public void setSeed(String seed) {
		this.textSeed.setText(seed);
	}

	/**
	 * Changes the state of all buttons to enabled or disabled.
	 * 
	 * @param status
	 *            value to define if the buttons are enabled
	 */
	public void setButtonsEnabled(boolean status) {
		buttonInputOntologyDirectory.setEnabled(status);
		buttonBayesianNetworkDirectory.setEnabled(status);
		buttonOutputDirectory.setEnabled(status);
		buttonUpdateSeed.setEnabled(status);
		buttonComputeInference.setEnabled(status);
	}

}
