package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.citation.Book;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 * @author lgonzales
 */
public class BookTest {

    @Test
    public void testBookSimple() {

        Citation citation = BookBuilder.newInstance().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("book", jsonNode.get("citationType").asText());
    }

    @Test
    public void testBookComplete() {

        Citation citation = getBook();
        ValidateJson.verifyJsonRoundTripParser(citation);
        ValidateJson.verifyEmptyFields(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        CitationUtil.validateCitation(jsonNode);

        assertNotNull(jsonNode.get("citationType"));
        assertEquals("book", jsonNode.get("citationType").asText());

        assertNotNull(jsonNode.get("bookName"));
        assertEquals("book Name", jsonNode.get("bookName").asText());
        assertNotNull(jsonNode.get("editors"));
        assertEquals(1,jsonNode.get("editors").size());
        assertEquals("editor Leo", jsonNode.get("editors").get(0).asText());
        assertNotNull(jsonNode.get("firstPage"));
        assertEquals("first page", jsonNode.get("firstPage").asText());
        assertNotNull(jsonNode.get("lastPage"));
        assertEquals("last page", jsonNode.get("lastPage").asText());
        assertNotNull(jsonNode.get("volume"));
        assertEquals("book volume", jsonNode.get("volume").asText());
        assertNotNull(jsonNode.get("publisher"));
        assertEquals("the publisher", jsonNode.get("publisher").asText());
        assertNotNull(jsonNode.get("address"));
        assertEquals("address value", jsonNode.get("address").asText());
    }

    public static Book getBook(){
        return BookBuilder.newInstance()
                .bookName("book Name")
                .editors(Collections.singletonList(AbstractCitationBuilder.createAuthor("editor Leo")))
                .firstPage("first page")
                .lastPage("last page")
                .volume("book volume")
                .publisher("the publisher")
                .address("address value")
                .publicationDate(AbstractCitationBuilder.createPublicationDate("date value"))
                .authoringGroups(Collections.singletonList("auth group"))
                .authors(Collections.singletonList(AbstractCitationBuilder.createAuthor("author Leo")))
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1")))
                .build();
    }


}
