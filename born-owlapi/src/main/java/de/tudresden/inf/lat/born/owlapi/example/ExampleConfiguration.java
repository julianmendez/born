package de.tudresden.inf.lat.born.owlapi.example;

import org.semanticweb.owlapi.model.OWLOntology;

/**
 * This interface models the configuration of an example.
 * 
 * @author Julian Mendez
 *
 */
public interface ExampleConfiguration {

	/**
	 * Returns the ontology name.
	 * 
	 * @return the ontology name
	 */
	String getOntologyName();

	/**
	 * Returns the ontology file name.
	 * 
	 * @return the ontology file name
	 */
	String getOntologyFileName();

	/**
	 * Returns the OWL ontology.
	 * 
	 * @return the OWL ontology
	 */
	OWLOntology getOntology();

	/**
	 * Returns the Bayesian network file name.
	 * 
	 * @return the Bayesian network file name
	 */
	String getBayesianNetworkFileName();

	/**
	 * Returns the Bayesian network.
	 * 
	 * @return the Bayesian network
	 */
	String getBayesianNetwork();

	/**
	 * Returns the query.
	 * 
	 * @return the query
	 */
	String getQuery();

}
