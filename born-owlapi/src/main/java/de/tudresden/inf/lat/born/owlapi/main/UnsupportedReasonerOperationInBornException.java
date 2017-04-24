/*
 *
 * Copyright (C) 2015 Julian Mendez
 *
 */

package de.tudresden.inf.lat.born.owlapi.main;

/**
 * This exception is thrown when the reasoner is requested an operation that is
 * unsupported. This happens because the reasoner implements an interface that
 * requires a wider functionality than the one given by the reasoner. This
 * exception is thrown by <code>BornReasoner</code>.
 * 
 * @author Julian Mendez
 * 
 * @see BornReasoner
 */
public class UnsupportedReasonerOperationInBornException extends UnsupportedOperationException {

	private static final long serialVersionUID = 5757365447889383439L;

	/**
	 * Constructs a new unsupported-reasoner-operation exception.
	 */
	public UnsupportedReasonerOperationInBornException() {
		super("This operation is not supported by BORN.");
	}

	/**
	 * Constructs a new unsupported-reasoner-operation exception.
	 * 
	 * @param message
	 *            message
	 */
	public UnsupportedReasonerOperationInBornException(String message) {
		super(message);
	}

	/**
	 * Constructs a new unsupported-reasoner-operation exception.
	 * 
	 * @param message
	 *            message
	 * @param cause
	 *            cause
	 */
	public UnsupportedReasonerOperationInBornException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new unsupported-reasoner-operation exception.
	 * 
	 * @param cause
	 *            cause
	 */
	public UnsupportedReasonerOperationInBornException(Throwable cause) {
		super(cause);
	}

}
