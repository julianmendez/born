package de.tudresden.inf.lat.born.owlapi.multiprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

import de.tudresden.inf.lat.born.owlapi.processor.ProcessorConfiguration;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorCore;

/**
 * An object of this class processed several ontologies with their respective
 * Bayesian networks, using pseudorandomly generated queries.
 * 
 * @author Julian Mendez
 *
 */
public class MultiProcessorCore {
	public static final char TAB_CHAR = '\t';
	public static final char NEW_LINE_CHAR = '\n';

	/**
	 * Constructs a new multi processor core.
	 */
	public MultiProcessorCore() {
	}

	List<OWLClass> getClasses(OWLOntology ontology) {
		List<OWLClass> listOfClasses = new ArrayList<OWLClass>();
		Set<OWLClass> treeSet = new TreeSet<OWLClass>();
		treeSet.addAll(ontology.getClassesInSignature());
		listOfClasses.addAll(treeSet);
		return listOfClasses;
	}

	SubsumptionQuery getNextQuery(List<OWLClass> listOfClasses, PseudorandomNumberGenerator random) {
		int n = listOfClasses.size();
		int a = random.nextInt(n);
		int b = random.nextInt(n);
		return new SubsumptionQuery(listOfClasses.get(a), listOfClasses.get(b));
	}

	List<SubsumptionQuery> getQueries(OWLOntology ontology, int numberOfQueries, PseudorandomNumberGenerator random) {
		List<OWLClass> listOfClasses = getClasses(ontology);
		List<SubsumptionQuery> listOfQueries = new ArrayList<SubsumptionQuery>();
		for (int i = 0; i < numberOfQueries; i++) {
			listOfQueries.add(getNextQuery(listOfClasses, random));
		}
		return listOfQueries;
	}

	public List<String> run(MultiProcessorConfiguration conf, long start) {
		List<String> ret = new ArrayList<String>();
		PseudorandomNumberGenerator random = new PseudorandomNumberGenerator(conf.getSeed());
		ProcessorCore core = new ProcessorCore();

		for (OntologyAndNetwork ontPair : conf.getOntologyList()) {

			ProcessorConfiguration configuration = new ProcessorConfiguration();
			configuration.setOntology(ontPair.getOntology());
			configuration.setBayesianNetwork(ontPair.getBayesianNetwork());
			List<SubsumptionQuery> queries = getQueries(ontPair.getOntology(), conf.getNumberOfQueries(), random);
			StringBuffer sbuf = new StringBuffer();

			for (SubsumptionQuery query : queries) {

				configuration.setQuery(query.asProblogString());
				String result = core.run(configuration, start);
				sbuf.append(query.getSubClass().getIRI());
				sbuf.append(TAB_CHAR);
				sbuf.append(query.getSuperClass().getIRI());
				sbuf.append(TAB_CHAR);
				sbuf.append(result);
				sbuf.append(NEW_LINE_CHAR);
			}
			ret.add(sbuf.toString());
		}
		return ret;
	}

}
