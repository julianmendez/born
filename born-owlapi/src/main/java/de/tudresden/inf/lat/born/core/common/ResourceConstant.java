package de.tudresden.inf.lat.born.core.common;

import java.net.URI;

/**
 * 
 * @author Julian Mendez
 *
 */
public interface ResourceConstant {

	URI DEFAULT_PROBLOG_DOWNLOAD_URI = URI.create("https://bitbucket.org/problog/problog/get/master.zip");

	URI PROBABILITY_URI = URI.create("http://lat.inf.tu-dresden.de/systems/born#probability");

	String FILE_SEPARATOR = System.getProperty("file.separator");

	String USER_HOME_DIRECTORY = System.getProperty("user.home");

	String BORN_WORKING_DIRECTORY = USER_HOME_DIRECTORY + FILE_SEPARATOR + ".cache" + FILE_SEPARATOR + "born";

	String DEFAULT_PROBLOG_INSTALLATION_DIRECTORY = BORN_WORKING_DIRECTORY;

	String PROBLOG_OUTPUT_FILE = BORN_WORKING_DIRECTORY + FILE_SEPARATOR + "input_for_problog.txt";

	String DEFAULT_TEMPORARY_FILE_NAME = BORN_WORKING_DIRECTORY + FILE_SEPARATOR + "output_from_problog.txt";

	String DEFAULT_PROBLOG_ZIP_FILE = BORN_WORKING_DIRECTORY + FILE_SEPARATOR + "problog-2.1-SNAPSHOT.zip";

	String DEFAULT_VERIFICATION_FILE = DEFAULT_PROBLOG_ZIP_FILE + ".sha";

}
