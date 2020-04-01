package org.uniprot.core.uniprotkb.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.impl.CofactorCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.feature.FeatureType;
import org.uniprot.core.uniprotkb.feature.impl.FeatureBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;

class UniProtKBEntryBuilderTest {
    private UniProtKBEntry minEntry =
            new UniProtKBEntryBuilder("acc", "id", UniProtKBEntryType.TREMBL).build();

    @Test
    void canCreateBuilderFromInstance() {
        UniProtKBEntryBuilder builder = UniProtKBEntryBuilder.from(minEntry);
        assertNotNull(builder);
    }

    @Test
    void accessionCanBeChangeFromBuilder_string() {
        String access = "new";
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession().getValue());
    }

    @Test
    void accessionCanBeChangeFromBuilder() {
        UniProtKBAccession access = new UniProtKBAccessionBuilder("new").build();
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).primaryAccession(access).build();
        assertEquals(access, entry.getPrimaryAccession());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder_string() {
        String id = "new";
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtkbId().getValue());
    }

    @Test
    void uniProtIdCanBeChangeFromBuilder() {
        UniProtKBId id = new UniProtKBIdBuilder("new").build();
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).uniProtId(id).build();
        assertEquals(id, entry.getUniProtkbId());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .build();
        assertEquals(UniProtKBEntryType.SWISSPROT, entry.getEntryType());
    }

    @Test
    void entryTypeCanBeChangeFromBuilder_butCannotBeNull() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UniProtKBEntryBuilder.from(minEntry).entryType(null).build());
    }

    @Test
    void canAddAuditEntry() {
        EntryAudit entryAudit = new EntryAuditBuilder().build();
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).entryAudit(entryAudit).build();
        assertEquals(entryAudit, entry.getEntryAudit());
    }

    @Test
    void canAddAnnotationScore() {
        double score = 454;
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).annotationScore(score).build();
        assertEquals(score, entry.getAnnotationScore());
        assertTrue(entry.hasAnnotationScore());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().build();
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).organism(organism).build();
        assertTrue(entry.hasOrganism());
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSet_proteinExistence() {
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .proteinExistence(ProteinExistence.HOMOLOGY)
                        .build();
        assertTrue(entry.hasProteinExistence());
        assertEquals(ProteinExistence.HOMOLOGY, entry.getProteinExistence());
    }

    @Test
    void canSet_proteinDescription() {
        ProteinDescription description = new ProteinDescriptionBuilder().build();
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).proteinDescription(description).build();
        assertTrue(entry.hasProteinDescription());
        assertEquals(description, entry.getProteinDescription());
    }

    @Test
    void canSet_sequence() {
        Sequence sequence = new SequenceBuilder("seq").build();
        UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canSet_internalSection() {
        InternalSection internalSection = new InternalSectionBuilder().build();
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).internalSection(internalSection).build();
        assertEquals(internalSection, entry.getInternalSection());
    }

    @Test
    void cannotAddExtraAttributeWithNullValue() {
        UniProtKBEntryBuilder.ExtraAttributeName attrib1 =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_COMMENT_TYPE_ATTRIB;
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).extraAttributesAdd(attrib1, null).build();
        assertNotNull(entry);
        assertEquals(0, entry.getExtraAttributes().size());
    }

    @Test
    void canAddExtraAttribute() {
        UniProtKBEntryBuilder.ExtraAttributeName attrib1 =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_COMMENT_TYPE_ATTRIB;
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry).extraAttributesAdd(attrib1, "value1").build();
        assertNotNull(entry);
        assertEquals(1, entry.getExtraAttributes().size());
        assertTrue(entry.getExtraAttributes().containsKey(attrib1.getDisplayName()));
        assertEquals("value1", entry.getExtraAttributes().get(attrib1.getDisplayName()));
    }

    @Test
    void canAddExtraAttributes() {
        UniProtKBEntryBuilder.ExtraAttributeName attrib1 =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_COMMENT_TYPE_ATTRIB;
        UniProtKBEntryBuilder.ExtraAttributeName attrib2 =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_FEATURE_TYPE_ATTRIB;
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .extraAttributesAdd(attrib1, "value1")
                        .extraAttributesAdd(attrib2, "value2")
                        .build();
        assertNotNull(entry);
        assertEquals(2, entry.getExtraAttributes().size());
        assertTrue(entry.getExtraAttributes().containsKey(attrib1.getDisplayName()));
        assertEquals("value1", entry.getExtraAttributes().get(attrib1.getDisplayName()));
        assertTrue(entry.getExtraAttributes().containsKey(attrib2.getDisplayName()));
        assertEquals("value2", entry.getExtraAttributes().get(attrib2.getDisplayName()));
    }

    @Test
    void canOverwriteExtraAttributeValue() {
        UniProtKBEntryBuilder.ExtraAttributeName attrib1 =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_COMMENT_TYPE_ATTRIB;
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .extraAttributesAdd(attrib1, "oldValue")
                        .extraAttributesAdd(attrib1, "latestValue")
                        .build();
        assertNotNull(entry);
        assertEquals(1, entry.getExtraAttributes().size());
        assertTrue(entry.getExtraAttributes().containsKey(attrib1.getDisplayName()));
        assertEquals("latestValue", entry.getExtraAttributes().get(attrib1.getDisplayName()));
    }

    @Test
    void canAddExtraAttributesOnCommentTypeCount() {
        UniProtKBEntryBuilder.ExtraAttributeName commentAttrib =
                UniProtKBEntryBuilder.ExtraAttributeName.COUNT_BY_FEATURE_TYPE_ATTRIB;
        Comment comment = new DiseaseCommentBuilder().build();
        UniProtKBEntry entry =
                UniProtKBEntryBuilder.from(minEntry)
                        .commentsAdd(comment)
                        .extraAttributesAdd(commentAttrib, "value1")
                        .build();
        assertNotNull(entry);
        assertEquals(2, entry.getExtraAttributes().size());
        assertTrue(
                entry.getExtraAttributes()
                        .containsKey(
                                UniProtKBEntryBuilder.ExtraAttributeName
                                        .COUNT_BY_COMMENT_TYPE_ATTRIB
                                        .getDisplayName()));
        assertTrue(entry.getExtraAttributes().containsKey(commentAttrib.getDisplayName()));
        assertEquals("value1", entry.getExtraAttributes().get(commentAttrib.getDisplayName()));
    }

    @Nested
    class InactiveEntry {
        private EntryInactiveReason reason =
                new EntryInactiveReasonBuilder().type(InactiveReasonType.DELETED).build();

        @Test
        void minimumEntryWithStrings() {
            UniProtKBEntry minimumInactiveEntry =
                    new UniProtKBEntryBuilder("acc", "id", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void minimumEntry() {
            UniProtKBEntry minimumInactiveEntry =
                    new UniProtKBEntryBuilder(
                                    new UniProtKBAccessionBuilder("acc").build(),
                                    new UniProtKBIdBuilder("id").build(),
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
                    () -> new UniProtKBEntryBuilder(null, "id", reason).build());
        }

        @Test
        void nullUniprotIdAllowed() {
            UniProtKBEntry minimumInactiveEntry =
                    new UniProtKBEntryBuilder(new UniProtKBAccessionBuilder("acc").build(), reason)
                            .build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtkbId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void nullStringUniprotIdAllowed() {
            UniProtKBEntry minimumInactiveEntry = new UniProtKBEntryBuilder("acc", reason).build();

            assertFalse(minimumInactiveEntry.isActive());
            assertNull(minimumInactiveEntry.getUniProtkbId());
            assertEquals(
                    InactiveReasonType.DELETED,
                    minimumInactiveEntry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void canCreateInactiveFromInactiveEntry() {

            UniProtKBEntry inactive =
                    new UniProtKBEntryBuilder(
                                    minEntry.getPrimaryAccession(),
                                    minEntry.getUniProtkbId(),
                                    reason)
                            .build();

            UniProtKBEntry entry = UniProtKBEntryBuilder.from(inactive).build();

            assertFalse(entry.isActive());
            assertEquals(
                    InactiveReasonType.DELETED, entry.getInactiveReason().getInactiveReasonType());
        }

        @Test
        void typeCannotBeOtherThanInactive() {
            UniProtKBEntry inactive =
                    new UniProtKBEntryBuilder(
                                    minEntry.getPrimaryAccession(),
                                    minEntry.getUniProtkbId(),
                                    reason)
                            .build();

            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtKBEntryBuilder.from(inactive)
                                    .entryType(UniProtKBEntryType.SWISSPROT)
                                    .build());
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtKBEntryBuilder.from(inactive)
                                    .entryType(UniProtKBEntryType.TREMBL)
                                    .build());
        }
    }

    @Nested
    class ActiveEntry {

        @Test
        void minimumEntryString() {
            assertEquals("acc", minEntry.getPrimaryAccession().getValue());
            assertEquals("id", minEntry.getUniProtkbId().getValue());
            assertEquals(UniProtKBEntryType.TREMBL, minEntry.getEntryType());
            assertTrue(minEntry.isActive());
        }

        @Test
        void minimumEntry() {
            UniProtKBAccession accession = new UniProtKBAccessionBuilder("acc").build();
            UniProtKBId protId = new UniProtKBIdBuilder("id").build();
            UniProtKBEntry minimumEntry =
                    new UniProtKBEntryBuilder(accession, protId, UniProtKBEntryType.SWISSPROT)
                            .build();

            assertEquals("acc", minimumEntry.getPrimaryAccession().getValue());
            assertEquals("id", minimumEntry.getUniProtkbId().getValue());
            assertEquals(UniProtKBEntryType.SWISSPROT, minimumEntry.getEntryType());
            assertTrue(minimumEntry.isActive());
        }

        @Test
        void nullStringAccessionNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            new UniProtKBEntryBuilder(null, "id", UniProtKBEntryType.SWISSPROT)
                                    .build());
        }

        @Test
        void nullStringUniprotIdNotAllowed() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            new UniProtKBEntryBuilder("acc", null, UniProtKBEntryType.TREMBL)
                                    .build());
        }

        @Test
        void canActiveFromActiveEntry() {
            UniProtKBEntry entry = UniProtKBEntryBuilder.from(minEntry).build();

            assertTrue(entry.isActive());
        }

        @Test
        void typeCannotBeInactive() {
            assertThrows(
                    IllegalArgumentException.class,
                    () ->
                            UniProtKBEntryBuilder.from(minEntry)
                                    .entryType(UniProtKBEntryType.INACTIVE)
                                    .build());
        }
    }

    @Nested
    class secondaryAccessions {
        private UniProtKBAccession acc = new UniProtKBAccessionBuilder("abc").build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).secondaryAccessionsAdd(acc).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).secondaryAccessionsAdd(null).build();
            assertNotNull(obj.getSecondaryAccessions());
            assertTrue(obj.getSecondaryAccessions().isEmpty());
            assertFalse(obj.hasSecondaryAccessions());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(emptyList())
                            .secondaryAccessionsAdd(acc)
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .secondaryAccessionsSet(singletonList(acc))
                            .build();
            assertNotNull(obj.getSecondaryAccessions());
            assertFalse(obj.getSecondaryAccessions().isEmpty());
            assertTrue(obj.hasSecondaryAccessions());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
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
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).organismHostsAdd(host).build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).organismHostsAdd(null).build();
            assertNotNull(obj.getOrganismHosts());
            assertTrue(obj.getOrganismHosts().isEmpty());
            assertFalse(obj.hasOrganismHosts());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .organismHostsSet(emptyList())
                            .organismHostsAdd(host)
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .organismHostsSet(singletonList(host))
                            .build();
            assertNotNull(obj.getOrganismHosts());
            assertFalse(obj.getOrganismHosts().isEmpty());
            assertTrue(obj.hasOrganismHosts());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
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
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).genesAdd(gene).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).genesAdd(null).build();
            assertNotNull(obj.getGenes());
            assertTrue(obj.getGenes().isEmpty());
            assertFalse(obj.hasGenes());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .genesSet(emptyList())
                            .genesAdd(gene)
                            .build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).genesSet(singletonList(gene)).build();
            assertNotNull(obj.getGenes());
            assertFalse(obj.getGenes().isEmpty());
            assertTrue(obj.hasGenes());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Gene> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).genesAdd(gene).genesSet(list).build();
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
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).commentsAdd(comment).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
            assertEquals(1, obj.getExtraAttributes().size());
            assertTrue(
                    obj.getExtraAttributes()
                            .keySet()
                            .contains(
                                    UniProtKBEntryBuilder.ExtraAttributeName
                                            .COUNT_BY_COMMENT_TYPE_ATTRIB
                                            .getDisplayName()));
            Map<String, Object> countByType =
                    (Map<String, Object>)
                            obj.getExtraAttributes()
                                    .get(
                                            UniProtKBEntryBuilder.ExtraAttributeName
                                                    .COUNT_BY_COMMENT_TYPE_ATTRIB
                                                    .getDisplayName());
            assertEquals(1, countByType.size());
            assertTrue(countByType.containsKey(CommentType.DISEASE.toDisplayName()));
            assertEquals(1, countByType.get(CommentType.DISEASE.toDisplayName()));
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).commentsAdd(null).build();
            assertNotNull(obj.getComments());
            assertTrue(obj.getComments().isEmpty());
            assertFalse(obj.hasComments());
            assertTrue(obj.getExtraAttributes().isEmpty());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .commentsSet(emptyList())
                            .commentsAdd(comment)
                            .build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
            assertEquals(1, obj.getExtraAttributes().size());
            assertTrue(
                    obj.getExtraAttributes()
                            .keySet()
                            .contains(
                                    UniProtKBEntryBuilder.ExtraAttributeName
                                            .COUNT_BY_COMMENT_TYPE_ATTRIB
                                            .getDisplayName()));
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .commentsSet(singletonList(comment))
                            .build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
            assertEquals(1, obj.getExtraAttributes().size());
            assertTrue(
                    obj.getExtraAttributes()
                            .keySet()
                            .contains(
                                    UniProtKBEntryBuilder.ExtraAttributeName
                                            .COUNT_BY_COMMENT_TYPE_ATTRIB
                                            .getDisplayName()));
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Comment> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .commentsAdd(comment)
                            .commentsSet(list)
                            .build();
            assertNotNull(obj.getComments());
            assertTrue(obj.getComments().isEmpty());
            assertFalse(obj.hasComments());
            assertTrue(obj.getExtraAttributes().isEmpty());
        }

        @Test
        void canAddMoreThanOneComment() {
            Comment comment2 = new DiseaseCommentBuilder().build();
            Comment comment3 = new CofactorCommentBuilder().build();
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).commentsAdd(comment).commentsAdd(comment3).commentsAdd(comment2).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getComments().isEmpty());
            assertTrue(obj.hasComments());
            assertEquals(1, obj.getExtraAttributes().size());
            Map<String, Object> countByType =
                    (Map<String, Object>)
                            obj.getExtraAttributes()
                                    .get(
                                            UniProtKBEntryBuilder.ExtraAttributeName
                                                    .COUNT_BY_COMMENT_TYPE_ATTRIB
                                                    .getDisplayName());
            assertEquals(2, countByType.size());
            assertTrue(countByType.containsKey(CommentType.DISEASE.toDisplayName()));
            assertEquals(2, countByType.get(CommentType.DISEASE.toDisplayName()));
            assertTrue(countByType.containsKey(CommentType.COFACTOR.toDisplayName()));
            assertEquals(1, countByType.get(CommentType.COFACTOR.toDisplayName()));
        }
    }

    @Nested
    class features {
        private Feature feature = new FeatureBuilder().type(FeatureType.CHAIN).build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).featuresAdd(feature).build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
            assertEquals(1, obj.getExtraAttributes().size());
            assertTrue(
                    obj.getExtraAttributes()
                            .keySet()
                            .contains(
                                    UniProtKBEntryBuilder.ExtraAttributeName
                                            .COUNT_BY_FEATURE_TYPE_ATTRIB
                                            .getDisplayName()));
            Map<String, Object> countByType =
                    (Map<String, Object>)
                            obj.getExtraAttributes()
                                    .get(
                                            UniProtKBEntryBuilder.ExtraAttributeName
                                                    .COUNT_BY_FEATURE_TYPE_ATTRIB
                                                    .getDisplayName());
            assertEquals(1, countByType.size());
            assertTrue(countByType.containsKey(FeatureType.CHAIN.toDisplayName()));
            assertEquals(1, countByType.get(FeatureType.CHAIN.toDisplayName()));
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).featuresAdd(null).build();
            assertNotNull(obj.getFeatures());
            assertTrue(obj.getFeatures().isEmpty());
            assertFalse(obj.hasFeatures());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .featuresSet(emptyList())
                            .featuresAdd(feature)
                            .build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .featuresSet(singletonList(feature))
                            .build();
            assertNotNull(obj.getFeatures());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Feature> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .featuresAdd(feature)
                            .featuresSet(list)
                            .build();
            assertNotNull(obj.getFeatures());
            assertTrue(obj.getFeatures().isEmpty());
            assertFalse(obj.hasFeatures());
        }

        @Test
        void canAddMoreThanOneFeature() {
            Feature feature2 = new FeatureBuilder().type(FeatureType.CHAIN).build();
            Feature feature3 = new FeatureBuilder().type(FeatureType.VARIANT).build();
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).featuresAdd(feature).featuresAdd(feature3).featuresAdd(feature2).build();
            assertNotNull(obj.getComments());
            assertFalse(obj.getFeatures().isEmpty());
            assertTrue(obj.hasFeatures());
            assertEquals(1, obj.getExtraAttributes().size());
            Map<String, Object> countByType =
                    (Map<String, Object>)
                            obj.getExtraAttributes()
                                    .get(
                                            UniProtKBEntryBuilder.ExtraAttributeName
                                                    .COUNT_BY_FEATURE_TYPE_ATTRIB
                                                    .getDisplayName());
            assertEquals(2, countByType.size());
            assertTrue(countByType.containsKey(FeatureType.CHAIN.toDisplayName()));
            assertEquals(2, countByType.get(FeatureType.CHAIN.toDisplayName()));
            assertTrue(countByType.containsKey(FeatureType.VARIANT.toDisplayName()));
            assertEquals(1, countByType.get(FeatureType.VARIANT.toDisplayName()));
        }
    }

    @Nested
    class geneLocations {
        private GeneLocation location = new GeneLocationBuilder().build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).geneLocationsAdd(location).build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).geneLocationsAdd(null).build();
            assertNotNull(obj.getGeneLocations());
            assertTrue(obj.getGeneLocations().isEmpty());
            assertFalse(obj.hasGeneLocations());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .geneLocationsSet(emptyList())
                            .geneLocationsAdd(location)
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .geneLocationsSet(singletonList(location))
                            .build();
            assertNotNull(obj.getGeneLocations());
            assertFalse(obj.getGeneLocations().isEmpty());
            assertTrue(obj.hasGeneLocations());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<GeneLocation> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
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
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).keywordsAdd(keyword).build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).keywordsAdd(null).build();
            assertNotNull(obj.getKeywords());
            assertTrue(obj.getKeywords().isEmpty());
            assertFalse(obj.hasKeywords());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .keywordsSet(emptyList())
                            .keywordsAdd(keyword)
                            .build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .keywordsSet(singletonList(keyword))
                            .build();
            assertNotNull(obj.getKeywords());
            assertFalse(obj.getKeywords().isEmpty());
            assertTrue(obj.hasKeywords());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<Keyword> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
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
        private UniProtKBReference reference = new UniProtKBReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).referencesAdd(reference).build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).referencesAdd(null).build();
            assertNotNull(obj.getReferences());
            assertTrue(obj.getReferences().isEmpty());
            assertFalse(obj.hasReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .referencesSet(emptyList())
                            .referencesAdd(reference)
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .referencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getReferences());
            assertFalse(obj.getReferences().isEmpty());
            assertTrue(obj.hasReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtKBReference> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
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
        private UniProtKBCrossReference reference = new UniProtCrossReferenceBuilder().build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesAdd(reference)
                            .build();
            assertNotNull(obj.getUniProtKBCrossReferences());
            assertFalse(obj.getUniProtKBCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry).uniProtCrossReferencesAdd(null).build();
            assertNotNull(obj.getUniProtKBCrossReferences());
            assertTrue(obj.getUniProtKBCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(emptyList())
                            .uniProtCrossReferencesAdd(reference)
                            .build();
            assertNotNull(obj.getUniProtKBCrossReferences());
            assertFalse(obj.getUniProtKBCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesSet(singletonList(reference))
                            .build();
            assertNotNull(obj.getUniProtKBCrossReferences());
            assertFalse(obj.getUniProtKBCrossReferences().isEmpty());
            assertTrue(obj.hasUniProtCrossReferences());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<UniProtKBCrossReference> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .uniProtCrossReferencesAdd(reference)
                            .uniProtCrossReferencesSet(list)
                            .build();
            assertNotNull(obj.getUniProtKBCrossReferences());
            assertTrue(obj.getUniProtKBCrossReferences().isEmpty());
            assertFalse(obj.hasUniProtCrossReferences());
        }
    }

    @Nested
    class lineages {
        private TaxonomyLineage lineage = new TaxonomyLineageBuilder().build();

        @Test
        void canAddSingle() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).lineagesAdd(lineage).build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void null_singleAdd_willBeIgnore() {
            UniProtKBEntry obj = UniProtKBEntryBuilder.from(minEntry).lineagesAdd(null).build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }

        @Test
        void set_willConvertUnModifiable_toModifiable() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .lineagesSet(emptyList())
                            .lineagesAdd(lineage)
                            .build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void canSetList() {
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .lineagesSet(singletonList(lineage))
                            .build();
            assertNotNull(obj.getLineages());
            assertFalse(obj.getLineages().isEmpty());
        }

        @Test
        void previousAddedWillBeIgnoreUponSet() {
            List<TaxonomyLineage> list = null;
            UniProtKBEntry obj =
                    UniProtKBEntryBuilder.from(minEntry)
                            .lineagesAdd(lineage)
                            .lineagesSet(list)
                            .build();
            assertNotNull(obj.getLineages());
            assertTrue(obj.getLineages().isEmpty());
        }
    }
}
