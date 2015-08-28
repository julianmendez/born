package de.tudresden.inf.lat.born.gui.processor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorCore;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class ProcessorController implements ActionListener {

	private static final String actionSelectInputOntologyFile = "open file";
	private static final String actionSelectBayesianNetworkFile = "Bayesian network file";
	private static final String actionComputeInference = "compute inference";

	public static final String DEFAULT_PROBLOG_DIRECTORY = ProcessorSubApp.DEFAULT_PROBLOG_DIRECTORY;

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;

	private final ProcessorView view;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public ProcessorController(ProcessorView view,
			OWLOntologyManager ontologyManager) {
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
		if (cmd.equals(actionSelectInputOntologyFile)) {
			executeActionSelectInputOntologyFile();
		} else if (cmd.equals(actionSelectBayesianNetworkFile)) {
			executeActionSelectBayesianNetworkFile();
		} else if (cmd.equals(actionComputeInference)) {
			executeActionComputeInference();
		} else {
			throw new IllegalStateException();
		}
	}

	void executeActionSelectInputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setInputOntologyFile(file.getAbsolutePath());
			update();
		}
	}

	void executeActionSelectBayesianNetworkFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			getView().setBayesianNetworkFile(file.getAbsolutePath());
			update();
		}
	}

	void executeActionComputeInference() {
		update();
		long start = System.nanoTime();
		ProcessorCore core = new ProcessorCore();
		String result = core.run(getModel(), start);
		getView().setResult(result);
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
		getView().addButtonSelectInputOntologyFileListener(this,
				actionSelectInputOntologyFile);
		getView().addButtonSelectBayesianNetworkFileListener(this,
				actionSelectBayesianNetworkFile);
		getView().addButtonComputeInferenceListener(this,
				actionComputeInference);

		getModel().setOutputFileName(DEFAULT_TEMPORARY_FILE_NAME);
		getModel().setProblogDirectory(DEFAULT_PROBLOG_DIRECTORY);

		reset();
	}

	public void reset() {

	}

	public void update() {
		getView().update();
	}

}
