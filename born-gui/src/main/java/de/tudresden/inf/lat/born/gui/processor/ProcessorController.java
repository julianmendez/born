package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.example.ExampleLoader;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorCore;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class ProcessorController implements ActionListener {

	/**
	 * This class lets the processor run in a separate thread.
	 * 
	 * @author Julian Mendez
	 *
	 */
	class ProcessorRunner extends Thread {

		public void run() {
			long start = System.nanoTime();
			ProcessorCore core = new ProcessorCore();
			String result = core.run(getModel(), start);

			getView().setResult(result);
			getView().setComputing(false);
			getView().setButtonsEnabled(true);
		}

	}

	private static final String actionInputOntology = "open file";
	private static final String actionBayesianNetwork = "Bayesian network file";
	private static final String actionConsoleInput = "read console";
	private static final String actionConsoleOutput = "console output";
	private static final String actionComputeInference = "compute inference";
	private static final String actionComboBoxExample = "choose example";
	private static final String actionUpdateExample = "update example";

	public static final String DEFAULT_PROBLOG_DIRECTORY = ProcessorSubApp.DEFAULT_PROBLOG_DIRECTORY;

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;

	private final ProcessorView view;

	private ProcessorRunner processorRunner;

	private final ExampleLoader exampleLoader = new ExampleLoader();

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public ProcessorController(ProcessorView view, OWLOntologyManager ontologyManager) {
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
			executeActionInputOntology();
		} else if (cmd.equals(actionBayesianNetwork)) {
			executeActionBayesianNetwork();
		} else if (cmd.equals(actionConsoleInput)) {
			executeActionConsoleInput();
		} else if (cmd.equals(actionConsoleOutput)) {
			executeActionConsoleOutput();
		} else if (cmd.equals(actionComputeInference)) {
			executeActionComputeInference();
		} else if (cmd.equals(actionComboBoxExample)) {
			executeActionComboBoxExample();
		} else if (cmd.equals(actionUpdateExample)) {
			executeActionUpdateExample();
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionInputOntology() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setOntologyFile(file.getAbsolutePath());
			getView().updateOntologyFile();
		}
	}

	void executeActionBayesianNetwork() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setBayesianNetworkFile(file.getAbsolutePath());
			getView().updateBayesianNetworkFile();
		}
	}

	void executeActionConsoleInput() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().readConsoleInput(file.getAbsolutePath());
		}
	}

	void executeActionConsoleOutput() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().writeConsoleOutput(file.getAbsolutePath());
		}
	}

	void executeActionComputeInference() {
		getView().setButtonsEnabled(false);
		getView().setComputing(true);
		getView().update();
		processorRunner = new ProcessorRunner();
		processorRunner.start();
	}

	void executeActionComboBoxExample() {
		// ignore this action
	}

	void executeActionUpdateExample() {
		int index = getView().getComboBoxExampleIndex();
		ExampleConfiguration exampleConfiguration = this.exampleLoader.getExampleConfigurations().get(index);

		getView().setOntologyFile(exampleConfiguration.getOntologyFileName());
		getModel().setOntology(exampleConfiguration.getOntology());

		getView().setBayesianNetworkFile(exampleConfiguration.getBayesianNetworkFileName());
		getModel().setBayesianNetwork(exampleConfiguration.getBayesianNetwork());

		getView().setConsoleInput(exampleConfiguration.getQuery());
		getModel().setQuery(exampleConfiguration.getQuery());
	}

	public ProcessorConfiguration getModel() {
		return getView().getModel();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	public ProcessorView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		getView().addButtonOntologyFileListener(this, actionInputOntology);
		getView().addButtonBayesianNetworkFileListener(this, actionBayesianNetwork);
		getView().addButtonConsoleInputListener(this, actionConsoleInput);
		getView().addButtonConsoleOutputListener(this, actionConsoleOutput);
		getView().addButtonComputeInferenceListener(this, actionComputeInference);
		getView().addComboBoxExampleListener(this, actionComboBoxExample);
		getView().addButtonUpdateExampleListener(this, actionUpdateExample);

		getView().addExamples(this.exampleLoader.getExampleConfigurations());

		getModel().setOutputFileName(DEFAULT_TEMPORARY_FILE_NAME);
		getModel().setProblogDirectory(DEFAULT_PROBLOG_DIRECTORY);

		reset();

	}

	public void reset() {

	}

}
