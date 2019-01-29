package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.builder.EntryInactiveReasonBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtAccessionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtIdBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
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
        EntryInactiveReason inactiveReason = new EntryInactiveReasonBuilder()
                .type(InactiveReasonType.MERGED)
                .addMergeDemergeTo("merge id")
                .build();

        UniProtId uniProtId = new UniProtIdBuilder("uniprot id").build();
        UniProtAccession accession = new UniProtAccessionBuilder("accession value").build();

        UniProtEntry inactiveEntry = new UniProtEntryBuilder()
                .primaryAccession(accession)
                .uniProtId(uniProtId)
                .inactive()
                .inactiveReason(inactiveReason)
                .build();
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

        UniProtId uniProtId = new UniProtIdBuilder("uniprot id").build();
        UniProtEntryBuilder builder = new UniProtEntryBuilder();
        UniProtEntry entry = builder.primaryAccession(UniProtAccessionTest.getUniProtAccession())
                .uniProtId(uniProtId)
                .active()
                .entryType(UniProtEntryType.SWISSPROT)
                .addSecondaryAccession(UniProtAccessionTest.getUniProtAccession())
                .entryAudit(EntryAuditTest.getEntryAudit())
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .proteinDescription(ProteinDescriptionTest.getProteinDescription())
                .genes(Collections.singletonList(GeneTest.createCompleteGene()))
                .organism(OrganimsTest.getOrganism())
                .organismHosts(Collections.singletonList(OrganimHostTest.getOrganismHost()))
                .comments(comments)
                .features(Collections.singletonList(FeatureTest.getFeature()))
                .internalSection(InternalSectionTest.getInternalSection())
                .keywords(Collections.singletonList(KeywordTest.getKeyword()))
                .geneLocations(Collections.singletonList(GeneLocationTest.getGeneLocation()))
                .references(UniProtReferenceTest.getUniProtReferences())
                .databaseCrossReferences(Collections.singletonList(UniProtDBCrossReferenceTest.getUniProtDBCrossReference()))
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
