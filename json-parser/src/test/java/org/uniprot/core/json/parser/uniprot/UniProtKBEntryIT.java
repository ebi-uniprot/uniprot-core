package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.taxonomy.TaxonomyLineageTest;
import org.uniprot.core.json.parser.uniprot.comment.*;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.impl.EntryInactiveReasonBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/** @author lgonzales */
class UniProtKBEntryIT {

    private static Logger logger = LoggerFactory.getLogger(UniProtKBEntryIT.class);

    @Test
    void testInactiveUniProtEntryComplete() {
        EntryInactiveReason inactiveReason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.MERGED)
                        .mergeDemergeTosAdd("merge id")
                        .build();

        UniProtKBId uniProtkbId = new UniProtKBIdBuilder("uniprot id").build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("accession value").build();

        UniProtKBEntry inactiveEntry =
                new UniProtKBEntryBuilder(accession, uniProtkbId, inactiveReason).build();
        ValidateJson.verifyJsonRoundTripParser(inactiveEntry);
        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(inactiveEntry);
        assertNotNull(jsonNode.get("primaryAccession"));
        assertEquals("accession value", jsonNode.get("primaryAccession").asText());
    }

    @Test
    void testUniProtEntryComplete() {
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

        UniProtKBId uniProtkbId = new UniProtKBIdBuilder("uniprot id").build();
        UniProtKBEntryBuilder builder =
                new UniProtKBEntryBuilder(
                        UniProtKBAccessionTest.getUniProtAccession(),
                        uniProtkbId,
                        UniProtKBEntryType.SWISSPROT);
        UniProtKBEntry entry =
                builder.secondaryAccessionsAdd(UniProtKBAccessionTest.getUniProtAccession())
                        .entryAudit(EntryAuditTest.getEntryAudit())
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .proteinDescription(ProteinDescriptionTest.getProteinDescription())
                        .genesSet(Collections.singletonList(GeneTest.createCompleteGene()))
                        .annotationScore(2)
                        .organism(OrganimsTest.getOrganism())
                        .organismHostsSet(
                                Collections.singletonList(OrganimHostTest.getOrganismHost()))
                        .commentsSet(comments)
                        .featuresSet(Collections.singletonList(FeatureTest.getFeature()))
                        .internalSection(InternalSectionTest.getInternalSection())
                        .keywordsSet(Collections.singletonList(KeywordTest.getKeyword()))
                        .geneLocationsSet(
                                Collections.singletonList(GeneLocationTest.getGeneLocation()))
                        .referencesSet(UniProtKBReferenceTest.getUniProtReferences())
                        .uniProtCrossReferencesSet(
                                Collections.singletonList(
                                        UniProtKBCrossReferenceTest.getUniProtDBCrossReference()))
                        .sequence(SequenceTest.getSequence())
                        .lineagesSet(
                                Collections.singletonList(
                                        TaxonomyLineageTest.getCompleteTaxonomyLineage()))
                        .build();

        ValidateJson.verifyJsonRoundTripParser(entry);
        ValidateJson.verifyEmptyFields(entry);

        try {
            ObjectMapper mapper = UniprotKBJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
