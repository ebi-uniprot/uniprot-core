package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTLigand;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTLigandPart;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.FTType;

/**
 * @author jluo
 * @date: 9 Feb 2022
 */
public class FtLineBindLigandParserTest {

    @Test
    void testBindWithLigand() {
        String ftLines =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FTType.BINDING, "152", "152", null, null);
        verifyLigand(
                obj.getFts().get(0).getLigand(), "Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        assertNull(obj.getFts().get(0).getLigandPart());
    }

    @Test
    void testBindWithLigandComplete() {
        String ftLines =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /note=\"axial binding residue\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FtLineObject.FTType.BINDING,
                "152",
                "152",
                "axial binding residue",
                null);
        verifyLigand(
                obj.getFts().get(0).getLigand(), "Zn(2+)", "ChEBI:CHEBI:29105", "1", "structural");
        assertNull(obj.getFts().get(0).getLigandPart());
    }

    @Test
    void testBindWithLigandNoNote() {
        String ftLines =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_label=\"1\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "152", "152", null, null);
        verifyLigand(obj.getFts().get(0).getLigand(), "Zn(2+)", "ChEBI:CHEBI:29105", "1", null);
    }

    @Test
    void testBindWithLigandNoLabel() {
        String ftLines =
                "FT   BINDING         152\n"
                        + "FT                   /ligand=\"Zn(2+)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29105\"\n"
                        + "FT                   /ligand_note=\"structural\"\n"
                        + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU01239\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "152", "152", null, null);
        verifyLigand(
                obj.getFts().get(0).getLigand(), "Zn(2+)", "ChEBI:CHEBI:29105", null, "structural");
    }

    @Test
    void testBindWithLigandNoLabelNoNote() {
        String ftLines =
                "FT   BINDING         348..349\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "348", "349", null, null);
        verifyLigand(obj.getFts().get(0).getLigand(), "tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
    }

    @Test
    void testBindWithLigandOnlyName() {
        String ftLines =
                "FT   BINDING         348..349\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "348", "349", null, null);
        verifyLigand(obj.getFts().get(0).getLigand(), "tRNA(Thr)", null, null, null);
    }

    @Test
    void testBindWithLigandAndPart() {
        String ftLines =
                "FT   BINDING         692\n"
                        + "FT                   /ligand=\"divinyl chlorophyll-a'\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:?????\"\n"
                        + "FT                   /ligand_label=\"A1\"\n"
                        + "FT                   /ligand_part=\"Mg\"\n"
                        + "FT                   /ligand_part_id=\"ChEBI:CHEBI:?????\"\n"
                        + "FT                   /note=\"axial ligand\"\n"
                        + "FT                   /evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(
                obj.getFts().get(0),
                FtLineObject.FTType.BINDING,
                "692",
                "692",
                "axial ligand",
                null);
        verifyLigand(
                obj.getFts().get(0).getLigand(),
                "divinyl chlorophyll-a'",
                "ChEBI:CHEBI:?????",
                "A1",
                null);
        verifyLigandPart(
                obj.getFts().get(0).getLigandPart(), "Mg", "ChEBI:CHEBI:?????", null, null);
    }

    @Test
    void testBindWithLigandAndPart2() {
        String ftLines =
                "FT   BINDING         200..219\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /ligand_part=\"tRNA acceptor stem\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "200", "219", null, null);
        verifyLigand(obj.getFts().get(0).getLigand(), "tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        verifyLigandPart(
                obj.getFts().get(0).getLigandPart(), "tRNA acceptor stem", null, null, null);
    }

    @Test
    void testBindWithLigandAndPart3() {
        String ftLines =
                "FT   BINDING         313..317\n"
                        + "FT                   /ligand=\"tRNA(Thr)\"\n"
                        + "FT                   /ligand_id=\"ChEBI:CHEBI:29180\"\n"
                        + "FT                   /ligand_part=\"tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-\n"
                        + "FT                   adenosine residue\"\n"
                        + "FT                   /ligand_part_id=\"ChEBI:CHEBI:83071\"\n"
                        + "FT                   /evidence=\"ECO:0000269|PubMed:10319817\"\n";
        UniprotKBLineParser<FtLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createFtLineParser();
        FtLineObject obj = parser.parse(ftLines);
        assertEquals(1, obj.getFts().size());
        verify(obj.getFts().get(0), FtLineObject.FTType.BINDING, "313", "317", null, null);
        verifyLigand(obj.getFts().get(0).getLigand(), "tRNA(Thr)", "ChEBI:CHEBI:29180", null, null);
        verifyLigandPart(
                obj.getFts().get(0).getLigandPart(),
                "tRNA 3'-terminal nucleotidyl-cytidyl-cytidyl-adenosine residue",
                "ChEBI:CHEBI:83071",
                null,
                null);
    }

    private void verify(
            FtLineObject.FT ft,
            FTType type,
            String start,
            String end,
            String description,
            String ftid) {
        assertEquals(type, ft.getType());
        assertEquals(start, ft.getLocationStart());
        assertEquals(end, ft.getLocationEnd());
        assertEquals(description, ft.getFtText());
        assertEquals(ftid, ft.getFtId());
    }

    void verifyLigand(FTLigand ligand, String name, String id, String label, String note) {
        assertEquals(name, ligand.getName());
        assertEquals(id, ligand.getId());
        assertEquals(label, ligand.getLabel());
        assertEquals(note, ligand.getNote());
    }

    void verifyLigandPart(
            FTLigandPart ligandPart, String name, String id, String label, String note) {
        assertEquals(name, ligandPart.getName());
        assertEquals(id, ligandPart.getId());
        assertEquals(label, ligandPart.getLabel());
        assertEquals(note, ligandPart.getNote());
    }
}
