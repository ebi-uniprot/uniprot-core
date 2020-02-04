package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Literature;
import org.uniprot.core.citation.builder.LiteratureBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author lgonzales
 * @since 2020-01-29
 */
public class LiteratureTest {

    @Test
    void testLiteratureSimple() {
        Citation citation = new LiteratureBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("UniProt indexed literatures", jsonNode.get("citationType").asText());
    }

    @Test
    void testLiteratureComplete() {
        Citation citation = getCompleteLiterature();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("UniProt indexed literatures", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("journal"));
        assertEquals("journal name", jsonNode.get("journal").asText());

        assertNotNull(jsonNode.get("firstPage"));
        assertEquals("first page", jsonNode.get("firstPage").asText());

        assertNotNull(jsonNode.get("lastPage"));
        assertEquals("last page", jsonNode.get("lastPage").asText());

        assertNotNull(jsonNode.get("volume"));
        assertEquals("volume value", jsonNode.get("volume").asText());

        assertNotNull(jsonNode.get("completeAuthorList"));
        assertEquals("true", jsonNode.get("completeAuthorList").asText());

        assertNotNull(jsonNode.get("literatureAbstract"));
        assertEquals("the abstract", jsonNode.get("literatureAbstract").asText());
    }

    public static Literature getCompleteLiterature() {
        DBCrossReference<CitationXrefType> pubmed =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("12345")
                        .build();

        DBCrossReference<CitationXrefType> doi =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.DOI)
                        .id("doiId")
                        .build();
        return new LiteratureBuilder()
                .journalName("journal name")
                .firstPage("first page")
                .lastPage("last page")
                .volume("volume value")
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .completeAuthorList(true)
                .literatureAbstract("the abstract")
                .addCitationXrefs(pubmed)
                .addCitationXrefs(doi)
                .build();
    }
}
