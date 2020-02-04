package org.uniprot.core.json.parser.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.builder.ThesisBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class ThesisTest {

    @Test
    void testThesisSimple() {
        Citation citation = new ThesisBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());
    }

    @Test
    void testThesisComplete() {
        Citation citation = getThesis();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("thesis", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("institute"));
        assertEquals("thesis institute", jsonNode.get("institute").asText());

        assertNotNull(jsonNode.get("address"));
        assertEquals("thesis address", jsonNode.get("address").asText());
    }

    public static Thesis getThesis() {
        DBCrossReference<CitationXrefType> xref =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("somepID1")
                        .build();
        return new ThesisBuilder()
                .address("thesis address")
                .institute("thesis institute")
                .publicationDate("date value")
                .authorGroupsAdd("auth group")
                .authorsAdd("author Leo")
                .title("Leo book tittle")
                .citationXrefsSet(Collections.singletonList(xref))
                .build();
    }
}
