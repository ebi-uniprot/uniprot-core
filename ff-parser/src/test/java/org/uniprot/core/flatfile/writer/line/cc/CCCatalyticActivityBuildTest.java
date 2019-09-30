package org.uniprot.core.flatfile.writer.line.cc;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.Comment;

class CCCatalyticActivityBuildTest extends CCBuildTestAbstr {
    private static UniprotLineParser<CcLineObject> parser;
    private final CcLineConverter converter =
            new CcLineConverter(new HashMap<>(), new HashMap<>(), true);

    @BeforeAll
    static void setup() {
        parser = new DefaultUniprotLineParserFactory().createCcLineParser();
    }

    @Test
    void testNoPD() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=cytidine(32)/guanosine(34) in tRNA + 2 S-adenosyl-L-\n"
                        + "CC         methionine = 2'-O-methylcytidine(32)/2'-O-methylguanosine(34) in\n"
                        + "CC         tRNA + 2 H(+) + 2 S-adenosyl-L-cysteine; Xref=Rhea:RHEA:42396,\n"
                        + "CC         Rhea:RHEA-COMP:10246, ChEBI:CHEBI:74269, ChEBI:CHEBI:82748,\n"
                        + "CC         ChEBI:CHEBI:59789, Rhea:RHEA-COMP:10247, ChEBI:CHEBI:74445,\n"
                        + "CC         ChEBI:CHEBI:74495, ChEBI:CHEBI:15378, ChEBI:CHEBI:57856;\n"
                        + "CC         EC=2.1.1.205; Evidence={ECO:0000255|HAMAP-Rule:MF_03162};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testWithSinglePD() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
                        + "CC         rhamnose + H(+); Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
                        + "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
                        + "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
                        + "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
                        + "CC         ECO:0000269|PubMed:9473059};\n"
                        + "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
                        + "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testWithMultiPD() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
                        + "CC         rhamnose + H(+); Xref=Rhea:RHEA:18885, ChEBI:CHEBI:57273,\n"
                        + "CC         ChEBI:CHEBI:58349, ChEBI:CHEBI:57964, ChEBI:CHEBI:57783;\n"
                        + "CC         EC=1.1.1.271; Evidence={ECO:0000255|HAMAP-Rule:MF_00956,\n"
                        + "CC         ECO:0000269|PubMed:10480878, ECO:0000269|PubMed:11021971,\n"
                        + "CC         ECO:0000269|PubMed:9473059};\n"
                        + "CC       PhysiologicalDirection=left-to-right; Xref=Rhea:RHEA:18886;\n"
                        + "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00956};\n"
                        + "CC       PhysiologicalDirection=right-to-left; Xref=Rhea:RHEA:18898;\n"
                        + "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00957};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testCaBasedOnEnzyme() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=GDP-beta-L-fucose + NADP(+) = GDP-4-dehydro-alpha-D-\n"
                        + "CC         rhamnose; EC=1.1.1.271; Evidence={ECO:0000269|PubMed:10480878,\n"
                        + "CC         ECO:0000269|PubMed:11021971, ECO:0000269|PubMed:9473059};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testLineWrap() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=H2O + L-arginyl-[protein] = L-citrullyl-[protein] +\n"
                        + "CC         NH4(+); Xref=Rhea:RHEA:18089, Rhea:RHEA-COMP:10532, Rhea:RHEA-\n"
                        + "CC         COMP:10588, ChEBI:CHEBI:15377, ChEBI:CHEBI:28938; EC=3.5.3.15;\n"
                        + "CC         Evidence={ECO:0000269|PubMed:27866708};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testLineWrap2() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=Cleavage of non-reducing alpha-(1->3)-N-\n"
                        + "CC         acetylgalactosamine residues from human blood group A and AB\n"
                        + "CC         mucin glycoproteins, Forssman hapten and blood group A lacto\n"
                        + "CC         series glycolipids.; EC=3.2.1.49;\n"
                        + "CC         Evidence={ECO:0000269|PubMed:19683538};";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    @Test
    void testNoXref() {
        String ccLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=Preferential cleavage of Arg-|-Xaa bonds in small\n"
                        + "CC         molecule substrates. Highly selective action to release kallidin\n"
                        + "CC         (lysyl-bradykinin) from kininogen involves hydrolysis of Met-|-\n"
                        + "CC         Xaa or Leu-|-Xaa.; EC=3.4.21.35;";
        CatalyticActivityComment comment = convert(ccLine + "\n");
        doTest(ccLine, comment);
    }

    private CatalyticActivityComment convert(String ccLine) {
        CcLineObject obj = parser.parse(ccLine);
        List<Comment> comments = converter.convert(obj);
        assertEquals(1, comments.size());
        Comment com = comments.get(0);
        assertTrue(com instanceof CatalyticActivityComment);
        return (CatalyticActivityComment) com;
    }
}
