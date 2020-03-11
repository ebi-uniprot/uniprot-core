package org.uniprot.core.uniprot.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithEvidences;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.UniProtDatabaseMock;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.citation.impl.PatentBuilder;
import org.uniprot.core.impl.ECNumberBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.impl.*;
import org.uniprot.core.uniprot.description.impl.*;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.impl.FeatureBuilder;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismImpl;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.impl.UniProtCrossReferenceBuilder;

class UniProtEntryImplTest {
    private UniProtEntry minEntry =
            new UniProtEntryBuilder("acc", "id", UniProtEntryType.SWISSPROT).build();
    private List<Comment> comments =
            asList(new BPCPCommentBuilder().build(), new DiseaseCommentBuilder().build());
    private List<Feature> features =
            asList(new FeatureBuilder().build(),
                    new FeatureBuilder().type(FeatureType.CHAIN).build(),
                    new FeatureBuilder().type(FeatureType.VARIANT).build());
    private List<UniProtReference> references=asList(
      new UniProtReferenceImpl(),
      new UniProtReferenceImpl(new BookBuilder().build(),asList("ref1","ref2", "ref3"),
        singletonList(new ReferenceCommentBuilder().build()), ObjectsForTests.createEvidences()),
      new UniProtReferenceImpl(new PatentBuilder().build(),asList("pos1","pos2", "pos3"),
        singletonList(new ReferenceCommentBuilder().type(ReferenceCommentType.TRANSPOSON).build()), ObjectsForTests.createEvidences())
    );

    private List<UniProtCrossReference> uniProtCrossReferences = asList(
      new UniProtCrossReferenceBuilder().build(),
      new UniProtCrossReferenceBuilder().database(new UniProtDatabaseMock("db")).build(),
      new UniProtCrossReferenceBuilder().database(new UniProtDatabaseMock("db2")).build(),
      new UniProtCrossReferenceBuilder().isoformId("iso").build()
    );

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtEntry obj = new UniProtEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void canFilterByCommentType() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).commentsSet(comments).build();
        List<DiseaseComment> comments = entry.getCommentsByType(CommentType.DISEASE);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }

    @Test
    void canFilterByFeatureType() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).featuresSet(features).build();
        List<Feature> comments = entry.getFeaturesByType(FeatureType.CHAIN);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }

    @Test
    void normalEntryWillNotBeFragment_featuresExists() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).featuresSet(features).build();
        assertFalse(entry.isFragment());
    }

    @Test
    void minEntryWillNotBeFragment() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).featuresSet(features).build();
        assertFalse(entry.isFragment());
    }

    @Test
    void entryIsFragmentWhenFeaturesContainNonTerminalResidue() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry)
          .featuresSet(features)
          .featuresAdd(new FeatureBuilder().type(FeatureType.NON_TER).build())
          .build();
        assertTrue(entry.isFragment());
    }

    @Test
    void canFilterReferencesByCitationType() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).
          referencesSet(references).build();
        List<UniProtReference> references = entry.getReferencesByType(CitationType.BOOK);

        assertFalse(references.isEmpty());
        assertEquals(1, references.size());
    }

    @Test
    void canFilterUniCrossReferencesByDataBase() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).
          uniProtCrossReferencesSet(uniProtCrossReferences).build();
        List<UniProtCrossReference> references = entry.getUniProtCrossReferencesByType(new UniProtDatabaseMock("db2"));

        assertFalse(references.isEmpty());
        assertEquals(1, references.size());
    }

    @Test
    void canFilterUniCrossReferencesByDataBase_string() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).
          uniProtCrossReferencesSet(uniProtCrossReferences).build();
        List<UniProtCrossReference> references = entry.getUniProtCrossReferencesByType("db");

        assertFalse(references.isEmpty());
        assertEquals(1, references.size());
    }

    @Test
    void minEntryDonotHaveEvidences() {
        List<Evidence> evidences = minEntry.gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_organism_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_organism() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .organism(new OrganismBuilder().evidencesAdd(createEvidence()).build())
          .build().gatherEvidences();

        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder().build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .submissionNamesAdd(new ProteinSubNameBuilder().build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .submissionNamesAdd(new ProteinSubNameBuilder().fullName(new NameBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .submissionNamesAdd(new ProteinSubNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .submissionNamesAdd(new ProteinSubNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .submissionNamesAdd(new ProteinSubNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder().build()).build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder().build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .includesAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().build())
            .build())
          .organism(new OrganismBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder().build()).build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().recommendedName(new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().build()).build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder().build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_FullName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().fullName(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder().build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_ShortNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder()
              .evidencesAdd(createEvidence())
              .build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder().build()).build())
              .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_EcNumbers() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .containsAdd(new ProteinSectionBuilder().alternativeNamesAdd(new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder()
              .evidencesAdd(createEvidence())
              .build()).build()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AllergenName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .allergenName(new NameBuilder().build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AllergenName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .allergenName(new NameBuilder().evidencesAdd(createEvidence()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_BiotechName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .biotechName(new NameBuilder().build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_BiotechName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .biotechName(new NameBuilder().evidencesAdd(createEvidence()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_CdAntigenNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .cdAntigenNamesAdd(new NameBuilder().build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_CdAntigenNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .cdAntigenNamesAdd(new NameBuilder().evidencesAdd(createEvidence()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_InnNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .innNamesAdd(new NameBuilder().build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_InnNames() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .proteinDescription(new ProteinDescriptionBuilder()
            .innNamesAdd(new NameBuilder().evidencesAdd(createEvidence()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Features_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .featuresSet(features)
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Features() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .featuresAdd(new FeatureBuilder().evidencesAdd(createEvidence()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Keywords_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .keywordsAdd(new KeywordBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Keywords() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .keywordsAdd(new KeywordBuilder().evidencesAdd(createEvidence()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_GeneLocations_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .geneLocationsAdd(new GeneLocationBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_GeneLocations() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .geneLocationsAdd(new GeneLocationBuilder().evidencesAdd(createEvidence()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_geneName_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().geneName(new GeneNameBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_geneName() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().geneName(new GeneNameBuilder()
            .evidencesAdd(createEvidence())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_Synonyms_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().synonymsAdd(new GeneNameSynonymBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_Synonyms() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().synonymsAdd(new GeneNameSynonymBuilder()
            .evidencesAdd(createEvidence())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrfNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().orfNamesAdd(new ORFNameBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrfNames_Synonyms() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().orfNamesAdd(new ORFNameBuilder()
            .evidencesAdd(createEvidence())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrderedLocusNames_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().orderedLocusNamesAdd(new OrderedLocusNameBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrderedLocusNames_Synonyms() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .genesAdd(new GeneBuilder().orderedLocusNamesAdd(new OrderedLocusNameBuilder()
            .evidencesAdd(createEvidence())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_References_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .referencesAdd(new UniProtReferenceBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_References() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .referencesAdd(new UniProtReferenceBuilder()
            .evidencesAdd(createEvidence())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_References_ReferenceComments_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .referencesAdd(new UniProtReferenceBuilder().referenceCommentsAdd(new ReferenceCommentBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_References_ReferenceComments() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .referencesAdd(new UniProtReferenceBuilder().referenceCommentsAdd(new ReferenceCommentBuilder()
            .evidencesAdd(createEvidence())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new FreeTextCommentBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText_noEvidenceInEvidenceValue() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new FreeTextCommentBuilder()
            .textsAdd(new EvidencedValueBuilder().build())
            .build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new FreeTextCommentBuilder()
            .textsAdd(new EvidencedValueBuilder().evidencesAdd(createEvidence()).build())
            .build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }
    //
    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_whenNoIsoforms() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder().build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_name_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .name(new IsoformNameBuilder().build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_name() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .name(new IsoformNameBuilder().evidencesAdd(createEvidence()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Synonyms_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .synonymsAdd(new IsoformNameBuilder().build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Synonyms() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .synonymsAdd(new IsoformNameBuilder().evidencesAdd(createEvidence()).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Note_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .note(new NoteBuilder(null).build())
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Note() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder().isoformsAdd(new APIsoformBuilder()
            .note(new NoteBuilder(Collections.singletonList(createEvidenceValuesWithEvidences().get(0))).build())
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Note_whenNoEvidence() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder()
            .note(new NoteBuilder(null)
            .build()).build())
          .build().gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Note() {
        List<Evidence> evidences = UniProtEntryBuilder.from(minEntry)
          .commentsAdd(new AlternativeProductsCommentBuilder()
            .note(new NoteBuilder(Collections.singletonList(createEvidenceValuesWithEvidences().get(0)))
            .build()).build())
          .build().gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtEntry impl = new UniProtEntryImpl(UniProtEntryType.SWISSPROT, new UniProtAccessionImpl("acc"),
          singletonList(new UniProtAccessionImpl("sec")), new UniProtIdImpl("id"),
          new EntryAuditBuilder().build(), 5.6D, new OrganismBuilder().build(),Collections.emptyList(),
          ProteinExistence.TRANSCRIPT_LEVEL, new ProteinDescriptionBuilder().build(), Collections.emptyList(),
          Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null,
          null, new SequenceBuilder("seq").build(), null, Collections.emptyList(), null);
        UniProtEntry obj = UniProtEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
