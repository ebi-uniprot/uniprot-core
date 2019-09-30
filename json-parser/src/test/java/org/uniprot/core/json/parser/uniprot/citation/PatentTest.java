package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.builder.PatentBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class PatentTest {

    @Test
    void testPatentSimple() {
        Citation citation = new PatentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("patent", jsonNode.get("citationType").asText());
    }

    @Test
    void testPatentComplete() {
        Citation citation = getPatent();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("patent", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("patentNumber"));
        assertEquals("patent number", jsonNode.get("patentNumber").asText());
    }

    public static Patent getPatent() {
        DBCrossReference<CitationXrefType> xref =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("somepID1")
                        .build();
        return new PatentBuilder()
                .patentNumber("patent number")
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
}
