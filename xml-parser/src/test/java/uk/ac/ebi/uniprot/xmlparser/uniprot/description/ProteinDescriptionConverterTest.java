package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ProteinType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class ProteinDescriptionConverterTest {

	@Test
	void test() {
		List<Evidence> evidences = createEvidences();
		Name allergenName = ProteinDescriptionFactory.INSTANCE.createName("allergen", evidences);
		Name biotechName = ProteinDescriptionFactory.INSTANCE.createName("biotech", evidences);
		List<Name> antigenNames = new ArrayList<>();
		antigenNames.add(ProteinDescriptionFactory.INSTANCE.createName("cd antigen", evidences));

		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers = createECNumbers();
		ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames,
				ecNumbers);

		List<ProteinName> proteinAltNames = createAltName();
		Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);

		List<EC> ecNumbers1 = createECNumbers();
		ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null, ecNumbers1);
		List<ProteinName> subNames = new ArrayList<>();
		subNames.add(subName);
		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		ProteinDescription description = builder.recommendedName(recommendedName).submissionNames(subNames)
				.alternativeNames(proteinAltNames).allergenName(allergenName).biotechName(biotechName)
				.cdAntigenNames(antigenNames).build();
		ProteinDescriptionConverter converter = createConverter();
		ProteinType xmlObj = converter.toXml(description);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, ProteinType.class, "protein"));
		ProteinDescription converted = converter.fromXml(xmlObj);
		assertEquals(description, converted);
		List<DbReferenceType> dbReferences = converter.toXmlDbReferences(description);
		dbReferences.forEach( val -> System.out.println(
		UniProtXmlTestHelper.toXmlString(val, DbReferenceType.class, "dbReference"))
		);
	}

	@Test
	void testFull() {
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<Name> shortNames = createShortNames();
		List<EC> ecNumbers = createECNumbers();
		ProteinName recommendedName = ProteinDescriptionFactory.INSTANCE
				.createProteinName(fullName, shortNames, ecNumbers);
		List<ProteinName> proteinAltNames = createAltName();
		Name fullName1 = ProteinDescriptionFactory.INSTANCE.createName("a full Name", evidences);
		List<EC> ecNumbers1 = createECNumbers();
		ProteinName subName = ProteinDescriptionFactory.INSTANCE.createProteinName(fullName1, null,
				ecNumbers1);

		ProteinSection included1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
				null);
		ProteinSection contain1 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
				null);
		ProteinSection contain2 = ProteinDescriptionFactory.INSTANCE.createProteinNameSection(recommendedName,
				proteinAltNames);
		List<ProteinSection> includes = new ArrayList<>();
		includes.add(included1);
		List<ProteinSection> contains = new ArrayList<>();
		contains.add(contain1);
		contains.add(contain2);
		List<ProteinName> subNames = new ArrayList<>();
		subNames.add(subName);
		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance() ;
				builder.recommendedName(recommendedName)
				.submissionNames(subNames)
				.alternativeNames(proteinAltNames)
				.includes(includes)
				.contains(contains);
		ProteinDescription description = builder.build();
		ProteinDescriptionConverter converter = createConverter();
		ProteinType xmlObj = converter.toXml(description);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj, ProteinType.class, "protein"));
		ProteinDescription converted = converter.fromXml(xmlObj);
		assertEquals(description, converted);
				
	}
	private ProteinDescriptionConverter createConverter() {
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		ECConverter ecConverter = new ECConverter(evRefMapper);
		NameConverter nameConverter = new NameConverter(evRefMapper);
		RecNameConverter recNameConverter = new RecNameConverter(nameConverter, ecConverter);
		AltNameConverter altNameConverter = new AltNameConverter(nameConverter, ecConverter);
		SubNameConverter subNameConverter = new SubNameConverter(nameConverter, ecConverter);

		return new ProteinDescriptionConverter(recNameConverter, altNameConverter, subNameConverter, nameConverter);

	}

	private List<ProteinName> createAltName() {
		List<ProteinName> alternativeNames = new ArrayList<>();
		List<Evidence> evidences = createEvidences();
		Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
		List<EC> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));

		alternativeNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers));

		return alternativeNames;
	}

	private List<Name> createShortNames() {
		List<Evidence> evidences = createEvidences();
		List<Name> shortNames = new ArrayList<>();
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
		shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
		return shortNames;
	}

	private List<EC> createECNumbers() {
		List<Evidence> evidences = createEvidences();
		List<EC> ecNumbers = new ArrayList<>();
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
		ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
		return ecNumbers;
	}

	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}
}
