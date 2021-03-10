package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProcessorConfigurationImplTest {

	String exampleText = "This\n is\n  am\n\texample.\n";

	@Test
	public void testRead() throws IOException {
		StringReader reader = new StringReader(exampleText);
		String actual = ProcessorConfigurationImpl.read(reader);
		Assertions.assertEquals(exampleText, actual);
	}

	@Test
	public void testWrite() throws IOException {
		StringReader reader = new StringReader(exampleText);
		StringWriter output = new StringWriter();
		ProcessorConfigurationImpl.write(reader, output);
		String actual = output.toString();
		Assertions.assertEquals(exampleText, actual);
	}

}
