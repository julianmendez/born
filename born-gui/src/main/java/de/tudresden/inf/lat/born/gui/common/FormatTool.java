package de.tudresden.inf.lat.born.gui.common;

import java.io.BufferedReader;
import java.io.StringReader;

public class FormatTool {

	static final String NEW_LINE = "\n";
	static final String SPACE = " ";

	public FormatTool() {
	}

	public String formatText(String content) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new StringReader(content));
		in.lines().forEach(line -> sb.append(SPACE + line + SPACE + NEW_LINE));
		return sb.toString();
	}

	public String trimText(String content) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new StringReader(content));
		in.lines().forEach(line -> sb.append(line.trim() + NEW_LINE));
		return sb.toString();
	}

}
