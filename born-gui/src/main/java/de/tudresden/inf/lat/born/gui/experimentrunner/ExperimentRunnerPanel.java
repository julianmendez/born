package de.tudresden.inf.lat.born.gui.experimentrunner;

import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.tudresden.inf.lat.born.gui.common.BornIcon;
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

	private FormatTool formatTool = new FormatTool();
	private JButton buttonInputOntologyDirectory = new JButton();
	private JButton buttonBayesianNetworkDirectory = new JButton();
	private JButton buttonOutputDirectory = new JButton();
	private JButton buttonUpdateSeed = new JButton();
	private JButton buttonComputeInference = new JButton();
	private JLabel labelProgress = new JLabel(Message.LBL_COMPUTING);
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
		setLayout(null);

		setBackground(BornIcon.BACKGROUND_COLOR);

		JLabel lblInputOntologyDirectory = new JLabel(Message.LBL_ONTOLOGY_DIRECTORY);
		lblInputOntologyDirectory.setForeground(BornIcon.FOREGROUND_COLOR);
		lblInputOntologyDirectory.setBounds(292, 83, 236, 15);
		add(lblInputOntologyDirectory);

		buttonInputOntologyDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonInputOntologyDirectory.setBounds(216, 43, 54, 28);
		buttonInputOntologyDirectory.setToolTipText(Message.TOOLTIP_BUTTON_OPEN_INPUT_ONTOLOGY_FILE);
		buttonInputOntologyDirectory.setOpaque(false);
		buttonInputOntologyDirectory.setContentAreaFilled(false);
		buttonInputOntologyDirectory.setBorderPainted(false);
		add(buttonInputOntologyDirectory);

		textInputOntologyDirectory.setBackground(BornIcon.TEXT_BACKGROUND_COLOR);
		textInputOntologyDirectory.setBounds(282, 43, 688, 28);
		textInputOntologyDirectory.setToolTipText(Message.TOOLTIP_TEXT_FIELD_INPUT_ONTOLOGY_FILE);
		textInputOntologyDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textInputOntologyDirectory.setEditable(false);
		add(textInputOntologyDirectory);

		JLabel lblBayesianNetworkDirectory = new JLabel(Message.LBL_BAYESIAN_NETWORK_DIRECTORY);
		lblBayesianNetworkDirectory.setForeground(BornIcon.FOREGROUND_COLOR);
		lblBayesianNetworkDirectory.setBounds(292, 186, 236, 15);
		add(lblBayesianNetworkDirectory);

		buttonBayesianNetworkDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonBayesianNetworkDirectory.setBounds(216, 135, 54, 28);
		buttonBayesianNetworkDirectory.setToolTipText(Message.TOOLTIP_BUTTON_OPEN_INPUT_ONTOLOGY_FILE);
		buttonBayesianNetworkDirectory.setOpaque(false);
		buttonBayesianNetworkDirectory.setContentAreaFilled(false);
		buttonBayesianNetworkDirectory.setBorderPainted(false);
		add(buttonBayesianNetworkDirectory);

		textBayesianNetworkDirectory.setBackground(BornIcon.TEXT_BACKGROUND_COLOR);
		textBayesianNetworkDirectory.setBounds(282, 135, 688, 28);
		textBayesianNetworkDirectory.setToolTipText(Message.TOOLTIP_TEXT_FIELD_OPEN_BAYESIAN_NETWORK_FILE);
		textBayesianNetworkDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textBayesianNetworkDirectory.setEditable(false);
		add(textBayesianNetworkDirectory);

		JLabel lblInput = new JLabel(Message.LBL_OUTPUT_DIRECTORY);
		lblInput.setForeground(BornIcon.FOREGROUND_COLOR);
		lblInput.setBounds(292, 283, 236, 15);
		add(lblInput);

		buttonOutputDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonOutputDirectory.setBounds(216, 243, 54, 28);
		buttonOutputDirectory.setToolTipText(Message.TOOLTIP_BUTTON_OUTPUT_DIRECTORY);
		buttonOutputDirectory.setOpaque(false);
		buttonOutputDirectory.setContentAreaFilled(false);
		buttonOutputDirectory.setBorderPainted(false);
		add(buttonOutputDirectory);

		textOutputDirectory.setBackground(BornIcon.TEXT_BACKGROUND_COLOR);
		textOutputDirectory.setBounds(282, 243, 688, 28);
		textOutputDirectory.setToolTipText(Message.TOOLTIP_TEXT_FIELD_OUTPUT_DIRECTORY);
		textOutputDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textOutputDirectory.setEditable(false);
		add(textOutputDirectory);

		JLabel lblNumberOfQueries = new JLabel(Message.LBL_NUMBER_OF_QUERIES);
		lblNumberOfQueries.setForeground(BornIcon.FOREGROUND_COLOR);
		lblNumberOfQueries.setBounds(292, 381, 177, 15);
		add(lblNumberOfQueries);

		textNumberOfQueries.setBackground(BornIcon.TEXT_BACKGROUND_COLOR);
		textNumberOfQueries.setBounds(290, 341, 106, 28);
		textNumberOfQueries.setToolTipText(Message.TOOLTIP_TEXT_FIELD_NUMBER_OF_QUERIES);
		textNumberOfQueries.setAlignmentX(LEFT_ALIGNMENT);
		add(textNumberOfQueries);

		JLabel lblSeed = new JLabel(Message.LBL_SEED);
		lblSeed.setForeground(BornIcon.FOREGROUND_COLOR);
		lblSeed.setBounds(550, 381, 89, 15);
		add(lblSeed);

		textSeed.setBackground(BornIcon.TEXT_BACKGROUND_COLOR);
		textSeed.setBounds(540, 341, 89, 28);
		textSeed.setToolTipText(Message.TOOLTIP_TEXT_FIELD_SEED);
		textSeed.setAlignmentX(LEFT_ALIGNMENT);
		add(textSeed);

		buttonUpdateSeed.setIcon(BornIcon.REFRESH);
		buttonUpdateSeed.setBounds(474, 341, 54, 28);
		buttonUpdateSeed.setToolTipText(Message.TOOLTIP_BUTTON_UPDATE_SEED);
		buttonUpdateSeed.setOpaque(false);
		buttonUpdateSeed.setContentAreaFilled(false);
		buttonUpdateSeed.setBorderPainted(false);
		add(buttonUpdateSeed);

		buttonComputeInference.setIcon(BornIcon.RUN);
		buttonComputeInference.setBounds(227, 429, 54, 28);
		buttonComputeInference.setToolTipText(Message.TOOLTIP_BUTTON_COMPUTE_INFERENCE);
		buttonComputeInference.setOpaque(false);
		buttonComputeInference.setContentAreaFilled(false);
		buttonComputeInference.setBorderPainted(false);
		add(buttonComputeInference);

		labelProgress.setBounds(237, 478, 99, 15);
		labelProgress.setForeground(BornIcon.FOREGROUND_COLOR);
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(BornIcon.BACKGROUND, 0, 0, null);
	}

}
