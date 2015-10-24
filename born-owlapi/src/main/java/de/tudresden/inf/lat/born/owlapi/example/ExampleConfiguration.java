package de.tudresden.inf.lat.born.owlapi.example;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * An object of this class is an example configuration. It is a tuple containing
 * an ontology name, an OWL ontology, a Bayesian network, and a query. The
 * ontology name can be a file name, but that is not mandatory.
 * 
 * @author Julian Mendez
 *
 */
public class ExampleConfiguration {

	private final String ontologyName;
	private final OWLOntology owlOntology;
	private final String bayesianNetwork;
	private final String query;

	/**
	 * Constructs a new ontology and Bayesian network object.
	 * 
	 * @param ontologyName
	 *            ontology name
	 * @param owlOntology
	 *            OWL ontology
	 * @param bayesianNetwork
	 *            Bayesian network (in ProbLog syntax)
	 * @param query
	 *            query (in ProbLog syntax)
	 */
	public ExampleConfiguration(String ontologyName, OWLOntology owlOntology, String bayesianNetwork, String query) {
		if (ontologyName == null) {
			throw new IllegalArgumentException("Null arguments.");
		}
		if (owlOntology == null) {
			throw new IllegalArgumentException("Null arguments.");
		}
		if (bayesianNetwork == null) {
			throw new IllegalArgumentException("Null arguments.");
		}
		if (query == null) {
			throw new IllegalArgumentException("Null arguments.");
		}

		this.owlOntology = owlOntology;
		this.ontologyName = ontologyName;
		this.bayesianNetwork = bayesianNetwork;
		this.query = query;
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

	/**
	 * Returns the query.
	 * 
	 * @return the query
	 */
	public String getQuery() {
		return this.query;
	}

	@Override
	public int hashCode() {
		return this.ontologyName.hashCode() + 0x1F * (this.owlOntology.hashCode()
				+ 0x1F * (this.bayesianNetwork.hashCode() + 0x1F * this.query.hashCode()));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof ExampleConfiguration) {
			ExampleConfiguration other = (ExampleConfiguration) obj;
			return getOntologyName().equals(other.getOntologyName()) && getOntology().equals(other.getOntology())
					&& getBayesianNetwork().equals(other.getBayesianNetwork()) && getQuery().equals(other.getQuery());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "\nname = " + getOntologyName() + "\n\nBayesian network = " + getBayesianNetwork() + "\n\nontology = "
				+ getOntology() + "\n\nquery = " + getQuery() + "\n\n";
	}

}
