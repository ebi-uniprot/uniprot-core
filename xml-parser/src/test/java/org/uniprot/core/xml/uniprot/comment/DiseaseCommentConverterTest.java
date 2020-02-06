package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.DiseaseBuilder;
import org.uniprot.core.uniprot.comment.builder.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class DiseaseCommentConverterTest {

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
                .reference(createDBCrossReference(DiseaseReferenceType.MIM, "219500"))
                .evidencesSet(evidences);
        Disease disease = builder.build();
        String noteStr =
                "The disease is caused by mutations affecting the gene represented in this entry";
        Note note = createNote(noteStr, Collections.emptyList());

        DiseaseCommentBuilder commentBuilder = new DiseaseCommentBuilder();
        DiseaseComment comment = commentBuilder.disease(disease).note(note).build();

        DiseaseCommentConverter converter = new DiseaseCommentConverter(new EvidenceIndexMapper());
        CommentType xmlObj = converter.toXml(comment);

        System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, CommentType.class, "comment"));
        DiseaseComment converted = converter.fromXml(xmlObj);
        assertEquals(comment, converted);
    }

    private Note createNote(String val, List<String> evids) {
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(val, createEvidence(evids)).build());
        return new NoteBuilder(texts).build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }

    private DBCrossReference<DiseaseReferenceType> createDBCrossReference(
            DiseaseReferenceType type, String id) {
        return new DBCrossReferenceBuilder<DiseaseReferenceType>()
                .databaseType(type)
                .id(id)
                .build();
    }
}
