package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import com.google.common.base.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.citation.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.impl.ECNumberImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CatalyticActivityCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.ReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.*;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceHelper.parseEvidenceLine;


class CatalyticActivityCommentConverterTest {
    private static ObjectFactory objectFactory;
    private static CatalyticActivityCommentConverter converter;

    @BeforeAll
    public static void setup() {
        objectFactory = new ObjectFactory();


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

        converter = new CatalyticActivityCommentConverter(evidenceReferenceHandler, objectFactory);
    }


    @Test
    void testFromXmlBindingWithPhysioReaction() {
        ReactionType reactionType = objectFactory.createReactionType();
        reactionType.setText("another text");
        reactionType.getDbReference().add(createDbReferenceType("ChEBI", "CHEBI:29105"));
        reactionType.getDbReference().add(createDbReferenceType("Rhea", "RHEA:125"));
        reactionType.getDbReference().add(createDbReferenceType("EC", "1.2.1.32"));
        reactionType.getEvidence().add(2);
        reactionType.getEvidence().add(3);

        CommentType commentType = objectFactory.createCommentType();
        commentType.setReaction(reactionType);

        PhysiologicalReactionType physioReactionType = objectFactory.createPhysiologicalReactionType();
        physioReactionType.setDirection("right-to-left");
        physioReactionType.setDbReference(createDbReferenceType("Rhea", "RHEA:125"));
        physioReactionType.getEvidence().add(2);
        physioReactionType.getEvidence().add(3);

        commentType.getPhysiologicalReaction().add(physioReactionType);
        CatalyticActivityComment converted = converter.fromXml(commentType);

        Reaction reaction = converted.getReaction();
        assertEquals("another text", reaction.getName());
        assertEquals(2, reaction.getReactionReferences().size());
        verifyReactionReference(reaction.getReactionReferences().get(0), ReactionReferenceType.CHEBI, "CHEBI:29105");
        verifyReactionReference(reaction.getReactionReferences().get(1), ReactionReferenceType.RHEA, "RHEA:125");
        assertEquals("1.2.1.32", reaction.getEcNumber().getValue());
        List<Evidence> evids = reaction.getEvidences();
        assertEquals(2, evids.size());
        assertEquals("ECO:0000269|PubMed:9060646", evids.get(0).getValue());
        assertEquals("ECO:0000269|PubMed:9060647", evids.get(1).getValue());
        List<PhysiologicalReaction> physioReactions = converted.getPhysiologicalReactions();
        assertEquals(1, physioReactions.size());

        PhysiologicalReaction physioReaction = physioReactions.get(0);

        assertEquals(PhysiologicalDirectionType.RIGHT_TO_LEFT, physioReaction.getDirectionType());

        verifyReactionReference(physioReaction.getReactionReference(), ReactionReferenceType.RHEA, "RHEA:125");
        evids = physioReaction.getEvidences();
        assertEquals(2, evids.size());
        assertEquals("ECO:0000269|PubMed:9060646", evids.get(0).getValue());
        assertEquals("ECO:0000269|PubMed:9060647", evids.get(1).getValue());
    }

    private void verifyReactionReference(DBCrossReference<ReactionReferenceType> ref, ReactionReferenceType type, String id) {
        assertEquals(type, ref.getDatabaseType());
        assertEquals(id, ref.getId());
    }

    private DbReferenceType createDbReferenceType(String type, String id) {
        DbReferenceType dbref = objectFactory.createDbReferenceType();
        dbref.setId(id);
        dbref.setType(type);
        return dbref;
    }

    @Test
    void testToXmlBindingWithPhysioReaction() {
        Reaction reaction = createReaction("some value", "1.1.2.42");
        List<PhysiologicalReaction> physioReactions = new ArrayList<>();
        physioReactions.add(createPhysiologicalReaction(PhysiologicalDirectionType.LEFT_TO_RIGHT, "RHEA:321"));
        physioReactions.add(createPhysiologicalReaction(PhysiologicalDirectionType.RIGHT_TO_LEFT, "RHEA:12"));

        CatalyticActivityComment comment =
                new CatalyticActivityCommentBuilder()
                        .reaction(reaction)
                        .physiologicalReactions(physioReactions)
                        .build();

        CommentType commentType = converter.toXml(comment);
        assertNotNull(commentType);
        ReactionType reactionType = commentType.getReaction();
        assertNotNull(reactionType);
        assertEquals("some value", reactionType.getText());
        assertEquals(4, reactionType.getDbReference().size());
        assertEquals("Rhea", reactionType.getDbReference().get(2).getType());
        assertEquals("RHEA:322", reactionType.getDbReference().get(2).getId());
        assertEquals("EC", reactionType.getDbReference().get(3).getType());
        assertEquals("1.1.2.42", reactionType.getDbReference().get(3).getId());

        List<Integer> evs = reactionType.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(3, evs.get(1).intValue());
        List<PhysiologicalReactionType> prts = commentType.getPhysiologicalReaction();
        assertEquals(2, prts.size());
        PhysiologicalReactionType prt = prts.get(0);
        assertEquals("left-to-right", prt.getDirection());
        assertEquals("Rhea", prt.getDbReference().getType());
        assertEquals("RHEA:321", prt.getDbReference().getId());

        evs = prt.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(3, evs.get(1).intValue());

        System.out.println(UniProtXmlTestHelper.toXmlString(commentType, CommentType.class, "comment"));
    }

    @Test
    void testToXmlBindingWithPhysioReactionRoundTrip() {

        Reaction reaction = createReaction("some value", "1.1.2.42");
        List<PhysiologicalReaction> physioReactions = new ArrayList<>();

        physioReactions.add(createPhysiologicalReaction(PhysiologicalDirectionType.LEFT_TO_RIGHT, "RHEA:321"));
        physioReactions.add(createPhysiologicalReaction(PhysiologicalDirectionType.RIGHT_TO_LEFT, "RHEA:12"));
        CatalyticActivityComment comment =
                new CatalyticActivityCommentBuilder()
                        .reaction(reaction)
                        .physiologicalReactions(physioReactions)
                        .build();
        CommentType commentType = converter.toXml(comment);
        CatalyticActivityComment converted = converter.fromXml(commentType);
        assertEquals(comment, converted);

    }


    @Test
    void testToXmlBindingWithOutPhysioReaction() {

        Reaction reaction = createReaction("some value", "1.1.2.42");
        CatalyticActivityComment comment =
                new CatalyticActivityCommentBuilder()
                        .reaction(reaction)
                        .build();

        CommentType commentType = converter.toXml(comment);
        assertNotNull(commentType);
        ReactionType reactionType = commentType.getReaction();
        assertNotNull(reactionType);
        assertEquals("some value", reactionType.getText());
        assertEquals(4, reactionType.getDbReference().size());
        assertEquals("Rhea", reactionType.getDbReference().get(2).getType());
        assertEquals("RHEA:322", reactionType.getDbReference().get(2).getId());
        assertEquals("EC", reactionType.getDbReference().get(3).getType());
        assertEquals("1.1.2.42", reactionType.getDbReference().get(3).getId());

        List<Integer> evs = reactionType.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(3, evs.get(1).intValue());
        List<PhysiologicalReactionType> prts = commentType.getPhysiologicalReaction();
        assertEquals(0, prts.size());

        CatalyticActivityComment converted = converter.fromXml(commentType);
        assertEquals(comment, converted);

    }

    @Test
    void testToXmlBindingWithOutPhysioReaction2() {
        Reaction reaction = createReaction2("some value", "1.1.2.42");

        CatalyticActivityComment comment =
                new CatalyticActivityCommentBuilder()
                        .reaction(reaction)
                        .build();
        CommentType commentType = converter.toXml(comment);
        assertNotNull(commentType);
        ReactionType reactionType = commentType.getReaction();
        assertNotNull(reactionType);
        assertEquals("some value", reactionType.getText());
        assertEquals(1, reactionType.getDbReference().size());

        assertEquals("EC", reactionType.getDbReference().get(0).getType());
        assertEquals("1.1.2.42", reactionType.getDbReference().get(0).getId());

        List<Integer> evs = reactionType.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(3, evs.get(1).intValue());
        List<PhysiologicalReactionType> prts = commentType.getPhysiologicalReaction();
        assertEquals(0, prts.size());

    }

    private Reaction createReaction(String name, String ec) {
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:12"));
        references.add(createReference(ReactionReferenceType.CHEBI, "CHEBI:22"));
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:322"));

        ECNumber ecNumber = null;
        if (!Strings.isNullOrEmpty(ec))
            ecNumber = new ECNumberImpl(ec);
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);

        return new ReactionBuilder()
                .name(name)
                .references(references)
                .ecNumber(ecNumber)
                .evidences(evids)
                .build();
    }

    private Reaction createReaction2(String name, String ec) {

        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();

        ECNumber ecNumber = null;
        if (!Strings.isNullOrEmpty(ec))
            ecNumber = new ECNumberImpl(ec);
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        return new ReactionBuilder()
                .name(name)
                .references(references)
                .ecNumber(ecNumber)
                .evidences(evids)
                .build();
    }

    private PhysiologicalReaction createPhysiologicalReaction(PhysiologicalDirectionType type,
                                                              String rheaId) {
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        return new PhysiologicalReactionBuilder()
                .directionType(type)
                .reactionReference(createReference(ReactionReferenceType.RHEA, rheaId))
                .evidences(evids)
                .build();
    }

    private DBCrossReference<ReactionReferenceType> createReference(ReactionReferenceType type, String rheaId) {
        return new DBCrossReferenceBuilder<ReactionReferenceType>()
                                   .databaseType(type)
                                   .id(rheaId)
                                   .build();
    }
}
