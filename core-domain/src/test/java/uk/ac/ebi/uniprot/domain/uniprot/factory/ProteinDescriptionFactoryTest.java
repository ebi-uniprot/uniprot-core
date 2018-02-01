package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.uniprot.description.AltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecommendedName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubmissionName;
import uk.ac.ebi.uniprot.domain.uniprot.description.RecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.impl.AltNameImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		ProteinName proteinName = ProteinDescriptionFactory.INSTANCE.createProteinName(val, evidences);
		assertEquals(val, proteinName.getValue());
		assertEquals(evidences, proteinName.getEvidences());
	}

	@Test
	public void testCreateRecName() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		assertEquals(fullName, recName.getFullName());
		assertEquals(shortNames, recName.getShortNames());
		assertEquals(ecNumbers, recName.getEcNumbers());
		assertTrue(recName.isValid());

	}

	@Test
	public void testRecNameValid() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		assertTrue(recName.isValid());
		fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("", evidences);
		recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		assertFalse(recName.isValid());
		recName = ProteinDescriptionFactory.INSTANCE.createRecName(null, shortNames, ecNumbers);
		assertFalse(recName.isValid());
	}

	@Test
	public void testCreateAltName() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(fullName, shortNames, ecNumbers);
		assertEquals(fullName, altName.getFullName());
		assertEquals(shortNames, altName.getShortNames());
		assertEquals(ecNumbers, altName.getEcNumbers());
		assertNull(altName.getAllergenName());
		assertNull(altName.getBiotechName());
		assertTrue(altName.getCDAntigenNames().isEmpty());
		assertTrue(altName.getINNNames().isEmpty());
	}

	@Test
	public void testCreateAltNameByBuilder() {
		AltNameImpl.Builder builder = AltNameImpl.newBuilder();
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		builder.fullName(fullName)
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences))
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences))
				.ecNumber(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences))
				.allergenName(ProteinDescriptionFactory.INSTANCE.createProteinName("allergen", evidences))
				.biotechName(ProteinDescriptionFactory.INSTANCE.createProteinName("biotech", evidences))
				.cDAntigenName(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen", evidences));

		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(builder);

		assertEquals(fullName, altName.getFullName());
		assertEquals(2, altName.getShortNames().size());
		assertEquals(1, altName.getEcNumbers().size());
		assertNotNull(altName.getAllergenName());
		assertNotNull(altName.getBiotechName());
		assertEquals(1, altName.getCDAntigenNames().size());
		assertTrue(altName.getINNNames().isEmpty());
	}
	
	
	@Test
	public void testCreateProteinSubmissionName() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);

		List<ECNumber> ecNumbers = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName, ecNumbers);
		assertEquals(fullName, subName.getFullName());
		assertEquals(ecNumbers, subName.getEcNumbers());
		assertTrue(subName.isValid());
	}
	
	@Test
	public void testCreateRecommendedName() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		AltName altName = createAltName();
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		assertEquals(recName, recommendedName.getRecName());
		assertEquals(altNames, recommendedName.getAltNames());
	}

	@Test
	public void testCreateProteinDescription() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		AltName altName = createAltName();
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		
		ProteinName fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);

		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subName);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subName, description.getSubmmissonName());
		assertNull(description.getFlag());
		assertTrue(description.getIncludes().isEmpty());
		assertTrue(description.getContains().isEmpty());
		assertTrue(description.isValid());
		
	}
	
	@Test
	public void testCreateProteinDescriptionWithFlag() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		AltName altName = createAltName();
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		
		ProteinName fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		Flag flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		List<ECNumber> ecNumbers1 = createECNumbers();
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subName, flag);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subName, description.getSubmmissonName());
		assertEquals(flag, description.getFlag());
		assertTrue(description.getIncludes().isEmpty());
		assertTrue(description.getContains().isEmpty());
		assertTrue(description.isValid());
		
	}
	@Test
	public void testCreateProteinDescriptionFull() {
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		List<ProteinName> shortNames = createShortNames();
		List<ECNumber> ecNumbers = createECNumbers();
		RecName recName = ProteinDescriptionFactory.INSTANCE.createRecName(fullName, shortNames, ecNumbers);
		AltName altName = createAltName();
		List<AltName> altNames = new ArrayList<>();
		altNames.add(altName);
		ProteinRecommendedName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		
		ProteinName fullName1 = ProteinDescriptionFactory.INSTANCE.createProteinName("a full Name", evidences);
		Flag flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		List<ECNumber> ecNumbers1 = createECNumbers();
		
		
		
		ProteinSubmissionName subName = ProteinDescriptionFactory.INSTANCE.createProteinSubmissionName(fullName1, ecNumbers1);
		
		ProteinRecommendedName included1 = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, null);
		ProteinRecommendedName contain1 = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, null);
		ProteinRecommendedName contain2 = ProteinDescriptionFactory.INSTANCE.createProteinRecommendedName(recName, altNames);
		List<ProteinRecommendedName> includes =new ArrayList<>();
		includes.add(included1);
		List<ProteinRecommendedName> contains =new ArrayList<>();
		contains.add(contain1);
		contains.add(contain2);
		
		ProteinDescription description = ProteinDescriptionFactory.INSTANCE.createProteinDescription(recommendedName, subName, flag, includes, contains);
		assertEquals(recommendedName, description.getRecommendedName());
		assertEquals(subName, description.getSubmmissonName());
		assertEquals(flag, description.getFlag());
		assertEquals(includes, description.getIncludes());
		assertEquals(contains, description.getContains());
		assertTrue(description.isValid());
		
	}
	private AltName createAltName() {
		AltNameImpl.Builder builder = AltNameImpl.newBuilder();
		List<Evidence> evidences = createEvidences();
		ProteinName fullName = ProteinDescriptionFactory.INSTANCE.createProteinName("a full alt Name", evidences);
		builder.fullName(fullName)
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name1", evidences))
				.shortName(ProteinDescriptionFactory.INSTANCE.createProteinName("short name2", evidences))
				.ecNumber(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences))
				.allergenName(ProteinDescriptionFactory.INSTANCE.createProteinName("allergen", evidences))
				.biotechName(ProteinDescriptionFactory.INSTANCE.createProteinName("biotech", evidences))
				.cDAntigenName(ProteinDescriptionFactory.INSTANCE.createProteinName("cd antigen", evidences));

		AltName altName = ProteinDescriptionFactory.INSTANCE.createAltName(builder);
		return altName;
	}
	private List<ProteinName> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<ProteinName> shortNames = new ArrayList<>();
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
