package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.List;

class DiseaseConverterTest {

    @Test
    void test() {
        DiseaseBuilder builder = new DiseaseBuilder();

        //		CC   -!- DISEASE: Cystathioninuria (CSTNU) [MIM:219500]: Autosomal
        //		CC       recessive phenotype characterized by abnormal accumulation of
        //		CC       plasma cystathionine, leading to increased urinary excretion.
        //		CC       {ECO:0000269|PubMed:12574942, ECO:0000269|PubMed:18476726}.
        //		CC       Note=The disease is caused by mutations affecting the gene
        //		CC       represented in this entry.

        List<Evidence> evidences = new ArrayList<>();
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:12574942");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:18476726");
        evidences.add(evidence1);
        evidences.add(evidence2);

        String description =
                "Autosomal recessive phenotype characterized "
                        + "by abnormal accumulation of plasma cystathionine, leading"
                        + " to increased urinary excretion.";
        builder.acronym("CSTNU")
                .diseaseId("Cystathioninuria")
                .description(description)
                .diseaseAc("DI-01465")
                .diseaseCrossReference(createDBCrossReference(DiseaseDatabase.MIM, "219500"))
                .evidencesSet(evidences);
        Disease disease = builder.build();
        DiseaseConverter converter = new DiseaseConverter();
        CommentType.Disease xmlObj = converter.toXml(disease);
        assertEquals(description, xmlObj.getDescription());
        assertEquals("CSTNU", xmlObj.getAcronym());
        assertEquals("DI-01465", xmlObj.getId());
        assertEquals("Cystathioninuria", xmlObj.getName());
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlObj, CommentType.Disease.class, "disease"));
    }

    private CrossReference<DiseaseDatabase> createDBCrossReference(
            DiseaseDatabase type, String id) {
        return new CrossReferenceBuilder<DiseaseDatabase>().database(type).id(id).build();
    }
}
