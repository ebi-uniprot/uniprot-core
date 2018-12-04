package uk.ac.ebi.uniprot.domain.uniprot.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonNode;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyRank;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.OrganismNameImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNameImpl;
import uk.ac.ebi.uniprot.domain.taxonomy.impl.TaxonNodeImpl;

public class TaxonomyFactoryTest {

	@Test
	public void testCreateTaxonWithScientificNameOnly() {
		long taxId = 9606;
		String scientificName = "Homo sapiens";
		Taxon taxonByF = TaxonomyFactory.INSTANCE.createTaxon(scientificName, taxId);
		Taxon taxon = new TaxonImpl(new TaxonNameImpl(scientificName), taxId);
		assertEquals(taxon, taxonByF);
		TestHelper.verifyJson(taxon);
	}

	@Test
	public void testCreateTaxonWithScientificNameCommonName() {
		long taxId = 9606;
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		TaxonName name = new TaxonNameImpl(scientificName, commonName);
		Taxon taxonByF = TaxonomyFactory.INSTANCE.createTaxon(name, taxId);

		Taxon taxon = new TaxonImpl(name, taxId);
		assertEquals(taxon, taxonByF);
		TestHelper.verifyJson(taxon);
	}

	@Test
	public void testCreateTaxonNode() {
		TaxonNode parent = TaxonomyFactory.INSTANCE.createTaxonNode(null,
				TaxonomyFactory.INSTANCE.createTaxon("Homo", 9605), TaxonomyRank.GENUS);

		TaxonNode node = TaxonomyFactory.INSTANCE.createTaxonNode(parent,
				TaxonomyFactory.INSTANCE.createTaxon("Homo sapiens", 9606), TaxonomyRank.SPECIES);

		TaxonNode parent1 = new TaxonNodeImpl(null, new TaxonImpl(new TaxonNameImpl("Homo"), 9605), TaxonomyRank.GENUS);

		TaxonNode node1 = new TaxonNodeImpl(parent1, new TaxonImpl(new TaxonNameImpl("Homo sapiens"), 9606),
				TaxonomyRank.SPECIES);
		assertEquals(node1, node);
	}

	@Test
	public void testParseScientificNameOnly() {
		String str = "Homo sapiens";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);
		assertEquals(str, organism.getScientificName());
		assertEquals(str, organism.toString());
		TestHelper.verifyJson(organism);
	}

	@Test
	public void testParseScientificNameCommonName() {
		String str = "Homo sapiens (Human)";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);
		assertEquals("Homo sapiens", organism.getScientificName());
		assertEquals("Human", organism.getCommonName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals(str, organism.toString());
		TestHelper.verifyJson(organism);

	}

	@Test
	public void testParseScientificNameCommonNameSynonyms() {

		String str = "Homo sapiens (Human) (Name1, Name2)";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);
		assertEquals("Homo sapiens", organism.getScientificName());
		assertEquals("Human", organism.getCommonName());
		assertEquals(2, organism.getSynonyms().size());
		assertEquals(str, organism.toString());
		TestHelper.verifyJson(organism);

	}

	@Test
	public void testCreateScientificNameOnly() {

		String scientificName = "Homo sapiens";
		OrganismName organism = TaxonomyFactory.INSTANCE.createOrganismName(scientificName);
		OrganismName organismImpl = new OrganismNameImpl(scientificName);
		assertEquals(organismImpl, organism);
		TestHelper.verifyJson(organism);
	}

	@Test
	public void testCreateScientificNameCommonName() {
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		OrganismName organism = TaxonomyFactory.INSTANCE.createOrganismName(scientificName, commonName);
		OrganismName organismImpl = new OrganismNameImpl(scientificName, commonName);
		assertEquals(organismImpl, organism);
	}

	@Test
	public void testCreateScientificNameCommonNameSynonyms() {
		String str = "Homo sapiens (Human) (Name1, Name2)";
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		List<String> synonyms = Arrays.asList(new String[] { "Name1", "Name2" });
		OrganismName organism = TaxonomyFactory.INSTANCE.createOrganismName(scientificName, commonName, synonyms);
		OrganismName organismImpl = new OrganismNameImpl(scientificName, commonName, synonyms);
		assertEquals(organismImpl, organism);
		TestHelper.verifyJson(organism);

	}

	@Test
	public void testCreateWithDot() {
		String str = "Roseovarius sp. HI0049";
		String scientificName = "Roseovarius sp. HI0049";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);

		assertEquals(scientificName, organism.getScientificName());
		assertEquals("", organism.getCommonName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals(str, organism.toString());

	}

	@Test
	public void testCreateWith_() {
		String str = "Candidatus Heimdallarchaeota archaeon LC_2";
		String scientificName = "Candidatus Heimdallarchaeota archaeon LC_2";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);

		assertEquals(scientificName, organism.getScientificName());
		assertEquals("", organism.getCommonName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals(str, organism.toString());

	}

	@Test
	public void testCreateWithSlash() {

		String str = "Salmonella paratyphi B (strain ATCC BAA-1250 / SPB7)";

		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);

		assertEquals(str, organism.getScientificName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals(str, organism.toString());

	}

	@Test
	public void testStrain() {
		String str = "Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958) (Rice blast fungus) (Pyricularia oryzae)";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);

		assertEquals("Magnaporthe oryzae (strain 70-15 / ATCC MYA-4617 / FGSC 8958)", organism.getScientificName());
		assertEquals("Rice blast fungus", organism.getCommonName());
		assertEquals(Arrays.asList("Pyricularia oryzae"), organism.getSynonyms());

	}

	@Test
	public void testStrainWithBrakect2() {
		String str = "Synechococcus sp. (strain JA-2-3B'a(2-13)) (Cyanobacteria bacterium Yellowstone B-Prime)";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);
		assertEquals("Synechococcus sp. (strain JA-2-3B'a(2-13))", organism.getScientificName());
		assertEquals("Cyanobacteria bacterium Yellowstone B-Prime", organism.getCommonName());
		assertTrue(organism.getSynonyms().isEmpty());

	}
	@Test
	public void testIsolate() {
		String str = "Variola virus (isolate Human/India/Ind3/1967) (VARV) (Smallpox virus)";
		OrganismName organism = TaxonomyFactory.INSTANCE.createFromOrganismLine(str);
		assertEquals("Variola virus (isolate Human/India/Ind3/1967)", organism.getScientificName());
		assertEquals("VARV", organism.getCommonName());
		assertEquals(Arrays.asList("Smallpox virus"), organism.getSynonyms());

	}
	
}
