package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.impl.ElectronicArticleBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.impl.UniProtKBReferenceBuilder;

class EntryReferenceMapTest {

    private static final String FIELD_SEPARATOR = "; ";

    @Test
    void testFields() {
        List<String> fields = EntryReferenceMap.FIELDS;
        List<String> expected = Arrays.asList("lit_pubmed_id", "lit_doi_id");
        assertEquals(expected, fields);
    }

    @Test
    void testLitDoiId() {
        String doiId = "doiId1";
        List<UniProtKBReference> references = getUniProtKBReferences(CitationDatabase.DOI, doiId);
        EntryReferenceMap mapper = new EntryReferenceMap(references);
        Map<String, String> result = mapper.attributeValues();

        verifyFieldValue(result, EntryReferenceMap.FIELDS.get(1), doiId);
    }

    @Test
    void testMultipleLitDoiId() {
        String doiId = "doiId1";
        String doiId2 = "doiId2";
        List<UniProtKBReference> references =
                getUniProtKBReferences(CitationDatabase.DOI, doiId, doiId2);
        EntryReferenceMap mapper = new EntryReferenceMap(references);
        Map<String, String> result = mapper.attributeValues();

        verifyFieldValue(result, EntryReferenceMap.FIELDS.get(1), doiId + FIELD_SEPARATOR + doiId2);
    }

    @Test
    void testLitPubmedId() {
        String pubmedId = "pubmedId1";
        List<UniProtKBReference> references =
                getUniProtKBReferences(CitationDatabase.PUBMED, pubmedId);
        EntryReferenceMap mapper = new EntryReferenceMap(references);
        Map<String, String> result = mapper.attributeValues();

        verifyFieldValue(result, EntryReferenceMap.FIELDS.get(0), pubmedId);
    }

    @Test
    void testMultipleLitPubmedId() {
        String pubmedId = "pubmedId1";
        String pubmedId2 = "pubmedId2";
        List<UniProtKBReference> references =
                getUniProtKBReferences(CitationDatabase.PUBMED, pubmedId, pubmedId2);

        EntryReferenceMap mapper = new EntryReferenceMap(references);
        Map<String, String> result = mapper.attributeValues();

        verifyFieldValue(
                result, EntryReferenceMap.FIELDS.get(0), pubmedId + FIELD_SEPARATOR + pubmedId2);
    }

    @Test
    void testMixedIds() {
        String pubmedId = "pubmedId1";
        String pubmedId2 = "pubmedId2";
        String doiId = "doiId1";
        String doiId2 = "doiId2";
        List<UniProtKBReference> references =
                getUniProtKBReferences(CitationDatabase.PUBMED, pubmedId, pubmedId2);
        references.addAll(getUniProtKBReferences(CitationDatabase.DOI, doiId, doiId2));

        EntryReferenceMap mapper = new EntryReferenceMap(references);
        Map<String, String> result = mapper.attributeValues();

        verifyFieldValue(
                result, EntryReferenceMap.FIELDS.get(0), pubmedId + FIELD_SEPARATOR + pubmedId2);
        verifyFieldValue(result, EntryReferenceMap.FIELDS.get(1), doiId + FIELD_SEPARATOR + doiId2);
    }

    private List<UniProtKBReference> getUniProtKBReferences(
            CitationDatabase database, String... ids) {
        List<UniProtKBReference> result = new ArrayList<>();
        for (String id : ids) {
            Citation citation =
                    new ElectronicArticleBuilder()
                            .citationCrossReferencesAdd(
                                    new CrossReferenceBuilder<CitationDatabase>()
                                            .database(database)
                                            .id(id)
                                            .build())
                            .build();
            UniProtKBReference reference =
                    new UniProtKBReferenceBuilder().citation(citation).build();
            result.add(reference);
        }
        return result;
    }

    private void verifyFieldValue(
            Map<String, String> result, String fieldName, String expectedValue) {
        String resultValue = result.get(fieldName);
        assertNotNull(resultValue);
        assertEquals(expectedValue, resultValue);
    }
}
