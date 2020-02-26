package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.taxonomy.TaxonomyLineageTest;
import org.uniprot.core.json.parser.uniprot.comment.*;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.builder.EntryInactiveReasonBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.impl.UniProtEntryImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.bohnman.squiggly.context.provider.SimpleSquigglyContextProvider;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import com.github.bohnman.squiggly.parser.SquigglyParser;

/** @author lgonzales */
class UniProtEntryIT {

    private static Logger logger = LoggerFactory.getLogger(UniProtEntryIT.class);

    @Test
    void testInactiveUniProtEntryComplete() {
        EntryInactiveReason inactiveReason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.MERGED)
                        .mergeDemergeTosAdd("merge id")
                        .build();

        UniProtId uniProtId = new UniProtIdBuilder("uniprot id").build();
        UniProtAccession accession = new UniProtAccessionBuilder("accession value").build();

        UniProtEntry inactiveEntry =
                new UniProtEntryBuilder(accession, uniProtId, inactiveReason).build();
        ValidateJson.verifyJsonRoundTripParser(inactiveEntry);
        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(inactiveEntry);
        assertNotNull(jsonNode.get("primaryAccession"));
        assertEquals("accession value", jsonNode.get("primaryAccession").asText());
    }

    @Test
    void testUniProtEntryComplete() {

        UniProtEntry entry = getCompleteUniProtEntry();
        ValidateJson.verifyJsonRoundTripParser(entry);
        ValidateJson.verifyEmptyFields(entry);

        try {
            ObjectMapper mapper = UniprotJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testFilterFieldsInEntry() throws Exception {
        UniProtEntry entry = getCompleteUniProtEntry();
        ObjectMapper mapper = UniprotJsonConfig.getInstance().getFullObjectMapper().copy();
        String fieldsPath =
                "primaryAccession,proteinDescription.cdAntigenNames,proteinDescription.recommendedName";
        SquigglyPropertyFilter filter =
                new SquigglyPropertyFilter(
                        new SimpleSquigglyContextProvider(new SquigglyParser(), fieldsPath));
        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider().addFilter(SquigglyPropertyFilter.FILTER_ID, filter);
        mapper.setFilterProvider(filterProvider);
        String pathJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entry);
        System.out.println("Filtered Fields Only: \n" + pathJson);
        UniProtEntry converted = mapper.readValue(pathJson, UniProtEntryImpl.class);

        // returned requested ones
        assertNotNull(converted.getPrimaryAccession());
        assertNotNull(converted.getProteinDescription());
        assertNotNull(converted.getProteinDescription().getCdAntigenNames());
        assertNotNull(converted.getProteinDescription().getRecommendedName());
        assertFalse(converted.getProteinDescription().getCdAntigenNames().isEmpty());

        // not requested fields are null or empty
        assertNull(converted.getProteinDescription().getAllergenName());
        assertNull(converted.getEntryType());
        assertNull(converted.getOrganism());
        assertTrue(converted.getComments().isEmpty());
    }

    private UniProtEntry getCompleteUniProtEntry() {
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
        UniProtEntryBuilder builder =
                new UniProtEntryBuilder(
                        UniProtAccessionTest.getUniProtAccession(),
                        uniProtId,
                        UniProtEntryType.SWISSPROT);
        return builder.secondaryAccessionsAdd(UniProtAccessionTest.getUniProtAccession())
                .entryAudit(EntryAuditTest.getEntryAudit())
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .proteinDescription(ProteinDescriptionTest.getProteinDescription())
                .genesAdd(GeneTest.createCompleteGene())
                .annotationScore(2)
                .organism(OrganimsTest.getOrganism())
                .organismHostsSet(Collections.singletonList(OrganimHostTest.getOrganismHost()))
                .commentsSet(comments)
                .featuresAdd(FeatureTest.getFeature())
                .internalSection(InternalSectionTest.getInternalSection())
                .keywordsSet(Collections.singletonList(KeywordTest.getKeyword()))
                .geneLocationsSet(Collections.singletonList(GeneLocationTest.getGeneLocation()))
                .referencesSet(UniProtReferenceTest.getUniProtReferences())
                .databaseCrossReferencesSet(
                        Collections.singletonList(
                                UniProtDBCrossReferenceTest.getUniProtDBCrossReference()))
                .sequence(SequenceTest.getSequence())
                .lineagesSet(
                        Collections.singletonList(TaxonomyLineageTest.getCompleteTaxonomyLineage()))
                .build();
    }
}
