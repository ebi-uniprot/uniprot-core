package org.uniprot.core.uniprotkb.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidenceValueWithSingleEvidence;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.UniProtKBDatabaseMock;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.citation.impl.PatentBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;
import org.uniprot.core.uniprotkb.feature.impl.UniProtKBFeatureBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;

class UniProtKBEntryImplTest {
    private UniProtKBEntry minEntry =
            new UniProtKBEntryBuilder("acc", "id", UniProtKBEntryType.SWISSPROT).build();
    private List<Comment> comments =
            asList(
                    new BPCPCommentBuilder().build(),
                    new DiseaseCommentBuilder().build(),
                    new BPCPCommentBuilder().build());
    private List<UniProtKBFeature> features =
            asList(
                    new UniProtKBFeatureBuilder().build(),
                    new UniProtKBFeatureBuilder().type(UniprotKBFeatureType.CHAIN).build(),
                    new UniProtKBFeatureBuilder().type(UniprotKBFeatureType.VARIANT).build());
    private List<UniProtKBReference> references =
            asList(
                    new UniProtKBReferenceImpl(),
                    new UniProtKBReferenceImpl(
                            new BookBuilder().build(),
                            asList("ref1", "ref2", "ref3"),
                            singletonList(new ReferenceCommentBuilder().build()),
                            ObjectsForTests.createEvidences()),
                    new UniProtKBReferenceImpl(
                            new PatentBuilder().build(),
                            asList("pos1", "pos2", "pos3"),
                            singletonList(
                                    new ReferenceCommentBuilder()
                                            .type(ReferenceCommentType.TRANSPOSON)
                                            .build()),
                            ObjectsForTests.createEvidences()));

    private List<UniProtKBCrossReference> uniProtKBCrossReferences =
            asList(
                    new UniProtCrossReferenceBuilder().build(),
                    new UniProtCrossReferenceBuilder()
                            .database(new UniProtKBDatabaseMock("db"))
                            .build(),
                    new UniProtCrossReferenceBuilder()
                            .database(new UniProtKBDatabaseMock("db2"))
                            .build(),
                    new UniProtCrossReferenceBuilder().isoformId("iso").build());

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtKBEntry obj = new UniProtKBEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void canFilterByCommentType() {
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).commentsSet(comments).build();
        List<DiseaseComment> comments = entry.getCommentsByType(CommentType.DISEASE);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }

    @Test
    void canFilterByFeatureType() {
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).featuresSet(features).build();
        List<UniProtKBFeature> comments = entry.getFeaturesByType(UniprotKBFeatureType.CHAIN);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }

    @Test
    void normalEntryWillNotBeFragment_featuresExists() {
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).featuresSet(features).build();
        assertFalse(entry.isFragment());
    }

    @Test
    void minEntryWillNotBeFragment() {
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).featuresSet(features).build();
        assertFalse(entry.isFragment());
    }

    @Test
    void entryIsFragmentWhenFeaturesContainNonTerminalResidue() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .featuresSet(features)
                        .featuresAdd(
                                new UniProtKBFeatureBuilder()
                                        .type(UniprotKBFeatureType.NON_TER)
                                        .build())
                        .build();
        assertTrue(entry.isFragment());
    }

    @Test
    void canFilterReferencesByCitationType() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).referencesSet(references).build();
        List<UniProtKBReference> references = entry.getReferencesByType(CitationType.BOOK);

        assertFalse(references.isEmpty());
        assertEquals(1, references.size());
    }

    @Test
    void canFilterUniCrossReferencesByDataBase() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .uniProtCrossReferencesSet(uniProtKBCrossReferences)
                        .build();
        List<UniProtKBCrossReference> references =
                entry.getUniProtCrossReferencesByType(new UniProtKBDatabaseMock("db2"));

        assertFalse(references.isEmpty());
        assertEquals(1, references.size());
    }

    @Test
    void canFilterUniCrossReferencesByDataBase_string() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .uniProtCrossReferencesSet(uniProtKBCrossReferences)
                        .build();
        List<UniProtKBCrossReference> references = entry.getUniProtCrossReferencesByType("db");

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
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_organism() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .organism(new OrganismBuilder().evidencesAdd(createEvidence()).build())
                        .build()
                        .gatherEvidences();

        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(new ProteinDescriptionBuilder().build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(new ProteinNameBuilder().build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .fullName(new NameBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .shortNamesAdd(new NameBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .shortNamesAdd(
                                                                new NameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .ecNumbersAdd(new ECBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_RecommendedName_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinNameBuilder()
                                                        .ecNumbersAdd(
                                                                new ECBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(new ProteinNameBuilder().build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_isNull() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder().alternativeNamesAdd(null).build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .fullName(new NameBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .shortNamesAdd(new NameBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .shortNamesAdd(
                                                                new NameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .ecNumbersAdd(new ECBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AlternativeNames_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .alternativeNamesAdd(
                                                new ProteinNameBuilder()
                                                        .ecNumbersAdd(
                                                                new ECBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .submissionNamesAdd(new ProteinSubNameBuilder().build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_isNull() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder().submissionNamesAdd(null).build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .fullName(new NameBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .ecNumbersAdd(new ECBuilder().build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_SubmissionNames_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .ecNumbersAdd(
                                                                new ECBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(new ProteinSectionBuilder().build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_RecommendedName_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_Includes_AlternativeNames_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .includesAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(new ProteinSectionBuilder().build())
                                        .build())
                        .organism(new OrganismBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_RecommendedName_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .recommendedName(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_FullName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_FullName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .fullName(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_ShortNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_ShortNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .shortNamesAdd(
                                                                                new NameBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_EcNumbers_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_contains_AlternativeNames_EcNumbers() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .containsAdd(
                                                new ProteinSectionBuilder()
                                                        .alternativeNamesAdd(
                                                                new ProteinNameBuilder()
                                                                        .ecNumbersAdd(
                                                                                new ECBuilder()
                                                                                        .evidencesAdd(
                                                                                                createEvidence())
                                                                                        .build())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AllergenName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .allergenName(new NameBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_AllergenName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .allergenName(
                                                new NameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_BiotechName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .biotechName(new NameBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_BiotechName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .biotechName(
                                                new NameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_CdAntigenNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .cdAntigenNamesAdd(new NameBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_CdAntigenNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .cdAntigenNamesAdd(
                                                new NameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_InnNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .innNamesAdd(new NameBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_ProteinDescription_InnNames() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .innNamesAdd(
                                                new NameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Features_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .featuresSet(features)
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Features() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .featuresAdd(
                                new UniProtKBFeatureBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Keywords_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .keywordsAdd(new KeywordBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Keywords() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .keywordsAdd(new KeywordBuilder().evidencesAdd(createEvidence()).build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_GeneLocations_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .geneLocationsAdd(new GeneLocationBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_GeneLocations() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .geneLocationsAdd(
                                new GeneLocationBuilder().evidencesAdd(createEvidence()).build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(new GeneBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_geneName_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(new GeneBuilder().geneName(new GeneNameBuilder().build()).build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_geneName() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .geneName(
                                                new GeneNameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_Synonyms_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .synonymsAdd(new GeneNameSynonymBuilder().build())
                                        .geneName(new GeneNameBuilder().value("gene").build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_Synonyms() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .synonymsAdd(
                                                new GeneNameSynonymBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .geneName(new GeneNameBuilder().value("gene").build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrfNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder().orfNamesAdd(new ORFNameBuilder().build()).build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrfNames_Synonyms() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .orfNamesAdd(
                                                new ORFNameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrderedLocusNames_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .orderedLocusNamesAdd(new OrderedLocusNameBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Genes_OrderedLocusNames_Synonyms() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .genesAdd(
                                new GeneBuilder()
                                        .orderedLocusNamesAdd(
                                                new OrderedLocusNameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_References_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .referencesAdd(new UniProtKBReferenceBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_References() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .referencesAdd(
                                new UniProtKBReferenceBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_References_ReferenceComments_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .referencesAdd(
                                new UniProtKBReferenceBuilder()
                                        .referenceCommentsAdd(new ReferenceCommentBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_References_ReferenceComments() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .referencesAdd(
                                new UniProtKBReferenceBuilder()
                                        .referenceCommentsAdd(
                                                new ReferenceCommentBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new FreeTextCommentBuilder()
                                        .commentType(CommentType.FUNCTION)
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText_noEvidenceInEvidenceValue() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new FreeTextCommentBuilder()
                                        .commentType(CommentType.PTM)
                                        .textsAdd(new EvidencedValueBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_FreeText() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new FreeTextCommentBuilder()
                                        .commentType(CommentType.DISRUPTION_PHENOTYPE)
                                        .textsAdd(
                                                new EvidencedValueBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }
    //
    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_whenNoIsoforms() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new AlternativeProductsCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(new APIsoformBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_name_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .name(new IsoformNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_name() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .name(
                                                                new IsoformNameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Synonyms_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .synonymsAdd(
                                                                new IsoformNameBuilder().build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Synonyms() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .synonymsAdd(
                                                                new IsoformNameBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Note_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .note(new NoteBuilder(null).build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Isoforms_Note() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .isoformsAdd(
                                                new APIsoformBuilder()
                                                        .note(
                                                                new NoteBuilder(
                                                                                Collections
                                                                                        .singletonList(
                                                                                                createEvidenceValueWithSingleEvidence()))
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Note_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .note(new NoteBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_AlternativeProducts_Note() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new AlternativeProductsCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new BPCPCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_Absorption_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .absorption(new AbsorptionBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_Absorption() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .absorption(
                                                new AbsorptionBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_PhDependence_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .phDependence(new PhDependenceBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_PhDependence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .phDependence(
                                                new PhDependenceBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_RedoxPotential_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .redoxPotential(new RedoxPotentialBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_RedoxPotential() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .redoxPotential(
                                                new RedoxPotentialBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_TemperatureDependence_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .temperatureDependence(
                                                new TemperatureDependenceBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_TemperatureDependence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .temperatureDependence(
                                                new TemperatureDependenceBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(new KineticParametersBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_MaximumVelocities_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .maximumVelocitiesAdd(
                                                                new MaximumVelocityBuilder()
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_MaximumVelocities() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .maximumVelocitiesAdd(
                                                                new MaximumVelocityBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_MichaelisConstants_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .michaelisConstantsAdd(
                                                                new MichaelisConstantBuilder()
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_MichaelisConstants() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .michaelisConstantsAdd(
                                                                new MichaelisConstantBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_Note_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .note(new NoteBuilder(null).build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_BPCPComment_KineticParameters_Note() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .note(
                                                                new NoteBuilder(
                                                                                Collections
                                                                                        .singletonList(
                                                                                                createEvidenceValueWithSingleEvidence()))
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CatalyticActivityComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new CatalyticActivityCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CatalyticActivityComment_Reaction_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CatalyticActivityCommentBuilder()
                                        .reaction(new ReactionBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CatalyticActivityComment_Reaction() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CatalyticActivityCommentBuilder()
                                        .reaction(
                                                new ReactionBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_CatalyticActivityComment_PhysiologicalReactions_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CatalyticActivityCommentBuilder()
                                        .physiologicalReactionsAdd(
                                                new PhysiologicalReactionBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CatalyticActivityComment_PhysiologicalReactions() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CatalyticActivityCommentBuilder()
                                        .physiologicalReactionsAdd(
                                                new PhysiologicalReactionBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CofactorComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new CofactorCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CofactorComment_notes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CofactorCommentBuilder()
                                        .note(new NoteBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CofactorComment_notes() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CofactorCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CofactorComment_Cofactors_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CofactorCommentBuilder()
                                        .cofactorsAdd(new CofactorBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_CofactorComment_Cofactors() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new CofactorCommentBuilder()
                                        .cofactorsAdd(
                                                new CofactorBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_DiseaseComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new DiseaseCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_DiseaseComment_notes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .note(new NoteBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_DiseaseComment_notes() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_DiseaseComment_Disease_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .disease(new DiseaseBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_DiseaseComment_Disease() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .disease(
                                                new DiseaseBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_RnaEditingComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new RnaEditingCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_RnaEditingComment_notes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .note(new NoteBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_RnaEditingComment_notes() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_RnaEditingComment_Positions_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .positionsAdd(new RnaEditingPositionBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_RnaEditingComment_Positions() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .positionsAdd(
                                                new RnaEditingPositionBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_SubcellularLocationComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new SubcellularLocationCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_SubcellularLocationComment_notes_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .note(new NoteBuilder(null).build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_SubcellularLocationComment_notes() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder().build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_location_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .location(
                                                                new SubcellularLocationValueBuilder()
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_location() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .location(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_orientation_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .orientation(
                                                                new SubcellularLocationValueBuilder()
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_orientation() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .orientation(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_topology_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .topology(
                                                                new SubcellularLocationValueBuilder()
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void
            canGatherEvidencesFrom_Comments_SubcellularLocationComment_SubcellularLocations_topology() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .topology(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_MassSpectrometryComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new MassSpectrometryCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_MassSpectrometryComment() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new MassSpectrometryCommentBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_Comments_SequenceCautionComment_whenNoEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(new SequenceCautionCommentBuilder().build())
                        .build()
                        .gatherEvidences();
        assertTrue(evidences.isEmpty());
    }

    @Test
    void canGatherEvidencesFrom_Comments_SequenceCautionComment() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SequenceCautionCommentBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(1, evidences.size());
    }

    @Test
    void canGatherEvidencesFrom_multipleSourcesUsesSameEvidence_shouldReturnUniqueEvidence() {
        List<Evidence> evidences =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(
                                new SequenceCautionCommentBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .commentsAdd(
                                new MassSpectrometryCommentBuilder()
                                        .evidencesAdd(createEvidence())
                                        .build())
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .topology(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .orientation(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .subcellularLocationsAdd(
                                                new SubcellularLocationBuilder()
                                                        .location(
                                                                new SubcellularLocationValueBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new SubcellularLocationCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .positionsAdd(
                                                new RnaEditingPositionBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new RnaEditingCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .disease(
                                                new DiseaseBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new DiseaseCommentBuilder()
                                        .note(
                                                new NoteBuilder(
                                                                Collections.singletonList(
                                                                        createEvidenceValueWithSingleEvidence()))
                                                        .build())
                                        .build())
                        .commentsAdd(
                                new BPCPCommentBuilder()
                                        .kineticParameters(
                                                new KineticParametersBuilder()
                                                        .maximumVelocitiesAdd(
                                                                new MaximumVelocityBuilder()
                                                                        .evidencesAdd(
                                                                                createEvidence())
                                                                        .build())
                                                        .build())
                                        .build())
                        .referencesAdd(
                                new UniProtKBReferenceBuilder()
                                        .referenceCommentsAdd(
                                                new ReferenceCommentBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .genesAdd(
                                new GeneBuilder()
                                        .orderedLocusNamesAdd(
                                                new OrderedLocusNameBuilder()
                                                        .evidencesAdd(createEvidence())
                                                        .build())
                                        .build())
                        .build()
                        .gatherEvidences();
        assertEquals(2, evidences.size());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        UniProtKBEntry impl =
                new UniProtKBEntryImpl(
                        UniProtKBEntryType.SWISSPROT,
                        new UniProtKBAccessionImpl("acc"),
                        singletonList(new UniProtKBAccessionImpl("sec")),
                        new UniProtKBIdImpl("id"),
                        new EntryAuditBuilder().build(),
                        5.6D,
                        new OrganismBuilder().build(),
                        Collections.emptyList(),
                        ProteinExistence.TRANSCRIPT_LEVEL,
                        new ProteinDescriptionBuilder().build(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        null,
                        null,
                        null,
                        new SequenceBuilder("seq").build(),
                        null,
                        Collections.emptyList(),
                        null,
                        Collections.emptyMap());
        UniProtKBEntry obj = UniProtKBEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
