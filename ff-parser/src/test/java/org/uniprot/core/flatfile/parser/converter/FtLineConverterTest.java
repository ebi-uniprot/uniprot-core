package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineConverter;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineObject.Ligand;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FtLineConverterTest {
    private final FtLineConverter converter = new FtLineConverter();

    @Test
    void test1() {
        // "FT   HELIX      33     83
        // "FT    SIGNAL     <1     34       Potential.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.HELIX);
        ft.setLocationStart("33");
        ft.setLocationEnd("83");
        fobj.getFts().add(ft);
        FtLineObject.FT ft2 = new FtLineObject.FT();
        ft2.setType(FtLineObject.FTType.SIGNAL);
        ft2.setLocationStart("<1");
        ft2.setLocationEnd("34");
        ft2.setFtText("Potential");
        fobj.getFts().add(ft2);

        FtLineObject.FT ft3 = new FtLineObject.FT();
        ft3.setType(FtLineObject.FTType.NP_BIND);
        ft3.setLocationStart("1");
        ft3.setLocationEnd(">17");
        ft3.setFtText("NAD (By similarity)");
        fobj.getFts().add(ft3);

        FtLineObject.FT ft4 = new FtLineObject.FT();
        ft4.setType(FtLineObject.FTType.NP_BIND);
        ft4.setLocationStart("1");
        ft4.setLocationEnd(">17");
        ft4.setFtText("NAD");
        fobj.getFts().add(ft4);

        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(4, features.size());
        UniProtKBFeature feature1 = features.get(0);
        UniProtKBFeature feature2 = features.get(1);
        UniProtKBFeature feature3 = features.get(2);
        UniProtKBFeature feature4 = features.get(3);
        assertEquals(UniprotKBFeatureType.HELIX, feature1.getType());
        assertEquals(UniprotKBFeatureType.SIGNAL, feature2.getType());
        assertEquals(UniprotKBFeatureType.NP_BIND, feature3.getType());
        assertEquals(UniprotKBFeatureType.NP_BIND, feature4.getType());
        validateLocation(
                feature1.getLocation(), 33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.HELIX, feature1.getType());

        validateLocation(
                feature2.getLocation(), 1, 34, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.SIGNAL, feature2.getType());

        validateLocation(
                feature3.getLocation(), 1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(UniprotKBFeatureType.NP_BIND, feature3.getType());

        assertEquals(feature3.getDescription().getValue(), "NAD (By similarity)");

        validateLocation(
                feature4.getLocation(), 1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(UniprotKBFeatureType.NP_BIND, feature4.getType());

        assertEquals(feature4.getDescription().getValue(), "NAD");
    }

    @Test
    void testMutagen() throws Exception {
        /*
        *  """FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and
                      |FT                                ADP-ribosyl cyclase activity.
        */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.MUTAGEN);
        ft.setLocationStart("119");
        ft.setLocationEnd("119");
        ft.setFtText("C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 119, 119, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.MUTAGEN, feature1.getType());

        assertEquals(
                "Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
                feature1.getDescription().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("R");
        altSeq.add("E");
        altSeq.add("A");
        validateAltSeq(feature1, "C", altSeq);
    }

    @Test
    void testVarSeq() throws Exception {
        /**
         * "FT VAR_SEQ 33 83 TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL |FT DGRTKFSQRG ->
         * SECLTYGKQPLTSFHPFTSQMPP (in |FT isoform 2). |FT /FTId=VSP_004370.
         */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.VAR_SEQ);
        ft.setLocationStart("33");
        ft.setLocationEnd("83");
        ft.setFtText(
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP"
                        + " (in isoform 2)");
        ft.setFtId("VSP_004370");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature1.getType());
        assertEquals("in isoform 2", feature1.getDescription().getValue());
        //		assertEquals(1, feature1.getAlternativeSequence().getReport().getValue().size());
        //		assertEquals("2", feature1.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("VSP_004370", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("SECLTYGKQPLTSFHPFTSQMPP");
        validateAltSeq(feature1, "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG", altSeq);
    }

    @Test
    void testVarSeq2() throws Exception {
        /**
         * "FT VAR_SEQ 33 83 TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL |FT DGRTKFSQRG ->
         * SECLTYGKQPLTSFHPFTSQMPP (in |FT isoform 2). |FT /FTId=VSP_004370.
         */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.VAR_SEQ);
        ft.setLocationStart("33");
        ft.setLocationEnd("83");
        ft.setFtText(
                "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP(in"
                        + " isoform 2)");
        ft.setFtId("VSP_004370");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature1.getType());
        assertEquals("in isoform 2", feature1.getDescription().getValue());
        //		assertEquals(1, feature1.getAlternativeSequence().getReport().getValue().size());
        //		assertEquals("2", feature1.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("VSP_004370", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("SECLTYGKQPLTSFHPFTSQMPP");
        validateAltSeq(feature1, "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG", altSeq);
    }

    @Test
    void testVariant() throws Exception {
        /**
         * FT VARIANT 102 102 V -> I (in HH2; dbSNP:rs55642501). FT /FTId=VAR_030972.
         * /FTId=VSP_004370.
         */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.VARIANT);
        ft.setLocationStart("102");
        ft.setLocationEnd("102");
        ft.setFtText("V -> I (in HH2; dbSNP:rs55642501)");
        ft.setFtId("VAR_030972");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 102, 102, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.VARIANT, feature1.getType());

        assertEquals("in HH2; dbSNP:rs55642501", feature1.getDescription().getValue());
        assertEquals("VAR_030972", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("I");
        validateAltSeq(feature1, "V", altSeq);
    }

    @Test
    void testVariant2() throws Exception {
        /**
         * FT VARIANT 102 102 V -> I (in HH2; dbSNP:rs55642501). FT /FTId=VAR_030972.
         * /FTId=VSP_004370.
         */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.VARIANT);
        ft.setLocationStart("102");
        ft.setLocationEnd("102");
        ft.setFtText("V -> I(in HH2; dbSNP:rs55642501)");
        ft.setFtId("VAR_030972");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 102, 102, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.VARIANT, feature1.getType());

        assertEquals("in HH2; dbSNP:rs55642501", feature1.getDescription().getValue());
        assertEquals("VAR_030972", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("I");
        validateAltSeq(feature1, "V", altSeq);
    }

    @Test
    void testVariantNoText() throws Exception {
        /**
         * "FT VARIANT 267 294 ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n" + "FT
         * LKVPVVQKVTKRLGVTSPD.\n";
         */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.VARIANT);
        ft.setLocationStart("267");
        ft.setLocationEnd("294");
        ft.setFtText("ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD");
        ft.setFtId("");
        fobj.getFts().add(ft);
        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature feature1 = features.get(0);
        validateLocation(
                feature1.getLocation(), 267, 294, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.VARIANT, feature1.getType());

        assertEquals("", feature1.getDescription().getValue());
        assertEquals("", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("ITAVTLPPDLKVPVVQKVTKRLGVTSPD");
        validateAltSeq(feature1, "ASAIILRSQLIVALAQKLSRTVGVNKAV", altSeq);
    }

    @Test
    void testEvidence() {
        // "FT   HELIX      33     83{EI1,EI2}
        // "FT    SIGNAL     <1     34       Potential{EI2,EI3}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.HELIX);
        ft.setLocationStart("33");
        ft.setLocationEnd("83");
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10081");
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10082");
        fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
        fobj.getFts().add(ft);
        FtLineObject.FT ft2 = new FtLineObject.FT();
        ft2.setType(FtLineObject.FTType.SIGNAL);
        ft2.setLocationStart("<1");
        ft2.setLocationEnd("34");
        ft2.setFtText("Potential");
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10082");
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10083");
        fobj.getEvidenceInfo().getEvidences().put(ft2, evIds);
        fobj.getFts().add(ft2);

        FtLineObject.FT ft3 = new FtLineObject.FT();
        ft3.setType(FtLineObject.FTType.NP_BIND);
        ft3.setLocationStart("1");
        ft3.setLocationEnd(">17");
        ft3.setFtText("NAD (By similarity)");
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10083");
        fobj.getEvidenceInfo().getEvidences().put(ft3, evIds);
        fobj.getFts().add(ft3);

        FtLineObject.FT ft4 = new FtLineObject.FT();
        ft4.setType(FtLineObject.FTType.NP_BIND);
        ft4.setLocationStart("1");
        ft4.setLocationEnd(">17");
        ft4.setFtText("NAD");
        fobj.getFts().add(ft4);

        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(4, features.size());
        UniProtKBFeature unfeature1 = features.get(0);
        UniProtKBFeature unfeature2 = features.get(1);
        UniProtKBFeature unfeature3 = features.get(2);
        UniProtKBFeature unfeature4 = features.get(3);
        UniProtKBFeature feature1 = unfeature1;
        UniProtKBFeature feature2 = unfeature2;
        UniProtKBFeature feature3 = unfeature3;
        UniProtKBFeature feature4 = unfeature4;
        assertEquals(UniprotKBFeatureType.HELIX, feature1.getType());
        assertEquals(UniprotKBFeatureType.SIGNAL, feature2.getType());
        assertEquals(UniprotKBFeatureType.NP_BIND, feature3.getType());
        assertEquals(UniprotKBFeatureType.NP_BIND, feature4.getType());

        List<Evidence> eviIds = unfeature1.getEvidences();
        assertEquals(2, eviIds.size());
        Evidence eviId = eviIds.get(0);
        Evidence eviId2 = eviIds.get(1);
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10081", eviId.getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10082", eviId2.getValue());

        eviIds = unfeature2.getEvidences();
        assertEquals(2, eviIds.size());
        eviId = eviIds.get(0);
        eviId2 = eviIds.get(1);
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10082", eviId.getValue());
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10083", eviId2.getValue());

        eviIds = unfeature3.getEvidences();
        assertEquals(1, eviIds.size());
        eviId = eviIds.get(0);
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10083", eviId.getValue());
        eviIds = unfeature4.getEvidences();
        assertEquals(0, eviIds.size());

        validateLocation(
                feature1.getLocation(), 33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.HELIX, feature1.getType());

        validateLocation(
                feature2.getLocation(), 1, 34, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.SIGNAL, feature2.getType());

        assertEquals(feature2.getDescription().getValue(), "Potential");

        validateLocation(
                feature3.getLocation(), 1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(UniprotKBFeatureType.NP_BIND, feature3.getType());
        assertEquals(feature3.getDescription().getValue(), "NAD (By similarity)");

        validateLocation(
                feature4.getLocation(), 1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(UniprotKBFeatureType.NP_BIND, feature4.getType());
        assertEquals(feature4.getDescription().getValue(), "NAD");
    }

    @Test
    void testEvidence2() {
        // "FT   ACT_SITE    150    150       {ECO:0000255|PROSITE-ProRule:PRU10088}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.ACT_SITE);
        ft.setLocationStart("150");
        ft.setLocationEnd("150");
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|PROSITE-ProRule:PRU10088");
        fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
        fobj.getFts().add(ft);

        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature unFeature1 = features.get(0);
        UniProtKBFeature feature1 = unFeature1;
        assertEquals(UniprotKBFeatureType.ACT_SITE, feature1.getType());

        List<Evidence> eviIds = unFeature1.getEvidences();
        assertEquals(1, eviIds.size());
        Evidence eviId = eviIds.get(0);
        assertEquals("PROSITE-ProRule", eviId.getEvidenceCrossReference().getDatabase().getName());
        assertEquals("ECO:0000255|PROSITE-ProRule:PRU10088", eviId.getValue());

        validateLocation(
                feature1.getLocation(), 150, 150, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.ACT_SITE, feature1.getType());
    }

    @Test
    void testEvidence3() {
        // "FT   ACT_SITE    150    150       {ECO:0000255|PROSITE-ProRule:PRU10088}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.ACT_SITE);
        ft.setLocationStart("150");
        ft.setLocationEnd("150");
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10088");
        fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
        fobj.getFts().add(ft);

        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature unFeature = features.get(0);
        UniProtKBFeature feature1 = unFeature;
        assertEquals(UniprotKBFeatureType.ACT_SITE, feature1.getType());

        List<Evidence> eviIds = unFeature.getEvidences();
        assertEquals(1, eviIds.size());
        assertEquals(
                "HAMAP-Rule", eviIds.get(0).getEvidenceCrossReference().getDatabase().getName());
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10088", eviIds.get(0).getValue());

        validateLocation(
                feature1.getLocation(), 150, 150, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(UniprotKBFeatureType.ACT_SITE, feature1.getType());
    }
    
    /*
     * "FT   BINDING         692..697\n"
                  + "FT                   /ligand=\"divinyl chlorophyll-a'\"\n"
                  + "FT                   /ligand_id=\"ChEBI:CHEBI:1234\"\n"
                  + "FT                   /ligand_label=\"A1\"\n"
                  + "FT                   /ligand_part=\"Mg\"\n"
                  + "FT                   /ligand_part_id=\"ChEBI:CHEBI:4325\"\n"
                  + "FT                   /note=\"axial ligand\"\n"
                  + "FT                   /evidence=\"ECO:0000255|HAMAP-Rule:MF_00458\"\n";
     */
    @Test
    void testBindingFeature() {
    	FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.setType(FtLineObject.FTType.BINDING);
        ft.setLocationStart("692");
        ft.setLocationEnd("697");
        ft.setFtText("axial ligand");
        Ligand ligand = new Ligand();
        ligand.setName("divinyl chlorophyll-a'");
        ligand.setId("ChEBI:CHEBI:1234");
        ligand.setLabel("A1");
        ft.setLigand(ligand);
        Ligand ligandPart = new Ligand();
        ligandPart.setName("Mg");
        ligandPart.setId("ChEBI:CHEBI:4325");
        ft.setLigandPart(ligandPart);
        List<String> evIds =List.of("ECO:0000255|HAMAP-Rule:MF_00458");
        fobj.getEvidenceInfo().getEvidences().put(ft, evIds);
        fobj.getFts().add(ft);

        List<UniProtKBFeature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        UniProtKBFeature unFeature = features.get(0);
        UniProtKBFeature feature1 = unFeature;
        assertEquals(UniprotKBFeatureType.BINDING, feature1.getType());

        List<Evidence> eviIds = unFeature.getEvidences();
        assertEquals(1, eviIds.size());
        assertEquals(
                "HAMAP-Rule", eviIds.get(0).getEvidenceCrossReference().getDatabase().getName());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00458", eviIds.get(0).getValue());

        validateLocation(
                feature1.getLocation(), 692, 697, PositionModifier.EXACT, PositionModifier.EXACT);
        org.uniprot.core.uniprotkb.feature.Ligand ftLigand = feature1.getLigand();
        assertNotNull(ftLigand);
        assertEquals("divinyl chlorophyll-a'", ftLigand.getName());
        assertEquals("ChEBI:CHEBI:1234", ftLigand.getId());
        assertEquals("A1", ftLigand.getLabel());
        assertNull(ftLigand.getNote());
        LigandPart ftLigandPart  = feature1.getLigandPart();
        assertNotNull(ftLigandPart);
        assertEquals("Mg", ftLigandPart.getName());
        assertEquals("ChEBI:CHEBI:4325", ftLigandPart.getId());
        assertNull(ftLigandPart.getLabel());
        assertNull(ftLigandPart.getNote());
        
        
    }

    private void validateAltSeq(UniProtKBFeature as, String val, List<String> target) {
        assertEquals(val, as.getAlternativeSequence().getOriginalSequence());
        List<String> altSeq = as.getAlternativeSequence().getAlternativeSequences();
        assertEquals(target, altSeq);
    }

    private void validateLocation(
            Range floc, int start, int end, PositionModifier startF, PositionModifier endF) {
        assertEquals(floc.getStart().getModifier(), startF);
        assertEquals(floc.getEnd().getModifier(), endF);
        if (startF != PositionModifier.UNKNOWN)
            assertEquals(floc.getStart().getValue().intValue(), start);
        if (startF != PositionModifier.UNKNOWN)
            assertEquals(floc.getEnd().getValue().intValue(), end);
    }
}
