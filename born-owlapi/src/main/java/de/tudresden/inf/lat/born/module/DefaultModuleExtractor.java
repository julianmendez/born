
package de.tudresden.inf.lat.born.module;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.tudresden.inf.lat.jcel.coreontology.axiom.NormalizedIntegerAxiom;

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
	Map<Integer, Set<DefaultIdentifierCollector>> buildMapOfAxioms(Set<DefaultIdentifierCollector> normalizedAxioms) {
		Map<Integer, Set<DefaultIdentifierCollector>> map = new HashMap<>();
		normalizedAxioms.forEach(axiom -> {
			Set<Integer> classesOnTheLeft = axiom.getClassesOnTheLeft();
			classesOnTheLeft.forEach(classId -> {
				Set<DefaultIdentifierCollector> value = map.get(classId);
				if (Objects.isNull(value)) {
					value = new HashSet<>();
					map.put(classId, value);
				}
				value.add(axiom);
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
			Map<Integer, Set<DefaultIdentifierCollector>> map) {
		Set<DefaultIdentifierCollector> ret = new HashSet<>();
		classesToVisit.forEach(classId -> {
			Set<DefaultIdentifierCollector> newAxioms = map.get(classId);
			if (Objects.nonNull(newAxioms)) {
				ret.addAll(newAxioms);
			}
		});
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
	public Set<NormalizedIntegerAxiom> extractModule(Collection<NormalizedIntegerAxiom> setOfAxioms,
			Set<Integer> setOfClasses) {

		Set<NormalizedIntegerAxiom> ret = new HashSet<>();

		Set<DefaultIdentifierCollector> axioms = new HashSet<>();
		setOfAxioms.forEach(axiom -> axioms.add(new DefaultIdentifierCollector(axiom)));

		ret.addAll(getAxiomsWithoutEntitiesOnTheLeft(axioms));

		Map<Integer, Set<DefaultIdentifierCollector>> map = buildMapOfAxioms(axioms);

		Set<Integer> visitedClasses = new HashSet<Integer>();
		Set<Integer> classesToVisit = new HashSet<Integer>();
		classesToVisit.addAll(setOfClasses);
		int resultSize = -1;
		while (ret.size() > resultSize) {
			resultSize = ret.size();

			Set<DefaultIdentifierCollector> axiomsToVisit = getAxiomsWithClassesOnTheLeft(classesToVisit, map);
			visitedClasses.addAll(classesToVisit);
			classesToVisit.clear();

			axiomsToVisit.forEach(axiom -> {
				classesToVisit.addAll(axiom.getClassesOnTheRight());
				ret.add(axiom.getAxiom());
			});
			classesToVisit.removeAll(visitedClasses);
		}

		return ret;
	}

}
