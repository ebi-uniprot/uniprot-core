package org.uniprot.core.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.ReactionType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;
import org.uniprot.core.xml.uniprot.comment.CAReactionConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;


class CAReactionConverterTest {
    ObjectFactory objectFactory = new ObjectFactory();

    @Test
    void testFromXmlBinding() {
        ReactionType reactionType = objectFactory.createReactionType();
        reactionType.setText("another text");
        reactionType.getDbReference().add(createDbReferenceType("ChEBI", "CHEBI:29105"));
        reactionType.getDbReference().add(createDbReferenceType("Rhea", "RHEA:125"));
        reactionType.getDbReference().add(createDbReferenceType("EC", "1.2.1.32"));
        reactionType.getEvidence().add(2);
        reactionType.getEvidence().add(3);
        CAReactionConverter converter = new CAReactionConverter(new EvidenceIndexMapper());
        Reaction reaction = converter.fromXml(reactionType);
        assertEquals("another text", reaction.getName());
        assertEquals(2, reaction.getReactionReferences().size());
        verifyReactionReference(reaction.getReactionReferences().get(0), ReactionReferenceType.CHEBI, "CHEBI:29105");
        verifyReactionReference(reaction.getReactionReferences().get(1), ReactionReferenceType.RHEA, "RHEA:125");
        assertEquals("1.2.1.32", reaction.getEcNumber().getValue());
    }

    private void verifyReactionReference(DBCrossReference<ReactionReferenceType> ref,
                                         ReactionReferenceType type, String id) {
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
    void testToXmlBinding() {

        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:12"));
        references.add(createReference(ReactionReferenceType.CHEBI, "CHEBI:22"));
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:322"));

        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        Reaction reaction = new ReactionBuilder().name("Some value").references(references).ecNumber(("1.2.3.32"))
                .evidences(evids).build();
        CAReactionConverter converter = new CAReactionConverter(new EvidenceIndexMapper());


        ReactionType reactionType = converter.toXml(reaction);

        System.out.println(UniProtXmlTestHelper.toXmlString(reactionType, ReactionType.class, "reaction"));

        assertEquals("Some value", reactionType.getText());
        assertEquals(4, reactionType.getDbReference().size());
        assertEquals("Rhea", reactionType.getDbReference().get(2).getType());
        assertEquals("RHEA:322", reactionType.getDbReference().get(2).getId());
        assertEquals("EC", reactionType.getDbReference().get(3).getType());
        assertEquals("1.2.3.32", reactionType.getDbReference().get(3).getId());

        List<Integer> evs = reactionType.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(2, evs.get(1).intValue());


    }

    @Test
    void testRoundTrip() {
        List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:12"));
        references.add(createReference(ReactionReferenceType.CHEBI, "CHEBI:22"));
        references.add(createReference(ReactionReferenceType.RHEA, "RHEA:322"));
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(evidence1);
        evidences.add(evidence2);
        Reaction reaction = new ReactionBuilder().name("Some value").references(references).ecNumber(("1.2.3.32"))
                .evidences(evidences).build();
        CAReactionConverter converter = new CAReactionConverter(new EvidenceIndexMapper());

        ReactionType reactionType = converter.toXml(reaction);
        System.out.println(UniProtXmlTestHelper.toXmlString(reactionType, ReactionType.class, "reaction"));
        Reaction converted = converter.fromXml(reactionType);
        assertEquals(reaction, converted);
    }

    private DBCrossReference<ReactionReferenceType> createReference(ReactionReferenceType type, String id) {
        return new DBCrossReferenceBuilder<ReactionReferenceType>().databaseType(type).id(id).build();
    }
}
