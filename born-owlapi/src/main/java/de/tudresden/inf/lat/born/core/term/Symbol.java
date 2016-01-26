package de.tudresden.inf.lat.born.core.term;

import java.net.URI;

/**
 * This interface contains all the valid symbols.
 * 
 * @author Julian Mendez
 *
 */
public interface Symbol {

	char PAR_A_CHAR = '(';
	char PAR_B_CHAR = ')';

	char SQB_A_CHAR = '[';
	char SQB_B_CHAR = ']';
	char VERT_CHAR = '|';

	char COMMA_CHAR = ',';
	char SEMICOLON = ';';
	char POINT_CHAR = '.';

	char EXCL_MARK_CHAR = '!';
	char UNDERSCORE_CHAR = '_';
	char PERCENT_CHAR = '%';
	char EQUALS_CHAR = '=';
	char COLON_CHAR = ':';
	String COLON_COLON = "" + COLON_CHAR + COLON_CHAR;
	char HYPHEN_CHAR = '-';
	String IF_SYMBOL = "" + COLON_CHAR + HYPHEN_CHAR;
	char APOSTROPHE_CHAR = '\'';
	char QUOTES_CHAR = '\"';
	char NEW_LINE_CHAR = '\n';
	char SPACE_CHAR = ' ';

	URI PROBABILITY_URI = URI.create("http://lat.inf.tu-dresden.de/systems/born#probability");

	String FILE_SEPARATOR = System.getProperty("file.separator");

	String LINE_SEPARATOR = System.getProperty("line.separator");

}
