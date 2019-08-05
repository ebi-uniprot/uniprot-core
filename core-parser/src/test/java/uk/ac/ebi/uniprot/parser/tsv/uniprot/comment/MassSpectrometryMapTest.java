package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MassSpectrometryMapTest {

    @Test
    void testMassSpectrometryMapping() {
        String massSpectrometryLine = "CC   -!- MASS SPECTROMETRY: Mass=17581.1; Method=MALDI; Range=2-165;\n" +
                "     CC       Evidence={ECO:0000269|PubMed:10094780};\n" +
                "     CC   -!- MASS SPECTROMETRY: Mass=66643; Mass_error=13; Method=Electrospray;\n" +
                "     CC       Range=2-165; Note=Isolated L10(L12)4.;\n" +
                "     CC       Evidence={ECO:0000269|PubMed:15923259};\n" +
                "     CC   -!- MASS SPECTROMETRY: Mass=17580; Mass_error=2; Method=Electrospray;\n" +
                "     CC       Range=2-165; Evidence={ECO:0000269|PubMed:15923259};";

        UniProtEntry entry = CommentTestUtil.createUniProtEntryFromCommentLine(massSpectrometryLine);

        List<MassSpectrometryComment> massSpectrometryComments = entry.getCommentByType(CommentType.MASS_SPECTROMETRY);
        assertNotNull(entry);
        MassSpectrometryMap massSpectrometryMap = new MassSpectrometryMap(massSpectrometryComments);
        Map<String,String> mappedMassSpectrometry = massSpectrometryMap.attributeValues();
        assertNotNull(mappedMassSpectrometry);
        String value = mappedMassSpectrometry.get("cc:mass_spectrometry");
        String expectedValue = "MASS SPECTROMETRY: Mass=17581.1; Method=MALDI; Range=2-165; " +
                "Evidence={ECO:0000269|PubMed:10094780}; MASS SPECTROMETRY: Mass=66643; Mass_error=13; " +
                "Method=Electrospray; Range=2-165; Note=Isolated L10(L12)4.; Evidence={ECO:0000269|PubMed:15923259}; " +
                "MASS SPECTROMETRY: Mass=17580; Mass_error=2; Method=Electrospray; Range=2-165; " +
                "Evidence={ECO:0000269|PubMed:15923259};";
        assertEquals(expectedValue,value);
    }
}