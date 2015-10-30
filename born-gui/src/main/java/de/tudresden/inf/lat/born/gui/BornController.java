package de.tudresden.inf.lat.born.gui;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.gui.experimentmaker.ExperimentMakerController;
import de.tudresden.inf.lat.born.gui.processor.ProcessorController;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerController;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class BornController {

	private ProcessorController processorController;
	private ExperimentMakerController experimentMakerController;
	private TestMakerController testMakerController;

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
		this.processorController = new ProcessorController(view.getProcessorView(), ontologyManager);
		this.experimentMakerController = new ExperimentMakerController(view.getExperimentMakerView(), ontologyManager);
		this.testMakerController = new TestMakerController(view.getTestMakerView(), ontologyManager);
		reset();
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

	public void reset() {
		this.processorController.reset();
		this.experimentMakerController.reset();
		this.testMakerController.reset();
	}

	public ProcessorController getProcessorController() {
		return this.processorController;
	}

	public ExperimentMakerController getExperimentMakerController() {
		return this.experimentMakerController;
	}

	public TestMakerController getTestMakerController() {
		return this.testMakerController;
	}

}
