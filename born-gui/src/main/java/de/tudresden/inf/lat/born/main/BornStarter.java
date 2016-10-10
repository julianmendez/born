package de.tudresden.inf.lat.born.main;

import java.util.List;
import java.util.Objects;

import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.model.OWLOntologyLoaderListener;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.tudresden.inf.lat.born.gui.BornController;
import de.tudresden.inf.lat.born.gui.BornModelImpl;
import de.tudresden.inf.lat.born.gui.BornPanel;

/**
 * This is used to start the system.
 * 
 * @author Julian Mendez
 */
public class BornStarter implements OWLOntologyChangeListener, OWLOntologyLoaderListener {

	private static final long serialVersionUID = 3923893802046694090L;
	private final OWLOntologyManager ontologyManager;
	private final BornController panel;

	/**
	 * Constructs a new starter.
	 * 
	 * @param manager
	 *            OWL ontology manager
	 */
	public BornStarter(OWLOntologyManager manager) {
		this.ontologyManager = Objects.requireNonNull(manager, "Ontology manager must not be null");
		this.panel = new BornController(new BornPanel(new BornModelImpl()), this.ontologyManager);
		getOWLOntologyManager().addOntologyLoaderListener(this);
		getOWLOntologyManager().addOntologyChangeListener(this);
	}

	@Override
	public void finishedLoadingOntology(LoadingFinishedEvent event) {
		Objects.requireNonNull(event);
		// TODO
	}

	/**
	 * Returns the OWL ontology manager.
	 * 
	 * @return the OWL ontology manager
	 */
	public OWLOntologyManager getOWLOntologyManager() {
		return this.ontologyManager;
	}

	/**
	 * Returns the panel.
	 * 
	 * @return the panel
	 */
	public BornController getPanel() {
		return this.panel;
	}

	@Override
	public void ontologiesChanged(List<? extends OWLOntologyChange> change) {
		Objects.requireNonNull(change);
		// TODO
	}

	@Override
	public void startedLoadingOntology(LoadingStartedEvent event) {
		Objects.requireNonNull(event);
		// TODO
	}

}
