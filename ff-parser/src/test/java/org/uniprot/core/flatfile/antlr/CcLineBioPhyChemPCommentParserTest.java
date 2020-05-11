package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.BiophysicochemicalProperties;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;

class CcLineBioPhyChemPCommentParserTest {
    @Test
    void testProperty1() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Kinetic parameters:\n"
                    + "CC         KM=1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25"
                    + " degrees\n"
                    + "CC         Celsius and at pH 7.6);\n"
                    + "CC         Vmax=1.9 mmol/min/mg enzyme;\n"
                    + "CC       pH dependence:\n"
                    + "CC         Optimum pH is 7.75.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals(1, bp.getKms().size());
        assertEquals(
                "1.3 mM for L,L-SDAP (in the presence of Zn(2+) at 25 degrees Celsius and at pH"
                    + " 7.6)",
                bp.getKms().get(0).getValue());
        assertEquals(1, bp.getVmaxs().size());
        assertEquals("1.9 mmol/min/mg enzyme", bp.getVmaxs().get(0).getValue());
        assertEquals("Optimum pH is 7.75.", bp.getPhDependence().get(0).getValue());
    }

    @Test
    void testSmallVmaxValue() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Kinetic parameters:\n"
                    + "CC         KM=0.3913 uM for FAM fluorophore-coupled RNA substrate and a\n"
                    + "CC         quencher-coupled DNA primer {ECO:0000269|PubMed:26779609};\n"
                    + "CC         Vmax=0.000197 umol/sec/ug enzyme"
                    + " {ECO:0000269|PubMed:26779609};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals(1, bp.getKms().size());
        assertEquals(
                "0.3913 uM for FAM fluorophore-coupled RNA substrate and a quencher-coupled DNA"
                    + " primer",
                bp.getKms().get(0).getValue());
        assertEquals(1, bp.getVmaxs().size());
        assertEquals("0.000197 umol/sec/ug enzyme", bp.getVmaxs().get(0).getValue());
    }

    @Test
    void testProperty2() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Kinetic parameters:\n"
                        + "CC         KM=71 uM for ATP;\n"
                        + "CC         KM=98 uM for ADP;\n"
                        + "CC         KM=1.5 mM for acetate;\n"
                        + "CC         KM=0.47 mM for acetyl phosphate;\n"
                        + "CC       Temperature dependence:\n"
                        + "CC         Optimum temperature is 65 degrees Celsius. Protected from\n"
                        + "CC         thermal inactivation by ATP.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals(null, bp.getBsorptionAbs());
        assertEquals(0, bp.getBsorptionNote().size());
        assertEquals(4, bp.getKms().size());
        assertEquals("71 uM for ATP", bp.getKms().get(0).getValue());
        assertEquals("98 uM for ADP", bp.getKms().get(1).getValue());
        assertEquals("1.5 mM for acetate", bp.getKms().get(2).getValue());
        assertEquals("0.47 mM for acetyl phosphate", bp.getKms().get(3).getValue());
        assertEquals(0, bp.getVmaxs().size());

        assertEquals(
                "Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by"
                    + " ATP.",
                bp.getTemperatureDependence().get(0).getValue());
    }

    @Test
    void testTwoTempDependences() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Kinetic parameters:\n"
                    + "CC         KM=71 uM for ATP;\n"
                    + "CC         KM=98 uM for ADP;\n"
                    + "CC         KM=1.5 mM for acetate;\n"
                    + "CC         KM=0.47 mM for acetyl phosphate;\n"
                    + "CC       Temperature dependence:\n"
                    + "CC         Optimum temperature is 65 degrees Celsius. Protected from\n"
                    + "CC         thermal inactivation by ATP. {ECO:0000269|PubMed:10433555};\n"
                    + "CC         2 thermal inactivation by ATP. {ECO:0000269|PubMed:10433556};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals(null, bp.getBsorptionAbs());
        assertEquals(0, bp.getBsorptionNote().size());
        assertEquals(4, bp.getKms().size());
        assertEquals("71 uM for ATP", bp.getKms().get(0).getValue());
        assertEquals("98 uM for ADP", bp.getKms().get(1).getValue());
        assertEquals("1.5 mM for acetate", bp.getKms().get(2).getValue());
        assertEquals("0.47 mM for acetyl phosphate", bp.getKms().get(3).getValue());
        assertEquals(0, bp.getVmaxs().size());
        assertEquals(
                "Optimum temperature is 65 degrees Celsius. Protected from thermal inactivation by"
                    + " ATP.",
                bp.getTemperatureDependence().get(0).getValue());
        assertEquals(
                "ECO:0000269|PubMed:10433555",
                bp.getTemperatureDependence().get(0).getEvidences().get(0));
        assertEquals(
                "2 thermal inactivation by ATP.", bp.getTemperatureDependence().get(1).getValue());
        assertEquals(
                "ECO:0000269|PubMed:10433556",
                bp.getTemperatureDependence().get(1).getEvidences().get(0));
    }

    @Test
    void testAbsorption() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Absorption:\n"
                        + "CC         Abs(max)=578 nm;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("578 nm", bp.getBsorptionAbs().getValue());
        assertEquals(0, bp.getBsorptionNote().size());
        assertEquals(0, bp.getKms().size());
        assertEquals(0, bp.getVmaxs().size());
    }

    void testAbsorptionWithNote() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Absorption:\n"
                    + "CC         Abs(max)=~596 nm;\n"
                    + "CC         Note=In the presence of anions, the maximum absorption shifts"
                    + " to\n"
                    + "CC         about 575 nm.;\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("596 nm", bp.getBsorptionAbs().getValue());
        assertTrue(bp.isBsorptionAbsApproximate());
        assertEquals(1, bp.getBsorptionNote().size());
        assertEquals(
                "In the presence of anions, the maximum absorption shifts to about 575 nm.",
                bp.getBsorptionNote().get(0).getValue());
        assertEquals(0, bp.getKms().size());
        assertEquals(0, bp.getVmaxs().size());
    }

    void testAbsorptionWithNoteEvidences() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Absorption:\n"
                    + "CC         Abs(max)=~596 nm {ECO:0000313};\n"
                    + "CC         Note=In the presence of anions, the maximum absorption shifts"
                    + " to\n"
                    + "CC         about 575 nm. {ECO:0000314};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("596 nm", bp.getBsorptionAbs().getValue());
        assertEquals("ECO:0000313", bp.getBsorptionAbs().getEvidences().get(0));
        assertTrue(bp.isBsorptionAbsApproximate());
        assertEquals(1, bp.getBsorptionNote().size());
        assertEquals(
                "In the presence of anions, the maximum absorption shifts to about 575 nm.",
                bp.getBsorptionNote().get(0).getValue());
        assertEquals("ECO:0000314", bp.getBsorptionNote().get(0).getEvidences().get(0));
        assertEquals(0, bp.getKms().size());
        assertEquals(0, bp.getVmaxs().size());
    }

    @Test
    void testBPWithEvidences() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Absorption:\n"
                    + "CC         Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
                    + "CC         Note=The above maximum is for the oxidized form. Shows a"
                    + " maximal\n"
                    + "CC         peak at 330 nm in the reduced form. These absorption peaks are\n"
                    + "CC         for the tryptophylquinone cofactor. {ECO:0000303|Ref.6,\n"
                    + "CC         ECO:0000269|PubMed:10433554};\n"
                    + "CC       Kinetic parameters:\n"
                    + "CC         KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
                    + "CC         KM=688 uM for pyridoxal {ECO:0000313|EMBL:BAG16761.1,\n"
                    + "CC         ECO:0000269|PubMed:10433554};\n"
                    + "CC         Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
                    + "CC         Note=The enzyme is substrate inhibited at high substrate\n"
                    + "CC         concentrations (Ki=1.08 mM for tyramine).\n"
                    + "CC         {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("465 nm", bp.getBsorptionAbs().getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getBsorptionAbs().getEvidences().get(0));
        assertFalse(bp.isBsorptionAbsApproximate());
        assertEquals(1, bp.getBsorptionNote().size());
        assertEquals(
                "The above maximum is for the oxidized form. Shows a maximal"
                        + " peak at 330 nm in the reduced form. These absorption peaks are"
                        + " for the tryptophylquinone cofactor.",
                bp.getBsorptionNote().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", bp.getBsorptionNote().get(0).getEvidences().get(0));
        assertEquals(
                "ECO:0000269|PubMed:10433554", bp.getBsorptionNote().get(0).getEvidences().get(1));
        assertEquals(2, bp.getKms().size());

        assertEquals("5.4 uM for tyramine", bp.getKms().get(0).getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getKms().get(0).getEvidences().get(0));

        assertEquals("688 uM for pyridoxal", bp.getKms().get(1).getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getKms().get(1).getEvidences().get(0));
        assertEquals("ECO:0000269|PubMed:10433554", bp.getKms().get(1).getEvidences().get(1));

        assertEquals(1, bp.getVmaxs().size());

        assertEquals("17 umol/min/mg enzyme", bp.getVmaxs().get(0).getValue());
        assertEquals("ECO:0000313|PDB:3OW2", bp.getVmaxs().get(0).getEvidences().get(0));

        assertEquals(
                "The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM"
                    + " for tyramine).",
                bp.getKpNote().get(0).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205", bp.getKpNote().get(0).getEvidences().get(0));
    }

    @Test
    void testBPWithEvidences2() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       pH dependence:\n"
                    + "CC         Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
                    + "CC       Redox potential:\n"
                    + "CC         E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                    + "CC       Temperature dependence:\n"
                    + "CC         Highly active at low temperatures, even at 0 degree Celsius.\n"
                    + "CC         Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("Optimum pH is 8-10.", bp.getPhDependence().get(0).getValue());
        assertEquals(
                "ECO:0000313|EMBL:BAG16761.1", bp.getPhDependence().get(0).getEvidences().get(0));

        assertEquals("E(0) is -448 mV.", bp.getRdoxPotential().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", bp.getRdoxPotential().get(0).getEvidences().get(0));
        assertEquals("ECO:0000313|PDB:3OW2", bp.getRdoxPotential().get(0).getEvidences().get(1));

        assertEquals(
                "Highly active at low temperatures, even at 0 degree Celsius. Thermolabile.",
                bp.getTemperatureDependence().get(0).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205",
                bp.getTemperatureDependence().get(0).getEvidences().get(0));
    }

    @Test
    void testBPSpecialChars() {
        String lines =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                    + "CC       Kinetic parameters:\n"
                    + "CC         KM=19.7 uM for peptide substrate"
                    + " DABCYL-ARSGAKASGC(farnesyl)LVS-\n"
                    + "CC         EDANS where EDANS is 5-[(2-aminoethyl)amino]naphthalene-1-\n"
                    + "CC         sulphonic acid fluorophore and DABCYL is 4-{[4-\n"
                    + "CC         (dimethylamino)phenyl]azo}benzoic acid quencher.\n"
                    + "CC         {ECO:0000269|PubMed:24291792};\n"
                    + "CC         Note=kcat is 0.175 s(-1) for peptide substrate DABCYL-\n"
                    + "CC         ARSGAKASGC(farnesyl)LVS-EDANS. {ECO:0000269|PubMed:24291792};\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals(
                "19.7 uM for peptide substrate DABCYL-ARSGAKASGC(farnesyl)LVS-EDANS where EDANS is"
                    + " 5-[(2-aminoethyl)amino]naphthalene-1-sulphonic acid fluorophore and DABCYL"
                    + " is 4-{[4-(dimethylamino)phenyl]azo}benzoic acid quencher.",
                bp.getKms().get(0).getValue());
    }

    @Test
    void testNoHeaderWithEvidence() {
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
        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence2() {
        String ccLineStringEvidence =
                ("BIOPHYSICOCHEMICAL PROPERTIES:\n"
                     + "Absorption:\n"
                     + "Abs(max)=~465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
                     + "Note=The above maximum is for the oxidized form. Shows a maximal peak at"
                     + " 330 nm in the reduced form. {ECO:0000269|PubMed:10433554}. These"
                     + " absorption peaks are for the tryptophylquinone cofactor."
                     + " {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                     + "Kinetic parameters:\n"
                     + "KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
                     + "KM=688 uM for pyridoxal {ECO:0000269|PubMed:10433554,"
                     + " ECO:0000313|EMBL:BAG16761.1};\n"
                     + "Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
                     + "Note=The enzyme is substrate inhibited at high substrate concentrations"
                     + " (Ki=1.08 mM for tyramine). {ECO:0000256|HAMAP-Rule:MF_00205}. Another"
                     + " note is very very long. {ECO:0000256|HAMAP-Rule:MF_00205};");
        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(ccLineStringEvidence);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence3() {

        String linesNoHeader =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "pH dependence:\n"
                        + "  Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Redox potential:\n"
                        + "  E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                        + "Temperature dependence:\n"
                        + "  Highly active at low temperatures, even at 0 degree Celsius.\n"
                        + "  Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(linesNoHeader);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidence4() {

        String linesNoHeader =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "pH dependence:\n"
                        + "  Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Redox potential:\n"
                        + "  E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                        + "Temperature dependence:\n"
                        + "Highly active at low temperatures, even at 0 degree Celsius.\n"
                        + "Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(linesNoHeader);
        CcLineObject obj = parser.parse(lines);
        assertNotNull(obj);
    }

    @Test
    void testNoHeaderWithEvidences() {
        String linesNoHeader =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "Absorption:\n"
                        + "Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Note=The above maximum is for the oxidized form. Shows a maximal\n"
                        + "peak at 330 nm in the reduced form. These absorption peaks are\n"
                        + "for the tryptophylquinone cofactor. {ECO:0000303|Ref.6,\n"
                        + "ECO:0000269|PubMed:10433554};\n"
                        + "Kinetic parameters:\n"
                        + "KM=5.4 uM for tyramine {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "KM=688 uM for pyridoxal {ECO:0000313|EMBL:BAG16761.1,\n"
                        + "ECO:0000269|PubMed:10433554};\n"
                        + "Vmax=17 umol/min/mg enzyme {ECO:0000313|PDB:3OW2};\n"
                        + "Note=The enzyme is substrate inhibited at high substrate\n"
                        + "concentrations (Ki=1.08 mM for tyramine).\n"
                        + "{ECO:0000256|HAMAP-Rule:MF_00205};\n";
        CcLineFormater formater = new CcLineFormater();
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        String lines = formater.format(linesNoHeader);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof BiophysicochemicalProperties);
        BiophysicochemicalProperties bp = (BiophysicochemicalProperties) cc.getObject();
        assertEquals("465 nm", bp.getBsorptionAbs().getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getBsorptionAbs().getEvidences().get(0));
        assertFalse(bp.isBsorptionAbsApproximate());
        assertEquals(1, bp.getBsorptionNote().size());
        assertEquals(
                "The above maximum is for the oxidized form. Shows a maximal"
                        + " peak at 330 nm in the reduced form. These absorption peaks are"
                        + " for the tryptophylquinone cofactor.",
                bp.getBsorptionNote().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", bp.getBsorptionNote().get(0).getEvidences().get(0));
        assertEquals(
                "ECO:0000269|PubMed:10433554", bp.getBsorptionNote().get(0).getEvidences().get(1));
        assertEquals(2, bp.getKms().size());

        assertEquals("5.4 uM for tyramine", bp.getKms().get(0).getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getKms().get(0).getEvidences().get(0));

        assertEquals("688 uM for pyridoxal", bp.getKms().get(1).getValue());
        assertEquals("ECO:0000313|EMBL:BAG16761.1", bp.getKms().get(1).getEvidences().get(0));
        assertEquals("ECO:0000269|PubMed:10433554", bp.getKms().get(1).getEvidences().get(1));

        assertEquals(1, bp.getVmaxs().size());

        assertEquals("17 umol/min/mg enzyme", bp.getVmaxs().get(0).getValue());
        assertEquals("ECO:0000313|PDB:3OW2", bp.getVmaxs().get(0).getEvidences().get(0));

        assertEquals(
                "The enzyme is substrate inhibited at high substrate concentrations (Ki=1.08 mM"
                    + " for tyramine).",
                bp.getKpNote().get(0).getValue());
        assertEquals(
                "ECO:0000256|HAMAP-Rule:MF_00205", bp.getKpNote().get(0).getEvidences().get(0));
    }
}
