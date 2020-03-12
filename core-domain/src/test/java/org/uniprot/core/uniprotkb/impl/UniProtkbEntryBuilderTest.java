package org.uniprot.core.uniprotkb.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;

class UniProtkbEntryBuilderTest {
    private UniProtkbEntry minEntry =
            new UniProtkbEntryBuilder("acc", "id", UniProtkbEntryType.TREMBL).build();

    @Test
    void canCreateBuilderFromInstance() {
        UniProtkbEntryBuilder builder = UniProtkbEntryBuilder.from(minEntry);
        assertNotNull(builder);
    }

    @Test
    void accessionCanBeChangeFromBuilder_string() {
        String access = "new";
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession().getValue());
    }

    @Test
    void accessionCanBeChangeFromBuilder() {
        UniProtkbAccession access = new UniProtkbAccessionBuilder("new").build();
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder_string() {
        String id = "new";
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtkbId().getValue());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder() {
        UniProtkbId id = new UniProtkbIdBuilder("new").build();
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtkbId());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder() {
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry)
                        .entryType(UniProtkbEntryType.SWISSPROT)
                        .build();
        assertEquals(UniProtkbEntryType.SWISSPROT, entry.getEntryType());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder_butCannotBeNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UniProtkbEntryBuilder.from(minEntry).entryType(null).build());
    }

    @Test
    void canAddAuditEntry() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).entryAudit(entryAudit).build();
        assertEquals(entryAudit, entry.getEntryAudit());
    }

    @Test
    void canAddAnnotationScore() {
        double score = 454;
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).annotationScore(score).build();
        assertEquals(score, entry.getAnnotationScore());
        assertTrue(entry.hasAnnotationScore());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().build();
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).organism(organism).build();
        assertTrue(entry.hasOrganism());
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSet_proteinExistence() {
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry)
                        .proteinExistence(ProteinExistence.HOMOLOGY)
                        .build();
        assertTrue(entry.hasProteinExistence());
        assertEquals(ProteinExistence.HOMOLOGY, entry.getProteinExistence());
    }

    @Test
    void canSet_proteinDescription() {
        ProteinDescription description = new ProteinDescriptionBuilder().build();
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry).proteinDescription(description).build();
        assertTrue(entry.hasProteinDescription());
        assertEquals(description, entry.getProteinDescription());
    }

    @Test
    void canSet_sequence() {
        Sequence sequence = new SequenceBuilder("seq").build();
        UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canSet_internalSection() {
        InternalSection internalSection = new InternalSectionBuilder().build();
        UniProtkbEntry entry =
                UniProtkbEntryBuilder.from(minEntry).internalSection(internalSection).build();
        assertEquals(internalSection, entry.getInternalSection());
    }

    @Nested
    class InactiveEntry {
        private EntryInactiveReason reason =
                new EntryInactiveReasonBuilder().type(InactiveReasonType.DELETED).build();

        @Test
        void minimumEntryWithStrings() {
            UniProtkbEntry minimumInactiveEntry =
                    new UniProtkbEntryBuilder("acc", "id", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void minimumEntry() {
            UniProtkbEntry minimumInactiveEntry =
                    new UniProtkbEntryBuilder(
                                    new UniProtkbAccessionBuilder("acc").build(),
                                    new UniProtkbIdBuilder("id").build(),
                                    reason)
                            .build();

            assertFalse(minimumInactiveEntry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void nullStringAccessionNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new UniProtkbEntryBuilder(null, "id", reason).build());
        }

        @Test
        void nullUniprotIdAllowed() {
            UniProtkbEntry minimumInactiveEntry =
                    new UniProtkbEntryBuilder(new UniProtkbAccessionBuilder("acc").build(), reason)
                            .build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtkbId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void nullStringUniprotIdAllowed() {
            UniProtkbEntry minimumInactiveEntry = new UniProtkbEntryBuilder("acc", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtkbId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void canCreateInactiveFromInactiveEntry() {

            UniProtkbEntry inactive =
                    new UniProtkbEntryBuilder(
                                    minEntry.getPrimaryAccession(),
                                    minEntry.getUniProtkbId(),
                                    reason)
                            .build();

            UniProtkbEntry entry = UniProtkbEntryBuilder.from(inactive).build();

            assertFalse(entry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED, entry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void typeCannotBeOtherThanInactive() {
            UniProtkbEntry inactive =
                    new UniProtkbEntryBuilder(
                                    minEntry.getPrimaryAccession(),
                                    minEntry.getUniProtkbId(),
                                    reason)
                            .build();

            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtkbEntryBuilder.from(inactive)
                                    .entryType(UniProtkbEntryType.SWISSPROT)
                                    .build());
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtkbEntryBuilder.from(inactive)
                                    .entryType(UniProtkbEntryType.TREMBL)
                                    .build());
        }
    }

    @Nested
    class ActiveEntry {

        @Test
        void minimumEntryString() {
            assertEquals("acc", minEntry.getPrimaryAccession().getValue());
            assertEquals("id", minEntry.getUniProtkbId().getValue());
            assertEquals(UniProtkbEntryType.TREMBL, minEntry.getEntryType());
            assertTrue(minEntry.isActive());
        }

        @Test
        void minimumEntry() {
            UniProtkbAccession accession = new UniProtkbAccessionBuilder("acc").build();
            UniProtkbId protId = new UniProtkbIdBuilder("id").build();
            UniProtkbEntry minimumEntry =
                    new UniProtkbEntryBuilder(accession, protId, UniProtkbEntryType.SWISSPROT)
                            .build();

            assertEquals("acc", minimumEntry.getPrimaryAccession().getValue());
            assertEquals("id", minimumEntry.getUniProtkbId().getValue());
            assertEquals(UniProtkbEntryType.SWISSPROT, minimumEntry.getEntryType());
            assertTrue(minimumEntry.isActive());
        }

        @Test
        void nullStringAccessionNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            new UniProtkbEntryBuilder(null, "id", UniProtkbEntryType.SWISSPROT)
                                    .build());
        }

        @Test
        void nullStringUniprotIdNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            new UniProtkbEntryBuilder("acc", null, UniProtkbEntryType.TREMBL)
                                    .build());
        }

        @Test
        void canActiveFromActiveEntry() {
            UniProtkbEntry entry = UniProtkbEntryBuilder.from(minEntry).build();

            assertTrue(entry.isActive());
        }

        @Test
        void typeCannotBeInactive() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtkbEntryBuilder.from(minEntry)
                                    .entryType(UniProtkbEntryType.INACTIVE)
                                    .build());
        }
    }

    @Nested
    class secondaryAccessions {
        private UniProtkbAccession acc = new UniProtkbAccessionBuilder("abc").build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).secondaryAccessionsAdd(acc).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).secondaryAccessionsAdd(null).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertTrue(obj.getSecondaryAccessions().isEmpty());
            assertFalse(obj.hasSecondaryAccessions());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(emptyList())
                            .secondaryAccessionsAdd(acc)
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(singletonList(acc))
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .secondaryAccessionsAdd(acc)
                            .secondaryAccessionsSet(null)
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertTrue(obj.getSecondaryAccessions().isEmpty());
            assertFalse(obj.hasSecondaryAccessions());
        }
    }

    @Nested
    class organismHost {
        private OrganismHost host = new OrganismHostBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).organismHostsAdd(host).build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).organismHostsAdd(null).build();
            assertNotNull(obj.getOrganismHosts());
            assertTrue(obj.getOrganismHosts().isEmpty());
            assertFalse(obj.hasOrganismHosts());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .organismHostsSet(emptyList())
                            .organismHostsAdd(host)
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .organismHostsSet(singletonList(host))
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .organismHostsAdd(host)
                            .organismHostsSet(null)
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertTrue(obj.getOrganismHosts().isEmpty());
            assertFalse(obj.hasOrganismHosts());
        }
    }

    @Nested
    class genes {
        private Gene gene = new GeneBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).genesAdd(gene).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).genesAdd(null).build();
            assertNotNull(obj.getGenes());
            assertTrue(obj.getGenes().isEmpty());
            assertFalse(obj.hasGenes());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .genesSet(emptyList())
                            .genesAdd(gene)
                            .build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).genesSet(singletonList(gene)).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Gene> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).genesAdd(gene).genesSet(list).build();
            assertNotNull(obj.getOrganismHosts());
            assertTrue(obj.getOrganismHosts().isEmpty());
            assertFalse(obj.hasGenes());
        }
    }

    @Nested
    class comments {
        private Comment comment = new DiseaseCommentBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).commentsAdd(comment).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).commentsAdd(null).build();
            assertNotNull(obj.getComments());
            assertTrue(obj.getComments().isEmpty());
            assertFalse(obj.hasComments());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .commentsSet(emptyList())
                            .commentsAdd(comment)
                            .build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .commentsSet(singletonList(comment))
                            .build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Comment> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .commentsAdd(comment)
                            .commentsSet(list)
                            .build();
            assertNotNull(obj.getComments());
            assertTrue(obj.getComments().isEmpty());
            assertFalse(obj.hasComments());
        }
    }

    @Nested
    class features {
        private Feature feature = new FeatureBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).featuresAdd(feature).build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).featuresAdd(null).build();
            assertNotNull(obj.getFeatures());
            assertTrue(obj.getFeatures().isEmpty());
            assertFalse(obj.hasFeatures());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .featuresSet(emptyList())
                            .featuresAdd(feature)
                            .build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .featuresSet(singletonList(feature))
                            .build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Feature> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .featuresAdd(feature)
                            .featuresSet(list)
                            .build();
            assertNotNull(obj.getFeatures());
            assertTrue(obj.getFeatures().isEmpty());
            assertFalse(obj.hasFeatures());
        }
    }

    @Nested
    class geneLocations {
        private GeneLocation location = new GeneLocationBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).geneLocationsAdd(location).build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).geneLocationsAdd(null).build();
            assertNotNull(obj.getGeneLocations());
            assertTrue(obj.getGeneLocations().isEmpty());
            assertFalse(obj.hasGeneLocations());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .geneLocationsSet(emptyList())
                            .geneLocationsAdd(location)
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .geneLocationsSet(singletonList(location))
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<GeneLocation> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .geneLocationsAdd(location)
                            .geneLocationsSet(list)
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertTrue(obj.getGeneLocations().isEmpty());
            assertFalse(obj.hasGeneLocations());
        }
    }

    @Nested
    class keywords {
        private Keyword keyword = new KeywordBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).keywordsAdd(keyword).build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).keywordsAdd(null).build();
            assertNotNull(obj.getKeywords());
            assertTrue(obj.getKeywords().isEmpty());
            assertFalse(obj.hasKeywords());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .keywordsSet(emptyList())
                            .keywordsAdd(keyword)
                            .build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .keywordsSet(singletonList(keyword))
                            .build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Keyword> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .keywordsAdd(keyword)
                            .keywordsSet(list)
                            .build();
            assertNotNull(obj.getKeywords());
            assertTrue(obj.getKeywords().isEmpty());
            assertFalse(obj.hasKeywords());
        }
    }

    @Nested
    class references {
        private UniProtkbReference reference = new UniProtkbReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).referencesAdd(reference).build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).referencesAdd(null).build();
            assertNotNull(obj.getReferences());
            assertTrue(obj.getReferences().isEmpty());
            assertFalse(obj.hasReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .referencesSet(emptyList())
                            .referencesAdd(reference)
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .referencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtkbReference> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .referencesAdd(reference)
                            .referencesSet(list)
                            .build();
            assertNotNull(obj.getReferences());
            assertTrue(obj.getReferences().isEmpty());
            assertFalse(obj.hasReferences());
        }
    }

    @Nested
    class databaseCrossReferences {
        private UniProtkbCrossReference reference = new UniProtCrossReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesAdd(reference)
                            .build();
            assertNotNull(obj.getUniProtkbCrossReferences());
            assertFalse(obj.getUniProtkbCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry).uniProtCrossReferencesAdd(null).build();
            assertNotNull(obj.getUniProtkbCrossReferences());
            assertTrue(obj.getUniProtkbCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(emptyList())
                            .uniProtCrossReferencesAdd(reference)
                            .build();
            assertNotNull(obj.getUniProtkbCrossReferences());
            assertFalse(obj.getUniProtkbCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getUniProtkbCrossReferences());
            assertFalse(obj.getUniProtkbCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtkbCrossReference> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesAdd(reference)
                            .uniProtCrossReferencesSet(list)
                            .build();
            assertNotNull(obj.getUniProtkbCrossReferences());
            assertTrue(obj.getUniProtkbCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }
    }

    @Nested
    class lineages {
        private TaxonomyLineage lineage = new TaxonomyLineageBuilder().build();

        @Test
        void canAddSingle() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).lineagesAdd(lineage).build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtkbEntry obj = UniProtkbEntryBuilder.from(minEntry).lineagesAdd(null).build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .lineagesSet(emptyList())
                            .lineagesAdd(lineage)
                            .build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void canSetList() {
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .lineagesSet(singletonList(lineage))
                            .build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<TaxonomyLineage> list = null;
            UniProtkbEntry obj =
                    UniProtkbEntryBuilder.from(minEntry)
                            .lineagesAdd(lineage)
                            .lineagesSet(list)
                            .build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }
    }
}