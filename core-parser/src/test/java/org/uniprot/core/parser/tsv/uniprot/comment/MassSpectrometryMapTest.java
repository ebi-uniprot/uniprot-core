package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;

import java.util.List;
import java.util.Map;

class MassSpectrometryMapTest {

    @Test
    void testMassSpectrometryMapping() {
        String massSpectrometryLine =
                "CC   -!- MASS SPECTROMETRY: Mass=17581.1; Method=MALDI;\n"
                        + "     CC       Evidence={ECO:0000269|PubMed:10094780};\n"
                        + "     CC   -!- MASS SPECTROMETRY: Mass=66643; Mass_error=13;"
                        + " Method=Electrospray;\n"
                        + "     CC       Note=Isolated L10(L12)4.;\n"
                        + "     CC       Evidence={ECO:0000269|PubMed:15923259};\n"
                        + "     CC   -!- MASS SPECTROMETRY: Mass=17580; Mass_error=2;"
                        + " Method=Electrospray;\n"
                        + "     CC       Evidence={ECO:0000269|PubMed:15923259};";

        UniProtKBEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(massSpectrometryLine);

        List<MassSpectrometryComment> massSpectrometryComments =
                entry.getCommentsByType(CommentType.MASS_SPECTROMETRY);
        assertNotNull(entry);
        MassSpectrometryMap massSpectrometryMap = new MassSpectrometryMap(massSpectrometryComments);
        Map<String, String> mappedMassSpectrometry = massSpectrometryMap.attributeValues();
        assertNotNull(mappedMassSpectrometry);
        String value = mappedMassSpectrometry.get("cc_mass_spectrometry");
        String expectedValue =
                "MASS SPECTROMETRY: Mass=17581.1; Method=MALDI;"
                        + " Evidence={ECO:0000269|PubMed:10094780}; MASS SPECTROMETRY: Mass=66643;"
                        + " Mass_error=13; Method=Electrospray; Note=Isolated L10(L12)4.;"
                        + " Evidence={ECO:0000269|PubMed:15923259}; MASS SPECTROMETRY: Mass=17580;"
                        + " Mass_error=2; Method=Electrospray; "
                        + "Evidence={ECO:0000269|PubMed:15923259};";
        assertEquals(expectedValue, value);
    }
}
