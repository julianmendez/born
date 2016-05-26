package de.tudresden.inf.lat.born.gui.experimentrunner;

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.common.FormatTool;
import de.tudresden.inf.lat.born.gui.common.Message;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorCore;

/**
 * This is the panel for the experiment runner.
 * 
 * @author Julian Mendez
 */
public class ExperimentRunnerPanel extends JPanel implements ExperimentRunnerView {

	private static final long serialVersionUID = 8987374313881883318L;

	static final String WRONG_FILE_NAME_ERROR_MESSAGE = "WRONG FILE NAME! --> ";

	private FormatTool formatTool = new FormatTool();
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
	public ExperimentRunnerPanel(MultiProcessorConfiguration model) {
		Objects.requireNonNull(model);
		this.model = model;
		setLayout(null);
		createPanel();
	}

	@Override
	public void addButtonInputOntologyListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		this.buttonInputOntologyDirectory.addActionListener(listener);
		this.buttonInputOntologyDirectory.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonBayesianNetworkListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		this.buttonBayesianNetworkDirectory.addActionListener(listener);
		this.buttonBayesianNetworkDirectory.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonOutputDirectoryListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		this.buttonOutputDirectory.addActionListener(listener);
		this.buttonOutputDirectory.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonUpdateSeedListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
		this.buttonUpdateSeed.addActionListener(listener);
		this.buttonUpdateSeed.setActionCommand(actionCommand);
	}

	@Override
	public void addButtonComputeInferenceListener(ActionListener listener, String actionCommand) {
		Objects.requireNonNull(listener);
		Objects.requireNonNull(actionCommand);
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

	@Override
	public MultiProcessorConfiguration getModel() {
		return this.model;
	}

	@Override
	public String getInputOntologyDirectory() {
		return this.textInputOntologyDirectory.getText().trim();
	}

	@Override
	public void setInputOntologyDirectory(String directoryName) {
		Objects.requireNonNull(directoryName);
		this.textInputOntologyDirectory.setText(formatTool.formatText(directoryName));
		updateInputOntologyDirectory();
	}

	@Override
	public String getBayesianNetworkDirectory() {
		return this.textBayesianNetworkDirectory.getText().trim();
	}

	@Override
	public void setBayesianNetworkDirectory(String directoryName) {
		Objects.requireNonNull(directoryName);
		this.textBayesianNetworkDirectory.setText(formatTool.formatText(directoryName));
		updateBayesianNetworkDirectory();
	}

	@Override
	public String getOutputDirectory() {
		return this.textOutputDirectory.getText().trim();
	}

	@Override
	public void setOutputDirectory(String directoryName) {
		Objects.requireNonNull(directoryName);
		this.textOutputDirectory.setText(formatTool.formatText(directoryName));
		updateOutputDirectory();
	}

	@Override
	public void setButtonLoadEnabled(boolean status) {
		this.buttonInputOntologyDirectory.setEnabled(status);
	}

	@Override
	public void setButtonComputeInferenceEnabled(boolean status) {
		this.buttonComputeInference.setEnabled(status);
	}

	@Override
	public void updateInputOntologyDirectory() {
		String inputOntologyDirectory = getInputOntologyDirectory();
		if (Objects.nonNull(inputOntologyDirectory) && !inputOntologyDirectory.trim().isEmpty()) {
			getModel().setOntologyList(MultiProcessorCore.getOntologyAndNetworkList(getInputOntologyDirectory(),
					getBayesianNetworkDirectory()));
		}
	}

	@Override
	public void updateBayesianNetworkDirectory() {
		String bayesianNetworkDirectory = getBayesianNetworkDirectory();
		if (Objects.nonNull(bayesianNetworkDirectory) && !bayesianNetworkDirectory.trim().isEmpty()) {
			getModel().setOntologyList(MultiProcessorCore.getOntologyAndNetworkList(getInputOntologyDirectory(),
					getBayesianNetworkDirectory()));
		}
	}

	@Override
	public void updateOutputDirectory() {
		String outputDirectory = getOutputDirectory();
		if (Objects.nonNull(outputDirectory) && !outputDirectory.trim().isEmpty()) {
			getModel().setOutputDirectory(outputDirectory);
		}
	}

	@Override
	public void updateSeed() {
		String seedStr = this.textSeed.getText().trim();
		int seed = 0;
		try {
			seed = Integer.parseInt(seedStr);
		} catch (NumberFormatException e) {
		}
		this.textSeed.setText(formatTool.formatText("" + seed));
		this.model.setSeed(seed);
	}

	@Override
	public void updateNumberOfQueries() {
		String numberOfQueriesStr = this.textNumberOfQueries.getText().trim();
		int numberOfQueries = 0;
		try {
			numberOfQueries = Integer.parseInt(numberOfQueriesStr);
		} catch (NumberFormatException e) {
		}
		this.textNumberOfQueries.setText(formatTool.formatText("" + numberOfQueries));
		this.model.setNumberOfQueries(numberOfQueries);
	}

	@Override
	public void update() {
		updateInputOntologyDirectory();
		updateBayesianNetworkDirectory();
		updateOutputDirectory();
		updateSeed();
		updateNumberOfQueries();
	}

	@Override
	public void setComputing(boolean status) {
		labelProgress.setVisible(status);
	}

	@Override
	public String getNumebrOfQueries() {
		return this.textNumberOfQueries.getText().trim();
	}

	@Override
	public String getSeed() {
		return this.textSeed.getText().trim();
	}

	@Override
	public void setSeed(String seed) {
		Objects.requireNonNull(seed);
		this.textSeed.setText(formatTool.formatText(seed));
	}

	@Override
	public void setButtonsEnabled(boolean status) {
		buttonInputOntologyDirectory.setEnabled(status);
		buttonBayesianNetworkDirectory.setEnabled(status);
		buttonOutputDirectory.setEnabled(status);
		buttonUpdateSeed.setEnabled(status);
		buttonComputeInference.setEnabled(status);
	}

	@Override
	public JPanel getPanel() {
		return this;
	}

}
