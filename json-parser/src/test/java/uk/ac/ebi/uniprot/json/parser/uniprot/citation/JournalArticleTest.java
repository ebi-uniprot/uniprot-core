package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class JournalArticleTest {

    @Test
    public void testJournalArticleSimple() {
        Citation citation = new JournalArticleBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("journal article", jsonNode.get("citationType").asText());
    }

    @Test
    public void testJournalArticleComplete() {
        Citation citation = getJournalArticle();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("journal article", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("journal"));
        assertEquals("journal name", jsonNode.get("journal").asText());

        assertNotNull(jsonNode.get("firstPage"));
        assertEquals("first page", jsonNode.get("firstPage").asText());

        assertNotNull(jsonNode.get("lastPage"));
        assertEquals("last page", jsonNode.get("lastPage").asText());

        assertNotNull(jsonNode.get("volume"));
        assertEquals("volume value", jsonNode.get("volume").asText());
    }

    public static JournalArticle getJournalArticle(){
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("somepID1").build();
        return new JournalArticleBuilder()
                .journalName("journal name")
                .firstPage("first page")
                .lastPage("last page")
                .volume("volume value")
                .publicationDate("date value")
                .addAuthorGroup("auth group")
                .addAuthor("author Leo")
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(xref))
                .build();
    }
}
