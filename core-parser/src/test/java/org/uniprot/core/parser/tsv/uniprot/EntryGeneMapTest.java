package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprotkb.impl.*;

class EntryGeneMapTest {
    @Test
    void testFields() {
        List<String> fields = EntryGeneMap.FIELDS;
        List<String> expected =
                Arrays.asList("gene_names", "gene_primary", "gene_synonym", "gene_oln", "gene_orf");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataEmpty() {
        EntryGeneMap dl = new EntryGeneMap(null);
        Map<String, String> result = dl.attributeValues();
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetDataAll() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        builder.synonymsSet(createGeneNameSynonym(Arrays.asList("syn1", "syn2")));
        builder.orfNamesSet(createORFName(Arrays.asList("orf1", "orf2")));
        builder.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln1", "oln2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("gene_names", "name11 syn1 syn2 oln1 oln2 orf1 orf2", result);
        verify("gene_primary", "name11", result);
        verify("gene_synonym", "syn1 syn2", result);
        verify("gene_oln", "oln1 oln2", result);
        verify("gene_orf", "orf1 orf2", result);
    }

    @Test
    void testGetDataMulti() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        builder.synonymsSet(createGeneNameSynonym(Arrays.asList("syn1", "syn2")));
        builder.orfNamesSet(createORFName(Arrays.asList("orf1", "orf2")));
        builder.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln1", "oln2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        GeneBuilder builder2 = new GeneBuilder();
        builder2.geneName(createGeneName("name12"));
        builder2.synonymsSet(createGeneNameSynonym(Arrays.asList("syn3", "syn4")));
        genes.add(builder2.build());

        EntryGeneMap dl = new EntryGeneMap(genes);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("gene_names", "name11 syn1 syn2 oln1 oln2 orf1 orf2; name12 syn3 syn4", result);
        verify("gene_primary", "name11; name12", result);
        verify("gene_synonym", "syn1 syn2; syn3 syn4", result);
        verify("gene_oln", "oln1 oln2; ", result);
        verify("gene_orf", "orf1 orf2; ", result);
    }

    @Test
    void testGetDataOnlyPrimarySynonym() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        builder.synonymsSet(createGeneNameSynonym(Arrays.asList("syn1", "syn2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("gene_names", "name11 syn1 syn2", result);
        verify("gene_primary", "name11", result);
        verify("gene_synonym", "syn1 syn2", result);
        verify("gene_oln", "", result);
        verify("gene_orf", "", result);
    }

    @Test
    void testGetDataOnlyPrimaryOrf() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        builder.orfNamesSet(createORFName(Arrays.asList("orf1", "orf2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("gene_names", "name11 orf1 orf2", result);
        verify("gene_primary", "name11", result);
        verify("gene_synonym", "", result);
        verify("gene_oln", "", result);
        verify("gene_orf", "orf1 orf2", result);
    }

    @Test
    void testGetDataOnlyOln() {
        GeneBuilder builder = new GeneBuilder();
        builder.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln1", "oln2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        Map<String, String> result = dl.attributeValues();
        assertEquals(5, result.size());
        verify("gene_names", "oln1 oln2", result);
        verify("gene_primary", "", result);
        verify("gene_synonym", "", result);
        verify("gene_oln", "oln1 oln2", result);
        verify("gene_orf", "", result);
    }

    private void verify(String field, String expected, Map<String, String> result) {
        assertEquals(expected, result.get(field));
    }

    @Test
    void testGetGeneName() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        builder.synonymsSet(createGeneNameSynonym(Arrays.asList("syn1", "syn2")));
        builder.orfNamesSet(createORFName(Arrays.asList("orf1", "orf2")));
        builder.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln1", "oln2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getGeneName();
        String expected = "name11 syn1 syn2 oln1 oln2 orf1 orf2";
        assertEquals(expected, result);

        GeneBuilder builder2 = new GeneBuilder();
        builder2.geneName(createGeneName("name12"));
        builder2.synonymsSet(createGeneNameSynonym(Arrays.asList("syn3", "syn4")));
        genes.add(builder2.build());
        dl = new EntryGeneMap(genes);
        result = dl.getGeneName();
        expected = "name11 syn1 syn2 oln1 oln2 orf1 orf2; name12 syn3 syn4";
        assertEquals(expected, result);

        GeneBuilder builder3 = new GeneBuilder();
        builder3.orfNamesSet(createORFName(Arrays.asList("orf3", "orf4")));
        genes.add(builder3.build());
        dl = new EntryGeneMap(genes);
        result = dl.getGeneName();
        expected = "name11 syn1 syn2 oln1 oln2 orf1 orf2; name12 syn3 syn4; orf3 orf4";
        assertEquals(expected, result);

        GeneBuilder builder4 = new GeneBuilder();
        builder4.geneName(createGeneName("name14"));
        builder4.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln3", "oln4")));
        genes.add(builder4.build());
        dl = new EntryGeneMap(genes);
        result = dl.getGeneName();
        expected =
                "name11 syn1 syn2 oln1 oln2 orf1 orf2; name12 syn3 syn4; orf3 orf4; name14 oln3"
                        + " oln4";
        assertEquals(expected, result);
    }

    @Test
    void testGetPrimaryName() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(createGeneName("name11"));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getPrimaryName();
        assertEquals("name11", result);
        GeneBuilder builder2 = new GeneBuilder();
        builder2.geneName(createGeneName("name2"));
        genes.add(builder2.build());
        dl = new EntryGeneMap(genes);
        result = dl.getPrimaryName();
        assertEquals("name11; name2", result);

        genes = new ArrayList<>();
        genes.add(new GeneBuilder().build());
        genes.add(new GeneBuilder().build());
        dl = new EntryGeneMap(genes);
        result = dl.getPrimaryName();
        assertEquals("; ", result);
    }

    @Test
    void testGetSynonyms() {
        GeneBuilder builder = new GeneBuilder();
        builder.geneName(new GeneNameBuilder().value("name").build());
        builder.synonymsSet(createGeneNameSynonym(Arrays.asList("syn1", "syn2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getSynonyms();
        assertEquals("syn1 syn2", result);
        GeneBuilder builder2 = new GeneBuilder();
        builder2.geneName(new GeneNameBuilder().value("name").build());
        builder2.synonymsSet(createGeneNameSynonym(Arrays.asList("syn3", "syn4")));
        genes.add(builder2.build());
        dl = new EntryGeneMap(genes);
        result = dl.getSynonyms();
        assertEquals("syn1 syn2; syn3 syn4", result);

        genes = new ArrayList<>();
        genes.add(new GeneBuilder().build());
        genes.add(new GeneBuilder().build());
        dl = new EntryGeneMap(genes);
        result = dl.getSynonyms();
        assertEquals("; ", result);
    }

    @Test
    void testGetOlnName() {
        GeneBuilder builder = new GeneBuilder();
        builder.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln1", "oln2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getOlnName();
        assertEquals("oln1 oln2", result);
        GeneBuilder builder2 = new GeneBuilder();
        builder2.orderedLocusNamesSet(createOrderedLocusName(Arrays.asList("oln3", "oln4")));
        genes.add(builder2.build());
        dl = new EntryGeneMap(genes);
        result = dl.getOlnName();
        assertEquals("oln1 oln2; oln3 oln4", result);
    }

    @Test
    void testGetOlnNameEmpty() {
        List<Gene> genes = new ArrayList<>();
        genes.add(new GeneBuilder().build());
        genes.add(new GeneBuilder().build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getOlnName();
        assertEquals("; ", result);
    }

    @Test
    void testGetOrfName() {
        GeneBuilder builder = new GeneBuilder();
        builder.orfNamesSet(createORFName(Arrays.asList("orf1", "orf2")));
        List<Gene> genes = new ArrayList<>();
        genes.add(builder.build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getOrfName();
        assertEquals("orf1 orf2", result);
        GeneBuilder builder2 = new GeneBuilder();
        builder2.orfNamesSet(createORFName(Arrays.asList("orf3", "orf4")));
        genes.add(builder2.build());
        dl = new EntryGeneMap(genes);
        result = dl.getOrfName();
        assertEquals("orf1 orf2; orf3 orf4", result);
    }

    @Test
    void testGetOrfNameEmpty() {
        List<Gene> genes = new ArrayList<>();
        genes.add(new GeneBuilder().build());
        genes.add(new GeneBuilder().build());
        EntryGeneMap dl = new EntryGeneMap(genes);
        String result = dl.getOrfName();
        assertEquals("; ", result);
    }

    private List<ORFName> createORFName(List<String> value) {
        return value.stream().map(this::createORFName).collect(Collectors.toList());
    }

    private ORFName createORFName(String value) {
        return new ORFNameBuilder(value, Collections.emptyList()).build();
    }

    private List<GeneNameSynonym> createGeneNameSynonym(List<String> value) {
        return value.stream().map(this::createGeneNameSynonym).collect(Collectors.toList());
    }

    private GeneNameSynonym createGeneNameSynonym(String value) {
        return new GeneNameSynonymBuilder(value, Collections.emptyList()).build();
    }

    private List<OrderedLocusName> createOrderedLocusName(List<String> value) {
        return value.stream().map(this::createOrderedLocusName).collect(Collectors.toList());
    }

    private OrderedLocusName createOrderedLocusName(String value) {
        return new OrderedLocusNameBuilder(value, Collections.emptyList()).build();
    }

    private List<GeneName> createGeneName(List<String> value) {
        return value.stream().map(this::createGeneName).collect(Collectors.toList());
    }

    private GeneName createGeneName(String value) {
        return new GeneNameBuilder(value, Collections.emptyList()).build();
    }
}
