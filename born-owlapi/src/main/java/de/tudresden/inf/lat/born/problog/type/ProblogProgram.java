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

	/**
	 * Constructs a ProbLog program.
	 */
	public ProblogProgram() {
	}

	/**
	 * Returns the ProbLog program data, i.e. an object with the required data
	 * to build a ProbLog program.
	 * 
	 * @return the ProbLog program data
	 */
	public ProblogProgramData getData() {
		return this.data;
	}

	/**
	 * Returns the additional completion rules as text.
	 * 
	 * @return the additional completion rules as text
	 */
	public String getAdditionalCompletionRulesAsText() {
		return this.additionalCompletionRulesAsText;
	}

	/**
	 * Sets the additional completion rules.
	 * 
	 * @param completionRulesAsText
	 *            additional completion rules
	 */
	public void setAdditionalCompletionRulesAsText(String completionRulesAsText) {
		this.additionalCompletionRulesAsText = Objects.requireNonNull(completionRulesAsText);
	}

	/**
	 * Returns the addendum of the Bayesian network.
	 * 
	 * @return the addendum of the Bayesian network
	 */
	public String getBayesianNetworkAddendum() {
		return this.bayesianNetworkAddendum;
	}

	/**
	 * Sets the addendum of the Bayesian network.
	 * 
	 * @param bayesianNetworkAddendum
	 *            the addendum of the Bayesian network
	 */
	public void setBayesianNetworkAddendum(String bayesianNetworkAddendum) {
		this.bayesianNetworkAddendum = Objects.requireNonNull(bayesianNetworkAddendum);
	}

	/**
	 * Returns the addendum of the query list.
	 * 
	 * @return the addendum of the query list
	 */
	public String getQueryListAddendum() {
		return this.queryListAddendum;
	}

	/**
	 * Returns the addendum of the query list.
	 * 
	 * @param queryListAddendum
	 *            the addendum of the query list
	 */
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

	/**
	 * Returns the given list of completion rules as a string with tabs.
	 * 
	 * @param completionRules
	 *            list of completion rules
	 * 
	 * @return the given list of completion rules as a string with tabs
	 */
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

	/**
	 * Returns this ProbLog program as a string.
	 * 
	 * @return this ProbLog program as a string
	 */
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
