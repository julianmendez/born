package de.tudresden.inf.lat.born.owlapi.main;

import java.util.Map;
import java.util.TreeMap;

import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorSubApp;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;
import de.tudresden.inf.lat.born.owlapi.splitter.SplitterSubApp;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorSubApp;

/**
 * 
 * @author Julian Mendez
 *
 */
public class BornMain implements SubApp {

	private Map<String, SubApp> subAppMap = new TreeMap<String, SubApp>();

	static final String HELP = "\nBORN - Bayesian Ontology Reasoner"
			+ "\n\nParameters: <command> [<command parameters>]" + "\n\n";
	static final String LINE = "----------------------------------------------------------------";

	public BornMain() {
		this.subAppMap.put("get", new ProcessorSubApp());
		this.subAppMap.put("put", new AnnotatorSubApp());
		this.subAppMap.put("split", new SplitterSubApp());
		this.subAppMap.put("link", new BayesianNetworkCreatorSubApp());
	}

	@Override
	public String getHelp() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(HELP);
		for (String command : this.subAppMap.keySet()) {
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(LINE);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append("Command: " + command);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(this.subAppMap.get(command).getHelp());
			sbuf.append(Symbol.NEW_LINE_CHAR);
		}
		return sbuf.toString();
	}

	@Override
	public boolean isValid(String[] args) {
		if (args.length == 0) {
			return false;
		} else {
			String command = args[0];
			SubApp subApp = this.subAppMap.get(command);
			if (subApp == null) {
				return false;
			} else {
				String[] newArgs = new String[args.length - 1];
				System.arraycopy(args, 1, newArgs, 0, args.length - 1);
				return subApp.isValid(newArgs);
			}
		}
	}

	@Override
	public String run(String[] args) {
		if (isValid(args)) {
			String command = args[0];
			SubApp subApp = this.subAppMap.get(command);
			String[] newArgs = new String[args.length - 1];
			System.arraycopy(args, 1, newArgs, 0, args.length - 1);
			String ret = subApp.run(newArgs);
			return ret;
		} else {
			return getHelp();
		}
	}

	public static void main(String[] args) {
		BornMain instance = new BornMain();
		System.out.println(instance.run(args));
	}

}
