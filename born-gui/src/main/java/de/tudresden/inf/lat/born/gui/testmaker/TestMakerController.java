package de.tudresden.inf.lat.born.gui.testmaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;

/**
 * This class is a controller for the test maker.
 * 
 * @author Julian Mendez
 */
public class TestMakerController implements ActionListener {

	private static final String actionSelectInputOntologyFile = "open input ontology";
	private static final String actionSelectOutputOntologyFile = "save output ontology";
	private static final String actionSaveBayesianNetwork = "save Bayesian network";

	public static final String DEFAULT_PROBLOG_DIRECTORY = ProcessorSubApp.DEFAULT_PROBLOG_DIRECTORY;

	public static final String DEFAULT_TEMPORARY_FILE_NAME = "/tmp/temporary_born_output_file.txt";

	private final OWLOntologyManager owlOntologyManager;

	private final TestMakerView view;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public TestMakerController(TestMakerView view, OWLOntologyManager ontologyManager) {
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
		} else if (cmd.equals(actionSelectOutputOntologyFile)) {
			executeActionSelectOutputOntologyFile();
		} else if (cmd.equals(actionSaveBayesianNetwork)) {
			executeActionSaveBayesianNetwork();
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
			getView().updateOntologyFile();
		}
	}

	void executeActionSelectOutputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			// getView().writeConsoleOutput(file.getAbsolutePath());
		}
	}

	void executeActionSaveBayesianNetwork() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showSaveDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			// getView().writeConsoleOutput(file.getAbsolutePath());
		}
	}

	public AnnotatorConfiguration getModel() {
		return getView().getModel();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	public TestMakerView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		getView().addButtonSelectInputOntologyFileListener(this, actionSelectInputOntologyFile);
		getView().addButtonSelectOutputOntologyFileListener(this, actionSelectOutputOntologyFile);
		getView().addButtonSaveBayesianNetworkListener(this, actionSaveBayesianNetwork);
	}

	public void reset() {

	}

}
