package org.uniprot.core;

import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.PhysiologicalDirectionType;
import org.uniprot.core.uniprot.comment.PhysiologicalReaction;
import org.uniprot.core.uniprot.comment.Reaction;
import org.uniprot.core.uniprot.comment.ReactionReferenceType;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;

public class ObjectsForTests {
  public static Reaction createReaction() {
    List<Evidence> evidences = createEvidences();
    String name = "some reaction";
    List<DBCrossReference<ReactionReferenceType>> references = new ArrayList<>();
    references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:123"));
    references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.RHEA, "RHEA:323"));
    references.add(new DBCrossReferenceImpl<>(ReactionReferenceType.CHEBI, "ChEBI:3243"));
    ECNumber ecNumber = new ECNumberImpl("1.2.4.5");
    return new ReactionBuilder()
      .name(name)
      .references(references)
      .ecNumber(ecNumber)
      .evidences(evidences)
      .build();
  }

  public static PhysiologicalReaction createPhyReaction(){
    return createPhyReactions().get(0);
  }

  public static List<PhysiologicalReaction> createPhyReactions() {
    List<PhysiologicalReaction> phyReactions = new ArrayList<>();
    List<Evidence> evidences = createEvidences();
    phyReactions.add(
      new PhysiologicalReactionBuilder()
        .directionType(PhysiologicalDirectionType.LEFT_TO_RIGHT)
        .reactionReference(
          new DBCrossReferenceBuilder<ReactionReferenceType>()
            .databaseType(ReactionReferenceType.RHEA)
            .id("RHEA:123")
            .build())
        .evidences(evidences)
        .build());
    phyReactions.add(
      new PhysiologicalReactionBuilder()
        .directionType(PhysiologicalDirectionType.RIGHT_TO_LEFT)
        .reactionReference(
          new DBCrossReferenceBuilder<ReactionReferenceType>()
            .databaseType(ReactionReferenceType.RHEA)
            .id("RHEA:313")
            .build())
        .evidences(evidences)
        .build());
    return phyReactions;
  }
}
