package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.*;

import java.util.ArrayList;
import java.util.List;

class BPCPCommentTransformerTest {
    private final BPCPCommentTransformer transformer = new BPCPCommentTransformer();

    /**
     * String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" + "pH dependence:\n" + "Optimum pH
     * is 8-10.; Optimum pH is 3-5.;\n" + "Redox potential:\n" + "E(0) is -448 mV.; E(0) is -234
     * mV.;\n" + "Temperature dependence:\n" + "Highly active at low temperatures, even at 0 degree
     * Celsius. Thermolabile.;" + " Another active at low temperatures.;"); String
     * ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n" + "pH dependence:\n" + "Optimum pH
     * is 8-10. {ECO:0000313|EMBL:BAG16761.1};" + " Optimum pH is 3-5.
     * {ECO:0000313|EMBL:BAG16761.1};\n" + "Redox potential:\n" + "E(0) is -448 mV.
     * {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};" + " E(0) is -234 mV. {ECO:0000303|Ref.6,
     * ECO:0000313|PDB:3OW2};\n" + "Temperature dependence:\n" + "Highly active at low temperatures,
     * even at 0 degree Celsius. Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};" + " Another
     * active at low temperatures.;"); String ccLineString =("BIOPHYSICOCHEMICAL PROPERTIES:\n" +
     * "Absorption:\n" + "Abs(max)=465 nm;\n" + "Note=The above maximum is for the oxidized form.
     * Shows a maximal peak at 330 nm in" + " the reduced form.; These absorption peaks are for the
     * tryptophylquinone cofactor.;\n" + "Kinetic parameters:\n" + "KM=5.4 uM for tyramine;\n" +
     * "KM=688 uM for pyridoxal;\n" + "Vmax=17 umol/min/mg enzyme;\n" + "Note=The enzyme is
     * substrate inhibited at high substrate concentrations (Ki=1.08 mM for tyramine).;" + " Another
     * note is very very long.;"); String ccLineStringEvidence =("BIOPHYSICOCHEMICAL PROPERTIES:\n"
     * + "Absorption:\n" + "Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n" + "Note=The above
     * maximum is for the oxidized form. Shows a maximal peak at 330 nm in" + " the reduced form.
     * {ECO:0000269|PubMed:10433554}; " + "These absorption peaks are for the tryptophylquinone
     * cofactor. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" + "Kinetic parameters:\n" +
     * "KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" + "KM=688 uM for pyridoxal
     * {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n" + "Vmax=17 umol/min/mg enzyme
     * {ECO:0000313|PDB:3OW2};\n" + "Note=The enzyme is substrate inhibited at high substrate
     * concentrations (Ki=1.08 mM for tyramine)." + " {ECO:0000256|HAMAP-Rule:MF_00205}; " +
     * "Another note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");
     */
    @Test
    void testAbsorption() {
        // "Absorption:\n" +
        // "Abs(max)=465 nm;\n" +
        // "Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
        // + " the reduced form.; These absorption peaks are for the tryptophylquinone cofactor.;\n"
        // +
        List<String> lines = new ArrayList<>();
        lines.add("Abs(max)=465 nm;");
        lines.add(
                "Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm"
                    + " in the reduced form.. These absorption peaks are for the tryptophylquinone"
                    + " cofactor.;");
        Absorption absorption = transformer.buildAbsorption(lines);
        assertEquals(465, absorption.getMax());
        assertFalse(absorption.isApproximate());
        assertEquals(2, absorption.getNote().getTexts().size());
        String val1 =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the"
                    + " reduced form.";
        String val2 = "These absorption peaks are for the tryptophylquinone cofactor.";
        assertEquals(val1, absorption.getNote().getTexts().get(0).getValue());
        assertEquals(0, absorption.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(val2, absorption.getNote().getTexts().get(1).getValue());
    }

    @Test
    void testAbsorption2() {
        // "Absorption:\n" +
        // "Abs(max)=465 nm;\n" +
        // "Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
        // + " the reduced form.; These absorption peaks are for the tryptophylquinone cofactor.;\n"
        // +
        List<String> lines = new ArrayList<>();
        lines.add("Abs(max)=~465 nm;");
        lines.add(
                "Note=The above maximum is for the oxidized form. Shows a maximal peak at 330 nm"
                    + " in the reduced form. {ECO:0000269|PubMed:10433554}. These absorption peaks"
                    + " are for the tryptophylquinone cofactor. {ECO:0000269|PubMed:10433554,"
                    + " ECO:0000303|Ref.6};");
        Absorption absorption = transformer.buildAbsorption(lines);
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
    }

    @Test
    void testKM1() {
        String value = "KM=5.4 uM for tyramine;";
        MichaelisConstant km = transformer.buildMichaelisConstant(value);
        assertEquals(5.4f, km.getConstant(), 0.00001f);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, km.getUnit());
        assertEquals("tyramine", km.getSubstrate());
        assertEquals(0, km.getEvidences().size());
    }

    @Test
    void testKM2() {
        String value = "KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};";
        MichaelisConstant km = transformer.buildMichaelisConstant(value);
        assertEquals(5.4f, km.getConstant(), 0.00001f);
        assertEquals(MichaelisConstantUnit.MICRO_MOL, km.getUnit());
        assertEquals("tyramine", km.getSubstrate());
        assertEquals(1, km.getEvidences().size());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", km.getEvidences().get(0).getValue());
    }

    @Test
    void testMaxV1() {
        String val = "Vmax=17 umol/min/mg enzyme;";
        MaximumVelocity mv = transformer.buildMaximumVelocity(val);
        assertEquals(17.0f, mv.getVelocity(), 0.00001f);
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(0, mv.getEvidences().size());
    }

    @Test
    void testMaxV2() {
        String val = "Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};";
        MaximumVelocity mv = transformer.buildMaximumVelocity(val);
        assertEquals(17.0f, mv.getVelocity(), 0.00001f);
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(1, mv.getEvidences().size());
        assertEquals("ECO:0000313|PDB:3OW2", mv.getEvidences().get(0).getValue());
    }

    @Test
    void testKP1() {
        // "Kinetic parameters:\n" +
        // "KM=5.4 uM for tyramine;\n" +
        // "KM=688 uM for pyridoxal;\n" +
        // "Vmax=17 umol/min/mg enzyme;\n" +
        // "Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for
        // tyramine).;"
        // + " Another note is very very long.;"
        List<String> lines = new ArrayList<>();
        lines.add("KM=5.4 uM for tyramine;");
        lines.add("KM=688 uM for pyridoxal;");
        lines.add("Vmax=17 umol/min/mg enzyme;");
        lines.add(
                "Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08"
                    + " mM for tyramine).. Another note is very very long.;");
        KineticParameters kp = transformer.buildKineticParameters(lines);
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
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(0, mv.getEvidences().size());
        assertEquals(2, kp.getNote().getTexts().size());
        assertEquals("Another note is very very long.", kp.getNote().getTexts().get(1).getValue());
    }

    @Test
    void testKP2() {
        // "Kinetic parameters:\n" +
        // "KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n" +
        // "KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n" +
        // "Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n" +
        // "Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM for
        // tyramine)."
        // + " {ECO:0000256|HAMAP-Rule:MF_00205}; "
        // + "Another note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");
        List<String> lines = new ArrayList<>();
        lines.add("KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};");
        lines.add(
                "KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554,"
                    + " ECO:0000313|EMBL:BAG16761.1};");
        lines.add("Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};");
        lines.add(
                "Note=The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08"
                    + " mM for tyramine). {ECO:0000256|HAMAP-Rule:MF_00205}. Another note is very"
                    + " very long. {ECO:0000256|HAMAP-Rule:MF_00205};");
        KineticParameters kp = transformer.buildKineticParameters(lines);
        assertNotNull(kp);
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
    }

    @Test
    void testPhDependence1() {
        String val = "Optimum pH is 8-10.. Optimum pH is 3-5.;";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        PhDependence phd = transformer.buildPHDependence(lines);
        assertEquals(2, phd.getTexts().size());
        assertEquals("Optimum pH is 8-10.", phd.getTexts().get(0).getValue());
        assertEquals(0, phd.getTexts().get(0).getEvidences().size());
        assertEquals("Optimum pH is 3-5.", phd.getTexts().get(1).getValue());
        assertEquals(0, phd.getTexts().get(1).getEvidences().size());
    }

    @Test
    void testPhDependence2() {
        String val = "Optimum pH is 8-10.. Optimum pH is 3-5. {ECO:0000313|EMBL:BAG16761.1};";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        PhDependence phd = transformer.buildPHDependence(lines);
        assertEquals(2, phd.getTexts().size());
        assertEquals("Optimum pH is 8-10.", phd.getTexts().get(0).getValue());
        assertEquals(0, phd.getTexts().get(0).getEvidences().size());
        assertEquals("Optimum pH is 3-5.", phd.getTexts().get(1).getValue());
        assertEquals(1, phd.getTexts().get(1).getEvidences().size());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1",
                phd.getTexts().get(1).getEvidences().get(0).getValue());
    }

    @Test
    void testRedox1() {
        String val = "E(0) is -448 mV.;";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        RedoxPotential redox = transformer.buildRedoxPotential(lines);
        assertEquals(1, redox.getTexts().size());
        assertEquals("E(0) is -448 mV.", redox.getTexts().get(0).getValue());
        assertEquals(0, redox.getTexts().get(0).getEvidences().size());
    }

    @Test
    void testRedox2() {
        String val =
                "E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}."
                        + " E(0) is -234 mV. {ECO:0000303|Ref.6};";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        RedoxPotential redox = transformer.buildRedoxPotential(lines);
        assertEquals(2, redox.getTexts().size());
        assertEquals("E(0) is -448 mV.", redox.getTexts().get(0).getValue());
        assertEquals(2, redox.getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000313|PDB:3OW2", redox.getTexts().get(0).getEvidences().get(1).getValue());
        assertEquals("E(0) is -234 mV.", redox.getTexts().get(1).getValue());
        assertEquals(1, redox.getTexts().get(1).getEvidences().size());
        assertEquals("ECO:0000303|Ref.6", redox.getTexts().get(1).getEvidences().get(0).getValue());
    }

    @Test
    void testTemperatureDependence1() {
        String val = "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.;";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        TemperatureDependence tempD = transformer.buildTemperatureDependence(lines);
        assertEquals(1, tempD.getTexts().size());

        assertEquals(val.substring(0, val.length() - 1), tempD.getTexts().get(0).getValue());
        assertEquals(0, tempD.getTexts().get(0).getEvidences().size());
    }

    @Test
    void testTemperatureDependence2() {
        String val =
                "Highly active at low temperatures, even at 0 degree Celsius. "
                        + "Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205}."
                        + " Another active at low temperatures.;";
        List<String> lines = new ArrayList<>();
        lines.add(val);
        TemperatureDependence tempD = transformer.buildTemperatureDependence(lines);
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
        BPCPComment comment =
                transformer.transform(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, ccLineString);
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
        BPCPComment comment =
                transformer.transform(
                        CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, ccLineStringEvidence);
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
                ("Absorption:\n"
                     + "  Abs(max)=465 nm;\n"
                     + "  Note=The above maximum is for the oxidized form. Shows a maximal peak at"
                     + " 330 nm in the reduced form.;\n"
                     + "Kinetic parameters:\n"
                     + "  KM=5.4 uM for tyramine;\n"
                     + "  KM=688 uM for pyridoxal;\n"
                     + "  Vmax=17 umol/min/mg enzyme;\n"
                     + "  Note=The enzyme is substrate inhibited at high substrate concentrations"
                     + " (Ki=1.08 mM for tyramine).. Another note is very very long.;");

        BPCPComment comment =
                transformer.transform(CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, ccLineString);
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
        assertEquals(val1, absorption.getNote().getTexts().get(0).getValue());
        assertEquals(0, absorption.getNote().getTexts().get(0).getEvidences().size());

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
        assertEquals("umol/min/mg", mv.getUnit());
        assertEquals("enzyme", mv.getEnzyme());
        assertEquals(0, mv.getEvidences().size());
        assertEquals(2, kp.getNote().getTexts().size());
        assertEquals(
                "The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM"
                    + " for tyramine).",
                kp.getNote().getTexts().get(0).getValue());
        assertEquals("Another note is very very long.", kp.getNote().getTexts().get(1).getValue());
    }

    @Test
    void testBPCPComment4() {
        String ccLineStringEvidence =
                ("Absorption:\n"
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

        BPCPComment comment =
                transformer.transform(
                        CommentType.BIOPHYSICOCHEMICAL_PROPERTIES, ccLineStringEvidence);

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
    }
}
