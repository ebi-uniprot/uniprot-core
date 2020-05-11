package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.de.DeLineFormater;

class DeLineFormaterTest {
    @Test
    void test1() {
        String expected =
                "DE   RecName: Full=Annexin A5;\n"
                        + "DE            Short=Annexin-5;\n"
                        + "DE   AltName: Full=Annexin V;\n"
                        + "DE   AltName: Full=Lipocortin V;\n"
                        + "DE   AltName: Full=Placental anticoagulant protein I;\n"
                        + "DE            Short=PAP-I;\n"
                        + "DE   AltName: Full=PP4;\n"
                        + "DE   AltName: Full=Thromboplastin inhibitor;\n"
                        + "DE   AltName: Full=Vascular anticoagulant-alpha;\n"
                        + "DE            Short=VAC-alpha;\n"
                        + "DE   AltName: Full=Anchorin CII;\n"
                        + "DE   Flags: Precursor;\n";
        String lines =
                "RecName: Full=Annexin A5;\n"
                        + "Short=Annexin-5;\n"
                        + "AltName: Full=Annexin V;\n"
                        + "AltName: Full=Lipocortin V;\n"
                        + "AltName: Full=Placental anticoagulant protein I;\n"
                        + "Short=PAP-I;\n"
                        + "AltName: Full=PP4;\n"
                        + "AltName: Full=Thromboplastin inhibitor;\n"
                        + "AltName: Full=Vascular anticoagulant-alpha;\n"
                        + "Short=VAC-alpha;\n"
                        + "AltName: Full=Anchorin CII;\n"
                        + "Flags: Precursor;\n";
        DeLineFormater formater = new DeLineFormater();
        String formated = formater.format(lines);
        assertEquals(expected, formated);
    }

    @Test
    void testsubname() {
        String expected =
                "DE   SubName: Full=Annexin A5;\n"
                        + "DE            Short=Annexin-5;\n"
                        + "DE   SubName: Full=Annexin V;\n"
                        + "DE   Flags: Precursor;\n";
        String lines =
                "SubName: Full=Annexin A5;\n"
                        + "Short=Annexin-5;\n"
                        + "SubName: Full=Annexin V;\n"
                        + "Flags: Precursor;\n";
        DeLineFormater formater = new DeLineFormater();
        String formated = formater.format(lines);
        assertEquals(expected, formated);
    }

    @Test
    void testContainInclude() {
        String expected =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.-;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha"
                        + " chain;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta"
                        + " chain;\n";
        String lines =
                "RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "Includes:\n"
                        + "RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "EC=2.3.1.35;\n"
                        + "AltName: Full=Ornithine acetyltransferase;\n"
                        + "Short=OATase;\n"
                        + "AltName: Full=Ornithine transacetylase;\n"
                        + "Includes:\n"
                        + "RecName: Full=Amino-acid acetyltransferase;\n"
                        + "EC=2.3.1.-;\n"
                        + "AltName: Full=N-acetylglutamate synthase;\n"
                        + "Short=AGS;\n"
                        + "Contains:\n"
                        + "RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha"
                        + " chain;\n"
                        + "Contains:\n"
                        + "RecName: Full=Arginine biosynthesis bifunctional protein argJ beta"
                        + " chain;\n";
        DeLineFormater formater = new DeLineFormater();
        String formated = formater.format(lines);
        assertEquals(expected, formated);
    }
}
