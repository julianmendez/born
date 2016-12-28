package de.tudresden.inf.lat.born.owlapi.main;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import de.tudresden.inf.lat.born.core.term.SubApp;
import de.tudresden.inf.lat.born.core.term.Symbol;
import de.tudresden.inf.lat.born.owlapi.annotator.AnnotatorSubApp;
import de.tudresden.inf.lat.born.owlapi.multiprocessor.MultiProcessorSubApp;
import de.tudresden.inf.lat.born.owlapi.processor.ProcessorSubApp;
import de.tudresden.inf.lat.born.owlapi.splitter.SplitterSubApp;
import de.tudresden.inf.lat.born.problog.connector.BayesianNetworkCreatorSubApp;

/**
 * An object of this class models the BORN main application. This is the parent
 * application of all the children applications (sub apps).
 * 
 * @author Julian Mendez
 *
 */
public class BornMain implements SubApp {

	private Map<String, SubApp> subAppMap = new TreeMap<>();

	static final String HELP = "\nBORN - Bayesian Ontology Reasoner"
			+ "\n\nParameters: <command> [<command parameters>]" + "\n\n";
	static final String LINE = "----------------------------------------------------------------";

	/**
	 * Constructs a new BORN main application.
	 */
	public BornMain() {
		this.subAppMap.put(ProcessorSubApp.COMMAND, new ProcessorSubApp());
		this.subAppMap.put(MultiProcessorSubApp.COMMAND, new MultiProcessorSubApp());
		this.subAppMap.put(AnnotatorSubApp.COMMAND, new AnnotatorSubApp());
		this.subAppMap.put(SplitterSubApp.COMMAND, new SplitterSubApp());
		this.subAppMap.put(BayesianNetworkCreatorSubApp.COMMAND, new BayesianNetworkCreatorSubApp());
	}

	@Override
	public String getHelp() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(HELP);
		this.subAppMap.keySet().forEach(command -> {
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(LINE);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append("Command: " + command);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(Symbol.NEW_LINE_CHAR);
			sbuf.append(this.subAppMap.get(command).getHelp());
			sbuf.append(Symbol.NEW_LINE_CHAR);
		});
		return sbuf.toString();
	}

	@Override
	public boolean isValid(String[] args) {
		Objects.requireNonNull(args);
		if (args.length == 0) {
			return false;
		} else {
			String command = args[0];
			SubApp subApp = this.subAppMap.get(command);
			if (Objects.isNull(subApp)) {
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
