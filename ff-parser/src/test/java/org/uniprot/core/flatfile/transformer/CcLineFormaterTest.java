package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;

class CcLineFormaterTest {
    @Test
    void testInteraction() {
        String expected =
                "CC   -!- INTERACTION:\n"
                        + "CC       Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
                        + "CC       Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
        String lines =
                "INTERACTION:\n"
                        + "Q9W4W2:fs(1)Yb; NbExp=4; IntAct=EBI-2890374, EBI-3424083;\n"
                        + "Q9VKM1:piwi; NbExp=4; IntAct=EBI-2890374, EBI-3406276;\n";
        verify(expected, lines);
    }

    void verify(String expected, String lines) {
        CcLineFormater formater = new CcLineFormater();

        String formated = formater.format(lines);
        assertEquals(expected, formated);
    }

    @Test
    void testAPComment() {
        String expected =
                "CC   -!- ALTERNATIVE PRODUCTS:\n"
                        + "CC       Event=Alternative splicing; Named isoforms=6;\n"
                        + "CC         Comment=Additional isoforms seem to exist.\n"
                        + "CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "CC       Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                        + "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "CC         confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "CC       Name=2;\n"
                        + "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "CC         VSP_000480, VSP_000481;\n"
                        + "CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                        + "CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                        + "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "CC       Name=4; Synonyms=B;\n"
                        + "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "CC       Name=5;\n"
                        + "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "CC         Note=No experimental confirmation available.\n"
                        + "CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC       Name=6; Synonyms=D;\n"
                        + "CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "CC         Note=No experimental confirmation.;\n";
        String lines =
                "ALTERNATIVE PRODUCTS:\n"
                        + "Event=Alternative splicing; Named isoforms=6;\n"
                        + " Comment=Additional isoforms seem to exist.\n"
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                        + "{ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                        + "Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                        + "sequence is in conflict in positions: 33:I->T. No experimental\n"
                        + "confirmation available. {ECO:0000313|PDB:3OW2};\n"
                        + "Name=2;\n"
                        + " IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                        + "VSP_000480, VSP_000481;\n"
                        + "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                        + "{ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                        + "{ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                        + "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                        + "Name=4; Synonyms=B;\n"
                        + "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                        + "Name=5;\n"
                        + "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                        + "Note=No experimental confirmation available.\n"
                        + "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Name=6; Synonyms=D;\n"
                        + "IsoId=Q9V8R9-6; Sequence=Described;\n"
                        + "Note=No experimental confirmation.;\n";
        verify(expected, lines);
    }

    @Test
    void testBPCPCommentLine() {
        String expected =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       Absorption:\n"
                        + "CC         Abs(max)=465 nm {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC         Note=The above maximum is for the oxidized form. Shows a maximal\n"
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

        String lines =
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
        verify(expected, lines);
    }

    @Test
    void testBPCPCommentLine2() {
        String expected =
                "CC   -!- BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "CC       pH dependence:\n"
                        + "CC         Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "CC       Redox potential:\n"
                        + "CC         E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                        + "CC       Temperature dependence:\n"
                        + "CC         Highly active at low temperatures, even at 0 degree Celsius.\n"
                        + "CC         Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        String lines =
                "BIOPHYSICOCHEMICAL PROPERTIES:\n"
                        + "pH dependence:\n"
                        + "Optimum pH is 8-10. {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "Redox potential:\n"
                        + "E(0) is -448 mV. {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
                        + "Temperature dependence:\n"
                        + "Highly active at low temperatures, even at 0 degree Celsius.\n"
                        + "Thermolabile. {ECO:0000256|HAMAP-Rule:MF_00205};\n";
        verify(expected, lines);
    }

    @Test
    void testCofactor() {
        String expected =
                "CC   -!- COFACTOR: Isoform 1:\n"
                        + "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00087};\n"
                        + "CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00088};\n"
                        + "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "CC       {ECO:0000255|HAMAP-Rule:MF_00086};\n";
        String lines =
                "COFACTOR: Isoform 1:\n"
                        + "Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000255|HAMAP-Rule:MF_00087};\n"
                        + "Name=Co(2+); Xref=ChEBI:CHEBI:48828; Evidence={ECO:0000255|HAMAP-Rule:MF_00088};\n"
                        + "Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n"
                        + "{ECO:0000255|HAMAP-Rule:MF_00086};\n";
        verify(expected, lines);
    }

    @Test
    void testMS() {
        String expected =
                "CC   -!- MASS SPECTROMETRY: Mass=24948; Mass_error=6; Method=MALDI;\n"
                        + "CC       Range=1-228; Evidence={ECO:0000006|PubMed:16629414};\n";
        String lines =
                "MASS SPECTROMETRY: Mass=24948; Mass_error=6; Method=MALDI;\n"
                        + "Range=1-228; Evidence={ECO:0000006|PubMed:16629414};\n";
        verify(expected, lines);
    }

    @Test
    void testRnaEditing() {
        String expected =
                "CC   -!- RNA EDITING: Modified_positions=59 {ECO:0000313|EMBL:BAG16761.1}, 78, 94, 98, 102, 121; Note=The\n"
                        + "CC       nonsense codon at position 59 is modified to a sense codon. The\n"
                        + "CC       stop codon at position 121 is created by RNA editing. {ECO:0000313|PDB:3OW2,\n"
                        + "CC       ECO:0000256|HAMAP-Rule:MF_00205};\n";
        String lines =
                "RNA EDITING: Modified_positions=59 {ECO:0000313|EMBL:BAG16761.1}, 78, 94, 98, 102, 121; Note=The\n"
                        + "nonsense codon at position 59 is modified to a sense codon. The\n"
                        + "stop codon at position 121 is created by RNA editing. {ECO:0000313|PDB:3OW2,\n"
                        + "ECO:0000256|HAMAP-Rule:MF_00205};\n";
        verify(expected, lines);
    }

    @Test
    void testSeqCaution() {
        String expected =
                "CC   -!- SEQUENCE CAUTION:\n"
                        + "CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;\n"
                        + "CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;\n";
        String lines =
                "SEQUENCE CAUTION:\n"
                        + "Sequence=CAI12537.1; Type=Erroneous gene model prediction;\n"
                        + "Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;\n";
        verify(expected, lines);
    }

    @Test
    void testSubCellLocation() {
        String expected =
                "CC   -!- SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "CC       {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
                        + "CC       Note=Loosely associated with the inner membrane.\n"
                        + "CC       {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n";
        String lines =
                "SUBCELLULAR LOCATION: Mitochondrion intermembrane space\n"
                        + "{ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554}.\n"
                        + "Note=Loosely associated with the inner membrane.\n"
                        + "{ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}.\n";
        verify(expected, lines);
    }

    @Test
    void testTextComment() {
        String expected =
                "CC   -!- FUNCTION: May play a cri{tical role in virion formation. Essential\n"
                        + "CC       fo}r {virus} replication in vitro. {ECO:0000313|PDB:3OW2}.\n";
        String lines =
                "FUNCTION: May play a cri{tical role in virion formation. Essential\n"
                        + "fo}r {virus} replication in vitro. {ECO:0000313|PDB:3OW2}.\n";
        verify(expected, lines);
    }

    @Test
    void testWRComment() {
        String expected =
                "CC   -!- WEB RESOURCE: Name=Functional Glycomics Gateway - GTase;\n"
                        + "CC       Note=Beta1,4-N-acetylgalactosaminyltransferase III.;\n"
                        + "CC       URL=\"http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507\";\n";
        String lines =
                "WEB RESOURCE: Name=Functional Glycomics Gateway - GTase;\n"
                        + "Note=Beta1,4-N-acetylgalactosaminyltransferase III.;\n"
                        + "URL=\"http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507\";\n";
        verify(expected, lines);
    }
}
