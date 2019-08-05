package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.CommentType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CofactorMapTest {

    @Test
    void testCofactorMapping() {
        String cofactorLine = "CC   -!- COFACTOR:\n" +
                "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420;\n" +
                "CC         Evidence={ECO:0000255|PROSITE-ProRule:PRU00405};\n" +
                "CC       Note=The RT polymerase active site binds 2 magnesium ions.\n" +
                "CC       {ECO:0000255|PROSITE-ProRule:PRU00405};\n" +
                "CC   -!- COFACTOR:\n" +
                "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000305};\n" +
                "CC       Note=Binds 1 magnesium ions for ribonuclease H (RNase H) activity.\n" +
                "CC       {ECO:0000269|PubMed:16912289};\n" +
                "CC   -!- COFACTOR:\n" +
                "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000305};\n" +
                "CC       Note=Magnesium ions are required for integrase activity. Binds at\n" +
                "CC       least 1, maybe 2 magnesium ions. {ECO:0000305};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(cofactorLine);

        List<CofactorComment> cofactorComments = entry.getCommentByType(CommentType.COFACTOR);
        assertNotNull(entry);
        CofactorMap cofactorMap = new CofactorMap(cofactorComments);
        Map<String,String> mappedCofactor = cofactorMap.attributeValues();
        assertNotNull(mappedCofactor);
        String value = mappedCofactor.get("cc:cofactor");
        String expectedValue = "COFACTOR: Name=Mg(2+); Xref=ChEBI:CHEBI:18420; " +
                "Evidence={ECO:0000255|PROSITE-ProRule:PRU00405}; " +
                "Note=The RT polymerase active site binds 2 magnesium ions. {ECO:0000255|PROSITE-ProRule:PRU00405}; " +
                "COFACTOR: Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000305}; " +
                "Note=Binds 1 magnesium ions for ribonuclease H (RNase H) activity. {ECO:0000269|PubMed:16912289}; " +
                "COFACTOR: Name=Mg(2+); Xref=ChEBI:CHEBI:18420; Evidence={ECO:0000305}; " +
                "Note=Magnesium ions are required for integrase activity. " +
                "Binds at least 1, maybe 2 magnesium ions. {ECO:0000305};";
        assertEquals(expectedValue,value);
    }

    @Test
    void testCofactorMappingWithMolecule() {
        String cofactorLine = "CC   -!- COFACTOR: Serine protease NS3:\n"+
                "CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n"+
                "CC         Evidence={ECO:0000269|PubMed:9060645};\n"+
                "CC       Note=Binds 1 zinc ion. {ECO:0000269|PubMed:9060645};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(cofactorLine);

        List<CofactorComment> cofactorComments = entry.getCommentByType(CommentType.COFACTOR);
        assertNotNull(entry);
        CofactorMap cofactorMap = new CofactorMap(cofactorComments);
        Map<String,String> mappedCofactor = cofactorMap.attributeValues();
        assertNotNull(mappedCofactor);
        String value = mappedCofactor.get("cc:cofactor");
        String expectedValue = "COFACTOR: Serine protease NS3: Name=Zn(2+); Xref=ChEBI:CHEBI:29105; " +
                "Evidence={ECO:0000269|PubMed:9060645}; Note=Binds 1 zinc ion. {ECO:0000269|PubMed:9060645};";
        assertEquals(expectedValue,value);
    }


}