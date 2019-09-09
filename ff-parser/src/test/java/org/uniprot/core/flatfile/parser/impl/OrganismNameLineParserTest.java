package org.uniprot.core.flatfile.parser.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.uniprot.taxonomy.OrganismName;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrganismNameLineParserTest {

    @Test
    public void testParseScientificNameOnly() {
        String str = "Homo sapiens";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);
        assertEquals(str, organism.getScientificName());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testParseScientificNameCommonName() {
        String str = "Homo sapiens (Human)";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testParseScientificNameCommonNameSynonyms() {

        String str = "Homo sapiens (Human) (Name1, Name2)";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);
        assertEquals("Homo sapiens", organism.getScientificName());
        assertEquals("Human", organism.getCommonName());
        assertEquals(2, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testCreateWithDot() {
        String str = "Roseovarius sp. HI0049";
        String scientificName = "Roseovarius sp. HI0049";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);

        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals(str, organism.toString());
    }

    @Test
    public void testCreateWith_() {
        String str = "Candidatus Heimdallarchaeota archaeon LC_2";
        String scientificName = "Candidatus Heimdallarchaeota archaeon LC_2";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);

        assertEquals(scientificName, organism.getScientificName());
        assertEquals("", organism.getCommonName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals(str, organism.toString());

    }

    @Test
    public void testCreateWithSlash() {

        String str = "Salmonella paratyphi B (strain ATCC BAA-1250 / SPB7)";

        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);

        assertEquals(str, organism.getScientificName());
        assertEquals(0, organism.getSynonyms().size());
        assertEquals(str, organism.toString());

    }

    @Test
    public void testStrain() {
        String str = "Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958) (Rice blast fungus) (Pyricularia oryzae)";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);

        assertEquals("Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958)", organism.getScientificName());
        assertEquals("Rice blast fungus", organism.getCommonName());
        assertEquals(Collections.singletonList("Pyricularia oryzae"), organism.getSynonyms());

    }

    @Test
    public void testStrainWithBrakect2() {
        String str = "Synechococcus sp. (strain JA-2-3B'a(2-13)) (Cyanobacteria bacterium Yellowstone B-Prime)";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);
        assertEquals("Synechococcus sp. (strain JA-2-3B'a(2-13))", organism.getScientificName());
        assertEquals("Cyanobacteria bacterium Yellowstone B-Prime", organism.getCommonName());
        assertTrue(organism.getSynonyms().isEmpty());

    }

    @Test
    public void testIsolate() {
        String str = "Variola virus (isolate Human/India/Ind3/1967) (VARV) (Smallpox virus)";
        OrganismName organism = OrganismNameLineParser.createFromOrganismLine(str);
        assertEquals("Variola virus (isolate Human/India/Ind3/1967)", organism.getScientificName());
        assertEquals("VARV", organism.getCommonName());
        assertEquals(Arrays.asList("Smallpox virus"), organism.getSynonyms());

    }


}