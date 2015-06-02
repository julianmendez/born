/*
 *
 * Copyright (C) 2015 Julian Mendez
 *
 */

package de.tudresden.inf.lat.born.owlapi.main;

/**
 * This exception is thrown by the reasoner.
 * 
 * @author Julian Mendez
 * 
 * @see BornReasoner
 */
public class BornReasonerException extends RuntimeException {

	private static final long serialVersionUID = -7990182124681289015L;

	/**
	 * Constructs a new reasoner exception.
	 * 
	 * @param message
	 *            message to be displayed
	 */
	public BornReasonerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new reasoner exception.
	 * 
	 * @param message
	 *            message to be displayed
	 * @param cause
	 *            cause of the exception
	 */
	public BornReasonerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new reasoner exception.
	 * 
	 * @param cause
	 *            cause of the exception
	 */
	public BornReasonerException(Throwable cause) {
		super(cause);
	}

}
