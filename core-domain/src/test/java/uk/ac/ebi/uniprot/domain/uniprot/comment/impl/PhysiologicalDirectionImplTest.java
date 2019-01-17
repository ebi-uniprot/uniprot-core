package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation2.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalDirectionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.PhysiologicalReactionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PhysiologicalDirectionImplTest {

    @Test
    void testCreate() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000313, "Ensembl", "ENSP0001324"
        ));
        evidences.add(new EvidenceImpl(
                EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
        ));
        PhysiologicalReactionImpl reaction = new PhysiologicalReactionBuilder()
                .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
                .reactionReference(new DBCrossReferenceBuilder<ReactionReferenceType>()
                                           .databaseType(ReactionReferenceType.RHEA)
                                           .id("RHEA:123").build())
                .evidences(evidences)
                .build();

        assertEquals(PhysiologicalDirectionType.LEFT_TO_RIGHT, reaction.getDirectionType());
        assertEquals("RHEA:123", reaction.getReactionReference().getId());
        TestHelper.verifyJson(reaction);
    }

}
