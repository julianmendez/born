package de.tudresden.inf.lat.born.gui.experimentmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorCore;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class ExperimentMakerController implements ActionListener {

	/**
	 * This class lets the processor run in a separate thread.
	 * 
	 * @author Julian Mendez
	 *
	 */
	class ExperimentMakerRunner extends Thread {

		public void run() {
			long start = System.nanoTime();
			MultiProcessorCore core = new MultiProcessorCore();
			List<String> results = core.run(getModel(), start);

			try {
				core.storeResults(getModel(), results);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			getView().setComputing(false);
			getView().setButtonsEnabled(true);
		}

	}

	private static final String actionInputOntology = "select directory of ontologies";
	private static final String actionBayesianNetwork = "select directory of Bayesian network files";
	private static final String actionConsoleInput = "read console";
	private static final String actionConsoleOutput = "console output";
	private static final String actionComputeInference = "compute inference";

	public static final String DEFAULT_PROBLOG_DIRECTORY = ProcessorSubApp.DEFAULT_PROBLOG_DIRECTORY;

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;

	private final ExperimentMakerView view;

	private ExperimentMakerRunner experimentMakerRunner;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public ExperimentMakerController(ExperimentMakerView view, OWLOntologyManager ontologyManager) {
		this.view = view;
		this.owlOntologyManager = ontologyManager;
		init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		String cmd = e.getActionCommand();
		if (cmd.equals(actionInputOntology)) {
			executeActionInputOntologyDirectory();
		} else if (cmd.equals(actionBayesianNetwork)) {
			executeActionBayesianNetworkDirectory();
		} else if (cmd.equals(actionConsoleInput)) {
			executeActionOutputDirectory();
		} else if (cmd.equals(actionConsoleOutput)) {
			executeActionUpdateSeed();
		} else if (cmd.equals(actionComputeInference)) {
			executeActionComputeInference();
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionInputOntologyDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setInputOntologyDirectory(file.getAbsolutePath());
		}
	}

	void executeActionBayesianNetworkDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setBayesianNetworkDirectory(file.getAbsolutePath());
		}
	}

	void executeActionOutputDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setOutputDirectory(file.getAbsolutePath());
		}
	}

	void executeActionUpdateSeed() {
		double number = Math.random();
		int seed = (int) (number * 1000);
		getView().setSeed("" + seed);
	}

	void executeActionComputeInference() {
		getView().setButtonsEnabled(false);
		getView().setComputing(true);
		getView().update();
		experimentMakerRunner = new ExperimentMakerRunner();
		experimentMakerRunner.start();
	}

	public MultiProcessorConfiguration getModel() {
		return getView().getModel();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	public ExperimentMakerView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		getView().addButtonInputOntologyListener(this, actionInputOntology);
		getView().addButtonBayesianNetworkListener(this, actionBayesianNetwork);
		getView().addButtonConsoleInputListener(this, actionConsoleInput);
		getView().addButtonConsoleOutputListener(this, actionConsoleOutput);
		getView().addButtonComputeInferenceListener(this, actionComputeInference);

		// getModel().setOutputFileName(DEFAULT_TEMPORARY_FILE_NAME);
		getModel().setProblogDirectory(DEFAULT_PROBLOG_DIRECTORY);
	}

}
