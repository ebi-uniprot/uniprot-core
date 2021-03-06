package org.uniprot.core.parser.tsv.taxonomy;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.taxonomy.impl.TaxonomyEntryBuilder;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;
import org.uniprot.core.taxonomy.impl.TaxonomyStatisticsBuilder;
import org.uniprot.core.taxonomy.impl.TaxonomyStrainBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

class TaxonomyEntryValueMapperTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        TaxonomyEntry entry = new TaxonomyEntryBuilder().taxonId(9606L).build();
        Map<String, String> mappedEntries =
                new TaxonomyEntryValueMapper().mapEntity(entry, Collections.emptyList());
        assertThat(mappedEntries, notNullValue());
        assertEquals(14, mappedEntries.size());
        assertEquals("9606", mappedEntries.get("id"));
        mappedEntries.remove("id");
        List<String> result =
                mappedEntries.values().stream()
                        .filter(val -> !val.isEmpty())
                        .collect(Collectors.toList());
        assertTrue(result.isEmpty());
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        TaxonomyEntry entry = getTaxonomyEntry();

        Map<String, String> mappedEntries =
                new TaxonomyEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertEquals(14, mappedEntries.size());
        assertEquals("9606", mappedEntries.get("id"));
        assertEquals("9605", mappedEntries.get("parent"));
        assertEquals("name", mappedEntries.get("strains"));
        assertEquals("link", mappedEntries.get("links"));
        assertEquals("scientificName", mappedEntries.get("scientific_name"));
        assertEquals("otherName", mappedEntries.get("other_names"));
        assertEquals("synonym", mappedEntries.get("synonyms"));
        assertEquals("Homo sapiens", mappedEntries.get("hosts"));
        assertEquals("mnemonic", mappedEntries.get("mnemonic"));
        assertEquals("kingdom", mappedEntries.get("rank"));
        assertEquals("reviewed", mappedEntries.get("reviewed"));
        assertEquals("commonName", mappedEntries.get("common_name"));
        assertEquals("lineage value", mappedEntries.get("lineage"));
        assertEquals(
                "reviewed:10; annotated:0; reference:0; complete:0",
                mappedEntries.get("statistics"));
    }

    @Test
    void checkReviewedEntryAnnotatedAttributeValues() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.statistics(new TaxonomyStatisticsBuilder().unreviewedProteinCount(10).build());
        TaxonomyEntry entry = builder.build();
        Map<String, String> mappedEntries =
                new TaxonomyEntryValueMapper().mapEntity(entry, Collections.emptyList());
        assertEquals(14, mappedEntries.size());
        assertEquals("annotated", mappedEntries.get("reviewed"));
    }

    private TaxonomyEntry getTaxonomyEntry() {
        TaxonomyEntryBuilder builder = new TaxonomyEntryBuilder();
        builder.taxonId(9606L);
        builder.scientificName("scientificName");
        builder.commonName("commonName");
        builder.mnemonic("mnemonic");
        builder.parent(getTaxonomy());
        builder.rank(TaxonomyRank.KINGDOM);
        builder.synonymsSet(Collections.singletonList("synonym"));
        builder.otherNamesSet(Collections.singletonList("otherName"));
        builder.lineagesSet(Collections.singletonList(getTaxonomyLineage()));
        builder.strainsSet(Collections.singletonList(getTaxonomyStrain()));
        builder.hostsSet(Collections.singletonList(getTaxonomy()));
        builder.linksSet(Collections.singletonList("link"));
        builder.statistics(new TaxonomyStatisticsBuilder().reviewedProteinCount(10).build());
        return builder.build();
    }

    private TaxonomyLineage getTaxonomyLineage() {
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.scientificName("lineage value");
        return builder.build();
    }

    private TaxonomyStrain getTaxonomyStrain() {
        TaxonomyStrainBuilder builder = new TaxonomyStrainBuilder();
        builder.name("name");
        return builder.build();
    }

    private Taxonomy getTaxonomy() {
        return new TaxonomyBuilder().taxonId(9605L).scientificName("Homo sapiens").build();
    }
}
