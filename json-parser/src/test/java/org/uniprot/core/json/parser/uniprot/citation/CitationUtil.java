package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.impl.AbstractCitationBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
class CitationUtil {

    static void validateCitation(JsonNode jsonNode) {
        assertNotNull(jsonNode.get("publicationDate"));
        assertEquals("date value", jsonNode.get("publicationDate").asText());

        assertNotNull(jsonNode.get("authoringGroup"));
        assertEquals(1, jsonNode.get("authoringGroup").size());
        assertEquals("auth group", jsonNode.get("authoringGroup").get(0).asText());

        assertNotNull(jsonNode.get("authors"));
        assertEquals(1, jsonNode.get("authors").size());
        assertEquals("author Leo", jsonNode.get("authors").get(0).asText());

        assertNotNull(jsonNode.get("title"));
        assertEquals("Leo book tittle", jsonNode.get("title").asText());

        assertNotNull(jsonNode.get("citationCrossReferences"));
        assertEquals(2, jsonNode.get("citationCrossReferences").size());
        JsonNode citationCrossReferences = jsonNode.get("citationCrossReferences").get(0);

        assertNotNull(citationCrossReferences.get("database"));
        assertEquals("PubMed", citationCrossReferences.get("database").asText());

        assertNotNull(citationCrossReferences.get("id"));
        assertEquals("12345", citationCrossReferences.get("id").asText());
    }

    public static void populateBasicCitation(
            AbstractCitationBuilder<? extends AbstractCitationBuilder, ? extends Citation>
                    builder) {
        builder.publicationDate("date value");
        builder.authoringGroupsAdd("auth group");
        builder.authorsAdd("author Leo");
        builder.title("Leo book tittle");
        builder.citationCrossReferencesSet(createCitationCrossRefs());
    }

    public static List<CrossReference<CitationDatabase>> createCitationCrossRefs() {
        List<CrossReference<CitationDatabase>> result = new ArrayList<>();

        result.add(
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("12345")
                        .build());

        result.add(
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.DOI)
                        .id("doiId")
                        .build());

        return result;
    }
}
