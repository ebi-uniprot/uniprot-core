package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class CommentTransformerHelperTest {

    public void testSemiComma() {
        String value =
                "A chromosomal aberration involving BCL2 has been found in chronic lymphatic leukemia."
                        + " Translocation t(14;18)(q32;q21) with immunoglobulin gene regions. BCL2 mutations"
                        + " found in non-Hodgkin lymphomas carrying the chromosomal translocation could be"
                        + " attributed to the Ig somatic hypermutation mechanism resulting in nucleotide"
                        + " transitions.";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(value, true);
        for (EvidencedValue ev : evValues) {
            System.out.println(ev.getValue());
        }
    }

    @Test
    void testParseEvidencedValues1() {
        String value =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min."
                        + " {ECO:0000269|PubMed:10433555}. The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000269|PubMed:10433554, ECO:0000269|PubMed:104335};";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(value, true);
        String note1 =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min";
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min";
        assertEquals(2, evValues.size());
        List<String> evs = new ArrayList<>(Arrays.asList("ECO:0000269|PubMed:10433555"));
        verify(evValues.get(0), note1, evs);
        evs =
                new ArrayList<>(
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335"));
        verify(evValues.get(1), note2, evs);
    }

    @Test
    void testParseEvidencedValues2() {
        String value =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min."
                        + " {ECO:0000269|PubMed:10433555}. The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000269|PubMed:10433554, ECO:0000269|PubMed:104335};";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(value, false);
        String note1 =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min.";
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        assertEquals(2, evValues.size());
        List<String> evs = new ArrayList<>(Arrays.asList("ECO:0000269|PubMed:10433555"));
        verify(evValues.get(0), note1, evs);
        evs =
                new ArrayList<>(
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335"));
        verify(evValues.get(1), note2, evs);
    }

    @Test
    void testParseEvidencedValuesNoEv1() {
        String value =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min.."
                        + " The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min.;";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(value, true);
        String note1 =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min";
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min";
        assertEquals(2, evValues.size());
        List<String> evs = new ArrayList<>();
        verify(evValues.get(0), note1, evs);
        evs = new ArrayList<>();
        verify(evValues.get(1), note2, evs);
    }

    @Test
    void testParseEvidencedValuesNoEv2() {
        String value =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min.."
                        + " The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min.;";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(value, false);
        String note1 =
                "Remains fully active after heating at 50 degrees Celsius and pH 4.0 for 10 min."
                        + " Retains 65% of its activity after heating at 55 degrees Celsius for 10 min.";
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        assertEquals(2, evValues.size());
        List<String> evs = new ArrayList<>();
        verify(evValues.get(0), note1, evs);
        evs = new ArrayList<>();
        verify(evValues.get(1), note2, evs);
    }

    @Test
    void testParseEvidenceValue1() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000269|PubMed:10433554, ECO:0000269|PubMed:104335};";
        String str =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        EvidencedValue evValue = CommentTransformerHelper.parseEvidencedValue(value, false);
        List<String> evs =
                new ArrayList<>(
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335"));
        verify(evValue, str, evs);
    }

    @Test
    void testParseEvidenceValue2() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000269|PubMed:10433554, ECO:0000269|PubMed:104335};";
        String str =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min";
        EvidencedValue evValue = CommentTransformerHelper.parseEvidencedValue(value, true);
        List<String> evs =
                new ArrayList<>(
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335"));
        verify(evValue, str, evs);
    }

    @Test
    void testParseEvidenceValueNotEv1() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min.;";
        String str =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        EvidencedValue evValue = CommentTransformerHelper.parseEvidencedValue(value, false);
        List<String> evs = new ArrayList<>();
        verify(evValue, str, evs);
    }

    @Test
    void testParseEvidenceValueNoEv2() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min.;";
        String str =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min";
        EvidencedValue evValue = CommentTransformerHelper.parseEvidencedValue(value, true);
        List<String> evs = new ArrayList<>();
        verify(evValue, str, evs);
    }

    @Test
    void testStripEvidence() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min. {ECO:0000269|PubMed:10433554, ECO:0000269|PubMed:104335};";
        List<Evidence> evIds = new ArrayList<>();
        String annot = CommentTransformerHelper.stripEvidences(value, evIds);
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        assertEquals(annot, note2);
        List<String> evs =
                new ArrayList<>(
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335"));
        verify(evIds, evs);
    }

    @Test
    void testStripEvidence2() {
        String value =
                "The half-life value for loss of activity at 60"
                        + " degrees Celsius and pH 4.0 is 3.5 min.";
        List<Evidence> evIds = new ArrayList<>();
        String annot = CommentTransformerHelper.stripEvidences(value, evIds);
        String note2 =
                "The half-life value for loss of activity at 60 degrees Celsius and pH 4.0 is 3.5 min.";
        assertEquals(annot, note2);
        assertEquals(0, evIds.size());
    }

    @Test
    void testParseNoteString() {
        String note =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
                        + " the reduced form.. These absorption peaks are for the tryptophylquinone cofactor.;";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(note, false);
        assertEquals(2, evValues.size());
        String val1 =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the reduced form.";
        assertEquals(val1, evValues.get(0).getValue());
        assertEquals(0, evValues.get(0).getEvidences().size());
        String val2 = "These absorption peaks are for the tryptophylquinone cofactor.";
        assertEquals(val2, evValues.get(1).getValue());
        assertEquals(0, evValues.get(1).getEvidences().size());
    }

    @Test
    void testParseNoteString2() {
        String note =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in"
                        + " the reduced form. {ECO:0000269|PubMed:10433554}. "
                        + "These absorption peaks are for the tryptophylquinone cofactor. "
                        + "{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};";
        List<EvidencedValue> evValues = CommentTransformerHelper.parseEvidencedValues(note, false);
        assertEquals(2, evValues.size());
        String val1 =
                "The above maximum is for the oxidized form. Shows a maximal peak at 330 nm in the reduced form.";
        assertEquals(val1, evValues.get(0).getValue());
        assertEquals(1, evValues.get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554", evValues.get(0).getEvidences().get(0).getValue());
        String val2 = "These absorption peaks are for the tryptophylquinone cofactor.";
        assertEquals(val2, evValues.get(1).getValue());
        assertEquals(2, evValues.get(1).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554", evValues.get(1).getEvidences().get(0).getValue());
        assertEquals("ECO:0000303|Ref.6", evValues.get(1).getEvidences().get(1).getValue());
    }

    private void verify(EvidencedValue evValue, String val, List<String> evS) {
        assertEquals(val, evValue.getValue());
        verify(evValue.getEvidences(), evS);
    }

    private void verify(List<Evidence> evIds, List<String> evs) {
        List<Evidence> newEvIds = new ArrayList<>();
        for (String ev : evs) {
            newEvIds.add(parseEvidenceLine(ev));
        }
        assertEquals(newEvIds, evIds);
    }
}
