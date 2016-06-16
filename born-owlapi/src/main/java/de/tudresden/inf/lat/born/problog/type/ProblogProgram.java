package de.tudresden.inf.lat.born.problog.type;

import java.util.List;
import java.util.Objects;

import de.tudresden.inf.lat.born.core.rule.CompletionRule;
import de.tudresden.inf.lat.born.core.term.Clause;
import de.tudresden.inf.lat.born.core.term.ProbClause;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.core.term.Term;

/**
 * An object of this class models a ProbLog program as needed for the
 * translation.
 * 
 * @author Julian Mendez
 *
 */
public class ProblogProgram {

	static final String TITLE_COMPLETION_RULES = "%% EL completion rules";
	static final String TITLE_ONTOLOGY = "%% Ontology";
	static final String TITLE_BAYESIAN_NETWORK = "%% Bayesian Network";
	static final String TITLE_QUERIES = "%% Queries";

	private final ProblogProgramData data = new ProblogProgramData();
	private String additionalCompletionRulesAsText = "";
	private String bayesianNetworkAddendum = "";
	private String queryListAddendum = "";

	public ProblogProgram() {
	}

	public ProblogProgramData getData() {
		return this.data;
	}

	public String getAdditionalCompletionRulesAsText() {
		return this.additionalCompletionRulesAsText;
	}

	public void setAdditionalCompletionRulesAsText(String completionRulesAsText) {
		Objects.requireNonNull(completionRulesAsText);
		this.additionalCompletionRulesAsText = completionRulesAsText;
	}

	public String getBayesianNetworkAddendum() {
		return this.bayesianNetworkAddendum;
	}

	public void setBayesianNetworkAddendum(String bayesianNetworkAddendum) {
		Objects.requireNonNull(bayesianNetworkAddendum);
		this.bayesianNetworkAddendum = bayesianNetworkAddendum;
	}

	public String getQueryListAddendum() {
		return this.queryListAddendum;
	}

	public void setQueryListAddendum(String queryListAddendum) {
		Objects.requireNonNull(queryListAddendum);
		this.queryListAddendum = queryListAddendum;
	}

	String asString(List<Clause> clauses) {
		Objects.requireNonNull(clauses);
		StringBuffer sb = new StringBuffer();
		clauses.forEach(clause -> sb.append(clause.asString()));
		return sb.toString();
	}

	String asStringWithTabs(CompletionRule completionRule) {
		Term head = completionRule.getHead();
		if (head == null || completionRule.getBody().size() <= 1) {
			return completionRule.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append(head.asString());
			sb.append(Symbol.SPACE_CHAR);
			sb.append(Symbol.IF_SYMBOL);
			sb.append(Symbol.NEW_LINE_CHAR);
			boolean[] first = new boolean[1];
			first[0] = true;
			completionRule.getBody().forEach(term -> {
				if (first[0]) {
					first[0] = false;
				} else {
					sb.append(Symbol.COMMA_CHAR);
					sb.append(Symbol.NEW_LINE_CHAR);
				}
				sb.append(Symbol.SHORT_TAB);
				sb.append(term.asString());
			});
			sb.append(Symbol.POINT_CHAR);
			sb.append(Symbol.NEW_LINE_CHAR);
			return sb.toString();
		}
	}

	public String asStringWithTabs(List<CompletionRule> completionRules) {
		Objects.requireNonNull(completionRules);
		StringBuffer sb = new StringBuffer();
		completionRules.forEach(completionRule -> sb.append(asStringWithTabs(completionRule)));
		return sb.toString();
	}

	String asStringC(List<CompletionRule> completionRules) {
		Objects.requireNonNull(completionRules);
		StringBuffer sb = new StringBuffer();
		completionRules.forEach(completionRule -> sb.append(completionRule.asString()));
		return sb.toString();
	}

	String asStringP(List<ProbClause> clauses) {
		Objects.requireNonNull(clauses);
		StringBuffer sb = new StringBuffer();
		clauses.forEach(clause -> sb.append(clause.asString()));
		return sb.toString();
	}

	public String asString() {
		StringBuffer sb = new StringBuffer();
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(TITLE_COMPLETION_RULES);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(asStringC(this.data.getCompletionRules()));
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(this.additionalCompletionRulesAsText);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(TITLE_ONTOLOGY);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(asString(this.data.getOntology()));
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(TITLE_BAYESIAN_NETWORK);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(asStringP(this.data.getBayesianNetwork()));
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(this.bayesianNetworkAddendum);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(TITLE_QUERIES);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(asString(this.data.getQueries()));
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(this.queryListAddendum);
		sb.append(Symbol.NEW_LINE_CHAR);
		sb.append(Symbol.NEW_LINE_CHAR);
		return sb.toString();
	}

	@Override
	public String toString() {
		return asString();
	}

}
