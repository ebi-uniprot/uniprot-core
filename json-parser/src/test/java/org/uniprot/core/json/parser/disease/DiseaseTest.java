package org.uniprot.core.json.parser.disease;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.disease.impl.DiseaseEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.json.parser.ValidateJson;

class DiseaseTest {
    @Test
    void testCrossReference() {
        List<String> props = Arrays.asList("prop1", "prop2", "prop3");
        String id = "XREF-123";
        String databaseType = "SAMPLE_TYPE";
        DiseaseCrossReference cr =
                new DiseaseCrossReferenceBuilder()
                        .databaseType(databaseType)
                        .id(id)
                        .propertiesSet(props)
                        .build();
        ValidateJson.verifyJsonRoundTripParser(
                DiseaseJsonConfig.getInstance().getFullObjectMapper(), cr);
    }

    @Test
    void testKeyword() {
        String id = "Sample Keyword";
        String accession = "KW-1234";
        KeywordId keyword = new KeywordIdBuilder().name(id).id(accession).build();
        ValidateJson.verifyJsonRoundTripParser(
                DiseaseJsonConfig.getInstance().getFullObjectMapper(), keyword);
    }

    @Test
    void testDisease() {
        String id = "Sample DiseaseEntry";
        String accession = "DI-12345";
        String acronym = "SAMPLE-DIS";
        String def = "This is sample definition.";
        List<String> altNames = Arrays.asList("name1", "name2", "name3");
        Long reviwedProteinCount = 100L;
        Long unreviwedProteinCount = 200L;

        // cross ref
        List<String> props = Arrays.asList("prop1", "prop2", "prop3");
        String xrefId = "XREF-123";
        String databaseType = "SAMPLE_TYPE";
        DiseaseCrossReference cr =
                new DiseaseCrossReferenceBuilder()
                        .databaseType(databaseType)
                        .id(xrefId)
                        .propertiesSet(props)
                        .build();

        // keyword
        String kId = "Sample Keyword";
        String kwAC = "KW-1234";
        KeywordId keyword = new KeywordIdBuilder().name(kId).id(kwAC).build();

        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        builder.name(id).id(accession).acronym(acronym).definition(def);
        builder.alternativeNamesSet(altNames).crossReferencesAdd(cr);
        builder.keywordsAdd(keyword)
                .reviewedProteinCount(reviwedProteinCount)
                .unreviewedProteinCount(unreviwedProteinCount);

        DiseaseEntry disease = builder.build();

        ValidateJson.verifyJsonRoundTripParser(
                DiseaseJsonConfig.getInstance().getFullObjectMapper(), disease);
    }

    @Test
    void testDiseaseWithMandatoryFieldsSet() {
        String id = "Sample DiseaseEntry";
        String accession = "DI-12345";
        String acronym = "SAMPLE-DIS";
        String def = "This is sample definition.";

        // cross ref
        List<String> props = Arrays.asList("prop1", "prop2", "prop3");
        String xrefId = "XREF-123";
        String databaseType = "SAMPLE_TYPE";
        DiseaseCrossReference cr =
                new DiseaseCrossReferenceBuilder()
                        .databaseType(databaseType)
                        .id(xrefId)
                        .propertiesSet(props)
                        .build();

        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        builder.name(id).id(accession).acronym(acronym).definition(def);
        builder.crossReferencesAdd(cr);

        DiseaseEntry disease = builder.build();

        ValidateJson.verifyJsonRoundTripParser(
                DiseaseJsonConfig.getInstance().getFullObjectMapper(), disease);
    }

    @Test
    void testDiseaseWithJustId() {
        String id = "Sample DiseaseEntry";
        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        builder.name(id);
        DiseaseEntry disease = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                DiseaseJsonConfig.getInstance().getFullObjectMapper(), disease);
    }
}
