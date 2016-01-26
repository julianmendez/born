package de.tudresden.inf.lat.born.protege;

import java.awt.BorderLayout;

import org.protege.editor.owl.ui.view.cls.AbstractOWLClassViewComponent;
import org.semanticweb.owlapi.model.OWLClass;

import de.tudresden.inf.lat.born.main.BornStarter;

/**
 * This is the Protege view component of BORN.
 * 
 * @author Julian Mendez
 */
public class Main extends AbstractOWLClassViewComponent {

	private static final long serialVersionUID = -5363687740449453246L;

	private BornStarter bornStarter = null;

	@Override
	public void disposeView() {
	}

	/**
	 * Initializes the data and GUI. This method is called when the view is
	 * initialized.
	 */
	@Override
	public void initialiseClassView() {
		this.bornStarter = new BornStarter(getOWLModelManager().getOWLOntologyManager());
		this.setLayout(new BorderLayout());
		add(this.bornStarter.getPanel().getView().getPanel(), BorderLayout.CENTER);
	}

	@Override
	protected OWLClass updateView(OWLClass selectedClass) {
		return selectedClass;
	}

}
