package de.tudresden.inf.lat.born.gui.common;

import java.io.BufferedReader;
import java.io.StringReader;

/**
 * An object of this class is a tool to format text.
 * 
 * @author Julian Mendez
 *
 */
public class FormatTool {

	static final String NEW_LINE = "\n";
	static final String SPACE = " ";

	/**
	 * Constructs a new format tool.
	 */
	public FormatTool() {
	}

	/**
	 * Returns the given text with spaces before and after each line.
	 * 
	 * @param content
	 *            content
	 * @return the given text with spaces before and after each line
	 */
	public String formatText(String content) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new StringReader(content));
		in.lines().forEach(line -> sb.append(SPACE + line + SPACE + NEW_LINE));
		return sb.toString();
	}

	/**
	 * Returns the given text removing from each line any heading or trailing
	 * spaces.
	 * 
	 * @param content
	 *            content
	 * @return the given text removing from each line any heading or trailing
	 *         spaces
	 */
	public String trimText(String content) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new StringReader(content));
		in.lines().forEach(line -> sb.append(line.trim() + NEW_LINE));
		return sb.toString();
	}

}
