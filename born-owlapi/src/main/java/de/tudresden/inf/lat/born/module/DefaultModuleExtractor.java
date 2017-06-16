
package de.tudresden.inf.lat.born.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import de.tudresden.inf.lat.born.core.common.OptMap;
import de.tudresden.inf.lat.born.core.common.OptMapImpl;
import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;
import de.tudresden.inf.lat.jcel.coreontology.datatype.IntegerAxiom;

/**
 * An object of this class is a module extractor, i.e. it can extract a subset
 * of axioms that are relevant to answer a query.
 * 
 * @author Julian Mendez
 */
public class DefaultModuleExtractor {

	/**
	 * Constructs a new module extractor.
	 */
	public DefaultModuleExtractor() {
	}

	/**
	 * Returns a map that relates a class with the set of axioms where this
	 * class occurs on the left side of the axiom
	 * 
	 * @param normalizedAxioms
	 *            normalized axioms
	 * @return a map that relates a class with the set of axioms where this
	 *         class occurs on the left side of the axiom
	 */
	OptMap<Integer, Set<DefaultIdentifierCollector>> buildMapOfAxioms(
			Set<DefaultIdentifierCollector> normalizedAxioms) {
		OptMap<Integer, Set<DefaultIdentifierCollector>> map = new OptMapImpl<>(new HashMap<>());
		normalizedAxioms.forEach(axiom -> {
			Set<Integer> classesOnTheLeft = axiom.getClassesOnTheLeft();
			classesOnTheLeft.forEach(classId -> {
				Optional<Set<DefaultIdentifierCollector>> optValue = map.get(classId);
				if (!optValue.isPresent()) {
					optValue = Optional.of(new HashSet<>());
					map.put(classId, optValue.get());
				}
				optValue.get().add(axiom);
			});
		});
		return map;
	}

	Set<NormalizedIntegerAxiom> getAxiomsWithoutEntitiesOnTheLeft(Set<DefaultIdentifierCollector> axioms) {
		Set<NormalizedIntegerAxiom> ret = new HashSet<>();
		axioms.forEach(axiom -> {
			if (axiom.getClassesOnTheLeft().isEmpty() && axiom.getObjectPropertiesOnTheLeft().isEmpty()) {
				ret.add(axiom.getAxiom());
			}
		});
		return ret;
	}

	Set<DefaultIdentifierCollector> getAxiomsWithClassesOnTheLeft(Set<Integer> classesToVisit,
			OptMap<Integer, Set<DefaultIdentifierCollector>> map) {
		Set<DefaultIdentifierCollector> ret = new HashSet<>();
		classesToVisit.forEach(classId -> {
			Optional<Set<DefaultIdentifierCollector>> optNewAxioms = map.get(classId);
			if (optNewAxioms.isPresent()) {
				ret.addAll(optNewAxioms.get());
			}
		});
		return ret;
	}

	Set<Integer> getEntities(IntegerAxiom axiom) {
		Set<Integer> ret = new TreeSet<>();
		ret.addAll(axiom.getClassesInSignature());
		ret.addAll(axiom.getObjectPropertiesInSignature());
		ret.addAll(axiom.getIndividualsInSignature());
		ret.addAll(axiom.getDataPropertiesInSignature());
		ret.addAll(axiom.getDatatypesInSignature());
		return ret;
	}

	/**
	 * Returns a module, i.e. a subset of axioms relevant to answer a query.
	 * 
	 * @param setOfAxioms
	 *            set of axioms
	 * @param setOfClasses
	 *            set of classes
	 * @return a module, i.e. a subset of axioms relevant to answer a query
	 */
	public Module extractModule(Collection<NormalizedIntegerAxiom> setOfAxioms, Set<Integer> setOfClasses) {

		Set<NormalizedIntegerAxiom> newAxioms = new HashSet<>();

		Set<DefaultIdentifierCollector> axioms = new HashSet<>();
		setOfAxioms.forEach(axiom -> axioms.add(new DefaultIdentifierCollector(axiom)));

		newAxioms.addAll(getAxiomsWithoutEntitiesOnTheLeft(axioms));

		OptMap<Integer, Set<DefaultIdentifierCollector>> map = buildMapOfAxioms(axioms);

		Set<Integer> visitedClasses = new TreeSet<>();
		Set<Integer> classesToVisit = new TreeSet<>();
		classesToVisit.addAll(setOfClasses);
		int resultSize = -1;
		while (newAxioms.size() > resultSize) {
			resultSize = newAxioms.size();

			Set<DefaultIdentifierCollector> axiomsToVisit = getAxiomsWithClassesOnTheLeft(classesToVisit, map);
			visitedClasses.addAll(classesToVisit);
			classesToVisit.clear();

			axiomsToVisit.forEach(axiom -> {
				classesToVisit.addAll(axiom.getClassesOnTheRight());
				newAxioms.add(axiom.getAxiom());
			});
			classesToVisit.removeAll(visitedClasses);
		}

		Set<Integer> entities = new TreeSet<>();
		entities.addAll(visitedClasses);
		newAxioms.forEach(axiom -> entities.addAll(getEntities(axiom)));
		return new Module(entities, newAxioms);
	}

}
