package org.uniprot.core.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.ElectronicArticle;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class ElectronicArticleTest {

    @Test
    void testElectronicArticleSimple() {
        Citation citation = new ElectronicArticleBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("online journal article", jsonNode.get("citationType").asText());
    }

    @Test
    void testElectronicArticleComplete() {
        Citation citation = getElectronicArticle();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("online journal article", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("journal"));
        assertEquals("journal name", jsonNode.get("journal").asText());

        assertNotNull(jsonNode.get("locator"));
        assertEquals("locator value", jsonNode.get("locator").asText());
    }

    public static ElectronicArticle getElectronicArticle(){
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new ElectronicArticleBuilder()
                .journalName("journal name")
                .locator("locator value")
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
}
