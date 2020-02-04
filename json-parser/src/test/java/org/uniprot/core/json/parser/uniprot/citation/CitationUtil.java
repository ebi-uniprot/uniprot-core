package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.builder.AbstractCitationBuilder;

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

        assertNotNull(jsonNode.get("citationXrefs"));
        assertEquals(2, jsonNode.get("citationXrefs").size());
        JsonNode citationXrefs = jsonNode.get("citationXrefs").get(0);

        assertNotNull(citationXrefs.get("databaseType"));
        assertEquals("PubMed", citationXrefs.get("databaseType").asText());

        assertNotNull(citationXrefs.get("id"));
        assertEquals("12345", citationXrefs.get("id").asText());
    }

    public static void populateBasicCitation(
            AbstractCitationBuilder<? extends AbstractCitationBuilder, ? extends Citation>
                    builder) {
        builder.publicationDate("date value");
        builder.addAuthorGroup("auth group");
        builder.addAuthor("author Leo");
        builder.title("Leo book tittle");
        builder.citationXrefs(createCitationCrossRefs());
    }

    public static List<DBCrossReference<CitationXrefType>> createCitationCrossRefs() {
        List<DBCrossReference<CitationXrefType>> result = new ArrayList<>();

        result.add(
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("12345")
                        .build());

        result.add(
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.DOI)
                        .id("doiId")
                        .build());

        return result;
    }
}
