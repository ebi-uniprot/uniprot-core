package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.taxonomy.TaxonomyLineageTest;
import org.uniprot.core.json.parser.uniprot.comment.*;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentImpl;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.impl.EntryInactiveReasonBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.cv.xdb.UniProtDatabaseTypes;

import java.util.*;
import java.util.stream.Collectors;

/** @author lgonzales */
public class UniProtKBEntryIT {

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
        UniProtKBEntry entry = getCompleteUniProtEntry();

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

    public static UniProtKBEntry getCompleteColumnsUniProtEntry() {
        List<UniProtKBCrossReference> xrefs =
                UniProtDatabaseTypes.INSTANCE.getAllDbTypes().stream()
                        .map(UniProtDatabaseDetail::getName)
                        .map(UniProtKBCrossReferenceTest::getUniProtDBCrossReference)
                        .collect(Collectors.toList());

        xrefs.add(UniProtKBCrossReferenceTest.getUniProtDBGOCrossReferences("C", "IDA"));
        xrefs.add(UniProtKBCrossReferenceTest.getUniProtDBGOCrossReferences("F", "IDA"));
        xrefs.add(UniProtKBCrossReferenceTest.getUniProtDBGOCrossReferences("P", "IDA"));

        List<Feature> features =
                Arrays.stream(FeatureType.values())
                        .map(FeatureTest::getFeature)
                        .collect(Collectors.toList());

        List<Comment> freeTextComments =
                Arrays.stream(CommentType.values())
                        .filter(FreeTextCommentImpl::isFreeTextCommentType)
                        .map(FreeTextCommentTest::getFreeTextComment)
                        .collect(Collectors.toList());

        FreeTextComment similarityFamily =
                new FreeTextCommentBuilder()
                        .commentType(CommentType.SIMILARITY)
                        .textsAdd(
                                new EvidencedValueBuilder()
                                        .value("Belongs to the NSMF family")
                                        .build())
                        .build();
        freeTextComments.add(similarityFamily);

        UniProtKBEntry completeEntry = UniProtKBEntryIT.getCompleteUniProtEntry();
        List<Comment> allComments = completeEntry.getComments();
        allComments.addAll(freeTextComments);

        return UniProtKBEntryBuilder.from(completeEntry)
                .primaryAccession("P00001")
                .uniProtCrossReferencesSet(xrefs)
                .featuresSet(features)
                .commentsSet(allComments)
                .build();
    }

    public static UniProtKBEntry getCompleteUniProtEntry() {
        List<Comment> comments = new ArrayList<>();
        comments.add(AlternativeProductsCommentTest.getAlternativeProductsComment());
        comments.add(BPCPCommentTest.getBpcpComment());
        comments.add(CatalyticActivityCommentTest.getCatalyticActivityComment());
        comments.add(CofactorCommentTest.getCofactorComment());
        comments.add(DiseaseCommentTest.getDiseaseComment());
        comments.add(FreeTextCommentTest.getFreeTextComment());
        comments.add(FreeTextCommentTest.getFreeTextComment2());
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
        return builder.secondaryAccessionsAdd(UniProtKBAccessionTest.getUniProtAccession())
                .entryAudit(EntryAuditTest.getEntryAudit())
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .proteinDescription(ProteinDescriptionTest.getProteinDescription())
                .genesAdd(GeneTest.createCompleteGene())
                .annotationScore(2)
                .organism(OrganimsTest.getOrganism())
                .organismHostsAdd(OrganimHostTest.getOrganismHost())
                .commentsSet(comments)
                .featuresAdd(FeatureTest.getFeature())
                .internalSection(InternalSectionTest.getInternalSection())
                .keywordsAdd(KeywordTest.getKeyword())
                .geneLocationsAdd(GeneLocationTest.getGeneLocation())
                .referencesSet(UniProtKBReferenceTest.getUniProtReferences())
                .uniProtCrossReferencesAdd(UniProtKBCrossReferenceTest.getUniProtDBCrossReference())
                .sequence(SequenceTest.getSequence())
                .lineagesAdd(TaxonomyLineageTest.getCompleteTaxonomyLineage())
                .build();
    }
}
