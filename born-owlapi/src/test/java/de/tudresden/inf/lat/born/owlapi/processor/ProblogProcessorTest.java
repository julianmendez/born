package de.tudresden.inf.lat.born.owlapi.processor;


import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import de.tudresden.inf.lat.jproblog.JProblog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for ProblogProcessor.
 *
 * @author Julian Mendez
 */
public class ProblogProcessorTest {

    String INPUT_FOR_PROBLOG = "input_for_problog.txt";

    String OUTPUT_FROM_PROBLOG = "output_from_problog.txt";

    public String readFile(String fileName) {
        try {
            return new String(Files.readAllBytes(Paths.get((getClass().getResource(fileName).toURI()))));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This tests the example ontologies.
     */
    @Test
    public void testApply() {
        ProcessorConfiguration conf = new ProcessorConfigurationImpl();
        JProblog queryProcessor = new JProblog();
        conf.setQueryProcessor(queryProcessor);
        IntStream.range(0, 6).forEach(index -> {
            String input = readFile(File.separator + index + File.separator + INPUT_FOR_PROBLOG);
            String expected = readFile(File.separator + index + File.separator + OUTPUT_FROM_PROBLOG);
            String obtained = conf.getQueryProcessor().apply(input);
            Assertions.assertEquals(expected, obtained);
        });
    }

}
