package org.uniprot.core.xml.uniprot.comment;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorComment;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.comment.builder.CofactorCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CofactorType;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class CofactorCommentConverterTest {
    CofactorCommentConverter converter;

    @BeforeEach
    void setUp() {

        EvidenceIndexMapper evidenceReferenceHandler = new EvidenceIndexMapper();

        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060646");
        Evidence evidence3 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        Evidence evidence4 = parseEvidenceLine("ECO:0000269|PubMed:9060648");
        Map<Evidence, Integer> evIdMap = new HashMap<>();
        evIdMap.put(evidence1, 1);
        evIdMap.put(evidence2, 2);
        evIdMap.put(evidence3, 3);
        evIdMap.put(evidence4, 4);
        evidenceReferenceHandler.reset(evIdMap);

        this.converter = new CofactorCommentConverter(evidenceReferenceHandler);
    }

    @Test
    void test() {
        CofactorCommentBuilder builder = new CofactorCommentBuilder();
        builder.molecule("Isoform 1");
        List<String> evids = new ArrayList<>();
        evids.add("ECO:0000269|PubMed:9060645");
        List<Cofactor> cofactors = new ArrayList<>();
        cofactors.add(create("Zn(2+)", CofactorReferenceType.CHEBI, "CHEBI:29105", evids));
        evids = new ArrayList<>();
        evids.add("ECO:0000269|PubMed:9060646");
        cofactors.add(create("Co(2+)", CofactorReferenceType.CHEBI, "CHEBI:29106", evids));
        builder.cofactorsSet(cofactors);
        evids = new ArrayList<>();
        evids.add("ECO:0000269|PubMed:9060647");
        builder.note(createNote("Binds 2 zinc ions", evids));
        CofactorComment comment = builder.build();
        CommentType xmlComment = converter.toXml(comment);
        assertEquals("Isoform 1", xmlComment.getMolecule().getValue());
        List<CofactorType> xmlCofactors = xmlComment.getCofactor();
        assertEquals(2, xmlCofactors.size());
        List<Integer> xmlEv = new ArrayList<>();
        xmlEv.add(1);
        assertCofactorType(xmlCofactors.get(0), "Zn(2+)", "ChEBI", "CHEBI:29105", xmlEv);
        xmlEv = new ArrayList<>();
        xmlEv.add(2);
        assertCofactorType(xmlCofactors.get(1), "Co(2+)", "ChEBI", "CHEBI:29106", xmlEv);
        assertEquals(1, xmlComment.getText().size());
        EvidencedStringType note = xmlComment.getText().get(0);
        assertNotNull(note);
        assertEquals("Binds 2 zinc ions.", note.getValue());
        xmlEv = new ArrayList<>();
        xmlEv.add(3);
        assertEquals(xmlEv, note.getEvidence());
        CofactorComment convertedComment = converter.fromXml(xmlComment);
        assertEquals(comment, convertedComment);
        String createdXmlText =
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment");
        System.out.println(createdXmlText);
    }

    private void assertCofactorType(
            CofactorType cofactor, String name, String dbType, String dbId, List<Integer> evids) {
        assertEquals(name, cofactor.getName());
        assertEquals(dbType, cofactor.getDbReference().getType());
        assertEquals(dbId, cofactor.getDbReference().getId());
        assertEquals(evids, cofactor.getEvidence());
    }

    private Note createNote(String val, List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String evid : evids) {
            evidences.add(parseEvidenceLine(evid));
        }
        return new NoteBuilder(singletonList(new EvidencedValueBuilder(val, evidences).build()))
                .build();
    }

    private Cofactor create(
            String name, CofactorReferenceType type, String xrefId, List<String> evids) {
        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceBuilder<CofactorReferenceType>()
                        .databaseType(type)
                        .id(xrefId)
                        .build();
        return new CofactorBuilder()
                .name(name)
                .reference(reference)
                .evidencesSet(createEvidence(evids))
                .build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }
}
