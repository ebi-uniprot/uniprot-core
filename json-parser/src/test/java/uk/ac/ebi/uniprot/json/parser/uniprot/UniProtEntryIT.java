package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;
import uk.ac.ebi.uniprot.json.parser.uniprot.comment.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author lgonzales
 */
public class UniProtEntryIT {

    private static Logger logger = LoggerFactory.getLogger(UniProtEntryIT.class);

    @Test
    public void testInactiveUniProtEntryComplete() {
        List<String> mergeDemergeTo = Collections.singletonList("merge id");
        EntryInactiveReason inactiveReason = UniProtFactory.INSTANCE.createInactiveReason(InactiveReasonType.MERGED,mergeDemergeTo);
        UniProtId uniProtId = UniProtFactory.INSTANCE.createUniProtId("uniprot id");
        UniProtAccession accession = UniProtFactory.INSTANCE.createUniProtAccession("accession value");

        UniProtEntry inactiveEntry = UniProtFactory.INSTANCE.createInactiveEntry(accession, uniProtId, inactiveReason);
        ValidateJson.verifyJsonRoundTripParser(inactiveEntry);
        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(inactiveEntry);
        assertNotNull(jsonNode.get("primaryAccession"));
        assertEquals("accession value",jsonNode.get("primaryAccession").asText());
    }

    @Test
    public void testUniProtEntryComplete() {
        List<Comment> comments = new ArrayList<>();
        comments.add(AlternativeProductsCommentTest.getAlternativeProductsComment());
        comments.add(BPCPCommentTest.getBpcpComment());
        comments.add(CatalyticActivityCommentTest.getCatalyticActivityComment());
        comments.add(CofactorCommentTest.getCofactorComment());
        comments.add(DiseaseCommentTest.getDiseaseComment());
        comments.add(FreeTextCommentTest.getFreeTextComment());
        comments.add(InteractionCommentTest.getInteractionComment());
        comments.add(MassSpectrometryCommentTest.getMassSpectrometryComment());
        comments.add(RnaEditingCommentTest.getRnaEditingComment());
        comments.add(SequenceCautionCommentTest.getSequenceCautionComment());
        comments.add(SubcellularLocationCommentTest.getSubcellularLocationComment());
        comments.add(WebResourceCommentTest.getWebResourceComment());

        UniProtEntryBuilder builder = UniProtEntryBuilder.newInstance();
        UniProtEntry entry = builder.primaryAccession(UniProtAccessionTest.getUniProtAccession())
                .secondaryAccessions(Collections.singletonList(UniProtFactory.INSTANCE.createUniProtAccession("P12345")))
                .entryAudit(EntryAuditTest.getEntryAudit())
                .uniProtId("UniProt ID")
                .entryType(UniProtEntryType.SWISSPROT)
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .proteinDescription(ProteinDescriptionTest.getProteinDescription())
                .genes(Collections.singletonList(GeneTest.createCompleteGene()))
                .organism(OrganimsTest.getOrganism())
                .organismHosts(Collections.singletonList(OrganimHostTest.getOrganismHost()))
                .comments(comments)
                .features(Collections.singletonList(FeatureTest.getFeature()))
                .internalSection(InternalSectionTest.getInternalSection())
                .keywords(Collections.singletonList(KeywordTest.getKeyword()))
                .organelles(Collections.singletonList(OrganelleTest.getOrganelle()))
                .references(UniProtReferenceTest.getUniProtReferences())
                .uniProtDBCrossReferences(Collections.singletonList(UniProtDBCrossReferenceTest.getUniProtDBCrossReference()))
                .sequence(SequenceTest.getSequence())
                .build();

        ValidateJson.verifyJsonRoundTripParser(entry);
        ValidateJson.verifyEmptyFields(entry);

        try {
            ObjectMapper mapper = UniprotJsonConfig.getInstance().getPrettyObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        }catch(Exception e) {
            fail(e.getMessage());
        }
    }

}
