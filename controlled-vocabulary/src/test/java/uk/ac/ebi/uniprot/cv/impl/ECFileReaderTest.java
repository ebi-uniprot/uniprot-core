package uk.ac.ebi.uniprot.cv.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.ec.EC;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created 18/03/19
 *
 * @author Edd
 */
class ECFileReaderTest {
    private static final String FTP_LOCATION = "ftp://ftp.expasy.org/databases/enzyme/";

    @Test
    void canCreateECFileReader() {
        ECFileReader reader = new ECFileReader();
        List<EC> ecs = reader.parse(FTP_LOCATION);
        assertThat(ecs, is(not(emptyList())));
    }

    @Test
    void givenStringWith1LastDot_whenRemovingLastDot_lastDotIsRemoved() {
        String lineEndingWithoutDot = "hello. world";
        String lineEndingWithDot = lineEndingWithoutDot + ".";
        String processedLine = ECFileReader.removeLastDotsFrom(lineEndingWithDot);
        assertThat(processedLine, is(lineEndingWithoutDot));
    }

    @Test
    void givenStringWithMultipleLastDots_whenRemovingLastDots_lastDotsAreRemoved() {
        String lineEndingWithoutDots = "hello. world";
        String lineEndingWithDots = lineEndingWithoutDots + "....";
        String processedLine = ECFileReader.removeLastDotsFrom(lineEndingWithDots);
        assertThat(processedLine, is(lineEndingWithoutDots));
    }

    @Test
    void givenStringWithNoLastDot_whenRemovingLastDot_inputRemainsTheSame() {
        String lineEndingWithoutDot = "hello world";
        String processedLine = ECFileReader.removeLastDotsFrom(lineEndingWithoutDot);
        assertThat(processedLine, is(lineEndingWithoutDot));
    }
}