
package de.tudresden.inf.lat.born.module;

import java.util.Collection;
import java.util.Collections;
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

		Set<Integer> classes = new HashSet<>();
		classes.addAll(setOfClasses);

		Set<DefaultIdentifierCollector> axioms = new HashSet<>();
		setOfAxioms.forEach(axiom -> axioms.add(new DefaultIdentifierCollector(axiom)));

		Map<Integer, Set<DefaultIdentifierCollector>> map = buildMapOfAxioms(axioms);

		Set<Integer> visitedClasses = new HashSet<Integer>();
		Set<Integer> classesToVisit = new HashSet<Integer>();
		classesToVisit.addAll(setOfClasses);
		int resultSize = -1;
		while (ret.size() > resultSize) {
			resultSize = ret.size();

			Set<DefaultIdentifierCollector> axiomsToVisit = new HashSet<>();
			classesToVisit.forEach(classId -> {
				axiomsToVisit.addAll(map.get(classId));
			});
			visitedClasses.addAll(classesToVisit);
			classesToVisit.clear();

			axiomsToVisit.forEach(axiom -> {
				Set<Integer> classesOnTheLeft = axiom.getClassesOnTheLeft();
				Set<Integer> objectPropertiesOnTheLeft = axiom.getObjectPropertiesOnTheLeft();

				boolean case0 = !Collections.disjoint(classesOnTheLeft, classes);
				boolean case1 = (classesOnTheLeft.isEmpty() && objectPropertiesOnTheLeft.isEmpty());

				if (case0 || case1) {
					classesToVisit.addAll(axiom.getClassesOnTheRight());
					classesToVisit.removeAll(visitedClasses);
					ret.add(axiom.getAxiom());
				}
			});
		}

		return ret;
	}

}
