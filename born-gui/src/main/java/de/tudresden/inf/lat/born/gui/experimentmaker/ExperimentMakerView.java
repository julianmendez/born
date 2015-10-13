package de.tudresden.inf.lat.born.gui.experimentmaker;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import de.tudresden.inf.lat.born.gui.BornIcon;
import de.tudresden.inf.lat.born.gui.Message;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.OntologyAndNetwork;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This is the panel to compute inference.
 * 
 * @author Julian Mendez
 */
public class ExperimentMakerView extends JPanel {

	private static final long serialVersionUID = 8987374313881883318L;

	public static final String OWL_EXTENSION = ".owl";
	public static final String PL_EXTENSION = ".pl";

	public static final String SLASH = "/";

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

	public ExperimentMakerView(MultiProcessorConfiguration model) {
		if (model == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.model = model;
		setLayout(null);
		createPanel();
	}

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

	public void addButtonConsoleInputListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonOutputDirectory.addActionListener(listener);
		this.buttonOutputDirectory.setActionCommand(actionCommand);
	}

	public void addButtonConsoleOutputListener(ActionListener listener, String actionCommand) {
		if (listener == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (actionCommand == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.buttonUpdateSeed.addActionListener(listener);
		this.buttonUpdateSeed.setActionCommand(actionCommand);
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
		add(textBayesianNetworkDirectory);

		JLabel lblInput = new JLabel("output directory");
		lblInput.setBounds(292, 283, 128, 15);
		add(lblInput);

		buttonOutputDirectory.setIcon(BornIcon.OPEN_FILE);
		buttonOutputDirectory.setBounds(216, 243, 54, 28);
		buttonOutputDirectory.setToolTipText(Message.tooltipButtonOutputDirectory);
		add(buttonOutputDirectory);

		textOutputDirectory = new JTextField();
		textOutputDirectory.setToolTipText(Message.tooltipTextFieldOutputDirectory);
		textOutputDirectory.setAlignmentX(LEFT_ALIGNMENT);
		textOutputDirectory.setBounds(282, 243, 688, 28);
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

	public MultiProcessorConfiguration getModel() {
		return this.model;
	}

	public String getInputOntologyDirectory() {
		return this.textInputOntologyDirectory.getText();
	}

	public void setInputOntologyDirectory(String fileName) {
		this.textInputOntologyDirectory.setText(fileName);
		updateInputOntologyDirectory();
	}

	public String getBayesianNetworkDirectory() {
		return this.textBayesianNetworkDirectory.getText();
	}

	public void setBayesianNetworkDirectory(String fileName) {
		this.textBayesianNetworkDirectory.setText(fileName);
		updateBayesianNetworkDirectory();
	}

	public String getOutputDirectory() {
		return this.textOutputDirectory.getText();
	}

	public void setOutputDirectory(String fileName) {
		this.textOutputDirectory.setText(fileName);
		updateOutputDirectory();
	}

	public void setButtonLoadEnabled(boolean b) {
		this.buttonInputOntologyDirectory.setEnabled(b);
	}

	public void setButtonComputeInferenceEnabled(boolean b) {
		this.buttonComputeInference.setEnabled(b);
	}

	List<OntologyAndNetwork> getOntologyAndNetworkList(String ontologyDirectory, String bayesianNetworkDirectory) {
		if (ontologyDirectory == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (bayesianNetworkDirectory == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		try {
			List<OntologyAndNetwork> ret = new ArrayList<OntologyAndNetwork>();
			if (!ontologyDirectory.isEmpty() && !bayesianNetworkDirectory.isEmpty()) {
				File file = new File(ontologyDirectory);
				File[] files = file.listFiles();
				Arrays.sort(files);

				for (int index = 0; index < files.length; index++) {

					String fileName = files[index].getName();
					if (fileName.endsWith(OWL_EXTENSION)) {
						String ontologyName = fileName.substring(0, fileName.length() - OWL_EXTENSION.length());

						File ontologyFile = new File(ontologyDirectory + SLASH + ontologyName + OWL_EXTENSION);
						File bayesianNetworkFile = new File(
								bayesianNetworkDirectory + SLASH + ontologyName + PL_EXTENSION);

						OWLOntology owlOntology = ProcessorConfiguration
								.readOntology(new FileInputStream(ontologyFile));

						String bayesianNetwork = "";
						if (bayesianNetworkFile.exists()) {
							bayesianNetwork = ProcessorConfiguration.read(new FileReader(bayesianNetworkFile));
						}

						ret.add(new OntologyAndNetwork(ontologyName, owlOntology, bayesianNetwork));
					}
				}
			}
			return ret;

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OWLOntologyCreationException e) {
			throw new RuntimeException(e);
		}
	}

	void updateInputOntologyDirectory() {
		String inputOntologyDirectory = getInputOntologyDirectory();
		if (inputOntologyDirectory != null && !inputOntologyDirectory.trim().isEmpty()) {
			getModel().setOntologyList(
					getOntologyAndNetworkList(getInputOntologyDirectory(), getBayesianNetworkDirectory()));
		}
	}

	void updateBayesianNetworkDirectory() {
		String bayesianNetworkDirectory = getBayesianNetworkDirectory();
		if (bayesianNetworkDirectory != null && !bayesianNetworkDirectory.trim().isEmpty()) {
			getModel().setOntologyList(
					getOntologyAndNetworkList(getInputOntologyDirectory(), getBayesianNetworkDirectory()));
		}
	}

	void updateOutputDirectory() {
		String outputDirectory = getOutputDirectory();
		if (outputDirectory != null && !outputDirectory.trim().isEmpty()) {
			getModel().setOutputDirectory(outputDirectory);
		}
	}

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

	void updateNumberOfQueries() {
		String numberOfQueriesStr = this.textNumberOfQueries.getText();
		int numberOfQueries = 0;
		try {
			numberOfQueries = Integer.parseInt(numberOfQueriesStr);
		} catch (NumberFormatException e) {
		}
		this.textSeed.setText("" + numberOfQueries);
		this.model.setNumberOfQueries(numberOfQueries);
	}

	public void update() {
		updateInputOntologyDirectory();
		updateBayesianNetworkDirectory();
		updateOutputDirectory();
		updateSeed();
		updateNumberOfQueries();
	}

	public void setResult(String result) {
	}

	public void setComputing(boolean status) {
		labelProgress.setVisible(status);
	}

	public String getNumebrOfQueries() {
		return this.textNumberOfQueries.getText();
	}

	public String getSeed() {
		return this.textSeed.getText();
	}

	public void setSeed(String seed) {
		this.textSeed.setText(seed);
	}

	public void setButtonsEnabled(boolean status) {
		buttonInputOntologyDirectory.setEnabled(status);
		buttonBayesianNetworkDirectory.setEnabled(status);
		buttonOutputDirectory.setEnabled(status);
		buttonUpdateSeed.setEnabled(status);
		buttonComputeInference.setEnabled(status);
	}

}
