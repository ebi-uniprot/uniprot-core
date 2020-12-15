package org.uniprot.core.json.parser.publication;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.publication.impl.MappedSourceBuilder;
import org.uniprot.core.publication.impl.UniProtKBMappedReferenceBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sahmad
 */
class UniProtKBMappedReferenceTest {

    @Test
    void testSimpleUniProtKBMappedReference() {
        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        UniProtKBMappedReference mappedReference = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                UniProtKBMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
    }

    @Test
    void testCompleteUniProtMappedReference() {
        UniProtKBMappedReference mappedReference = getCompleteUniProtKBMappedReference();
        ValidateJson.verifyJsonRoundTripParser(
                UniProtKBMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
        ValidateJson.verifyEmptyFields(mappedReference);
    }

    static UniProtKBMappedReference getCompleteUniProtKBMappedReference() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");cats.add("cat2");
        List<ReferenceComment> comments = new ArrayList<>();
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).value("val1")
                .evidencesAdd(ObjectsForTests.createEvidence()).build());
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).value("val2")
                .evidencesAdd(ObjectsForTests.createEvidence()).build());

        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        builder.referenceCommentsSet(comments);
        builder.referencePositionsAdd("1");
        return builder.build();
    }
}
