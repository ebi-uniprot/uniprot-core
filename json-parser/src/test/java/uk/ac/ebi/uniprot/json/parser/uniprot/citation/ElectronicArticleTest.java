package uk.ac.ebi.uniprot.json.parser.uniprot.citation;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.ElectronicArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.ElectronicArticleBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class ElectronicArticleTest {

    @Test
    public void testElectronicArticleSimple() {
        Citation citation = ElectronicArticleBuilder.newInstance().build();
        ValidateJson.verifyJsonRoundTripParser(citation);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(citation);
        assertNotNull(jsonNode.get("citationType"));
        assertEquals("online journal article", jsonNode.get("citationType").asText());
    }

    @Test
    public void testElectronicArticleComplete() {
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
        return ElectronicArticleBuilder.newInstance()
                .journalName("journal name")
                .locator("locator value")
                .publicationDate(AbstractCitationBuilder.createPublicationDate("date value"))
                .authoringGroups(Collections.singletonList("auth group"))
                .authors(Collections.singletonList(AbstractCitationBuilder.createAuthor("author Leo")))
                .title("Leo book tittle")
                .citationXrefs(Collections.singletonList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "somepID1")))
                .build();
    }
}
