package org.uniprot.core.uniprot.impl;

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
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.impl.FeatureBuilder;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprot.taxonomy.impl.OrganismHostBuilder;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.impl.UniProtCrossReferenceBuilder;

class UniProtEntryBuilderTest {
    private UniProtEntry minEntry =
            new UniProtEntryBuilder("acc", "id", UniProtEntryType.TREMBL).build();

    @Test
    void canCreateBuilderFromInstance() {
        UniProtEntryBuilder builder = UniProtEntryBuilder.from(minEntry);
        assertNotNull(builder);
    }

    @Test
    void accessionCanBeChangeFromBuilder_string() {
        String access = "new";
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession().getValue());
    }

    @Test
    void accessionCanBeChangeFromBuilder() {
        UniProtAccession access = new UniProtAccessionBuilder("new").build();
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder_string() {
        String id = "new";
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtId().getValue());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder() {
        UniProtId id = new UniProtIdBuilder("new").build();
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtId());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder() {
        UniProtEntry entry =
                UniProtEntryBuilder.from(minEntry).entryType(UniProtEntryType.SWISSPROT).build();
        assertEquals(UniProtEntryType.SWISSPROT, entry.getEntryType());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder_butCannotBeNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UniProtEntryBuilder.from(minEntry).entryType(null).build());
    }

    @Test
    void canAddAuditEntry() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).entryAudit(entryAudit).build();
        assertEquals(entryAudit, entry.getEntryAudit());
    }

    @Test
    void canAddAnnotationScore() {
        double score = 454;
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).annotationScore(score).build();
        assertEquals(score, entry.getAnnotationScore());
        assertTrue(entry.hasAnnotationScore());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().build();
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).organism(organism).build();
        assertTrue(entry.hasOrganism());
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSet_proteinExistence() {
        UniProtEntry entry =
                UniProtEntryBuilder.from(minEntry)
                        .proteinExistence(ProteinExistence.HOMOLOGY)
                        .build();
        assertTrue(entry.hasProteinExistence());
        assertEquals(ProteinExistence.HOMOLOGY, entry.getProteinExistence());
    }

    @Test
    void canSet_proteinDescription() {
        ProteinDescription description = new ProteinDescriptionBuilder().build();
        UniProtEntry entry =
                UniProtEntryBuilder.from(minEntry).proteinDescription(description).build();
        assertTrue(entry.hasProteinDescription());
        assertEquals(description, entry.getProteinDescription());
    }

    @Test
    void canSet_sequence() {
        Sequence sequence = new SequenceBuilder("seq").build();
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canSet_internalSection() {
        InternalSection internalSection = new InternalSectionBuilder().build();
        UniProtEntry entry =
                UniProtEntryBuilder.from(minEntry).internalSection(internalSection).build();
        assertEquals(internalSection, entry.getInternalSection());
    }

    @Nested
    class InactiveEntry {
        private EntryInactiveReason reason =
                new EntryInactiveReasonBuilder().type(InactiveReasonType.DELETED).build();

        @Test
        void minimumEntryWithStrings() {
            UniProtEntry minimumInactiveEntry =
                    new UniProtEntryBuilder("acc", "id", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void minimumEntry() {
            UniProtEntry minimumInactiveEntry =
                    new UniProtEntryBuilder(
                                    new UniProtAccessionBuilder("acc").build(),
                                    new UniProtIdBuilder("id").build(),
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
                    () -> new UniProtEntryBuilder(null, "id", reason).build());
        }

        @Test
        void nullUniprotIdAllowed() {
            UniProtEntry minimumInactiveEntry =
                    new UniProtEntryBuilder(new UniProtAccessionBuilder("acc").build(), reason)
                            .build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void nullStringUniprotIdAllowed() {
            UniProtEntry minimumInactiveEntry = new UniProtEntryBuilder("acc", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void canCreateInactiveFromInactiveEntry() {

            UniProtEntry inactive =
                    new UniProtEntryBuilder(
                                    minEntry.getPrimaryAccession(), minEntry.getUniProtId(), reason)
                            .build();

            UniProtEntry entry = UniProtEntryBuilder.from(inactive).build();

            assertFalse(entry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED, entry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void typeCannotBeOtherThanInactive() {
            UniProtEntry inactive =
                    new UniProtEntryBuilder(
                                    minEntry.getPrimaryAccession(), minEntry.getUniProtId(), reason)
                            .build();

            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtEntryBuilder.from(inactive)
                                    .entryType(UniProtEntryType.SWISSPROT)
                                    .build());
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtEntryBuilder.from(inactive)
                                    .entryType(UniProtEntryType.TREMBL)
                                    .build());
        }
    }

    @Nested
    class ActiveEntry {

        @Test
        void minimumEntryString() {
            assertEquals("acc", minEntry.getPrimaryAccession().getValue());
            assertEquals("id", minEntry.getUniProtId().getValue());
            assertEquals(UniProtEntryType.TREMBL, minEntry.getEntryType());
            assertTrue(minEntry.isActive());
        }

        @Test
        void minimumEntry() {
            UniProtAccession accession = new UniProtAccessionBuilder("acc").build();
            UniProtId protId = new UniProtIdBuilder("id").build();
            UniProtEntry minimumEntry =
                    new UniProtEntryBuilder(accession, protId, UniProtEntryType.SWISSPROT).build();

            assertEquals("acc", minimumEntry.getPrimaryAccession().getValue());
            assertEquals("id", minimumEntry.getUniProtId().getValue());
            assertEquals(UniProtEntryType.SWISSPROT, minimumEntry.getEntryType());
            assertTrue(minimumEntry.isActive());
        }

        @Test
        void nullStringAccessionNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new UniProtEntryBuilder(null, "id", UniProtEntryType.SWISSPROT).build());
        }

        @Test
        void nullStringUniprotIdNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new UniProtEntryBuilder("acc", null, UniProtEntryType.TREMBL).build());
        }

        @Test
        void canActiveFromActiveEntry() {
            UniProtEntry entry = UniProtEntryBuilder.from(minEntry).build();

            assertTrue(entry.isActive());
        }

        @Test
        void typeCannotBeInactive() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtEntryBuilder.from(minEntry)
                                    .entryType(UniProtEntryType.INACTIVE)
                                    .build());
        }
    }

    @Nested
    class secondaryAccessions {
        private UniProtAccession acc = new UniProtAccessionBuilder("abc").build();

        @Test
        void canAddSingle() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).secondaryAccessionsAdd(acc).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).secondaryAccessionsAdd(null).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertTrue(obj.getSecondaryAccessions().isEmpty());
            assertFalse(obj.hasSecondaryAccessions());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(emptyList())
                            .secondaryAccessionsAdd(acc)
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(singletonList(acc))
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).organismHostsAdd(host).build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).organismHostsAdd(null).build();
            assertNotNull(obj.getOrganismHosts());
            assertTrue(obj.getOrganismHosts().isEmpty());
            assertFalse(obj.hasOrganismHosts());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .organismHostsSet(emptyList())
                            .organismHostsAdd(host)
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .organismHostsSet(singletonList(host))
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).genesAdd(gene).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).genesAdd(null).build();
            assertNotNull(obj.getGenes());
            assertTrue(obj.getGenes().isEmpty());
            assertFalse(obj.hasGenes());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).genesSet(emptyList()).genesAdd(gene).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).genesSet(singletonList(gene)).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Gene> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).genesAdd(gene).genesSet(list).build();
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
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).commentsAdd(comment).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).commentsAdd(null).build();
            assertNotNull(obj.getComments());
            assertTrue(obj.getComments().isEmpty());
            assertFalse(obj.hasComments());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .commentsSet(emptyList())
                            .commentsAdd(comment)
                            .build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).commentsSet(singletonList(comment)).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Comment> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).featuresAdd(feature).build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).featuresAdd(null).build();
            assertNotNull(obj.getFeatures());
            assertTrue(obj.getFeatures().isEmpty());
            assertFalse(obj.hasFeatures());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .featuresSet(emptyList())
                            .featuresAdd(feature)
                            .build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).featuresSet(singletonList(feature)).build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Feature> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).geneLocationsAdd(location).build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).geneLocationsAdd(null).build();
            assertNotNull(obj.getGeneLocations());
            assertTrue(obj.getGeneLocations().isEmpty());
            assertFalse(obj.hasGeneLocations());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .geneLocationsSet(emptyList())
                            .geneLocationsAdd(location)
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .geneLocationsSet(singletonList(location))
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<GeneLocation> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).keywordsAdd(keyword).build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).keywordsAdd(null).build();
            assertNotNull(obj.getKeywords());
            assertTrue(obj.getKeywords().isEmpty());
            assertFalse(obj.hasKeywords());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .keywordsSet(emptyList())
                            .keywordsAdd(keyword)
                            .build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).keywordsSet(singletonList(keyword)).build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Keyword> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
        private UniProtReference reference = new UniProtReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).referencesAdd(reference).build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).referencesAdd(null).build();
            assertNotNull(obj.getReferences());
            assertTrue(obj.getReferences().isEmpty());
            assertFalse(obj.hasReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .referencesSet(emptyList())
                            .referencesAdd(reference)
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .referencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtReference> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
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
        private UniProtCrossReference reference = new UniProtCrossReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).uniProtCrossReferencesAdd(reference).build();
            assertNotNull(obj.getUniProtCrossReferences());
            assertFalse(obj.getUniProtCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).uniProtCrossReferencesAdd(null).build();
            assertNotNull(obj.getUniProtCrossReferences());
            assertTrue(obj.getUniProtCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(emptyList())
                            .uniProtCrossReferencesAdd(reference)
                            .build();
            assertNotNull(obj.getUniProtCrossReferences());
            assertFalse(obj.getUniProtCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getUniProtCrossReferences());
            assertFalse(obj.getUniProtCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtCrossReference> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesAdd(reference)
                            .uniProtCrossReferencesSet(list)
                            .build();
            assertNotNull(obj.getUniProtCrossReferences());
            assertTrue(obj.getUniProtCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }
    }

    @Nested
    class lineages {
        private TaxonomyLineage lineage = new TaxonomyLineageBuilder().build();

        @Test
        void canAddSingle() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).lineagesAdd(lineage).build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtEntry obj = UniProtEntryBuilder.from(minEntry).lineagesAdd(null).build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .lineagesSet(emptyList())
                            .lineagesAdd(lineage)
                            .build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void canSetList() {
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry).lineagesSet(singletonList(lineage)).build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<TaxonomyLineage> list = null;
            UniProtEntry obj =
                    UniProtEntryBuilder.from(minEntry)
                            .lineagesAdd(lineage)
                            .lineagesSet(list)
                            .build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }
    }
}
