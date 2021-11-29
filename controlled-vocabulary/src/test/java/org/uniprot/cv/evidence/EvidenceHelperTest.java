package org.uniprot.cv.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class EvidenceHelperTest {

    private List<String> evs =
            Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000269|PubMed:104335");

    @Test
    void canParseMultipleEvidences() {
        List<Evidence> evidences = EvidenceHelper.parseEvidenceLines(evs);
        assertEquals(2, evidences.size());
    }

    @Disabled // enable once TRM-27027 is fixed
    @ParameterizedTest
    @ValueSource(strings = {"abc", " ", "abc|def", "|ab"})
    void illegalEvidenceWillThrowRte(String evd) {
        assertThrows(IllegalArgumentException.class, () -> EvidenceHelper.parseEvidenceLine(evd));
    }

    @Test
    void refIsACorrectEvidence() {
        String evd = "ECO:0000269|Ref.def";
        Evidence evidence = EvidenceHelper.parseEvidenceLine(evd);
        assertEquals(evd, evidence.getValue());
    }
}
