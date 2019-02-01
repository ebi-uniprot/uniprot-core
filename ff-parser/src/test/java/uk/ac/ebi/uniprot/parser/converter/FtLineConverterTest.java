package uk.ac.ebi.uniprot.parser.converter;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineObject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FtLineConverterTest {
    private final FtLineConverter converter = new FtLineConverter();

    @Test
    public void test1() {
        //"FT   HELIX      33     83
        //"FT    SIGNAL     <1     34       Potential.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.HELIX;
        ft.location_start = "33";
        ft.location_end = "83";
        fobj.fts.add(ft);
        FtLineObject.FT ft2 = new FtLineObject.FT();
        ft2.type = FtLineObject.FTType.SIGNAL;
        ft2.location_start = "<1";
        ft2.location_end = "34";
        ft2.ft_text = "Potential";
        fobj.fts.add(ft2);

        FtLineObject.FT ft3 = new FtLineObject.FT();
        ft3.type = FtLineObject.FTType.NP_BIND;
        ft3.location_start = "1";
        ft3.location_end = ">17";
        ft3.ft_text = "NAD (By similarity)";
        fobj.fts.add(ft3);

        FtLineObject.FT ft4 = new FtLineObject.FT();
        ft4.type = FtLineObject.FTType.NP_BIND;
        ft4.location_start = "1";
        ft4.location_end = ">17";
        ft4.ft_text = "NAD";
        fobj.fts.add(ft4);

        List<Feature> features = converter.convert(fobj);
        assertEquals(4, features.size());
        Feature feature1 = features.get(0);
        Feature feature2 = features.get(1);
        Feature feature3 = features.get(2);
        Feature feature4 = features.get(3);
        assertEquals(FeatureType.HELIX, feature1.getType());
        assertEquals(FeatureType.SIGNAL, feature2.getType());
        assertEquals(FeatureType.NP_BIND, feature3.getType());
        assertEquals(FeatureType.NP_BIND, feature4.getType());
        validateLocation(feature1.getLocation(),
                         33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.HELIX, feature1.getType());

        validateLocation(feature2.getLocation(),
                         1, 34, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        assertEquals(FeatureType.SIGNAL, feature2.getType());


        validateLocation(feature3.getLocation(),
                         1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(FeatureType.NP_BIND, feature3.getType());

        assertEquals(feature3.getDescription().getValue(), "NAD (By similarity)");

        validateLocation(feature4.getLocation(),
                         1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(FeatureType.NP_BIND, feature4.getType());

        assertEquals(feature4.getDescription().getValue(), "NAD");


    }


    @Test
    public void testMutagen() throws Exception {
		/*
		 *  """FT   MUTAGEN     119    119       C->R,E,A: Loss of cADPr hydrolase and
                 |FT                                ADP-ribosyl cyclase activity.
		 */
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.MUTAGEN;
        ft.location_start = "119";
        ft.location_end = "119";
        ft.ft_text = "C->R,E,A: Loss of cADPr hydrolase and ADP-ribosyl cyclase activity";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         119, 119, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.MUTAGEN, feature1.getType());


        assertEquals("Loss of cADPr hydrolase and ADP-ribosyl cyclase activity",
                     feature1.getDescription().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("R");
        altSeq.add("E");
        altSeq.add("A");
        validateAltSeq(feature1, "C", altSeq);

    }

    @Test
    public void testVarSeq() throws Exception {
        /**
         *
         "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
         |FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
         |FT                                isoform 2).
         |FT                                /FTId=VSP_004370.
         */

        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.VAR_SEQ;
        ft.location_start = "33";
        ft.location_end = "83";
        ft.ft_text = "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in isoform 2)";
        ft.ftId = "VSP_004370";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.VAR_SEQ, feature1.getType());
        assertEquals("in isoform 2", feature1.getDescription().getValue());
//		assertEquals(1, feature1.getAlternativeSequence().getReport().getValue().size());
//		assertEquals("2", feature1.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("VSP_004370", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("SECLTYGKQPLTSFHPFTSQMPP");
        validateAltSeq(feature1, "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG", altSeq);

    }


    @Test
    public void testVarSeq2() throws Exception {
        /**
         *
         "FT   VAR_SEQ      33     83       TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPL
         |FT                                DGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP (in
         |FT                                isoform 2).
         |FT                                /FTId=VSP_004370.
         */

        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.VAR_SEQ;
        ft.location_start = "33";
        ft.location_end = "83";
        ft.ft_text = "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG -> SECLTYGKQPLTSFHPFTSQMPP(in isoform 2)";
        ft.ftId = "VSP_004370";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.VAR_SEQ, feature1.getType());
        assertEquals("in isoform 2", feature1.getDescription().getValue());
//		assertEquals(1, feature1.getAlternativeSequence().getReport().getValue().size());
//		assertEquals("2", feature1.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("VSP_004370", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("SECLTYGKQPLTSFHPFTSQMPP");
        validateAltSeq(feature1, "TPDINPAWYTGRGIRPVGRFGRRRATPRDVTGLGQLSCLPLDGRTKFSQRG", altSeq);

    }


    @Test
    public void testVariant() throws Exception {
        /**
         * FT   VARIANT     102    102       V -> I (in HH2; dbSNP:rs55642501).
         FT                                /FTId=VAR_030972.                       /FTId=VSP_004370.
         */

        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.VARIANT;
        ft.location_start = "102";
        ft.location_end = "102";
        ft.ft_text = "V -> I (in HH2; dbSNP:rs55642501)";
        ft.ftId = "VAR_030972";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         102, 102, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.VARIANT, feature1.getType());

        assertEquals("in HH2; dbSNP:rs55642501", feature1.getDescription().getValue());
        assertEquals("VAR_030972", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("I");
        validateAltSeq(feature1, "V", altSeq);

    }

    @Test
    public void testVariant2() throws Exception {
        /**
         * FT   VARIANT     102    102       V -> I (in HH2; dbSNP:rs55642501).
         FT                                /FTId=VAR_030972.                       /FTId=VSP_004370.
         */

        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.VARIANT;
        ft.location_start = "102";
        ft.location_end = "102";
        ft.ft_text = "V -> I(in HH2; dbSNP:rs55642501)";
        ft.ftId = "VAR_030972";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         102, 102, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.VARIANT, feature1.getType());

        assertEquals("in HH2; dbSNP:rs55642501", feature1.getDescription().getValue());
        assertEquals("VAR_030972", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("I");
        validateAltSeq(feature1, "V", altSeq);

    }


    @Test
    public void testVariantNoText() throws Exception {
        /**
         "FT   VARIANT     267    294       ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPD\n" +
         "FT                                LKVPVVQKVTKRLGVTSPD.\n";
         */

        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.VARIANT;
        ft.location_start = "267";
        ft.location_end = "294";
        ft.ft_text = "ASAIILRSQLIVALAQKLSRTVGVNKAV -> ITAVTLPPDLKVPVVQKVTKRLGVTSPD";
        ft.ftId = "";
        fobj.fts.add(ft);
        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature feature1 = features.get(0);
        validateLocation(feature1.getLocation(),
                         267, 294, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.VARIANT, feature1.getType());

        assertEquals("", feature1.getDescription().getValue());
        assertEquals("", feature1.getFeatureId().getValue());
        List<String> altSeq = new ArrayList<String>();
        altSeq.add("ITAVTLPPDLKVPVVQKVTKRLGVTSPD");
        validateAltSeq(feature1, "ASAIILRSQLIVALAQKLSRTVGVNKAV", altSeq);

    }


    @Test
    public void testEvidence() {
        //"FT   HELIX      33     83{EI1,EI2}
        //"FT    SIGNAL     <1     34       Potential{EI2,EI3}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.HELIX;
        ft.location_start = "33";
        ft.location_end = "83";
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10081");
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10082");
        fobj.evidenceInfo.evidences.put(ft, evIds);
        fobj.fts.add(ft);
        FtLineObject.FT ft2 = new FtLineObject.FT();
        ft2.type = FtLineObject.FTType.SIGNAL;
        ft2.location_start = "<1";
        ft2.location_end = "34";
        ft2.ft_text = "Potential";
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10082");
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10083");
        fobj.evidenceInfo.evidences.put(ft2, evIds);
        fobj.fts.add(ft2);

        FtLineObject.FT ft3 = new FtLineObject.FT();
        ft3.type = FtLineObject.FTType.NP_BIND;
        ft3.location_start = "1";
        ft3.location_end = ">17";
        ft3.ft_text = "NAD (By similarity)";
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10083");
        fobj.evidenceInfo.evidences.put(ft3, evIds);
        fobj.fts.add(ft3);

        FtLineObject.FT ft4 = new FtLineObject.FT();
        ft4.type = FtLineObject.FTType.NP_BIND;
        ft4.location_start = "1";
        ft4.location_end = ">17";
        ft4.ft_text = "NAD";
        fobj.fts.add(ft4);

        List<Feature> features = converter.convert(fobj);
        assertEquals(4, features.size());
        Feature unfeature1 = features.get(0);
        Feature unfeature2 = features.get(1);
        Feature unfeature3 = features.get(2);
        Feature unfeature4 = features.get(3);
        Feature feature1 = unfeature1;
        Feature feature2 = unfeature2;
        Feature feature3 = unfeature3;
        Feature feature4 = unfeature4;
        assertEquals(FeatureType.HELIX, feature1.getType());
        assertEquals(FeatureType.SIGNAL, feature2.getType());
        assertEquals(FeatureType.NP_BIND, feature3.getType());
        assertEquals(FeatureType.NP_BIND, feature4.getType());

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

        validateLocation(feature1.getLocation(),
                         33, 83, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.HELIX, feature1.getType());

        validateLocation(feature2.getLocation(),
                         1, 34, PositionModifier.OUTSIDE, PositionModifier.EXACT);
        assertEquals(FeatureType.SIGNAL, feature2.getType());

        assertEquals(feature2.getDescription().getValue(), "Potential");

        validateLocation(feature3.getLocation(),
                         1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(FeatureType.NP_BIND, feature3.getType());
        assertEquals(feature3.getDescription().getValue(), "NAD (By similarity)");

        validateLocation(feature4.getLocation(),
                         1, 17, PositionModifier.EXACT, PositionModifier.OUTSIDE);
        assertEquals(FeatureType.NP_BIND, feature4.getType());
        assertEquals(feature4.getDescription().getValue(), "NAD");


    }

    @Test
    public void testEvidence2() {
        //"FT   ACT_SITE    150    150       {ECO:0000255|PROSITE-ProRule:PRU10088}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.ACT_SITE;
        ft.location_start = "150";
        ft.location_end = "150";
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|PROSITE-ProRule:PRU10088");
        fobj.evidenceInfo.evidences.put(ft, evIds);
        fobj.fts.add(ft);

        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature unFeature1 = features.get(0);
        Feature feature1 = unFeature1;
        assertEquals(FeatureType.ACT_SITE, feature1.getType());

        List<Evidence> eviIds = unFeature1.getEvidences();
        assertEquals(1, eviIds.size());
        Evidence eviId = eviIds.get(0);
        assertEquals("PROSITE-ProRule", eviId.getSource().getDatabaseType().getName());
        assertEquals("ECO:0000255|PROSITE-ProRule:PRU10088", eviId.getValue());


        validateLocation(feature1.getLocation(),
                         150, 150, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.ACT_SITE, feature1.getType());

    }

    @Test
    public void testEvidence3() {
        //"FT   ACT_SITE    150    150       {ECO:0000255|PROSITE-ProRule:PRU10088}.
        FtLineObject fobj = new FtLineObject();
        FtLineObject.FT ft = new FtLineObject.FT();
        ft.type = FtLineObject.FTType.ACT_SITE;
        ft.location_start = "150";
        ft.location_end = "150";
        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000255|HAMAP-Rule:PRU10088");
        fobj.evidenceInfo.evidences.put(ft, evIds);
        fobj.fts.add(ft);

        List<Feature> features = converter.convert(fobj);
        assertEquals(1, features.size());
        Feature unFeature = features.get(0);
        Feature feature1 = unFeature;
        assertEquals(FeatureType.ACT_SITE, feature1.getType());

        List<Evidence> eviIds = unFeature.getEvidences();
        assertEquals(1, eviIds.size());
        assertEquals("HAMAP-Rule", eviIds.get(0).getSource().getDatabaseType().getName());
        assertEquals("ECO:0000255|HAMAP-Rule:PRU10088", eviIds.get(0).getValue());


        validateLocation(feature1.getLocation(),
                         150, 150, PositionModifier.EXACT, PositionModifier.EXACT);
        assertEquals(FeatureType.ACT_SITE, feature1.getType());


    }


    private void validateAltSeq(Feature as, String val, List<String> target) {
        assertEquals(val, as.getAlternativeSequence().getOriginalSequence());
        List<String> altSeq = as.getAlternativeSequence().getAlternativeSequences();
        assertEquals(target, altSeq);

    }

    private void validateLocation(Range floc, int start, int end,
                                  PositionModifier startF, PositionModifier endF) {
        assertEquals(floc.getStart().getModifier(), startF);
        assertEquals(floc.getEnd().getModifier(), endF);
        if (startF != PositionModifier.UNKOWN)
            assertEquals(floc.getStart().getValue().intValue(), start);
        if (startF != PositionModifier.UNKOWN)
            assertEquals(floc.getEnd().getValue().intValue(), end);
    }
}
