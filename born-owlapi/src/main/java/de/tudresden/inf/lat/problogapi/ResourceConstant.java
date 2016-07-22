package de.tudresden.inf.lat.problogapi;

/**
 * 
 * @author Julian Mendez
 *
 */
public interface ResourceConstant {

	String FILE_SEPARATOR = System.getProperty("file.separator");

	String USER_HOME_DIRECTORY = System.getProperty("user.home");

	String BORN_WORKING_DIRECTORY = USER_HOME_DIRECTORY + FILE_SEPARATOR + ".cache" + FILE_SEPARATOR + "born";

}
