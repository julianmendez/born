package de.tudresden.inf.lat.born.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class BornController implements ActionListener {

	private static final String actionSelectInputOntologyFile = "open file";
	private static final String actionSelectBayesianNetworkFile = "Bayesian network file";
	private static final String actionComputeInference = "compute inference";

	private final OWLOntologyManager owlOntologyManager;

	private final BornView view;

	/**
	 * Constructs a new controller.
	 * 
	 * @param view
	 *            panel to be controlled
	 * @param ontologyManager
	 *            an OWL ontology manager
	 */
	public BornController(BornView view, OWLOntologyManager ontologyManager) {
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

	private void executeActionSelectInputOntologyFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			try {
				OWLOntology owlInputOntology = this.owlOntologyManager
						.loadOntologyFromOntologyDocument(file);
				getModel().getProcessorConfiguration().setOntologyFileName(
						file.getAbsolutePath());
				getModel().setInputOntology(owlInputOntology);
				update();
			} catch (OWLOntologyCreationException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void executeActionSelectBayesianNetworkFile() {
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(getView());
		File file = null;
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		if (file != null) {
			update();
		}
	}

	private void executeActionComputeInference() {

	}

	public BornModel getModel() {
		return getView().getModel();
	}

	public OWLOntologyManager getOWLOntologyManager() {
		return this.owlOntologyManager;
	}

	public BornView getView() {
		return this.view;
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	private void init() {
		// getView().addButtonSelectInputOntologyFileListener(this,
		// actionSelectInputOntologyFile);
		// getView().addButtonSelectBayesianNetworkFileListener(this,
		// actionSelectBayesianNetworkFile);
		// getView().addButtonComputeInferenceListener(this,
		// actionComputeInference);

		reset();
	}

	public void reset() {
		// getView().setButtonComputeInferenceEnabled(false);
	}

	public void update() {
		getView().update();
	}

}
