package de.tudresden.inf.lat.born.gui;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.gui.experimentrunner.ExperimentMakerController;
import de.tudresden.inf.lat.born.gui.processor.ProcessorController;
import de.tudresden.inf.lat.born.gui.testmaker.TestMakerController;

/**
 * This is the controller of the graphical user interface.
 * 
 * @author Julian Mendez
 */
public class BornController {

	private final ProcessorController processorController;
	private final TestMakerController testMakerController;
	private final ExperimentMakerController experimentMakerController;
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
	}

	/**
	 * Returns the model.
	 * 
	 * @return the model
	 */
	public BornModel getModel() {
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
	public BornView getView() {
		return this.view;
	}

	/**
	 * Returns the processor controller.
	 * 
	 * @return the processor controller
	 */
	public ProcessorController getProcessorController() {
		return this.processorController;
	}

	/**
	 * Returns the experiment maker controller.
	 * 
	 * @return the experiment maker controller
	 */
	public ExperimentMakerController getExperimentMakerController() {
		return this.experimentMakerController;
	}

	/**
	 * Returns the test maker controller.
	 * 
	 * @return the test maker controller
	 */
	public TestMakerController getTestMakerController() {
		return this.testMakerController;
	}

}
