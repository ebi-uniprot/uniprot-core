package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.DiseaseBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.DiseaseConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

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

        String description = "Autosomal recessive phenotype characterized "
                + "by abnormal accumulation of plasma cystathionine, leading"
                + " to increased urinary excretion.";
        builder.acronym("CSTNU")
                .diseaseId("Cystathioninuria")
                .description(description)
                .diseaseAc("DI-01465")
                .reference(
                        createDBCrossReference(DiseaseReferenceType.MIM, "219500"))
                .evidences(evidences)
        ;
        Disease disease = builder.build();
        DiseaseConverter converter = new DiseaseConverter();
        CommentType.Disease xmlObj = converter.toXml(disease);
        assertEquals(description, xmlObj.getDescription());
        assertEquals("CSTNU", xmlObj.getAcronym());
        assertEquals("DI-01465", xmlObj.getId());
        assertEquals("Cystathioninuria", xmlObj.getName());
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, CommentType.Disease.class, "disease"));
    }

    private DBCrossReference<DiseaseReferenceType> createDBCrossReference(DiseaseReferenceType type, String id) {
        return new DBCrossReferenceBuilder<DiseaseReferenceType>().databaseType(type).id(id).build();
    }
}
