package de.tudresden.inf.lat.born.problog.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

/**
 * Test class for {@link TokenCreator}.
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

	@Test
	public void testCreateTokens0() throws OWLRendererException, OWLOntologyCreationException, IOException {
		List<Token> expected = new ArrayList<Token>();
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

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCreateTokens1() throws OWLRendererException, OWLOntologyCreationException, IOException {
		List<Token> expected = new ArrayList<Token>();
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

		Assert.assertEquals(expected, actual);
	}

}
