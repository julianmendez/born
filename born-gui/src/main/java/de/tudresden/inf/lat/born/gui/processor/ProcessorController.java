package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.example.ExampleLoader;
import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorCore;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResult;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorExecutionResultImpl;
import de.tudresden.inf.lat.born.problog.type.ProblogProgram;

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
			ProcessorExecutionResult executionResult = new ProcessorExecutionResultImpl();
			core.run(getModel(), start, executionResult);

			getView().setResult(executionResult.getResult());
			getView().setComputing(false);
			getView().setButtonsEnabled(true);
		}

	}

	private static final String actionInputOntology = "open file";
	private static final String actionBayesianNetwork = "Bayesian network file";
	private static final String actionResetCompletionRules = "reset completion rules";
	private static final String actionGoToPreviousCompletionRules = "go to previous completion rules";
	private static final String actionConsoleInput = "read console";
	private static final String actionConsoleOutput = "console output";
	private static final String actionComputeInference = "compute inference";
	private static final String actionComboBoxExample = "choose example";
	private static final String actionUpdateExample = "update example";

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;
	private final ProcessorView view;
	private ProcessorRunner processorRunner;
	private final ExampleLoader exampleLoader = new ExampleLoader();
	private String previousCompletionRules = "";

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public ProcessorController(ProcessorView view, OWLOntologyManager ontologyManager) {
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
			executeActionInputOntology();
		} else if (cmd.equals(actionBayesianNetwork)) {
			executeActionBayesianNetwork();
		} else if (cmd.equals(actionResetCompletionRules)) {
			executeActionResetCompletionRules();
		} else if (cmd.equals(actionGoToPreviousCompletionRules)) {
			executeActionGoToPreviousCompletionRules();
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
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			getView().setOntologyFile(file.getAbsolutePath());
			getView().updateOntologyFile();
		}
	}

	void executeActionBayesianNetwork() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			getView().setBayesianNetworkFile(file.getAbsolutePath());
			getView().updateBayesianNetworkFile();
		}
	}

	void executeActionResetCompletionRules() {
		String defaultCompletionRules = (new ProblogProgram())
				.asStringC((new ProblogInputCreator()).getDefaultCompletionRules());
		getView().setCompletionRules(defaultCompletionRules);
	}

	void executeActionGoToPreviousCompletionRules() {
		if (!this.previousCompletionRules.isEmpty()) {
			getView().setCompletionRules(this.previousCompletionRules);
			this.previousCompletionRules = "";
			getView().setButtonGoToPreviousCompletionRulesEnabled(false);
		}
	}

	void executeActionConsoleInput() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			getView().readConsoleInput(file.getAbsolutePath());
		}
	}

	void executeActionConsoleOutput() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (!Objects.isNull(file)) {
			getView().writeConsoleOutput(file.getAbsolutePath());
		}
	}

	void executeActionComputeInference() {
		getView().setButtonsEnabled(false);
		getView().setComputing(true);
		getView().updateQuery();
		getView().updateCompletionRules();
		this.previousCompletionRules = getModel().getAdditionalCompletionRules();
		this.processorRunner = new ProcessorRunner();
		this.processorRunner.start();
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

		getView().setConsoleOutput("");
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public ProcessorConfiguration getModel() {
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
	public ProcessorView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		getModel().setUseOfDefaultCompletionRules(false);

		getView().addButtonOntologyFileListener(this, actionInputOntology);
		getView().addButtonBayesianNetworkFileListener(this, actionBayesianNetwork);
		getView().addButtonResetCompletionRulesListener(this, actionResetCompletionRules);
		getView().addButtonGoToPreviousCompletionRulesListener(this, actionGoToPreviousCompletionRules);
		getView().addButtonConsoleInputListener(this, actionConsoleInput);
		getView().addButtonConsoleOutputListener(this, actionConsoleOutput);
		getView().addButtonComputeInferenceListener(this, actionComputeInference);
		getView().addComboBoxExampleListener(this, actionComboBoxExample);
		getView().addButtonUpdateExampleListener(this, actionUpdateExample);

		getView().addExamples(this.exampleLoader.getExampleConfigurations());

		getModel().setOutputFileName(DEFAULT_TEMPORARY_FILE_NAME);

		executeActionResetCompletionRules();
	}

}
