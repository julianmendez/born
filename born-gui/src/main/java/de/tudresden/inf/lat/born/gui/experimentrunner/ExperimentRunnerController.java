package de.tudresden.inf.lat.born.gui.experimentrunner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorCore;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class ExperimentRunnerController implements ActionListener {

	/**
	 * This class lets the multi processor run in a separate thread.
	 * 
	 * @author Julian Mendez
	 *
	 */
	class ExperimentMakerRunner extends Thread {

		/**
		 * Runs the experiments.
		 */
		public void run() {
			long start = System.nanoTime();
			MultiProcessorCore core = new MultiProcessorCore();
			List<String> results = core.run(getModel(), start);

			try {
				core.storeResults(getModel(), results);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
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

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;
	private final ExperimentRunnerView view;
	private ExperimentMakerRunner experimentMakerRunner;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public ExperimentRunnerController(ExperimentRunnerView view, OWLOntologyManager ontologyManager) {
		Objects.requireNonNull(view);
		Objects.requireNonNull(ontologyManager);
		this.view = view;
		this.owlOntologyManager = ontologyManager;
		init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Objects.requireNonNull(e);
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

	/**
	 * Opens a dialog and choose the directory of ontologies.
	 */
	void executeActionInputOntologyDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setInputOntologyDirectory(file.getAbsolutePath());
		}
	}

	/**
	 * Opens a dialog and choose the directory of Bayesian networks.
	 */
	void executeActionBayesianNetworkDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setBayesianNetworkDirectory(file.getAbsolutePath());
		}
	}

	/**
	 * Open a dialog and choose the directory for the results.
	 */
	void executeActionOutputDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setOutputDirectory(file.getAbsolutePath());
		}
	}

	/**
	 * Updates the seed for the pseudorandom number generator.
	 */
	void executeActionUpdateSeed() {
		double number = Math.random();
		int seed = (int) (number * 1000);
		getView().setSeed("" + seed);
	}

	/**
	 * Runs the experiments for the given ontologies and Bayesian networks.
	 */
	void executeActionComputeInference() {
		getView().setButtonsEnabled(false);
		getView().setComputing(true);
		getView().update();
		experimentMakerRunner = new ExperimentMakerRunner();
		experimentMakerRunner.start();
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public MultiProcessorConfiguration getModel() {
		return getView().getModel();
	}

	/**
	 * Returns the OWL ontology manager.
	 * 
	 * @return the OWL ontology manager
	 */
	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	/**
	 * Returns the view.
	 * 
	 * @return the view
	 */
	public ExperimentRunnerView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	void init() {
		getView().addButtonInputOntologyListener(this, actionInputOntology);
		getView().addButtonBayesianNetworkListener(this, actionBayesianNetwork);
		getView().addButtonOutputDirectoryListener(this, actionConsoleInput);
		getView().addButtonUpdateSeedListener(this, actionConsoleOutput);
		getView().addButtonComputeInferenceListener(this, actionComputeInference);

		// getModel().setOutputFileName(DEFAULT_TEMPORARY_FILE_NAME);
	}

}
