/*
 *
 * Copyright (C) 2015 Julian Mendez
 *
 */

package de.tudresden.inf.lat.born.owlapi.main;

import java.util.Objects;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

/**
 * This factory creates an instance of a BORN reasoner based on an ontology and
 * a optional configuration.
 * 
 * @author Julian Mendez
 */
public class BornReasonerFactory implements OWLReasonerFactory {

	// private static final Logger logger = Logger
	// .getLogger("de.tudresden.inf.lat.born");

	@Override
	public BornReasoner createNonBufferingReasoner(OWLOntology ontology) {
		Objects.requireNonNull(ontology);
		return new BornReasoner(ontology, false);
	}

	@Override
	public BornReasoner createNonBufferingReasoner(OWLOntology ontology, OWLReasonerConfiguration configuration)
			throws IllegalConfigurationException {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(configuration);
		return new BornReasoner(ontology, false, configuration);
	}

	@Override
	public BornReasoner createReasoner(OWLOntology ontology) {
		Objects.requireNonNull(ontology);
		return new BornReasoner(ontology, true);
	}

	@Override
	public BornReasoner createReasoner(OWLOntology ontology, OWLReasonerConfiguration configuration)
			throws IllegalConfigurationException {
		Objects.requireNonNull(ontology);
		Objects.requireNonNull(configuration);
		return new BornReasoner(ontology, true, configuration);
	}

	@Override
	public String getReasonerName() {
		return getClass().getPackage().getImplementationTitle();
	}

}
