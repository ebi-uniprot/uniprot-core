package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.BPCPComment;
import org.uniprot.core.uniprot.comment.CommentType;

class BPCPMapTest {

    @Test
    void testBPCPAbsorptionWithNote() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Absorption:\n"
                        + "CC         Abs(max)=415 nm {ECO:0000269|PubMed:22547782};\n"
                        + "CC         Note=Shoulder at 335 nm (at pH 7.5 and 30 degrees Celsius).\n"
                        + "CC         {ECO:0000269|PubMed:22547782};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);
        String absorption = mappedBPCP.get("absorption");
        String absorptionExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Absorption: Abs(max)=415 nm {ECO:0000269|PubMed:22547782}; "
                        + "Note=Shoulder at 335 nm (at pH 7.5 and 30 degrees Celsius). {ECO:0000269|PubMed:22547782};";
        assertEquals(absorptionExpected, absorption);
    }

    @Test
    void testBPCPAbsorptionWithoutNote() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Absorption:\n"
                        + "CC         Abs(max)=~550 nm {ECO:0000269|PubMed:10510276};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);
        String absorption = mappedBPCP.get("absorption");
        String absorptionExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Absorption: Abs(max)=~550 nm {ECO:0000269|PubMed:10510276};";
        assertEquals(absorptionExpected, absorption);
    }

    @Test
    void testBPCPPhDependenceWithEvidence() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       pH dependence:\n"
                        + "CC         Optimum pH for enzymatic activity is substrate-dependent, with\n"
                        + "CC         optimal hyaluronate degradation at pH 5, poly-beta-D-glucuronate\n"
                        + "CC         degradation at pH 7, and alginate degradation at pH 9.\n"
                        + "CC         {ECO:0000269|PubMed:24257754};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        String pHdependence = mappedBPCP.get("ph_dependence");
        String pHdependenceExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  pH dependence: Optimum pH for enzymatic activity is "
                        + "substrate-dependent, with optimal hyaluronate degradation at pH 5, "
                        + "poly-beta-D-glucuronate degradation at pH 7, and alginate degradation at pH 9. "
                        + "{ECO:0000269|PubMed:24257754};";
        assertEquals(pHdependenceExpected, pHdependence);
    }

    @Test
    void testBPCPPhDependenceWithoutEvidence() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       pH dependence:\n"
                        + "CC         Optimally active at alkaline pHs.;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        String pHdependence = mappedBPCP.get("ph_dependence");
        String pHdependenceExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  pH dependence: Optimally active at alkaline pHs.;";
        assertEquals(pHdependenceExpected, pHdependence);
    }

    @Test
    void testBPCPKineticWithNoteMapping() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Kinetic parameters:\n"
                        + "CC         KM=0.14 mg/ml for poly-GlcA {ECO:0000269|PubMed:24257754};\n"
                        + "CC         KM=0.26 mg/ml for poly-ManA {ECO:0000269|PubMed:24257754};\n"
                        + "CC         KM=0.55 mg/ml for hyaluronan {ECO:0000269|PubMed:24257754};\n"
                        + "CC         KM=0.17 mM for poly-GlcA (at pH 7)\n"
                        + "CC         {ECO:0000269|PubMed:24808176};\n"
                        + "CC         KM=0.35 mM for poly-ManA (at pH 9)\n"
                        + "CC         {ECO:0000269|PubMed:24808176};\n"
                        + "CC         Note=Vmax for poly-GlcA is about 10-fold greater versus poly-\n"
                        + "CC         ManA or HA (PubMed:24257754). kcat is 31.9 sec(-1) with poly-\n"
                        + "CC         GlcA as substrate (at pH 7) (PubMed:24808176). kcat is 3.3 sec(-\n"
                        + "CC         1) with poly-ManA as substrate (at pH 9) (PubMed:24808176).\n"
                        + "CC         {ECO:0000269|PubMed:24257754, ECO:0000269|PubMed:24808176};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);
        String kinetic = mappedBPCP.get("kinetics");
        String kineticExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Kinetic parameters: KM=0.14 mg/ml for poly-GlcA"
                        + " {ECO:0000269|PubMed:24257754}; KM=0.26 mg/ml for poly-ManA {ECO:0000269|PubMed:24257754}; "
                        + "KM=0.55 mg/ml for hyaluronan {ECO:0000269|PubMed:24257754}; KM=0.17 mM for poly-GlcA (at pH 7) "
                        + "{ECO:0000269|PubMed:24808176}; KM=0.35 mM for poly-ManA (at pH 9) {ECO:0000269|PubMed:24808176}; "
                        + "Note=Vmax for poly-GlcA is about 10-fold greater versus poly-ManA or HA (PubMed:24257754). "
                        + "kcat is 31.9 sec(-1) with poly-GlcA as substrate (at pH 7) "
                        + "(PubMed:24808176). kcat is 3.3 sec(-1) with poly-ManA as substrate (at pH 9) (PubMed:24808176). "
                        + "{ECO:0000269|PubMed:24257754, ECO:0000269|PubMed:24808176};";
        assertEquals(kineticExpected, kinetic);
    }

    @Test
    void testBPCPKineticWithoutNoteMapping() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Absorption:\n"
                        + "CC         Abs(max)=550 nm {ECO:0000269|PubMed:10510276};\n"
                        + "CC       Kinetic parameters:\n"
                        + "CC         KM=8 uM for p-NPP (at pH 5.5 and 25 degrees Celsius)\n"
                        + "CC         {ECO:0000269|PubMed:10510276};\n"
                        + "CC         KM=5 uM for ATP (at pH 5.5 and 25 degrees Celsius)\n"
                        + "CC         {ECO:0000269|PubMed:10510276};\n"
                        + "CC         KM=6 uM for ADP (at pH 5.5 and 25 degrees Celsius)\n"
                        + "CC         {ECO:0000269|PubMed:10510276};\n"
                        + "CC         KM=9 uM for AMP (at pH 5.5 and 25 degrees Celsius)\n"
                        + "CC         {ECO:0000269|PubMed:10510276};\n"
                        + "CC         KM=9 uM for pyrophosphate (at pH 5.5 and 25 degrees Celsius)\n"
                        + "CC         {ECO:0000269|PubMed:10510276};\n"
                        + "CC         KM=30 uM for beta-glycerophosphate (at pH 5.5 and 25 degrees\n"
                        + "CC         Celsius) {ECO:0000269|PubMed:10510276};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);

        String kinetic = mappedBPCP.get("kinetics");
        String kineticExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Kinetic parameters: KM=8 uM for p-NPP "
                        + "(at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276}; "
                        + "KM=5 uM for ATP (at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276}; "
                        + "KM=6 uM for ADP (at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276}; "
                        + "KM=9 uM for AMP (at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276}; "
                        + "KM=9 uM for pyrophosphate (at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276}; "
                        + "KM=30 uM for beta-glycerophosphate (at pH 5.5 and 25 degrees Celsius) {ECO:0000269|PubMed:10510276};";
        assertEquals(kineticExpected, kinetic);
    }

    @Test
    void testBPCPTemperatureDependenceWithEvidence() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Temperature dependence:\n"
                        + "CC         Optimum temperature is 37 degrees Celsius for gelatinase\n"
                        + "CC         activity. Temperatures above 50 degrees Celsius inhibit\n"
                        + "CC         gelatinase activity. {ECO:0000269|PubMed:2172980,\n"
                        + "CC         ECO:0000269|PubMed:9065413};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);
        String tempDependence = mappedBPCP.get("temp_dependence");
        String tempDependenceExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Temperature dependence: Optimum temperature "
                        + "is 37 degrees Celsius for gelatinase activity. Temperatures above 50 degrees Celsius inhibit "
                        + "gelatinase activity. {ECO:0000269|PubMed:2172980, ECO:0000269|PubMed:9065413};";
        assertEquals(tempDependenceExpected, tempDependence);
    }

    @Test
    void testBPCPTemperatureDependenceWithoutEvidence() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Temperature dependence:\n"
                        + "CC         Optimum temperature is 28 degrees Celsius. Active from 4 to 40\n"
                        + "CC         degrees Celsius.;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);
        String tempDependence = mappedBPCP.get("temp_dependence");
        String tempDependenceExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Temperature dependence: Optimum temperature is 28 "
                        + "degrees Celsius. Active from 4 to 40 degrees Celsius.;";
        assertEquals(tempDependenceExpected, tempDependence);
    }

    @Test
    void testBPCPRedoxPotentialWithoutEvidenceMapping() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Redox potential:\n"
                        + "CC         E is 0 +/- 10 mV for 2Fe-2S at pH 7.5.;";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);

        String redoxPotential = mappedBPCP.get("redox_potential");
        String redoxPotentialExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Redox potential: E is 0 +/- 10 mV for 2Fe-2S at pH 7.5.;";
        assertEquals(redoxPotentialExpected, redoxPotential);
    }

    @Test
    void testBPCPRedoxPotentialWithEvidenceMapping() {
        String bpcpLine =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Redox potential:\n"
                        + "CC         E(0) is -432 for heme 2 at pH 7.0. This transition depends on pH\n"
                        + "CC         by approximately -45 mV/pH unit. {ECO:0000269|PubMed:10940005,\n"
                        + "CC         ECO:0000269|PubMed:17547421};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(bpcpLine);

        List<BPCPComment> bpcpComments =
                entry.getCommentsByType(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES);
        assertNotNull(entry);
        BPCPMap bpcpMap = new BPCPMap(bpcpComments);
        Map<String, String> mappedBPCP = bpcpMap.attributeValues();
        assertNotNull(mappedBPCP);

        String redoxPotential = mappedBPCP.get("redox_potential");
        String redoxPotentialExpected =
                "BIOPHYSICOCHEMICAL PROPERTIES:  Redox potential: E(0) is -432 for heme 2 at pH 7.0. "
                        + "This transition depends on pH by approximately -45 mV/pH unit. "
                        + "{ECO:0000269|PubMed:10940005, ECO:0000269|PubMed:17547421};";
        assertEquals(redoxPotentialExpected, redoxPotential);
    }
}
