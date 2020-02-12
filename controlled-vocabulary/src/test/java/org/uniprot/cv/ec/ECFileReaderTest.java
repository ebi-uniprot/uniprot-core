package org.uniprot.cv.ec;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.ECEntry;
import org.uniprot.core.cv.ec.ECEntryImpl;

/**
 * Created 18/03/19
 *
 * @author Edd
 */
class ECFileReaderTest {
    private static final String FTP_LOCATION = "ftp://ftp.expasy.org/databases/enzyme/";

    @Disabled
    @Test
    void canCreateECFileReaderFromFTP() {
        ECFileReader reader = new ECFileReader();
        List<ECEntry> ecs = reader.parse(FTP_LOCATION);
        assertThat(ecs, is(not(emptyList())));
    }

    @Test
    void canCreateECFileReaderFromFile() {
        ECFileReader reader = new ECFileReader();
        List<ECEntry> ecs = reader.parse("ec/");
        assertThat(ecs, is(not(emptyList())));
    }

    @Test
    void throwExceptionWhenCallingParseDirectly() {
        assertThrows(
                UnsupportedOperationException.class,
                () -> new ECFileReader().parseLines(emptyList()));
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

    @Test
    void parseECClassFileCorrectly() {
        final List<String> input =
                Arrays.asList(
                        "1. 1. 2.-    With a cytochrome as acceptor.",
                        "1. 1. 3.-    With oxygen as acceptor.");

        List<ECEntry> retList = new ECFileReader.ECClassFileReader().parseLines(input);
        assertAll(
                "ECEntry dat file parsing result",
                () -> assertNotNull(retList),
                () -> assertThat(retList, hasSize(2)),
                () ->
                        MatcherAssert.assertThat(
                                retList,
                                Matchers.containsInAnyOrder(
                                        new ECEntryImpl.Builder()
                                                .id("1.1.3.-")
                                                .label("With oxygen as acceptor")
                                                .build(),
                                        new ECEntryImpl.Builder()
                                                .id("1.1.2.-")
                                                .label("With a cytochrome as acceptor")
                                                .build())));
    }

    @Test
    void parseECDatFileCorrectly() {
        final List<String> input =
                Arrays.asList(
                        "CC   -----------------------------------------------------------------------",
                        "CC",
                        "CC   ENZYME nomenclature database",
                        "CC",
                        "CC   -----------------------------------------------------------------------",
                        "CC   Release of 13-Feb-2019",
                        "CC   -----------------------------------------------------------------------",
                        "CC",
                        "CC   Alan Bridge and Kristian Axelsen",
                        "CC   SIB Swiss Institute of Bioinformatics",
                        "CC   Centre Medical Universitaire (CMU)",
                        "CC   1, rue Michel Servet",
                        "CC   1211 Geneva 4",
                        "CC   Switzerland",
                        "CC",
                        "CC   Email: enzyme@expasy.org",
                        "CC",
                        "CC   WWW server: http://enzyme.expasy.org/",
                        "CC",
                        "CC   -----------------------------------------------------------------------",
                        "CC   Copyrighted by the SIB Swiss Institute of Bioinformatics.",
                        "CC   There are no restrictions on its use by any institutions as long as",
                        "CC   its content is in no way modified.",
                        "CC   -----------------------------------------------------------------------",
                        "//",
                        "ID   1.1.1.1",
                        "DE   Alcohol dehydrogenase.",
                        "AN   Aldehyde reductase.",
                        "CA   (1) A primary alcohol + NAD(+) = an aldehyde + NADH.",
                        "CA   (2) A secondary alcohol + NAD(+) = a ketone + NADH.",
                        "CF   Zn(2+) or Fe cation.",
                        "CC   -!- Acts on primary or secondary alcohols or hemi-acetals with very broad",
                        "CC       specificity; however the enzyme oxidizes methanol much more poorly",
                        "CC       than ethanol.",
                        "CC   -!- The animal, but not the yeast, enzyme acts also on cyclic secondary",
                        "CC       alcohols.",
                        "PR   PROSITE; PDOC00058;",
                        "PR   PROSITE; PDOC00059;",
                        "PR   PROSITE; PDOC00060;",
                        "DR   P07327, ADH1A_HUMAN;  P28469, ADH1A_MACMU;  Q5RBP7, ADH1A_PONAB;",
                        "//",
                        "ID   1.1.1.2",
                        "DE   Alcohol dehydrogenase (NADP(+)).",
                        "AN   Aldehyde reductase (NADPH).",
                        "CA   An alcohol + NADP(+) = an aldehyde + NADPH.",
                        "CF   Zn(2+).",
                        "CC   -!- Some members of this group oxidize only primary alcohols; others act",
                        "CC       also on secondary alcohols.",
                        "CC   -!- May be identical with ECEntry 1.1.1.19, ECEntry 1.1.1.33 and ECEntry 1.1.1.55.",
                        "CC   -!- Re-specific with respect to NADPH.",
                        "PR   PROSITE; PDOC00061;",
                        "DR   Q6AZW2, A1A1A_DANRE;  Q568L5, A1A1B_DANRE;  Q24857, ADH3_ENTHI ;",
                        "DR   P27800, ALDX_SPOSA ;  P75691, YAHK_ECOLI ;",
                        "//");

        List<ECEntry> retList = new ECFileReader.ECDatFileReader().parseLines(input);
        assertAll(
                "ECEntry dat file parsing result",
                () -> assertNotNull(retList),
                () -> assertThat(retList, hasSize(2)),
                () ->
                        assertThat(
                                retList,
                                Matchers.containsInAnyOrder(
                                        new ECEntryImpl.Builder()
                                                .id("1.1.1.1")
                                                .label("Alcohol dehydrogenase")
                                                .build(),
                                        new ECEntryImpl.Builder()
                                                .id("1.1.1.2")
                                                .label("Alcohol dehydrogenase (NADP(+))")
                                                .build())));
    }
}
