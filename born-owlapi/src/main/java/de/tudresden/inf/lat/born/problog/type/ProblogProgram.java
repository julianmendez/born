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
		this.additionalCompletionRulesAsText = Objects.requireNonNull(completionRulesAsText);
	}

	public String getBayesianNetworkAddendum() {
		return this.bayesianNetworkAddendum;
	}

	public void setBayesianNetworkAddendum(String bayesianNetworkAddendum) {
		this.bayesianNetworkAddendum = Objects.requireNonNull(bayesianNetworkAddendum);
	}

	public String getQueryListAddendum() {
		return this.queryListAddendum;
	}

	public void setQueryListAddendum(String queryListAddendum) {
		this.queryListAddendum = Objects.requireNonNull(queryListAddendum);
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
		String nl = "" + Symbol.NEW_LINE_CHAR;
		String nlnl = "" + Symbol.NEW_LINE_CHAR + Symbol.NEW_LINE_CHAR;
		StringBuffer sb = new StringBuffer();
		sb.append(nl);
		sb.append(TITLE_COMPLETION_RULES + nlnl);
		sb.append(asStringC(this.data.getCompletionRules()) + nl);
		sb.append(this.additionalCompletionRulesAsText + nlnl);
		sb.append(TITLE_ONTOLOGY + nlnl);
		sb.append(asString(this.data.getOntology()) + nlnl);
		sb.append(TITLE_BAYESIAN_NETWORK + nlnl);
		sb.append(asStringP(this.data.getBayesianNetwork()) + nl);
		sb.append(this.bayesianNetworkAddendum + nlnl);
		sb.append(TITLE_QUERIES + nlnl);
		sb.append(asString(this.data.getQueries()) + nl);
		sb.append(this.queryListAddendum + nlnl);
		return sb.toString();
	}

	@Override
	public String toString() {
		return asString();
	}

}
