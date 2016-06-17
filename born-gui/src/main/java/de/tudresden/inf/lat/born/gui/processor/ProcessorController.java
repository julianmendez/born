package de.tudresden.inf.lat.born.gui.processor;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.core.common.ResourceConstant;
import de.tudresden.inf.lat.born.gui.common.TextViewer;
import de.tudresden.inf.lat.born.owlapi.example.ExampleConfiguration;
import de.tudresden.inf.lat.born.owlapi.example.ExampleLoader;
import de.tudresden.inf.lat.born.owlapi.processor.ProblogInputCreator;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfigurationImpl;
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

			String resultText = executionResult.getResult();
			ProblogInputCreator problogInputCreator = new ProblogInputCreator();
			String result = problogInputCreator.replaceByPrefixes(getModel().getOntology(), resultText);

			getView().setResult(result);
			getView().setComputing(false);
			getView().setButtonsEnabled(true);
		}

	}

	private static final String actionInputOntology = "open ontology file";
	private static final String actionViewOntology = "view ontology file";
	private static final String actionBayesianNetwork = "Bayesian network file";
	private static final String actionViewBayesianNetwork = "view Bayesian network file";
	private static final String actionResetCompletionRules = "reset completion rules";
	private static final String actionGoToPreviousCompletionRules = "go to previous completion rules";
	private static final String actionConsoleInput = "read console";
	private static final String actionConsoleOutput = "console output";
	private static final String actionComputeInference = "compute inference";
	private static final String actionComboBoxExample = "choose example";

	private final OWLOntologyManager owlOntologyManager;
	private final ProcessorView view;
	private ProcessorRunner processorRunner;
	private final ExampleLoader exampleLoader = new ExampleLoader();
	private String lastUsedCompletionRules = "";
	private String previousToLastUsedCompletionRules = "";
	private File lastPath = null;

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
		} else if (cmd.equals(actionViewOntology)) {
			executeActionViewOntology();
		} else if (cmd.equals(actionBayesianNetwork)) {
			executeActionBayesianNetwork();
		} else if (cmd.equals(actionViewBayesianNetwork)) {
			executeActionViewBayesianNetwork();
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
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionInputOntology() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setOntologyFile(file.getAbsolutePath());
			getView().updateOntologyFile();
			this.lastPath = file.getParentFile();
		}
	}

	void executeActionViewOntology() {
		String text = "";
		try {
			text = ProcessorConfigurationImpl.read(new FileReader(getView().getOntologyFile()));
		} catch (IOException e) {
			text = e.getMessage();
		}
		TextViewer panel = new TextViewer();
		panel.setBounds(new Rectangle(0, 0, 800, 600));
		panel.setVisible(true);
		panel.getView().setModel(text);
	}

	void executeActionBayesianNetwork() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().setBayesianNetworkFile(file.getAbsolutePath());
			getView().updateBayesianNetworkFile();
			this.lastPath = file.getParentFile();
		}
	}

	void executeActionViewBayesianNetwork() {
		String text = "";
		try {
			text = ProcessorConfigurationImpl.read(new FileReader(getView().getBayesianNetworkFile()));
		} catch (IOException e) {
			text = e.getMessage();
		}
		TextViewer panel = new TextViewer();
		panel.setBounds(new Rectangle(0, 0, 800, 600));
		panel.setVisible(true);
		panel.getView().setModel(text);
	}

	void executeActionResetCompletionRules() {
		String defaultCompletionRules = (new ProblogProgram())
				.asStringWithTabs((new ProblogInputCreator()).getDefaultCompletionRules());
		getView().setCompletionRules(defaultCompletionRules);
		updatePreviousToLastUsedCompletionRules(defaultCompletionRules);
		updatePreviousToLastUsedCompletionRules(defaultCompletionRules);
	}

	void executeActionGoToPreviousCompletionRules() {
		this.lastUsedCompletionRules = this.previousToLastUsedCompletionRules;
		getView().setCompletionRules(this.previousToLastUsedCompletionRules);
	}

	void executeActionConsoleInput() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showOpenDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().readConsoleInput(file.getAbsolutePath());
			this.lastPath = file.getParentFile();
		}
	}

	void executeActionConsoleOutput() {
		JFileChooser fileChooser = new JFileChooser(this.lastPath);
		int returnVal = fileChooser.showSaveDialog(getView().getPanel());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (Objects.nonNull(file)) {
			getView().writeConsoleOutput(file.getAbsolutePath());
			this.lastPath = file.getParentFile();
		}
	}

	void executeActionComputeInference() {
		getView().setButtonsEnabled(false);
		getView().setComputing(true);
		getView().updateQuery();
		getView().updateCompletionRules();
		updatePreviousToLastUsedCompletionRules(getModel().getAdditionalCompletionRules());
		this.processorRunner = new ProcessorRunner();
		this.processorRunner.start();
	}

	void executeActionComboBoxExample() {
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
	void init() {
		getModel().setUseOfDefaultCompletionRules(false);
		getModel().setOutputFileName(ResourceConstant.DEFAULT_TEMPORARY_FILE_NAME);

		getView().addButtonOntologyFileListener(this, actionInputOntology);
		getView().addButtonViewOntologyListener(this, actionViewOntology);
		getView().addButtonBayesianNetworkFileListener(this, actionBayesianNetwork);
		getView().addButtonViewBayesianNetworkListener(this, actionViewBayesianNetwork);
		getView().addButtonResetCompletionRulesListener(this, actionResetCompletionRules);
		getView().addButtonGoToPreviousCompletionRulesListener(this, actionGoToPreviousCompletionRules);
		getView().addButtonConsoleInputListener(this, actionConsoleInput);
		getView().addButtonConsoleOutputListener(this, actionConsoleOutput);
		getView().addButtonComputeInferenceListener(this, actionComputeInference);
		getView().addComboBoxExampleListener(this, actionComboBoxExample);

		getView().addExamples(this.exampleLoader.getExampleConfigurations());

		executeActionResetCompletionRules();
	}

	void updatePreviousToLastUsedCompletionRules(String current) {
		this.previousToLastUsedCompletionRules = this.lastUsedCompletionRules;
		this.lastUsedCompletionRules = current;
	}

}
