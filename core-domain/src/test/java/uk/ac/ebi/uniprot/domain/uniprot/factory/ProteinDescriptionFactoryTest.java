package uk.ac.ebi.uniprot.domain.uniprot.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAlternativeName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinNameSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

public class ProteinDescriptionFactoryTest {

	@Test
	public void testCreateFlag() {
		Flag flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		assertEquals(FlagType.FRAGMENT, flag.getFlagType());
		flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT_PRECURSOR);
		assertEquals(FlagType.FRAGMENT_PRECURSOR, flag.getFlagType());
	}

	@Test
	public void testCreateECNumber() {
		String val = "1.2.3.4";
		List<Evidence> evidences = createEvidences();
		ECNumber ecNumber = ProteinDescriptionFactory.INSTANCE.createECNumber(val, evidences);
		assertEquals(val, ecNumber.getValue());
		assertEquals(evidences, ecNumber.getEvidences());
	}

	@Test
	public void testCreateProteinName() {
		String val = "some value";
		List<Evidence> evidences = createEvidences();
		Name proteinName = ProteinDescriptionFactory.INSTANCE.createProteinName(val, evidences);
		assertEquals(val, proteinName.getValue());
		assertEquals(evidences, proteinName.getEvidences());
	}

	@Test
	public void testCreateRecName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		ProteinRecommendedName recName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);
		assertEquals(fullName, recName.getFullName());
		assertEquals(shortNames, recName.getShortNames());
		assertEquals(ecNumbers, recName.getEcNumbers());
		assertTrue(recName.isValid());

	}

	@Test
	public void testRecNameValid() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		ProteinRecommendedName recName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);
		assertTrue(recName.isValid());
		fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("", evidences);
		recName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);
		assertFalse(recName.isValid());
		recName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(null, shortNames, ecNumbers);
		assertFalse(recName.isValid());
	}

	@Test
	public void testCreateAltName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(fullName, shortNames, ecNumbers);
		assertEquals(fullName, altName.getFullName());
		assertEquals(shortNames, altName.getShortNames());
		assertEquals(ecNumbers, altName.getEcNumbers());
	//	assertNull(altName.getAllergenName());
	//	assertNull(altName.getBiotechName());
	//	assertTrue(altName.getCDAntigenNames().isEmpty());
	//	assertTrue(altName.getINNNames().isEmpty());
	}

	@Test
	public void testCreateProteinAlternativeName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(fullName, shortNames, ecNumbers);
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		Name allergenName = ProteinDescriptionFactory.INSTANCE.createProteinName("allergen", evidences);
		Name biotechName = ProteinDescriptionFactory.INSTANCE.createProteinName("biotech", evidences);
		List<Name> cdAntigenNames = new ArrayList<>();
		cdAntigenNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen1", evidences));
		cdAntigenNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen2", evidences));
		List<Name> innNames = new ArrayList<>();
		innNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("cd inn", evidences));
		ProteinAlternativeName proteinAlterName =  
				ProteinDescriptionFactory.INSTANCE.createProteinAlternativeName(altNames, allergenName,
						biotechName, cdAntigenNames, innNames);
		
	
		assertEquals(altNames, proteinAlterName.getAltNames());
		assertEquals(allergenName, proteinAlterName.getAllergenName());
		assertEquals(biotechName, proteinAlterName.getBiotechName());
		assertEquals(cdAntigenNames, proteinAlterName.getCDAntigenNames());
		assertEquals(innNames, proteinAlterName.getINNNames());
	}
	
	
	@Test
	public void testCreateProteinSubmissionName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);

		List<ECNumber> ecNumbers = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName, ecNumbers);
		assertEquals(fullName, subName.getFullName());
		assertEquals(ecNumbers, subName.getEcNumbers());
		assertTrue(subName.isValid());
	}
	

	@Test
	public void testCreateProteinDescription() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);

		ProteinAlternativeName proteinAltName = createAltName();
		Name fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);

		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		List<ProteinSubmissionName> subNames = new ArrayList<>();
		subNames.add(subName);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subNames, proteinAltName);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subNames, description.getSubmissionNames());
		assertEquals(proteinAltName, description.getAlternativeName());
		assertNull(description.getFlag());
		assertTrue(description.getIncludes().isEmpty());
		assertTrue(description.getContains().isEmpty());
		assertTrue(description.isValid());
		
	}
	
	@Test
	public void testCreateProteinDescriptionWithFlag() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);
		ProteinAlternativeName proteinAltName = createAltName();
		Name fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		Flag flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		List<ProteinSubmissionName> subNames =new ArrayList<>();
		subNames.add(subName);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subNames, proteinAltName, flag);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subNames, description.getSubmissionNames());
		assertEquals(proteinAltName, description.getAlternativeName());
		assertEquals(flag, description.getFlag());
		assertTrue(description.getIncludes().isEmpty());
		assertTrue(description.getContains().isEmpty());
		assertTrue(description.isValid());
		
	
		
	}
	@Test
	public void testCreateProteinDescriptionFull() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(fullName, shortNames, ecNumbers);
		ProteinAlternativeName proteinAltName = createAltName();
		Name fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		Flag flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		
		
		ProteinNameSection included1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName, null);
				ProteinNameSection contain1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName, null);
		ProteinNameSection contain2 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName, proteinAltName);
		List<ProteinNameSection> includes =new ArrayList<>();
		includes.add(included1);
		List<ProteinNameSection> contains =new ArrayList<>();
		contains.add(contain1);
		contains.add(contain2);
		List<ProteinSubmissionName> subNames =new ArrayList<>();
		subNames.add(subName);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subNames, proteinAltName, flag, includes, contains);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subNames, description.getSubmissionNames());
		assertEquals(proteinAltName, description.getAlternativeName());
		assertEquals(flag, description.getFlag());
		assertTrue(description.isValid());
		assertEquals(includes, description.getIncludes());
		assertEquals(contains, description.getContains());
		assertTrue(description.isValid());
		
	}
	private ProteinAlternativeName createAltName() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences));
		List<ECNumber> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
		
		AltName altName =ProteinDescriptionFactory.INSTANCE.createAltName(fullName, shortNames, ecNumbers);
		
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		Name allergenName = ProteinDescriptionFactory.INSTANCE.createProteinName("allergen", evidences);
		Name biotechName = ProteinDescriptionFactory.INSTANCE.createProteinName("biotech", evidences);
		List<Name> antigenNames = new ArrayList<>();
		antigenNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen", evidences));
		ProteinAlternativeName proteinAltName = ProteinDescriptionFactory.INSTANCE.createProteinAlternativeName(altNames, allergenName, biotechName, antigenNames, null);
		return proteinAltName;
	}
	private List<Name> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences));
		return shortNames;
	}

	private List<ECNumber> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<ECNumber> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
		return ecNumbers;
	}

	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(EvidenceFactory.INSTANCE.createFromEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}

}
