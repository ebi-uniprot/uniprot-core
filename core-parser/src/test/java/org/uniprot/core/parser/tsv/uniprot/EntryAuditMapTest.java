package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.EntryAudit;
import org.uniprot.core.uniprotkb.impl.EntryAuditBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class EntryAuditMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryAuditMap.FIELDS;
        List<String> expected =
                Arrays.asList(
                        "sequence_version",
                        "date_sequence_modified",
                        "version",
                        "date_created",
                        "date_modified");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataWithEmptyAudit() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        EntryAuditMap dl = new EntryAuditMap(entryAudit);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("sequence_version", "0", result);
        verify("date_sequence_modified", "", result);
        verify("version", "0", result);
        verify("date_created", "", result);
        verify("date_modified", "", result);
    }

    private void verify(String field, String expected, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    @Test
    void testGetDataWithEntryAudit() {
        EntryAudit entryAudit =
                new EntryAuditBuilder()
                        .entryVersion(10)
                        .firstPublic(LocalDate.of(2000, 1, 25))
                        .lastAnnotationUpdate(LocalDate.of(2019, 1, 25))
                        .sequenceVersion(5)
                        .lastSequenceUpdate(LocalDate.of(2018, 1, 25))
                        .build();
        EntryAuditMap dl = new EntryAuditMap(entryAudit);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("sequence_version", "5", result);
        verify("date_sequence_modified", "2018-01-25", result);
        verify("version", "10", result);
        verify("date_created", "2000-01-25", result);
        verify("date_modified", "2019-01-25", result);
    }
}
