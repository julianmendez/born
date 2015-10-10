package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * An object of this class is a pair OWL ontology - Bayesian network.
 * 
 * @author Julian Mendez
 *
 */
public class OntologyAndNetwork {

	private final OWLOntology owlOntology;
	private final String bayesianNetwork;

	/**
	 * Constructs a new pair OWL ontology - Bayesian network.
	 */
	public OntologyAndNetwork(OWLOntology owlOntology, String bayesianNetwork) {
		if (owlOntology == null) {
			throw new IllegalArgumentException("Null arguments.");
		}
		if (bayesianNetwork == null) {
			throw new IllegalArgumentException("Null arguments.");
		}

		this.owlOntology = owlOntology;
		this.bayesianNetwork = bayesianNetwork;
	}

	/**
	 * Returns the OWL ontology;
	 * 
	 * @return the OWL ontology
	 */
	public OWLOntology getOntology() {
		return this.owlOntology;
	}

	/**
	 * Returns the Bayesian network.
	 * 
	 * @return the Bayesian network
	 */
	public String getBayesianNetwork() {
		return this.bayesianNetwork;
	}

	@Override
	public int hashCode() {
		return this.owlOntology.hashCode() + 0x1F * this.bayesianNetwork.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof OntologyAndNetwork) {
			OntologyAndNetwork other = (OntologyAndNetwork) obj;
			return getOntology().equals(other.getOntology()) && getBayesianNetwork().equals(other.getBayesianNetwork());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getBayesianNetwork() + "\n\n" + getOntology();
	}

}
