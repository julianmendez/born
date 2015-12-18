package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.Objects;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * An object of this class is a tuple containing an ontology name, an OWL
 * ontology, and a Bayesian network. The ontology name can be a file name, but
 * that is not mandatory.
 * 
 * @author Julian Mendez
 *
 */
public class OntologyAndNetwork {

	private final String ontologyName;
	private final OWLOntology owlOntology;
	private final String bayesianNetwork;

	/**
	 * Constructs a new ontology and Bayesian network object with an empty
	 * Bayesian network.
	 * 
	 * @param owlOntology
	 *            OWL ontology
	 */
	public OntologyAndNetwork(OWLOntology owlOntology) {
		this(owlOntology, "");
	}

	/**
	 * Constructs a new ontology and Bayesian network object. The ontology name
	 * is the OWL ontology ID.
	 * 
	 * @param owlOntology
	 *            OWL ontology
	 * @param bayesianNetwork
	 *            Bayesian network in ProbLog syntax
	 */
	public OntologyAndNetwork(OWLOntology owlOntology, String bayesianNetwork) {
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(bayesianNetwork);
		this.owlOntology = owlOntology;
		this.ontologyName = owlOntology.getOntologyID().toString();
		this.bayesianNetwork = bayesianNetwork;
	}

	/**
	 * Constructs a new ontology and Bayesian network object.
	 * 
	 * @param ontologyName
	 *            ontology name
	 * @param owlOntology
	 *            OWL ontology
	 * @param bayesianNetwork
	 *            Bayesian network in ProbLog syntax
	 */
	public OntologyAndNetwork(String ontologyName, OWLOntology owlOntology, String bayesianNetwork) {
		Objects.requireNonNull(ontologyName);
		Objects.requireNonNull(owlOntology);
		Objects.requireNonNull(bayesianNetwork);
		this.ontologyName = ontologyName;
		this.owlOntology = owlOntology;
		this.bayesianNetwork = bayesianNetwork;
	}

	/**
	 * Returns the ontology name.
	 * 
	 * @return the ontology name
	 */
	public String getOntologyName() {
		return this.ontologyName;
	}

	/**
	 * Returns the OWL ontology.
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
		return this.ontologyName.hashCode()
				+ 0x1F * (this.owlOntology.hashCode() + 0x1F * this.bayesianNetwork.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof OntologyAndNetwork) {
			OntologyAndNetwork other = (OntologyAndNetwork) obj;
			return getOntologyName().equals(other.getOntologyName()) && getOntology().equals(other.getOntology())
					&& getBayesianNetwork().equals(other.getBayesianNetwork());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return getOntologyName() + ":\n" + getBayesianNetwork() + "\n\n" + getOntology();
	}

}
