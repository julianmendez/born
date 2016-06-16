package de.tudresden.inf.lat.born.problog.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.rule.CompletionRule;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.ProbClause;

/**
 * An object of this class models the data needed to create the input ProbLog
 * program.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProgramData {

	private final List<CompletionRule> completionRules = new ArrayList<>();
	private final List<Clause> ontology = new ArrayList<>();
	private final List<ProbClause> bayesianNetwork = new ArrayList<>();
	private final List<Clause> queries = new ArrayList<>();

	public ProblogProgramData() {
	}

	public List<CompletionRule> getCompletionRules() {
		return Collections.unmodifiableList(this.completionRules);
	}

	public void setCompletionRules(List<CompletionRule> completionRules) {
		Objects.requireNonNull(completionRules);
		this.completionRules.clear();
		this.completionRules.addAll(completionRules);
	}

	public List<Clause> getOntology() {
		return Collections.unmodifiableList(this.ontology);
	}

	public void setOntology(List<Clause> ontology) {
		Objects.requireNonNull(ontology);
		this.ontology.clear();
		this.ontology.addAll(ontology);
	}

	public List<ProbClause> getBayesianNetwork() {
		return Collections.unmodifiableList(this.bayesianNetwork);
	}

	public void setBayesianNetwork(List<ProbClause> bayesianNetwork) {
		Objects.requireNonNull(bayesianNetwork);
		this.bayesianNetwork.clear();
		this.bayesianNetwork.addAll(bayesianNetwork);
	}

	public List<Clause> getQueries() {
		return this.queries;
	}

	public void setQueries(List<Clause> queries) {
		Objects.requireNonNull(queries);
		this.queries.clear();
		this.queries.addAll(queries);
	}

	@Override
	public String toString() {
		return "\ncompletion rules=" + this.completionRules.toString() + "\nontology=" + this.ontology.toString()
				+ "\nBayesian network=" + this.bayesianNetwork.toString() + "\nqueries=" + this.queries.toString()
				+ "\n";
	}

}
