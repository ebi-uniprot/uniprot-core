package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

class CcLineTransformerTest {
    private CcLineTransformer transformer = new CcLineTransformer();

    @Test
    void testAPComment() {
        String ccLineString =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist.;\n"
                        + "Name=1; Synonyms=A;\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
                        + " in conflict in positions: 33:I->T. No experimental confirmation"
                        + " available.;\n"
                        + "Name=2;\n"
                        + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480,"
                        + " VSP_000481;\n"
                        + "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD,"
                        + " BimAD;\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available.;\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation available.;";

        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof AlternativeProductsComment);
        AlternativeProductsComment comment = (AlternativeProductsComment) comments.get(0);

        assertEquals(1, comment.getEvents().size());
        assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(
                "Additional isoforms seem to exist.",
                comment.getNote().getTexts().get(0).getValue());
        assertEquals(6, comment.getIsoforms().size());
        APIsoform isoform3 = comment.getIsoforms().get(2);
        assertEquals("Bim-alpha3", isoform3.getName().getValue());
        assertEquals(3, isoform3.getSynonyms().size());
        assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
        assertEquals(1, isoform3.getIsoformIds().size());
        assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
        assertEquals(3, isoform3.getSequenceIds().size());
        assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
        assertFalse(isoform3.getNote() != null);
        APIsoform isoform5 = comment.getIsoforms().get(4);
        assertEquals(1, isoform5.getNote().getTexts().size());
        assertEquals(
                "No experimental confirmation available.",
                isoform5.getNote().getTexts().get(0).getValue());
    }

    @Test
    void testAPCommentWithEvidence() {
        String ccLineStringEvidence =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000303|Ref.6}; Another additional isoforms also seem to exist."
                        + " {ECO:0000269|PubMed:10433554};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A"
                        + " {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
                        + " in conflict in positions: 33:I->T. No experimental confirmation available."
                        + " {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480,"
                        + " VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};"
                        + " Synonyms=BCL2-like 11 transcript variant 10 {ECO:0000313|EMBL:BAG16761.1},"
                        + " Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000313|EMBL:BAG16761.1}; Another no experimental confirmation also"
                        + " available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation.;";
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof AlternativeProductsComment);
        AlternativeProductsComment comment = (AlternativeProductsComment) comments.get(0);

        assertEquals(1, comment.getEvents().size());
        assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
        assertEquals(2, comment.getNote().getTexts().size());
        assertEquals(
                "Additional isoforms seem to exist.",
                comment.getNote().getTexts().get(0).getValue());
        assertEquals(2, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "Another additional isoforms also seem to exist.",
                comment.getNote().getTexts().get(1).getValue());
        assertEquals(1, comment.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(6, comment.getIsoforms().size());
        APIsoform isoform1 = comment.getIsoforms().get(0);
        assertEquals("1", isoform1.getName().getValue());
        assertEquals(2, isoform1.getName().getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1", isoform1.getName().getEvidences().get(0).getValue());
        assertEquals("ECO:0000313|PDB:3OW2", isoform1.getName().getEvidences().get(1).getValue());
        assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
        assertEquals(0, isoform1.getSequenceIds().size());

        APIsoform isoform3 = comment.getIsoforms().get(2);
        assertEquals("Bim-alpha3", isoform3.getName().getValue());
        assertEquals(3, isoform3.getSynonyms().size());
        assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
        assertEquals(1, isoform3.getIsoformIds().size());
        assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
        assertEquals(3, isoform3.getSequenceIds().size());
        assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
        assertFalse(isoform3.getNote() != null);
        APIsoform isoform5 = comment.getIsoforms().get(4);
        assertEquals(2, isoform5.getNote().getTexts().size());
        assertEquals(
                "No experimental confirmation available.",
                isoform5.getNote().getTexts().get(0).getValue());
        assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                isoform5.getNote().getTexts().get(0).getEvidences().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                isoform5.getNote().getTexts().get(0).getEvidences().get(1).getValue());
        assertEquals(
                "Another no experimental confirmation also available.",
                isoform5.getNote().getTexts().get(1).getValue());
        assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:1043355",
                isoform5.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                isoform5.getNote().getTexts().get(1).getEvidences().get(1).getValue());
    }

    @Test
    void tesAPCommenttWithEvidenceFormated() {
        String ccLineStringEvidence =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + "  Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000303|Ref.6}; Another additional isoforms also seem to exist."
                        + " {ECO:0000269|PubMed:10433554};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A"
                        + " {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "  IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "  Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
                        + " in conflict in positions: 33:I->T. No experimental confirmation available."
                        + " {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + "  IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480,"
                        + " VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};"
                        + " Synonyms=BCL2-like 11 transcript variant 10 {ECO:0000313|EMBL:BAG16761.1},"
                        + " Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n"
                        + "  IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "  IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "  IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "  Note=No experimental confirmation available."
                        + " {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}; Another no"
                        + " experimental confirmation also available. {ECO:0000269|PubMed:1043355,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "  IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "  Note=No experimental confirmation.;";
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof AlternativeProductsComment);
        AlternativeProductsComment comment = (AlternativeProductsComment) comments.get(0);

        assertEquals(1, comment.getEvents().size());
        assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
        assertEquals(2, comment.getNote().getTexts().size());
        assertEquals(
                "Additional isoforms seem to exist.",
                comment.getNote().getTexts().get(0).getValue());
        assertEquals(2, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "Another additional isoforms also seem to exist.",
                comment.getNote().getTexts().get(1).getValue());
        assertEquals(1, comment.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(6, comment.getIsoforms().size());
        APIsoform isoform1 = comment.getIsoforms().get(0);
        assertEquals("1", isoform1.getName().getValue());
        assertEquals(2, isoform1.getName().getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1", isoform1.getName().getEvidences().get(0).getValue());
        assertEquals("ECO:0000313|PDB:3OW2", isoform1.getName().getEvidences().get(1).getValue());
        assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
        assertEquals(0, isoform1.getSequenceIds().size());

        APIsoform isoform3 = comment.getIsoforms().get(2);
        assertEquals("Bim-alpha3", isoform3.getName().getValue());
        assertEquals(3, isoform3.getSynonyms().size());
        assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
        assertEquals(1, isoform3.getIsoformIds().size());
        assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
        assertEquals(3, isoform3.getSequenceIds().size());
        assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
        assertFalse(isoform3.getNote() != null);
        APIsoform isoform5 = comment.getIsoforms().get(4);
        assertEquals(2, isoform5.getNote().getTexts().size());
        assertEquals(
                "No experimental confirmation available.",
                isoform5.getNote().getTexts().get(0).getValue());
        assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                isoform5.getNote().getTexts().get(0).getEvidences().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                isoform5.getNote().getTexts().get(0).getEvidences().get(1).getValue());
        assertEquals(
                "Another no experimental confirmation also available.",
                isoform5.getNote().getTexts().get(1).getValue());
        assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:1043355",
                isoform5.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                isoform5.getNote().getTexts().get(1).getEvidences().get(1).getValue());
    }

    @Test
    void testBPCPComment1() {
        String ccLineString =
                ("BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "pH dependence:\n"
                        + "Optimum pH is 8-10.. Optimum pH is 3-5.;\n"
                        + "Redox potential:\n"
                        + "E(0) is -448 mV.. E(0) is -234 mV.;\n"
                        + "Temperature dependence:\n"
                        + "Highly active at low temperatures, even at 0 degree Celsius."
                        + " Thermolabile.. Another active at low temperatures.;");
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof BPCPComment);
        BPCPComment comment = (BPCPComment) comments.get(0);
        assertNotNull(comment);
        assertFalse(comment.getAbsorption() != null);
        assertFalse(comment.getKineticParameters() != null);
        assertTrue(comment.getPhDependence() != null);
        PhDependence phd = comment.getPhDependence();
        assertEquals(2, phd.getTexts().size());
        assertEquals("Optimum pH is 8-10.", phd.getTexts().get(0).getValue());
        assertEquals(0, phd.getTexts().get(0).getEvidences().size());
        assertEquals("Optimum pH is 3-5.", phd.getTexts().get(1).getValue());
        assertEquals(0, phd.getTexts().get(1).getEvidences().size());

        assertTrue(comment.getRedoxPotential() != null);
        RedoxPotential redox = comment.getRedoxPotential();
        assertEquals(2, redox.getTexts().size());
        assertEquals("E(0) is -448 mV.", redox.getTexts().get(0).getValue());
        assertEquals(0, redox.getTexts().get(0).getEvidences().size());
        assertTrue(comment.getTemperatureDependence() != null);
        TemperatureDependence tempD = comment.getTemperatureDependence();
        assertEquals(2, tempD.getTexts().size());

        String val1 = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.";
        assertEquals(val1, tempD.getTexts().get(0).getValue());
        assertEquals(0, tempD.getTexts().get(0).getEvidences().size());
        // assertEquals("ECO:0000256|HAMAP-Rule:MF_00205",
        // tempD.getTexts().get(0).getEvidenceIds().get(0).getValue());
        String val2 = "Another active at low temperatures.";
        assertEquals(val2, tempD.getTexts().get(1).getValue());
        assertEquals(0, tempD.getTexts().get(1).getEvidences().size());
    }

    @Test
    void testBPCPComment2() {
        String ccLineStringEvidence =
                ("BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "pH dependence:\n"
                        + "Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1}. Optimum pH is 3-5."
                        + " {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Redox potential:\n"
                        + "E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}. E(0) is -234"
                        + " mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                        + "Temperature dependence:\n"
                        + "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile."
                        + " {ECO:0000256|HAMAP-Rule:MF_00205}. Another active at low temperatures.;");
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof BPCPComment);
        BPCPComment comment = (BPCPComment) comments.get(0);
        assertNotNull(comment);
        assertFalse(comment.getAbsorption() != null);
        assertFalse(comment.getKineticParameters() != null);
        assertTrue(comment.getPhDependence() != null);
        PhDependence phd = comment.getPhDependence();
        assertEquals(2, phd.getTexts().size());
        assertEquals("Optimum pH is 8-10.", phd.getTexts().get(0).getValue());
        assertEquals(1, phd.getTexts().get(0).getEvidences().size());
        assertEquals("Optimum pH is 3-5.", phd.getTexts().get(1).getValue());
        assertEquals(1, phd.getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                phd.getTexts().get(1).getEvidences().get(0).getValue());
        assertTrue(comment.getRedoxPotential() != null);
        RedoxPotential redox = comment.getRedoxPotential();
        assertEquals(2, redox.getTexts().size());
        assertEquals("E(0) is -448 mV.", redox.getTexts().get(0).getValue());
        assertEquals(2, redox.getTexts().get(0).getEvidences().size());
        assertEquals("ECO:0000303|Ref.6", redox.getTexts().get(0).getEvidences().get(0).getValue());
        assertTrue(comment.getTemperatureDependence() != null);
        TemperatureDependence tempD = comment.getTemperatureDependence();
        assertEquals(2, tempD.getTexts().size());

        String val1 = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.";
        assertEquals(val1, tempD.getTexts().get(0).getValue());
        assertEquals(1, tempD.getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                tempD.getTexts().get(0).getEvidences().get(0).getValue());
        String val2 = "Another active at low temperatures.";
        assertEquals(val2, tempD.getTexts().get(1).getValue());
        assertEquals(0, tempD.getTexts().get(1).getEvidences().size());
    }

    @Test
    void testBPCPComment3() {
        String ccLineString =
                ("BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Absorption:\n"
                        + "  Abs(max)=465 nm;\n"
                        + "  Note=The above maximum is for the oxidized form. Shows a maximal peak at"
                        + " 330 nm in the reduced form.;\n"
                        + "Kinetic parameters:\n"
                        + "  KM=5.4 uM for tyramine;\n"
                        + "  KM=688 uM for pyridoxal;\n"
                        + "  Vmax=17 umol/min/mg enzyme;\n"
                        + "  Note=The enzyme is substrate inhibited at high substrate concentrations"
                        + " (Ki=1.08 mM for tyramine).. Another note is very very long.;");

        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof BPCPComment);
        BPCPComment comment = (BPCPComment) comments.get(0);
        assertNotNull(comment);
        assertTrue(comment.getAbsorption() != null);
        assertTrue(comment.getKineticParameters() != null);
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
        Absorption absorption = comment.getAbsorption();
        assertEquals(465, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertEquals(1, absorption.getNote().getTexts().size());
        String val1 =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the"
                        + " reduced form.";
        //	String val2 = "These absorption peaks are for the tryptophylquinone cofactor.";
        assertEquals(val1, absorption.getNote().getTexts().get(0).getValue());
        assertEquals(0, absorption.getNote().getTexts().get(0).getEvidences().size());
        //	assertEquals(val2, absorption.getNote().getTexts().get(1).getValue());

        KineticParameters kp = comment.getKineticParameters();
        assertNotNull(kp);
        assertEquals(2, kp.getMichaelisConstants().size());
        MichaelisConstant km = kp.getMichaelisConstants().get(0);
        assertEquals(5.4f, km.getConstant(), 0.00001f);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, km.getUnit());
        assertEquals("tyramine", km.getSubstrate());
        assertEquals(0, km.getEvidences().size());
        assertEquals(1, kp.getMaximumVelocities().size());
        MaximumVelocity mv = kp.getMaximumVelocities().get(0);
        assertEquals(17.0f, mv.getVelocity(), 0.00001f);
        //	assertEquals(MaximumVelocityUnit.MICROMOL_PER_MINUTE_AND_MILLIGRAM,  mv.getUnit());
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(0, mv.getEvidences().size());
        assertEquals(2, kp.getNote().getTexts().size());
        assertEquals(
                "The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM"
                        + " for tyramine).",
                kp.getNote().getTexts().get(0).getValue());
        assertEquals("Another note is very very long.", kp.getNote().getTexts().get(1).getValue());
        //		BioPhysicoChemicalPropertiesComment comment2 =   CommentHelper.translateToComment(
        //				ccLineString, CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        //		assertEquals(comment2, comment);
    }

    @Test
    void testBPCPComment4() {
        String ccLineStringEvidence =
                ("BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Absorption:\n"
                        + "  Abs(max)=~465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "  Note=The above maximum is for the oxidized form. Shows a maximal peak at"
                        + " 330 nm in the reduced form. {ECO:0000269|PubMed:10433554}. These"
                        + " absorption peaks are for the tryptophylquinone cofactor."
                        + " {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "Kinetic parameters:\n"
                        + "  KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "  KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "  Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
                        + "  Note=The enzyme is substrate inhibited at high substrate concentrations"
                        + " (Ki=1.08 mM for tyramine). {ECO:0000256|HAMAP-Rule:MF_00205}. Another"
                        + " note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");

        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof BPCPComment);
        BPCPComment comment = (BPCPComment) comments.get(0);
        assertNotNull(comment);
        assertTrue(comment.getAbsorption() != null);
        assertTrue(comment.getKineticParameters() != null);
        assertFalse(comment.getPhDependence() != null);
        assertFalse(comment.getRedoxPotential() != null);
        assertFalse(comment.getTemperatureDependence() != null);
        Absorption absorption = comment.getAbsorption();
        assertEquals(465, absorption.getMax());
        assertTrue(absorption.isApproximate());
        assertEquals(2, absorption.getNote().getTexts().size());
        String val1 =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the"
                        + " reduced form.";
        String val2 = "These absorption peaks are for the tryptophylquinone cofactor.";
        assertEquals(val1, absorption.getNote().getTexts().get(0).getValue());
        assertEquals(1, absorption.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                absorption.getNote().getTexts().get(0).getEvidences().get(0).getValue());
        assertEquals(val2, absorption.getNote().getTexts().get(1).getValue());
        assertEquals(2, absorption.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000303|Ref.6",
                absorption.getNote().getTexts().get(1).getEvidences().get(1).getValue());

        KineticParameters kp = comment.getKineticParameters();
        assertEquals(2, kp.getMichaelisConstants().size());
        MichaelisConstant km = kp.getMichaelisConstants().get(0);
        assertEquals(5.4f, km.getConstant(), 0.00001f);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, km.getUnit());
        assertEquals("tyramine", km.getSubstrate());
        assertEquals(1, km.getEvidences().size());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", km.getEvidences().get(0).getValue());
        assertEquals(1, kp.getMaximumVelocities().size());
        MaximumVelocity mv = kp.getMaximumVelocities().get(0);
        assertEquals(17.0f, mv.getVelocity(), 0.00001f);
        //	assertEquals(MaximumVelocityUnit.MICROMOL_PER_MINUTE_AND_MILLIGRAM,  mv.getUnit());
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(1, mv.getEvidences().size());
        assertEquals("ECO:0000313|PDB:3OW2", mv.getEvidences().get(0).getValue());
        assertEquals(2, kp.getNote().getTexts().size());
        assertEquals("Another note is very very long.", kp.getNote().getTexts().get(1).getValue());
        assertEquals(1, kp.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                kp.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        //		BioPhysicoChemicalPropertiesComment comment2 =   CommentHelper.translateToComment(
        //				ccLineStringEvidence, CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        //		assertEquals(comment2, comment);
    }

    @Test
    void testCofactorEvidence1() {
        String ccLineStringEvidence =
                "COFACTOR:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828;"
                        + " Evidence={ECO:0000255|HAMAP-Rule:MF_00089, ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt). A second"
                        + " loosely associated metal ion is visible in the crystal structure."
                        + " {ECO:0000255|HAMAP-Rule:MF_00082};";
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof CofactorComment);
        CofactorComment comment = (CofactorComment) comments.get(0);
        assertNotNull(comment);
        assertFalse(comment.getMolecule() != null);
        assertEquals(2, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);
        Cofactor cofactor2 = comment.getCofactors().get(1);
        assertEquals("Mg(2+)", cofactor1.getName());
        assertEquals("CHEBI:18420", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", cofactor1.getEvidences().get(0).getValue());
        assertEquals("Co(2+)", cofactor2.getName());
        assertEquals("CHEBI:48828", cofactor2.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor2.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor2.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00089", cofactor2.getEvidences().get(0).getValue());
        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);
        assertEquals(
                "Binds 2 divalent ions per subunit (magnesium or cobalt). A second loosely"
                        + " associated metal ion is visible in the crystal structure.",
                note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00082", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testConfactorEvidence2() {
        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Name=A very looooooooooooong cofactor name with 1 evidence tag;"
                        + " Xref=ChEBI:CHEBI:12345; Evidence={ECO:0000269|PubMed:16683188};\n"
                        + "Name=A very very looooooooooooong cofactor name with X evidence tags;"
                        + " Xref=ChEBI:CHEBI:54321; Evidence={ECO:0000269|PubMed:16683188,"
                        + " ECO:0000269|PubMed:16683189};\n"
                        + "Note=Binds 2 divalent ions per subunit. {ECO:0000269|PubMed:16683188,"
                        + " ECO:0000255|HAMAP-Rule:MF_00086}. Another note."
                        + " {ECO:0000269|PubMed:16683189};";
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof CofactorComment);
        CofactorComment comment = (CofactorComment) comments.get(0);
        assertNotNull(comment);
        assertEquals("Serine protease NS3", comment.getMolecule());
        assertEquals(3, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);
        Cofactor cofactor3 = comment.getCofactors().get(2);
        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683188", cofactor1.getEvidences().get(0).getValue());
        assertEquals(
                "A very very looooooooooooong cofactor name with X evidence tags",
                cofactor3.getName());
        assertEquals("CHEBI:54321", cofactor3.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor3.getCofactorCrossReference().getDatabase());
        assertEquals(2, cofactor3.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683189", cofactor3.getEvidences().get(1).getValue());
        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(2, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);
        EvidencedValue note2 = note.getTexts().get(1);
        assertEquals("Binds 2 divalent ions per subunit.", note1.getValue());
        assertEquals(2, note1.getEvidences().size());
        assertEquals("ECO:0000255|HAMAP-Rule:MF_00086", note1.getEvidences().get(1).getValue());

        assertEquals("Another note.", note2.getValue());
        assertEquals(1, note2.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:16683189", note2.getEvidences().get(0).getValue());
    }

    @Test
    void testCofactor3() {
        String ccLineStringEvidence =
                "COFACTOR: [Serine protease NS3]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105;"
                        + " Evidence={ECO:0000269|PubMed:9060645};\n"
                        + "Note=Binds 1 zinc ion. {ECO:0000269|PubMed:9060645};";

        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof CofactorComment);
        CofactorComment comment = (CofactorComment) comments.get(0);
        assertNotNull(comment);
        assertEquals("Serine protease NS3", comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);

        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:9060645", cofactor1.getEvidences().get(0).getValue());

        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);

        assertEquals("Binds 1 zinc ion.", note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000269|PubMed:9060645", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testCofactor4() {
        String ccLineStringEvidence =
                "COFACTOR: [Non-structural protein 5A]:\n"
                        + "Name=Zn(2+); Xref=ChEBI:CHEBI:29105; Evidence={ECO:0000250};\n"
                        + " Note=Binds 1 zinc ion in the NS5A N-terminal domain. {ECO:0000250};";

        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof CofactorComment);
        CofactorComment comment = (CofactorComment) comments.get(0);
        assertNotNull(comment);
        assertEquals("Non-structural protein 5A", comment.getMolecule());
        assertEquals(1, comment.getCofactors().size());
        Cofactor cofactor1 = comment.getCofactors().get(0);

        assertEquals("Zn(2+)", cofactor1.getName());
        assertEquals("CHEBI:29105", cofactor1.getCofactorCrossReference().getId());
        assertEquals(CofactorDatabase.CHEBI, cofactor1.getCofactorCrossReference().getDatabase());
        assertEquals(1, cofactor1.getEvidences().size());
        assertEquals("ECO:0000250", cofactor1.getEvidences().get(0).getValue());

        Note note = comment.getNote();
        assertNotNull(note);
        assertEquals(1, note.getTexts().size());
        EvidencedValue note1 = note.getTexts().get(0);

        assertEquals("Binds 1 zinc ion in the NS5A N-terminal domain.", note1.getValue());
        assertEquals(1, note1.getEvidences().size());
        assertEquals("ECO:0000250", note1.getEvidences().get(0).getValue());
    }

    @Test
    void testDiseaseNoEvidence1() {
        String ccLineString =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis. Another note.");
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof DiseaseComment);
        DiseaseComment comment = (DiseaseComment) comments.get(0);
        assertNotNull(comment);

        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall"
                        + " of the large intestine (the colon) and the rectum. Genetic alterations are"
                        + " often associated with progression from premalignant lesion (adenoma) to"
                        + " invasive adenocarcinoma. Risk factors for cancer of the colon and rectum"
                        + " include colon polyps, long-standing ulcerative colitis, and genetic family"
                        + " history.";
        String note =
                "The gene represented in this "
                        + "entry is involved in disease pathogenesis. Another note";
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        assertNotNull(comment);
        assertEquals(description, comment.getDisease().getDescription());
        assertEquals(0, comment.getDisease().getEvidences().size());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(note, comment.getNote().getTexts().get(0).getValue());
        assertEquals(0, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(diseaseId, comment.getDisease().getDiseaseId());
        assertEquals(acronyn, comment.getDisease().getAcronym());
        assertEquals("114500", comment.getDisease().getDiseaseCrossReference().getId());
        assertEquals(
                DiseaseDatabase.MIM, comment.getDisease().getDiseaseCrossReference().getDatabase());
    }

    @Test
    void testDiseaseEvidence1() {
        String ccLineStringEvidence =
                ("DISEASE: Colorectal cancer (CRC) [MIM:114500]: A complex disease "
                        + "characterized by malignant lesions arising from the inner wall of "
                        + "the large intestine (the colon) and the rectum. Genetic "
                        + "alterations are often associated with progression from "
                        + "premalignant lesion (adenoma) to invasive adenocarcinoma. Risk "
                        + "factors for cancer of the colon and rectum include colon polyps, "
                        + "long-standing ulcerative colitis, and genetic family history. "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6, "
                        + "ECO:0000313|EMBL:BAG16761.1}. Note=The gene represented in this "
                        + "entry is involved in disease pathogenesis. {ECO:0000256|HAMAP-"
                        + "Rule:MF_00205, ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}. Another"
                        + " note. {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6}.");
        List<Comment> comments = transformer.transformNoHeader(ccLineStringEvidence);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof DiseaseComment);
        DiseaseComment comment = (DiseaseComment) comments.get(0);
        assertNotNull(comment);
        String description =
                "A complex disease characterized by malignant lesions arising from the inner wall"
                        + " of the large intestine (the colon) and the rectum. Genetic alterations are"
                        + " often associated with progression from premalignant lesion (adenoma) to"
                        + " invasive adenocarcinoma. Risk factors for cancer of the colon and rectum"
                        + " include colon polyps, long-standing ulcerative colitis, and genetic family"
                        + " history.";
        String note1 =
                "The gene represented in this " + "entry is involved in disease pathogenesis";
        String note2 = "Another note";
        String diseaseId = "Colorectal cancer";
        String acronyn = "CRC";
        assertNotNull(comment);
        assertEquals(description, comment.getDisease().getDescription());
        assertEquals(3, comment.getDisease().getEvidences().size());
        assertEquals("ECO:0000303|Ref.6", comment.getDisease().getEvidences().get(1).getValue());
        assertEquals(2, comment.getNote().getTexts().size());
        assertEquals(note1, comment.getNote().getTexts().get(0).getValue());
        assertEquals(3, comment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000313|PDB:3OW2",
                comment.getNote().getTexts().get(0).getEvidences().get(2).getValue());
        assertEquals(note2, comment.getNote().getTexts().get(1).getValue());
        assertEquals(2, comment.getNote().getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
        assertEquals(diseaseId, comment.getDisease().getDiseaseId());
        assertEquals(acronyn, comment.getDisease().getAcronym());
        assertEquals("114500", comment.getDisease().getDiseaseCrossReference().getId());
        assertEquals(
                DiseaseDatabase.MIM, comment.getDisease().getDiseaseCrossReference().getDatabase());
    }

    @Test
    void testFailed() {
        String val =
                "DISEASE: Juvenile polyposis/hereditary hemorrhagic telangiectasia syndrome"
                        + " (JP/HHT) [MIM:175050]: JP/HHT syndrome phenotype consists of the"
                        + " coexistence of juvenile polyposis (JIP) and hereditary hemorrhagic"
                        + " telangiectasia (HHT) [MIM:187300] in a single individual. JIP and HHT are"
                        + " autosomal dominant disorders with distinct and non-overlapping clinical"
                        + " features. The former, an inherited gastrointestinal malignancy"
                        + " predisposition, is caused by mutations in SMAD4 or BMPR1A, and the latter"
                        + " is a vascular malformation disorder caused by mutations in ENG or ACVRL1."
                        + " All four genes encode proteins involved in the"
                        + " transforming-growth-factor-signaling pathway. Although there are reports"
                        + " of patients and families with phenotypes of both disorders combined, the"
                        + " genetic etiology of this association is unknown."
                        + " {ECO:0000269|PubMed:15031030}. Note=The disease is caused by mutations"
                        + " affecting the gene represented in this entry.";
        List<Comment> comments = transformer.transformNoHeader(val);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof DiseaseComment);
        DiseaseComment comment = (DiseaseComment) comments.get(0);
        assertNotNull(comment);
        Disease disease = comment.getDisease();
        String diseaseId = "Juvenile polyposis/hereditary hemorrhagic telangiectasia syndrome";
        String diseaseAcronym = "JP/HHT";
        String diseaseReferenceType = "MIM";
        String diseaseReferenceId = "175050";
        String diseaseDescription =
                "JP/HHT syndrome phenotype consists of the coexistence of juvenile polyposis (JIP)"
                        + " and hereditary hemorrhagic telangiectasia (HHT) [MIM:187300] in a single"
                        + " individual. JIP and HHT are autosomal dominant disorders with distinct and"
                        + " non-overlapping clinical features. The former, an inherited"
                        + " gastrointestinal malignancy predisposition, is caused by mutations in"
                        + " SMAD4 or BMPR1A, and the latter is a vascular malformation disorder caused"
                        + " by mutations in ENG or ACVRL1. All four genes encode proteins involved in"
                        + " the transforming-growth-factor-signaling pathway. Although there are"
                        + " reports of patients and families with phenotypes of both disorders"
                        + " combined, the genetic etiology of this association is unknown.";
        String diseaseNote =
                "The disease is caused by mutations affecting the gene represented in this entry";
        assertEquals(disease.getDiseaseId(), diseaseId);
        assertEquals(disease.getAcronym(), diseaseAcronym);
        assertEquals(disease.getDescription(), diseaseDescription);

        assertEquals(
                disease.getDiseaseCrossReference().getDatabase().getDisplayName(),
                diseaseReferenceType);
        assertEquals(disease.getDiseaseCrossReference().getId(), diseaseReferenceId);

        assertEquals(comment.getNote().getTexts().get(0).getValue(), diseaseNote);
    }

    @Test
    void testRnaEditing() {
        String ccLineString =
                "RNA EDITING: Modified_positions=46 {ECO:0000269|PubMed:12527781,"
                        + " ECO:0000269|PubMed:12711687}, 1052 {ECO:0000269|PubMed:12527781,"
                        + " ECO:0000269|PubMed:12711687}; Note=The nonsense codons at positions 46,"
                        + " 421, 973, 984 and 1048 are modified to sense codons.;";
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof RnaEditingComment);
        RnaEditingComment comment = (RnaEditingComment) comments.get(0);
        assertNotNull(comment);

        assertEquals(2, comment.getPositions().size());
        assertEquals("46", comment.getPositions().get(0).getPosition());
        assertEquals(2, comment.getPositions().get(0).getEvidences().size());
        assertEquals(1, comment.getNote().getTexts().size());
        assertEquals(
                "The nonsense codons at positions 46, 421, 973, 984 and 1048 are modified to sense"
                        + " codons",
                comment.getNote().getTexts().get(0).getValue());
        assertTrue(comment.getNote() != null);
    }

    @Test
    void testMassSpec() {
        String ccLineString =
                "MASS SPECTROMETRY: Mass=8320; Mass_error=3; Method=Electrospray; Range=1-72;"
                        + " Evidence={ECO:0000269|PubMed:8735961};";
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof MassSpectrometryComment);
        MassSpectrometryComment comment = (MassSpectrometryComment) comments.get(0);
        assertNotNull(comment);
    }

    @Test
    void testTextOnly1() {
        String ccLineString =
                "ACTIVITY REGULATION: Inactivated by the serine"
                        + " protease inhibitor diisopropylfluorophosphate.";
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());

        assertTrue(comments.get(0) instanceof FreeTextComment);

        FreeTextComment comment = (FreeTextComment) comments.get(0);
        assertNotNull(comment);
        assertEquals(CommentType.ACTIVITY_REGULATION, comment.getCommentType());
    }

    @Test
    void testTextOnly2() {
        String ccLineString = "SIMILARITY: Belongs to the peptidase S1 family. Granzyme subfamily.";
        List<Comment> comments = transformer.transformNoHeader(ccLineString);
        assertEquals(1, comments.size());
        assertTrue(comments.get(0) instanceof FreeTextComment);
        FreeTextComment comment = (FreeTextComment) comments.get(0);
        assertEquals(CommentType.SIMILARITY, comment.getCommentType());
    }
}
