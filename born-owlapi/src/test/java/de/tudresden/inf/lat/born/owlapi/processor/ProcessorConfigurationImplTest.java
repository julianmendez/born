package de.tudresden.inf.lat.born.owlapi.processor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

public class ProcessorConfigurationImplTest {

	String exampleText = "This\n is\n  am\n\texample.\n";

	@Test
	public void testRead() throws IOException {
		StringReader reader = new StringReader(exampleText);
		String actual = ProcessorConfigurationImpl.read(reader);
		Assert.assertEquals(exampleText, actual);
	}

	@Test
	public void testWrite() throws IOException {
		StringReader reader = new StringReader(exampleText);
		StringWriter output = new StringWriter();
		ProcessorConfigurationImpl.write(reader, output);
		String actual = output.toString();
		Assert.assertEquals(exampleText, actual);
	}

}
