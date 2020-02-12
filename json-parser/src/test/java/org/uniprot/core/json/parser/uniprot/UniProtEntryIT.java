package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.bohnman.squiggly.context.provider.SimpleSquigglyContextProvider;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import com.github.bohnman.squiggly.parser.SquigglyParser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.literature.LiteratureJsonConfig;
import org.uniprot.core.json.parser.taxonomy.TaxonomyLineageTest;
import org.uniprot.core.json.parser.uniprot.comment.*;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.builder.EntryInactiveReasonBuilder;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;
import org.uniprot.core.uniprot.comment.Comment;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.uniprot.core.util.EnumDisplay;

/** @author lgonzales */
class UniProtEntryIT {

    private static Logger logger = LoggerFactory.getLogger(UniProtEntryIT.class);

    @Test
    void testInactiveUniProtEntryComplete() {
        EntryInactiveReason inactiveReason =
                new EntryInactiveReasonBuilder()
                        .type(InactiveReasonType.MERGED)
                        .addMergeDemergeTo("merge id")
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
        return builder.secondaryAccessionAdd(UniProtAccessionTest.getUniProtAccession())
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
                .referencesSet(UniProtReferenceTest.getUniProtReferences())
                .databaseCrossReferencesSet(
                        Collections.singletonList(
                                UniProtDBCrossReferenceTest.getUniProtDBCrossReference()))
                .sequence(SequenceTest.getSequence())
                .lineagesSet(
                        Collections.singletonList(
                                TaxonomyLineageTest.getCompleteTaxonomyLineage()))
                .build();
    }


    @Test
    void testFilterFieldsInLiteratureEntry() throws JsonProcessingException {
        UniProtEntry entry = getCompleteUniProtEntry();
        ObjectMapper mapper = UniprotJsonConfig.getInstance().getSimpleObjectMapper();

        //System.out.println(mapper.writeValueAsString(literatureEntry));

/*                PropertyFilter filter =
                SimpleBeanPropertyFilter.filterOutAllExcept(
                        "citation.title",
                        "statistics.mappedProteinCount");
        CustomFilterProvider filterProvider =
                new CustomFilterProvider().addFilter(JsonConfig.FILTER_ID,filter);*/

/*        SquigglyPropertyFilter propertyFilter =
                new SquigglyPropertyFilter(
                        new SimpleSquigglyContextProvider(
                                new SquigglyParser(),
                                "citation.authors,citation.literatureAbstract,statistics")); */
        Map<String,String> filters = new HashMap<>();
        filters.put("comments","commentType:ALTERNATIVE PRODUCTS");
        SimpleBeanPropertyFilter valueFilter = new SquigglyValuePropertyFilter(filters);
        SimpleFilterProvider filterProvider =
                new SimpleFilterProvider()
                        .addFilter(SquigglyPropertyFilter.FILTER_ID, valueFilter);

        mapper.setFilterProvider(filterProvider);

        System.out.println(mapper.writeValueAsString(entry));
        // System.out.println(SquigglyUtils.stringify(mapper, literatureEntry));
    }


    public static class SquigglyValuePropertyFilter extends SquigglyPropertyFilter implements Serializable {

        private static final long serialVersionUID = 9076392714197514092L;
        /**
         * Set of property names to serialize.
         */
        private final Map<String,String> fieldFilters;

        public SquigglyValuePropertyFilter(Map<String,String> fieldFilters) {
            super(new SimpleSquigglyContextProvider(new SquigglyParser(),""));
            this.fieldFilters = fieldFilters;
        }


        @Override
        protected boolean include(final PropertyWriter writer, final JsonGenerator jgen) {
            boolean result = true;
            JsonStreamContext streamContext = jgen.getOutputContext();
            String path = getPath(streamContext);
            if(fieldFilters.containsKey(path)){
                String[] filter = fieldFilters.get(path).split(":");
                String fieldName = filter[0];
                String fieldValue = filter[1];
                if(writer.getName().equalsIgnoreCase(fieldName)){
                    result = hasValidValue(writer,streamContext,fieldValue);
                }
            }
            return result;
        }

        private String getPath(JsonStreamContext sc) {
            List<String> path = new ArrayList<>();

            while (sc != null) {
                if (sc.getCurrentName() != null) {
                    path.add(sc.getCurrentName());
                }
                sc = sc.getParent();
            }
            return String.join(".", path);
        }

        private boolean hasValidValue(PropertyWriter writer, JsonStreamContext sc, String expectedValue){
            boolean result = false;
            try {
                if (writer instanceof BeanPropertyWriter){
                    BeanPropertyWriter beanWriter = (BeanPropertyWriter) writer;
                    if(writer.getType().isTypeOrSuperTypeOf(EnumDisplay.class)) {
                        EnumDisplay value = (EnumDisplay) beanWriter.get(sc.getCurrentValue());
                        result = expectedValue.equalsIgnoreCase(value.toDisplayName());
                    } else {
                        Object value = beanWriter.get(sc.getCurrentValue());
                        result = expectedValue.equalsIgnoreCase(value.toString());
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

    }
}
