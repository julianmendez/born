/*
 *
 * Copyright (C) 2015 Julian Mendez
 *
 */

package de.tudresden.inf.lat.born.owlapi.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;
import org.semanticweb.owlapi.reasoner.AxiomNotInProfileException;
import org.semanticweb.owlapi.reasoner.BufferingMode;
import org.semanticweb.owlapi.reasoner.ClassExpressionNotInProfileException;
import org.semanticweb.owlapi.reasoner.FreshEntitiesException;
import org.semanticweb.owlapi.reasoner.FreshEntityPolicy;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.IndividualNodeSetPolicy;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.reasoner.UnsupportedEntailmentTypeException;
import org.semanticweb.owlapi.util.Version;

/**
 * This class is the connection with the OWL API. It implements some functions,
 * and throws an exception for the unimplemented ones.
 * 
 * @author Julian Mendez
 */
public class BornReasoner implements OWLReasoner, OWLOntologyChangeListener {

	private static final Logger logger = Logger.getLogger(BornReasoner.class
			.getName());

	public static final String REASONER_NAME = "BORN";
	private final boolean buffering = false;
	private OWLReasonerConfiguration reasonerConfiguration = null;
	private final OWLOntology rootOntology;
	private final Date start = new Date();
	private boolean isUpdateNeeded = false;
	private long timeOut = 0x100000000L;

	/**
	 * Constructs a new BORN reasoner.
	 * 
	 * @param rootOntology
	 *            root ontology
	 * @param buffering
	 *            <code>true</code> if and only if the reasoner is buffering
	 */
	public BornReasoner(OWLOntology rootOntology, boolean buffering) {
		if (rootOntology == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.fine("configuring BORN reasoner ...");
		this.rootOntology = rootOntology;
		logger.fine("resetting reasoner ...");
		resetReasoner();

		logger.fine("jcel reasoner configured.");
	}

	/**
	 * Constructs a new BORN reasoner.
	 * 
	 * @param rootOntology
	 *            root ontology
	 * @param buffering
	 *            <code>true</code> if and only if the reasoner is buffering
	 * @param configuration
	 *            reasoner configuration
	 */
	public BornReasoner(OWLOntology rootOntology, boolean buffering,
			OWLReasonerConfiguration configuration) {
		this(rootOntology, buffering);
		if (configuration == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		this.reasonerConfiguration = configuration;
	}

	public boolean addAxiom(OWLAxiom axiom) {
		if (axiom == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("addAxiom(" + axiom + ")");
		if (!this.buffering) {
			resetReasoner();
		}
		return true;
	}

	@Override
	public void dispose() {
		logger.finer("dispose()");
		this.rootOntology.getOWLOntologyManager().removeOntologyChangeListener(
				this);
	}

	@Override
	public void flush() {
		logger.finer("flush()");
		resetReasoner();
	}

	@Override
	public Node<OWLClass> getBottomClassNode() {
		logger.finer("getBottomClassNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getBottomClassNode()");
	}

	@Override
	public Node<OWLDataProperty> getBottomDataPropertyNode() {
		logger.finer("getBottomDataPropertyNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getBottomDataPropertyNode()");
	}

	@Override
	public Node<OWLObjectPropertyExpression> getBottomObjectPropertyNode() {
		logger.finer("getBottomObjectPropertyNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getBottomObjectPropertyNode()");
	}

	@Override
	public BufferingMode getBufferingMode() {
		logger.finer("getBufferingMode()");
		BufferingMode ret = this.buffering ? BufferingMode.BUFFERING
				: BufferingMode.NON_BUFFERING;
		logger.finer("" + ret);
		return ret;
	}

	@Override
	public NodeSet<OWLClass> getDataPropertyDomains(
			OWLDataProperty dataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (dataProperty == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDataPropertyDomains(" + dataProperty + ", " + direct
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getDataPropertyDomains(OWLDataProperty, boolean)");
	}

	@Override
	public Set<OWLLiteral> getDataPropertyValues(OWLNamedIndividual individual,
			OWLDataProperty dataProperty) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		if (individual == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (dataProperty == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDataPropertyValues(" + individual + ", "
				+ dataProperty + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getDataPropertyValues(OWLNamedIndividual, OWLDataProperty)");
	}

	@Override
	public NodeSet<OWLNamedIndividual> getDifferentIndividuals(
			OWLNamedIndividual individual)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (individual == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDifferentIndividuals(" + individual + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getDifferentIndividuals(OWLNamedIndividual)");
	}

	@Override
	public NodeSet<OWLClass> getDisjointClasses(
			OWLClassExpression classExpression)
			throws ReasonerInterruptedException, TimeOutException,
			FreshEntitiesException, InconsistentOntologyException {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDisjointClasses(" + classExpression + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getDisjointClasses(OWLClassExpression)");
	}

	@Override
	public NodeSet<OWLDataProperty> getDisjointDataProperties(
			OWLDataPropertyExpression dataPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (dataPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDisjointDataProperties(" + dataPropertyExpression
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getDisjointDataProperties(OWLDataPropertyExpression)");
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getDisjointObjectProperties(
			OWLObjectPropertyExpression objectPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getDisjointDataProperties(" + objectPropertyExpression
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation :  getDisjointObjectProperties(OWLObjectPropertyExpression)");
	}

	@Override
	public Node<OWLClass> getEquivalentClasses(
			OWLClassExpression classExpression) {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getEquivalentClasses(" + classExpression + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation :  getEquivalentClasses(OWLClassExpression)");
	}

	@Override
	public Node<OWLDataProperty> getEquivalentDataProperties(
			OWLDataProperty dataProperty) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		if (dataProperty == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getEquivalentDataProperties(" + dataProperty + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getEquivalentDataProperties(OWLDataProperty)");
	}

	@Override
	public Node<OWLObjectPropertyExpression> getEquivalentObjectProperties(
			OWLObjectPropertyExpression objectPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getEquivalentObjectProperties("
				+ objectPropertyExpression + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getEquivalentObjectProperties(OWLObjectPropertyExpression)");
	}

	@Override
	public FreshEntityPolicy getFreshEntityPolicy() {
		logger.finer("getFreshEntityPolicy()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getFreshEntityPolicy()");
	}

	@Override
	public IndividualNodeSetPolicy getIndividualNodeSetPolicy() {
		logger.finer("getIndividualNodeSetPolicy()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getIndividualNodeSetPolicy()");
	}

	@Override
	public NodeSet<OWLNamedIndividual> getInstances(
			OWLClassExpression classExpression, boolean direct)
			throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getInstances(" + classExpression + ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getInstances(OWLClassExpression, boolean)");
	}

	@Override
	public Node<OWLObjectPropertyExpression> getInverseObjectProperties(
			OWLObjectPropertyExpression objectPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getInverseObjectProperties(" + objectPropertyExpression
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getInverseObjectProperties(OWLObjectPropertyExpression)");
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyDomains(
			OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getObjectPropertyDomains(" + objectPropertyExpression
				+ ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getObjectPropertyDomains(OWLObjectPropertyExpression, boolean)");
	}

	@Override
	public NodeSet<OWLClass> getObjectPropertyRanges(
			OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getObjectPropertyRanges(" + objectPropertyExpression
				+ ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getObjectPropertyRanges(OWLObjectPropertyExpression, boolean)");
	}

	@Override
	public NodeSet<OWLNamedIndividual> getObjectPropertyValues(
			OWLNamedIndividual individual,
			OWLObjectPropertyExpression objectPropertyExpression)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (individual == null) {
			throw new IllegalArgumentException("Null argument.");
		}
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getObjectPropertyValues(" + individual + ", "
				+ objectPropertyExpression + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation :  getObjectPropertyValues(OWLNamedIndividual)");
	}

	@Override
	public Set<OWLAxiom> getPendingAxiomAdditions() {
		logger.finer("getPendingAxiomAdditions()");
		Set<OWLAxiom> ret = new HashSet<OWLAxiom>();
		logger.finer("" + ret);
		return Collections.unmodifiableSet(ret);
	}

	@Override
	public Set<OWLAxiom> getPendingAxiomRemovals() {
		logger.finer("getPendingAxiomRemovals()");
		Set<OWLAxiom> ret = new HashSet<OWLAxiom>();
		logger.finer("" + ret);
		return Collections.unmodifiableSet(ret);
	}

	@Override
	public List<OWLOntologyChange> getPendingChanges() {
		logger.finer("getPendingChanges()");
		List<OWLOntologyChange> ret = new ArrayList<OWLOntologyChange>();
		logger.finer("" + ret);
		return Collections.unmodifiableList(ret);
	}

	@Override
	public Set<InferenceType> getPrecomputableInferenceTypes() {
		logger.finer("getPrecomputableInferenceTypes()");
		Set<InferenceType> ret = new HashSet<InferenceType>();
		ret.add(InferenceType.CLASS_HIERARCHY);
		ret.add(InferenceType.OBJECT_PROPERTY_HIERARCHY);
		ret.add(InferenceType.CLASS_ASSERTIONS);
		ret.add(InferenceType.OBJECT_PROPERTY_ASSERTIONS);
		ret.add(InferenceType.SAME_INDIVIDUAL);
		ret.add(InferenceType.DIFFERENT_INDIVIDUALS);
		logger.finer("" + ret);
		return ret;
	}

	public OWLReasonerConfiguration getReasonerConfiguration() {
		return this.reasonerConfiguration;
	}

	@Override
	public String getReasonerName() {
		logger.finer("getReasonerName()");
		String ret = REASONER_NAME;
		logger.finer("" + ret);
		return ret;
	}

	@Override
	public Version getReasonerVersion() {
		logger.finer("getReasonerVersion()");
		Version ret = BornVersion.VERSION;
		logger.finer("" + ret);
		return ret;
	}

	@Override
	public OWLOntology getRootOntology() {
		logger.finer("getRootOntology()");
		OWLOntology ret = this.rootOntology;
		logger.finer("" + ret);
		return ret;
	}

	@Override
	public Node<OWLNamedIndividual> getSameIndividuals(
			OWLNamedIndividual individual)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (individual == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSameIndividuals(" + individual + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSameIndividuals(OWLNamedIndividual)");
	}

	public Date getStartTime() {
		return this.start;
	}

	@Override
	public NodeSet<OWLClass> getSubClasses(OWLClassExpression classExpression,
			boolean direct) {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSubClasses(" + classExpression + ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSubClasses(OWLClassExpression, boolean)");
	}

	@Override
	public NodeSet<OWLDataProperty> getSubDataProperties(
			OWLDataProperty dataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (dataProperty == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSubDataProperties(" + dataProperty + ", " + direct
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSubDataProperties(OWLDataProperty, boolean)");
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSubObjectProperties(
			OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSubObjectProperties(" + objectPropertyExpression
				+ ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSubObjectProperties(OWLObjectPropertyExpression, boolean)");
	}

	@Override
	public NodeSet<OWLClass> getSuperClasses(
			OWLClassExpression classExpression, boolean direct)
			throws InconsistentOntologyException,
			ClassExpressionNotInProfileException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSuperClasses(" + classExpression + ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSuperClasses(OWLClassExpression, boolean)");
	}

	@Override
	public NodeSet<OWLDataProperty> getSuperDataProperties(
			OWLDataProperty dataProperty, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (dataProperty == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSuperDataProperties(" + dataProperty + ", " + direct
				+ ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSuperDataProperties(OWLDataProperty, boolean)");
	}

	@Override
	public NodeSet<OWLObjectPropertyExpression> getSuperObjectProperties(
			OWLObjectPropertyExpression objectPropertyExpression, boolean direct)
			throws InconsistentOntologyException, FreshEntitiesException,
			ReasonerInterruptedException, TimeOutException {
		if (objectPropertyExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getSuperObjectProperties(" + objectPropertyExpression
				+ ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getSuperObjectProperties(OWLObjectPropertyExpression, boolean)");
	}

	@Override
	public long getTimeOut() {
		logger.finer("getTimeOut()");
		long ret = this.timeOut;
		logger.finer("" + ret);
		return ret;
	}

	@Override
	public Node<OWLClass> getTopClassNode() {
		logger.finer("getTopClassNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation :  getTopClassNode() ");
	}

	@Override
	public Node<OWLDataProperty> getTopDataPropertyNode() {
		logger.finer("getTopDataPropertyNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getTopDataPropertyNode()");
	}

	@Override
	public Node<OWLObjectPropertyExpression> getTopObjectPropertyNode() {
		logger.finer("getTopObjectPropertyNode()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getTopObjectPropertyNode()");
	}

	@Override
	public NodeSet<OWLClass> getTypes(OWLNamedIndividual individual,
			boolean direct) throws InconsistentOntologyException,
			FreshEntitiesException, ReasonerInterruptedException,
			TimeOutException {
		if (individual == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("getTypes(" + individual + ", " + direct + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getTypes(OWLNamedIndividual, boolean)");
	}

	@Override
	public Node<OWLClass> getUnsatisfiableClasses()
			throws ReasonerInterruptedException, TimeOutException {
		logger.finer("getUnsatisfiableClasses()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : getUnsatisfiableClasses()");
	}

	@Override
	public void interrupt() {
		logger.finer("interrupt()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : interrupt()");
	}

	@Override
	public boolean isConsistent() throws ReasonerInterruptedException,
			TimeOutException {
		logger.finer("isConsistent()");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : isConsistent()");
	}

	@Override
	public boolean isEntailed(OWLAxiom axiom)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException {
		if (axiom == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("isEntailed((OWLAxiom) " + axiom + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation :  isEntailed(OWLAxiom)");
	}

	@Override
	public boolean isEntailed(Set<? extends OWLAxiom> axiomSet)
			throws ReasonerInterruptedException,
			UnsupportedEntailmentTypeException, TimeOutException,
			AxiomNotInProfileException, FreshEntitiesException {
		if (axiomSet == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("isEntailed((Set<? extends OWLAxiom>) " + axiomSet + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : isEntailed(Set<? extends OWLAxiom>)");
	}

	@Override
	public boolean isEntailmentCheckingSupported(AxiomType<?> axiomType) {
		if (axiomType == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("isEntailmentCheckingSupported(" + axiomType + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : isEntailmentCheckingSupported(AxiomType<?>)");
	}

	@Override
	public boolean isPrecomputed(InferenceType inferenceType) {
		if (inferenceType == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("isPrecomputed(" + inferenceType + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : isPrecomputed()");
	}

	@Override
	public boolean isSatisfiable(OWLClassExpression classExpression) {
		if (classExpression == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("isSatisfiable(" + classExpression + ")");
		throw new UnsupportedReasonerOperationInBornException(
				"Unsupported operation : isSatisfiable(OWLClassExpression)");
	}

	@Override
	public void ontologiesChanged(List<? extends OWLOntologyChange> changes)
			throws OWLException {
		// TODO not implemented
	}

	@Override
	public void precomputeInferences(InferenceType... inferenceTypes)
			throws ReasonerInterruptedException, TimeOutException,
			InconsistentOntologyException {
		if (inferenceTypes == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("precomputeInferences(" + inferenceTypes + ")");
		if (this.reasonerConfiguration != null) {
			this.reasonerConfiguration.getProgressMonitor()
					.reasonerTaskStarted(ReasonerProgressMonitor.CLASSIFYING);
			this.reasonerConfiguration.getProgressMonitor().reasonerTaskBusy();
		}

		logger.finer("preparing ontology ...");
		Date start = new Date();
		resetReasoner();
		logger.finer("BORN prepared the ontology in "
				+ ((new Date()).getTime() - start.getTime()) + "ms");

		if (this.reasonerConfiguration != null) {
			this.reasonerConfiguration.getProgressMonitor()
					.reasonerTaskStopped();
		}
	}

	public boolean removeAxiom(OWLAxiom axiom) {
		if (axiom == null) {
			throw new IllegalArgumentException("Null argument.");
		}

		logger.finer("removeAxiom(" + axiom + ")");
		this.isUpdateNeeded = true;
		if (!this.buffering) {
			resetReasoner();
		}
		return true;
	}

	private void resetReasoner() {
		if (isUpdateNeeded) {
			// TO DO
		}
		this.isUpdateNeeded = false;
	}

}
