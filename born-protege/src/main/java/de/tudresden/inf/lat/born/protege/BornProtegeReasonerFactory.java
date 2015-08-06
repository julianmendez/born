package de.tudresden.inf.lat.born.protege;

import org.protege.editor.owl.model.inference.AbstractProtegeOWLReasonerInfo;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import de.tudresden.inf.lat.born.owlapi.main.BornReasonerFactory;

/**
 * This factory is used by Protege.
 * 
 * @author Julian Mendez
 */
public class BornProtegeReasonerFactory extends AbstractProtegeOWLReasonerInfo {

	@Override
	public void dispose() throws Exception {
	}

	@Override
	public OWLReasonerFactory getReasonerFactory() {
		return new BornReasonerFactory();
	}

	@Override
	public BufferingMode getRecommendedBuffering() {
		return BufferingMode.BUFFERING;
	}

	@Override
	public void initialise() throws Exception {
	}

}
