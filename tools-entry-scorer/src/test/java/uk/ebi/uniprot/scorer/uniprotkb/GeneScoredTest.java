package uk.ebi.uniprot.scorer.uniprotkb;


import org.junit.Test;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class GeneScoredTest {
    private static final UniprotLineParserFactory PARSER_FACTORY = new DefaultUniprotLineParserFactory();

    @Test
    public void shouldGeneScore4() {
        String lines = "GN   Name=par-5; Synonyms=ftt-1; OrderedLocusNames=At1g22300;\n" +
                "GN   ORFNames=M117.2;\n";

        int score = 0;
        for (Gene g : parseLines(lines))
            score += new GeneScored(g).score();
        // should be 4 as its one name and one sys
        assertEquals(4, score, 0.001);
    }

    @Test
    public void shouldGenesScore16() {
        String lines = "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;\n" +
                "GN   and\n" +
                "GN   Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;\n";
        int score = 0;
        for (Gene g : parseLines(lines))
            score += new GeneScored(g).score();
        // its 2 names 6 syns
        assertEquals(16, score, 0.001);
    }

    @Test
    public void shouldGeneScore2() {
        String lines = "GN   Name=GF14A; OrderedLocusNames=Os08g0480800, LOC_Os08g37490;\n" +
                "GN   ORFNames=OJ1113_A10.40, OSJNBb0092C08.10;\n";
        int score = 0;
        for (Gene g : parseLines(lines)) {
            score += new GeneScored(g).score();
            // its 1 name
            assertEquals(2, score, 0.001);
        }
    }

    @Test
    public void shouldGenesScore0() {
        String lines = "GN   OrderedLocusNames=Os02g0224200, LOC_Os02g13110;\n" +
                "GN   ORFNames=P0470A03.14, OsJ_005772;\n";
        int score = 0;
        for (Gene g : parseLines(lines))
            score += new GeneScored(g).score();
        // nothing
        assertEquals(0, score);
    }

    @Test
    public void shouldGenesScore6() {
        String lines = "GN   Name=rlmE; Synonyms=ftsJ, rrmJ; ORFNames=HGR_12727;\n";
        int score = 0;
        for (Gene g : parseLines(lines))
            score += new GeneScored(g).score();
        // should be 4 as its one name and one sys
        assertEquals(6, score);
    }

    private List<Gene> parseLines(String lines) {
        UniprotLineParser<GnLineObject> parser = PARSER_FACTORY.createGnLineParser();
        GnLineObject obj = parser.parse(lines);
        GnLineConverter converter = new GnLineConverter();
        return converter.convert(obj);
    }
}