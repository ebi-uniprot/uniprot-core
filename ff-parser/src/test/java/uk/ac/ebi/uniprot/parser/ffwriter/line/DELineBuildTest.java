package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescriptionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.de.DELineBuilder;

public class DELineBuildTest {
	ProteinDescriptionFactory factory = ProteinDescriptionFactory.INSTANCE;
	DELineBuilder builder = new DELineBuilder();

	@Test
	public void test1() {
		Name recFullName = factory.createName("14-3-3-like protein GF14 iota", Collections.emptyList());
		List<Name> recShortNames = new ArrayList<>();
		recShortNames.add(factory.createName("ADAM 10", Collections.emptyList()));
		List<EC> recEcNumbers = new ArrayList<>();
		recEcNumbers.add(factory.createECNumber("2.3.1.35", Collections.emptyList()));
		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);
		Name altFullName = factory.createName("General regulatory factor 12", Collections.emptyList());
		ProteinName altName = factory.createProteinName(altFullName, Collections.emptyList(), Collections.emptyList());
		List<ProteinName> altNames = new ArrayList<>();
		altNames.add(altName);
	
		ProteinDescription proteinDescription = factory.createProteinDescription(recName, altNames, null, null);
		String deLine = "DE   RecName: Full=14-3-3-like protein GF14 iota;\n" + "DE            Short=ADAM 10;\n"
				+ "DE            EC=2.3.1.35;\n" + "DE   AltName: Full=General regulatory factor 12;";

		doTest(deLine, proteinDescription);

	}

	private void doTest(String deLine, ProteinDescription pd) {
		FFLine ffLine = builder.buildWithEvidence(pd);
		String resultString = ffLine.toString();
		// System.out.println(resultString);
		// System.out.println("\n");
		// System.out.println(deLine);
		assertEquals(deLine, resultString);
	}

	@Test
	public void test1Evidence() {
		Name recFullName = factory.createName("14-3-3-like protein GF14 iota", createEvidence(
				Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554", "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> recShortNames = new ArrayList<>();
		recShortNames.add(factory.createName("ADAM 10",
				createEvidence(Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554" }))));
		List<EC> recEcNumbers = new ArrayList<>();
		recEcNumbers.add(factory.createECNumber("2.3.1.35",
				createEvidence(Arrays.asList(new String[] { "ECO:0000303|Ref.6" }))));
		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);
		Name altFullName = factory.createName("General regulatory factor 12",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|PDB:3OW2" })));
		ProteinName altName = factory.createProteinName(altFullName, Collections.emptyList(), Collections.emptyList());
		List<ProteinName> altNames = new ArrayList<>();
		altNames.add(altName);
	
		ProteinDescription proteinDescription = factory.createProteinDescription(recName, altNames, null, null);

		String deLine = "DE   RecName: Full=14-3-3-like protein GF14 iota {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE            Short=ADAM 10 {ECO:0000269|PubMed:10433554};\n"
				+ "DE            EC=2.3.1.35 {ECO:0000303|Ref.6};\n"
				+ "DE   AltName: Full=General regulatory factor 12 {ECO:0000313|PDB:3OW2};";

		doTest(deLine, proteinDescription);

	}

	private List<Evidence> createEvidence(List<String> evIds) {
		return evIds.stream().map(val -> UniProtFactory.INSTANCE.createEvidence(val)).collect(Collectors.toList());

	}

	@Test
	public void test2() {

		Name recFullName = factory.createName("Granulocyte colony-stimulating factor", Collections.emptyList());
		List<Name> recShortNames = new ArrayList<>();
		recShortNames.add(factory.createName("G-CSF", Collections.emptyList()));
		List<EC> recEcNumbers = new ArrayList<>();

		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);
		Name altFullName = factory.createName("Pluripoietin", Collections.emptyList());
		ProteinName altName = factory.createProteinName(altFullName, Collections.emptyList(), Collections.emptyList());
		List<ProteinName> altNames = new ArrayList<>();
		altNames.add(altName);
		List<Name> innNames = new ArrayList<>();
		innNames.add(factory.createName("Lenograstim", Collections.emptyList()));
		List<Name> cdAntigenNames = new ArrayList<>();
		cdAntigenNames.add(factory.createName("CD156c", Collections.emptyList()));

	//	ProteinAlternativeName pAltName = factory.createProteinAlternativeName(altNames, null, null, cdAntigenNames,
	//			innNames);
		Flag flag = factory.createFlag(FlagType.FRAGMENT_PRECURSOR);
		
		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		ProteinDescription proteinDescription = 
		builder.recommendedName(recName)
		.alternativeNames(altNames)
		.innNames(innNames)
		.cdAntigenNames(cdAntigenNames)
		.flag(flag)
		.build();


		String deLine = "DE   RecName: Full=Granulocyte colony-stimulating factor;\n" + "DE            Short=G-CSF;\n"
				+ "DE   AltName: Full=Pluripoietin;\n" + "DE   AltName: CD_antigen=CD156c;\n"
				+ "DE   AltName: INN=Lenograstim;\n" +

				"DE   Flags: Fragment; Precursor;";

		doTest(deLine, proteinDescription);

	}

	@Test
	public void test2Evidence() {

		Name recFullName = factory.createName("Granulocyte colony-stimulating factor",
				createEvidence(Arrays.asList(new String[] { "ECO:0000303|Ref.6", "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> recShortNames = new ArrayList<>();
		recShortNames.add(factory.createName("G-CSF", createEvidence(
				Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554", "ECO:0000313|EMBL:BAG16761.1" }))));
		List<EC> recEcNumbers = new ArrayList<>();

		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);
		Name altFullName = factory.createName("Pluripoietin",
				createEvidence(Arrays.asList(new String[] { "ECO:0000256|HAMAP-Rule:MF_00205" })));
		ProteinName altName = factory.createProteinName(altFullName, Collections.emptyList(), Collections.emptyList());
		List<ProteinName> altNames = new ArrayList<>();
		altNames.add(altName);
		List<Name> innNames = new ArrayList<>();
		innNames.add(factory.createName("Lenograstim",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|PDB:3OW2" }))));
		List<Name> cdAntigenNames = new ArrayList<>();
		cdAntigenNames.add(factory.createName("CD156c", Collections.emptyList()));

//		ProteinAlternativeName pAltName = factory.createProteinAlternativeName(altNames, null, null, cdAntigenNames,
//				innNames);
		
		Flag flag = factory.createFlag(FlagType.FRAGMENT_PRECURSOR);
		
		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		ProteinDescription proteinDescription = 
		builder.recommendedName(recName)
		.alternativeNames(altNames)
		.innNames(innNames)
		.cdAntigenNames(cdAntigenNames)
		.flag(flag)
		.build();

		String deLine = "DE   RecName: Full=Granulocyte colony-stimulating factor {ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE            Short=G-CSF {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE   AltName: Full=Pluripoietin {ECO:0000256|HAMAP-Rule:MF_00205};\n"
				+ "DE   AltName: CD_antigen=CD156c;\n" + "DE   AltName: INN=Lenograstim {ECO:0000313|PDB:3OW2};\n" +

				"DE   Flags: Fragment; Precursor;";

		doTest(deLine, proteinDescription);

	}

	@Test
	public void testSubName() {

		List<ProteinName> submissionNames = new ArrayList<>();
		Name fullName1 = factory.createName("Expressed protein", Collections.emptyList());
		List<EC> ecNumbers1 = new ArrayList<>();
		submissionNames.add(factory.createProteinName(fullName1, null, ecNumbers1));
		Name fullName2 = factory.createName("cDNA clone:001-021-F08, full insert sequence", Collections.emptyList());
		List<EC> ecNumbers2 = new ArrayList<>();
		ecNumbers2.add(factory.createECNumber("2.3.1.35", createEvidence(Arrays.asList(new String[] {}))));
		submissionNames.add(factory.createProteinName(fullName2, null, ecNumbers2));

		Name fullName3 = factory.createName("cDNA clone:006-308-H01, full insert sequence", Collections.emptyList());
		List<EC> ecNumbers3 = new ArrayList<>();

		submissionNames.add(factory.createProteinName(fullName3, null, ecNumbers3));
		Flag flag = factory.createFlag(FlagType.FRAGMENT);

		ProteinDescription proteinDescription = factory.createProteinDescription(null, null, submissionNames, flag);
		String deLine = "DE   SubName: Full=Expressed protein;\n"
				+ "DE   SubName: Full=cDNA clone:001-021-F08, full insert sequence;\n" + "DE            EC=2.3.1.35;\n"
				+ "DE   SubName: Full=cDNA clone:006-308-H01, full insert sequence;\n" + "DE   Flags: Fragment;";

		doTest(deLine, proteinDescription);
	}

	@Test
	public void testSubNameWithEvidence() {
		List<ProteinName> submissionNames = new ArrayList<>();
		Name fullName1 = factory.createName("Expressed protein",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" })));
		List<EC> ecNumbers1 = new ArrayList<>();
		submissionNames.add(factory.createProteinName(fullName1, null, ecNumbers1));
		Name fullName2 = factory.createName("cDNA clone:001-021-F08, full insert sequence",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.2" })));
		List<EC> ecNumbers2 = new ArrayList<>();
		ecNumbers2.add(factory.createECNumber("2.3.1.35",
				createEvidence(Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554" }))));
		submissionNames.add(factory.createProteinName(fullName2, null, ecNumbers2));

		Name fullName3 = factory.createName("cDNA clone:006-308-H01, full insert sequence",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|PDB:3OW2" })));
		List<EC> ecNumbers3 = new ArrayList<>();

		submissionNames.add(factory.createProteinName(fullName3, null, ecNumbers3));
		Flag flag = factory.createFlag(FlagType.FRAGMENT);

		ProteinDescription proteinDescription = factory.createProteinDescription(null, submissionNames, null, flag);
		String deLine = "DE   SubName: Full=Expressed protein {ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE   SubName: Full=cDNA clone:001-021-F08, full insert sequence {ECO:0000313|EMBL:BAG16761.2};\n"
				+ "DE            EC=2.3.1.35 {ECO:0000269|PubMed:10433554};\n"
				+ "DE   SubName: Full=cDNA clone:006-308-H01, full insert sequence {ECO:0000313|PDB:3OW2};\n"
				+ "DE   Flags: Fragment;";

		doTest(deLine, proteinDescription);
	}

	@Test
	public void testIncludeAndContain() {
		String deLine = "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n" + "DE   Includes:\n"
				+ "DE     RecName: Full=Glutamate N-acetyltransferase;\n" + "DE              EC=2.3.1.35;\n"
				+ "DE     AltName: Full=Ornithine acetyltransferase;\n" + "DE              Short=OATase;\n"
				+ "DE     AltName: Full=Ornithine transacetylase;\n" + "DE   Includes:\n"
				+ "DE     RecName: Full=Amino-acid acetyltransferase;\n" + "DE              EC=2.3.1.1;\n"
				+ "DE     AltName: Full=N-acetylglutamate synthase;\n" + "DE              Short=AGS;\n"
				+ "DE   Contains:\n"
				+ "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain;\n"
				+ "DE   Contains:\n"
				+ "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain;";

		Name recFullName = factory.createName("Arginine biosynthesis bifunctional protein argJ",
				Collections.emptyList());
		List<Name> recShortNames = new ArrayList<>();
		List<EC> recEcNumbers = new ArrayList<>();
		;
		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);

		List<ProteinSection> includes = new ArrayList<>();

		Name includeRecFullName1 = factory.createName("Glutamate N-acetyltransferase", Collections.emptyList());
		List<Name> includeRecShortNames1 = new ArrayList<>();
		List<EC> includeRecEcNumbers1 = new ArrayList<>();
		;
		includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35", Collections.emptyList()));
		ProteinName includeRecName1 = factory.createProteinName(includeRecFullName1, includeRecShortNames1,
				includeRecEcNumbers1);

		Name includeAltFullName = factory.createName("Ornithine acetyltransferase", Collections.emptyList());
		List<Name> includAltShortNames1 = new ArrayList<>();
		includAltShortNames1.add(factory.createName("OATase", Collections.emptyList()));
		ProteinName includeAltName = factory.createProteinName(includeAltFullName, includAltShortNames1,
				Collections.emptyList());
		List<ProteinName> includeAltNames = new ArrayList<>();
		includeAltNames.add(includeAltName);

		Name includeAltFullName2 = factory.createName("Ornithine transacetylase", Collections.emptyList());
		List<Name> includAltShortNames2 = new ArrayList<>();
		// includAltShortNames2.add(factory.createProteinName("OATase",
		// Collections.emptyList()));
		ProteinName includeAltName2 = factory.createProteinName(includeAltFullName2, includAltShortNames2,
				Collections.emptyList());
		includeAltNames.add(includeAltName2);

		ProteinSection include1 = factory.createProteinNameSection(includeRecName1, includeAltNames);

		includes.add(include1);

		Name includeRecFullName11 = factory.createName("Amino-acid acetyltransferase", Collections.emptyList());
		List<Name> includeRecShortNames11 = new ArrayList<>();
		List<EC> includeRecEcNumbers11 = new ArrayList<>();
		;
		includeRecEcNumbers11.add(factory.createECNumber("2.3.1.1", Collections.emptyList()));
		ProteinName includeRecName11 = factory.createProteinName(includeRecFullName11, includeRecShortNames11,
				includeRecEcNumbers11);

		Name includeAltFullName12 = factory.createName("N-acetylglutamate synthase", Collections.emptyList());
		List<Name> includAltShortNames12 = new ArrayList<>();
		includAltShortNames12.add(factory.createName("AGS", Collections.emptyList()));
		ProteinName includeAltName12 = factory.createProteinName(includeAltFullName12, includAltShortNames12,
				Collections.emptyList());
		List<ProteinName> includeAltNames2 = new ArrayList<>();
		includeAltNames2.add(includeAltName12);

		ProteinSection include2 = factory.createProteinNameSection(includeRecName11, includeAltNames2);

		includes.add(include2);

		List<ProteinSection> contains = new ArrayList<>();
		Name containRecFullName1 = factory.createName("Arginine biosynthesis bifunctional protein argJ alpha chain",
				Collections.emptyList());
		List<Name> containRecShortNames1 = new ArrayList<>();
		List<EC> containRecEcNumbers1 = new ArrayList<>();
		;
		// includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35",
		// Collections.emptyList()));
		ProteinName containRecName1 = factory.createProteinName(containRecFullName1, containRecShortNames1,
				containRecEcNumbers1);
		ProteinSection contain1 = factory.createProteinNameSection(containRecName1, null);
		contains.add(contain1);

		Name containRecFullName2 = factory.createName("Arginine biosynthesis bifunctional protein argJ beta chain",
				Collections.emptyList());
		List<Name> containRecShortNames2 = new ArrayList<>();
		List<EC> containRecEcNumbers2 = new ArrayList<>();
		;
		// includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35",
		// Collections.emptyList()));
		ProteinName containRecName2 = factory.createProteinName(containRecFullName2, containRecShortNames2,
				containRecEcNumbers2);
		ProteinSection contain2 = factory.createProteinNameSection(containRecName2, null);
		contains.add(contain2);

		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		ProteinDescription proteinDescription = builder.recommendedName(recName).includes(includes).contains(contains)
				.build();

		doTest(deLine, proteinDescription);
	}

	@Test
	public void testIncludeAndContainWithEvidenceEvidence() {
		String deLine = "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ {ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE   Includes:\n"
				+ "DE     RecName: Full=Glutamate N-acetyltransferase {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE              EC=2.3.1.35 {ECO:0000269|PubMed:10433554};\n"
				+ "DE     AltName: Full=Ornithine acetyltransferase;\n" + "DE              Short=OATase;\n"
				+ "DE     AltName: Full=Ornithine transacetylase {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
				+ "DE   Includes:\n"
				+ "DE     RecName: Full=Amino-acid acetyltransferase {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6};\n"
				+ "DE              EC=2.3.1.1 {ECO:0000256|HAMAP-Rule:MF_00205};\n"
				+ "DE     AltName: Full=N-acetylglutamate synthase {ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2};\n"
				+ "DE              Short=AGS;\n" + "DE   Contains:\n"
				+ "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha chain {ECO:0000313|EMBL:BAG16761.1};\n"
				+ "DE   Contains:\n"
				+ "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta chain {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};";

		Name recFullName = factory.createName("Arginine biosynthesis bifunctional protein argJ",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> recShortNames = new ArrayList<>();
		List<EC> recEcNumbers = new ArrayList<>();
		;
		ProteinName recName = factory.createProteinName(recFullName, recShortNames, recEcNumbers);

		List<ProteinSection> includes = new ArrayList<>();

		Name includeRecFullName1 = factory.createName("Glutamate N-acetyltransferase", createEvidence(
				Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554", "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> includeRecShortNames1 = new ArrayList<>();
		List<EC> includeRecEcNumbers1 = new ArrayList<>();
		;
		includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35",
				createEvidence(Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554" }))));
		ProteinName includeRecName1 = factory.createProteinName(includeRecFullName1, includeRecShortNames1,
				includeRecEcNumbers1);

		Name includeAltFullName = factory.createName("Ornithine acetyltransferase",
				createEvidence(Arrays.asList(new String[] {})));
		List<Name> includAltShortNames1 = new ArrayList<>();
		includAltShortNames1.add(factory.createName("OATase", Collections.emptyList()));
		ProteinName includeAltName = factory.createProteinName(includeAltFullName, includAltShortNames1,
				Collections.emptyList());
		List<ProteinName> includeAltNames = new ArrayList<>();
		includeAltNames.add(includeAltName);

		Name includeAltFullName2 = factory.createName("Ornithine transacetylase",
				createEvidence(Arrays.asList(new String[] { "ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2" })));
		List<Name> includAltShortNames2 = new ArrayList<>();
		// includAltShortNames2.add(factory.createProteinName("OATase",
		// Collections.emptyList()));
		ProteinName includeAltName2 = factory.createProteinName(includeAltFullName2, includAltShortNames2,
				Collections.emptyList());
		includeAltNames.add(includeAltName2);

		ProteinSection include1 = factory.createProteinNameSection(includeRecName1, includeAltNames);

		includes.add(include1);

		Name includeRecFullName11 = factory.createName("Amino-acid acetyltransferase",
				createEvidence(Arrays.asList(new String[] { "ECO:0000256|HAMAP-Rule:MF_00205", "ECO:0000303|Ref.6" })));
		List<Name> includeRecShortNames11 = new ArrayList<>();
		List<EC> includeRecEcNumbers11 = new ArrayList<>();
		;
		includeRecEcNumbers11.add(factory.createECNumber("2.3.1.1",
				createEvidence(Arrays.asList(new String[] { "ECO:0000256|HAMAP-Rule:MF_00205" }))));
		ProteinName includeRecName11 = factory.createProteinName(includeRecFullName11, includeRecShortNames11,
				includeRecEcNumbers11);

		Name includeAltFullName12 = factory.createName("N-acetylglutamate synthase",
				createEvidence(Arrays.asList(new String[] { "ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2" })));
		List<Name> includAltShortNames12 = new ArrayList<>();
		includAltShortNames12.add(factory.createName("AGS", Collections.emptyList()));
		ProteinName includeAltName12 = factory.createProteinName(includeAltFullName12, includAltShortNames12,
				Collections.emptyList());
		List<ProteinName> includeAltNames2 = new ArrayList<>();
		includeAltNames2.add(includeAltName12);

		ProteinSection include2 = factory.createProteinNameSection(includeRecName11, includeAltNames2);

		includes.add(include2);

		List<ProteinSection> contains = new ArrayList<>();
		Name containRecFullName1 = factory.createName("Arginine biosynthesis bifunctional protein argJ alpha chain",
				createEvidence(Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> containRecShortNames1 = new ArrayList<>();
		List<EC> containRecEcNumbers1 = new ArrayList<>();
		;
		// includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35",
		// Collections.emptyList()));
		ProteinName containRecName1 = factory.createProteinName(containRecFullName1, containRecShortNames1,
				containRecEcNumbers1);
		ProteinSection contain1 = factory.createProteinNameSection(containRecName1, null);
		contains.add(contain1);

		Name containRecFullName2 = factory.createName("Arginine biosynthesis bifunctional protein argJ beta chain",
				createEvidence(
						Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554", "ECO:0000313|EMBL:BAG16761.1" })));
		List<Name> containRecShortNames2 = new ArrayList<>();
		List<EC> containRecEcNumbers2 = new ArrayList<>();
		;
		// includeRecEcNumbers1.add(factory.createECNumber("2.3.1.35",
		// Collections.emptyList()));
		ProteinName containRecName2 = factory.createProteinName(containRecFullName2, containRecShortNames2,
				containRecEcNumbers2);
		ProteinSection contain2 = factory.createProteinNameSection(containRecName2, null);
		contains.add(contain2);

		ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.newInstance();
		ProteinDescription proteinDescription = builder.recommendedName(recName).includes(includes).contains(contains)
				.build();

		doTest(deLine, proteinDescription);
	}

}
