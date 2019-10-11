package org.uniprot.core;

import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.impl.DBCrossReferenceImpl;
import org.uniprot.core.impl.ECNumberImpl;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.PhysiologicalReactionBuilder;
import org.uniprot.core.uniprot.comment.builder.ReactionBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

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

  public static Note createNote() {
      List<EvidencedValue> texts = new ArrayList<>();
      List<Evidence> evidences = new ArrayList<>();
      evidences.add(
              new EvidenceBuilder()
                      .databaseName("Ensembl")
                      .databaseId("ENSP0001324")
                      .evidenceCode(EvidenceCode.ECO_0000313)
                      .build());
      evidences.add(
              new EvidenceBuilder()
                      .databaseName("PIRNR")
                      .databaseId("PIRNR001361")
                      .evidenceCode(EvidenceCode.ECO_0000256)
                      .build());
      texts.add(new EvidencedValueBuilder("value 1", evidences).build());
      texts.add(new EvidencedValueBuilder("value2", emptyList()).build());
      return new NoteBuilder(texts).build();
  }

  public static List<IsoformName> createSynonyms() {
      List<IsoformName> synonyms = new ArrayList<>();
      List<Evidence> evidences = createEvidences();
      synonyms.add(new IsoformNameBuilder("Syn 1", evidences).build());
      synonyms.add(new IsoformNameBuilder("Syn 2", evidences).build());
      return synonyms;
  }

  public static Evidence createEvidence() {
      return createEvidences().get(0);
  }

  public static List<Evidence> createEvidences() {
      List<Evidence> evidences = new ArrayList<>();
      evidences.add(
              new EvidenceBuilder()
                      .databaseName("PROSITE-ProRule")
                      .databaseId("PRU10028")
                      .evidenceCode(EvidenceCode.ECO_0000255)
                      .build());
      evidences.add(
              new EvidenceBuilder()
                      .databaseName("PIRNR")
                      .databaseId("PIRNR001361")
                      .evidenceCode(EvidenceCode.ECO_0000256)
                      .build());
      return evidences;
  }

  public static List<EvidencedValue> createEvidenceValuesWithoutEvidences() {
      List<EvidencedValue> evidencedValues = new ArrayList<>();
      evidencedValues.add(new EvidencedValueBuilder("value1", emptyList()).build());
      evidencedValues.add(new EvidencedValueBuilder("value2", emptyList()).build());
      return evidencedValues;
  }

  public static List<EvidencedValue> createEvidenceValuesWithEvidences() {
      List<Evidence> evidences1 = new ArrayList<>();
      evidences1.add(
              new EvidenceBuilder()
                      .databaseName("Ensembl")
                      .databaseId("ENSP0001324")
                      .evidenceCode(EvidenceCode.ECO_0000313)
                      .build());
      evidences1.add(
              new EvidenceBuilder()
                      .databaseName("PIRNR")
                      .databaseName("PIRNR001361")
                      .evidenceCode(EvidenceCode.ECO_0000256)
                      .build());

      List<Evidence> evidences2 = new ArrayList<>();
      evidences1.add(
              new EvidenceBuilder()
                      .databaseName("Ensembl")
                      .databaseId("ENSP0001325")
                      .evidenceCode(EvidenceCode.ECO_0000313)
                      .build());

      List<EvidencedValue> evidencedValues = new ArrayList<>();
      evidencedValues.add(new EvidencedValueBuilder("value1", evidences1).build());
      evidencedValues.add(new EvidencedValueBuilder("value2", evidences2).build());
      return evidencedValues;
  }
}
