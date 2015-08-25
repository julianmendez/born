package de.tudresden.inf.lat.born.gui;

import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.gui.processor.ProcessorController;
import de.tudresden.inf.lat.born.gui.processor.ProcessorView;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;

/**
 * This class is a controller for the main panel.
 * 
 * @author Julian Mendez
 */
public class BornController {

	private ProcessorController processorController;
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
		this.processorController = new ProcessorController(new ProcessorView(
				new ProcessorConfiguration()), ontologyManager);
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
	}

	public void update() {
		getView().update();
	}

	public ProcessorController getProcessorController() {
		return this.processorController;
	}

}
