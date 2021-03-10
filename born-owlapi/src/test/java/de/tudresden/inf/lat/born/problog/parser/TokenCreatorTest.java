package de.tudresden.inf.lat.born.problog.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * This is a unit test for {@link TokenCreator}.
 * 
 * @author Julian Mendez
 *
 */
public class TokenCreatorTest {

	final String PROGRAM_0 = "sub(a, b) :- c.\nsub(c, 'this is a string').\n";

	final String PROGRAM_1 = "" + //
			"\n% This is ignored" + //
			"\n" + //
			"\nsub(a, b)." + //
			"\n";

	/**
	 * Test for program 0.
	 * 
	 * @throws OWLRendererException
	 *             if something went wrong when serializing the OWL ontology
	 * @throws OWLOntologyCreationException
	 *             if something went wrong when creating the ontology
	 * @throws IOException
	 *             if something went wrong with I/O
	 */
	@Test
	public void testCreateTokens0() throws OWLRendererException, OWLOntologyCreationException, IOException {
		List<Token> expected = new ArrayList<>();
		expected.add(new TokenImpl("sub", 1));
		expected.add(new TokenImpl("(", 1));
		expected.add(new TokenImpl("a", 1));
		expected.add(new TokenImpl(",", 1));
		expected.add(new TokenImpl("b", 1));
		expected.add(new TokenImpl(")", 1));
		expected.add(new TokenImpl(":-", 1));
		expected.add(new TokenImpl("c", 1));
		expected.add(new TokenImpl(".", 1));
		expected.add(new TokenImpl("sub", 2));
		expected.add(new TokenImpl("(", 2));
		expected.add(new TokenImpl("c", 2));
		expected.add(new TokenImpl(",", 2));
		expected.add(new TokenImpl("'this is a string'", 2));
		expected.add(new TokenImpl(")", 2));
		expected.add(new TokenImpl(".", 2));

		StringReader reader = new StringReader(PROGRAM_0);
		TokenCreator instance = new TokenCreator();
		List<Token> actual = instance.createTokens(reader);

		Assertions.assertEquals(expected, actual);
	}

	/**
	 * Test for program 1.
	 * 
	 * @throws OWLRendererException
	 *             if something went wrong when serializing the OWL ontology
	 * @throws OWLOntologyCreationException
	 *             if something went wrong when creating the ontology
	 * @throws IOException
	 *             if something went wrong with I/O
	 */
	@Test
	public void testCreateTokens1() throws OWLRendererException, OWLOntologyCreationException, IOException {
		List<Token> expected = new ArrayList<>();
		expected.add(new TokenImpl("sub", 4));
		expected.add(new TokenImpl("(", 4));
		expected.add(new TokenImpl("a", 4));
		expected.add(new TokenImpl(",", 4));
		expected.add(new TokenImpl("b", 4));
		expected.add(new TokenImpl(")", 4));
		expected.add(new TokenImpl(".", 4));

		StringReader reader = new StringReader(PROGRAM_1);
		TokenCreator instance = new TokenCreator();
		List<Token> actual = instance.createTokens(reader);

		Assertions.assertEquals(expected, actual);
	}

}
