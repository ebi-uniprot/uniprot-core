package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.de.DELineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.cv.evidence.EvidenceHelper;

class DELineBuildTest {
    private DELineBuilder builder = new DELineBuilder();

    @Test
    void test1() {
        Name recFullName = createName("14-3-3-like protein GF14 iota", Collections.emptyList());
        List<Name> recShortNames = new ArrayList<>();
        recShortNames.add(createName("ADAM 10", Collections.emptyList()));
        List<EC> recEcNumbers = new ArrayList<>();
        recEcNumbers.add(createECNumber("2.3.1.35", Collections.emptyList()));
        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);
        Name altFullName = createName("General regulatory factor 12", Collections.emptyList());
        ProteinName altName =
                createProteinAltName(altFullName, Collections.emptyList(), Collections.emptyList());
        List<ProteinName> altNames = new ArrayList<>();
        altNames.add(altName);

        ProteinDescription proteinDescription =
                createProteinDescription(recName, altNames, null, null);
        String deLine =
                "DE   RecName: Full=14-3-3-like protein GF14 iota;\n"
                        + "DE            Short=ADAM 10;\n"
                        + "DE            EC=2.3.1.35;\n"
                        + "DE   AltName: Full=General regulatory factor 12;";

        doTest(deLine, proteinDescription);
    }

    private ProteinDescription createProteinDescription(
            ProteinName recName,
            List<ProteinName> altNames,
            List<ProteinSubName> submissionNames,
            FlagType flag) {
        return new ProteinDescriptionBuilder()
                .recommendedName(recName)
                .alternativeNamesSet(altNames)
                .submissionNamesSet(submissionNames)
                .flag(flag)
                .build();
    }

    private ProteinName createProteinAltName(
            Name recFullName, List<Name> recShortNames, List<EC> recEcNumbers) {
        return new ProteinNameBuilder()
                .ecNumbersSet(recEcNumbers)
                .shortNamesSet(recShortNames)
                .fullName(recFullName)
                .build();
    }

    private ProteinName createProteinRecName(
            Name recFullName, List<Name> recShortNames, List<EC> recEcNumbers) {
        return new ProteinNameBuilder()
                .ecNumbersSet(recEcNumbers)
                .shortNamesSet(recShortNames)
                .fullName(recFullName)
                .build();
    }

    private ProteinSubName createProteinSubName(Name recFullName, List<EC> recEcNumbers) {
        return new ProteinSubNameBuilder().ecNumbersSet(recEcNumbers).fullName(recFullName).build();
    }

    private EC createECNumber(String ec, List<Evidence> evidences) {
        return new ECBuilder().value(ec).evidencesSet(evidences).build();
    }

    private Name createName(String name, List<Evidence> evidences) {
        return new NameBuilder().value(name).evidencesSet(evidences).build();
    }

    private void doTest(String deLine, ProteinDescription pd) {
        FFLine ffLine = builder.buildWithEvidence(pd);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        System.out.println(deLine);
        assertEquals(deLine, resultString);
    }

    @Test
    void test1Evidence() {
        Name recFullName =
                createName(
                        "14-3-3-like protein GF14 iota",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000269|PubMed:10433554",
                                            "ECO:0000313|EMBL:BAG16761.1"
                                        })));
        List<Name> recShortNames = new ArrayList<>();
        recShortNames.add(
                createName(
                        "ADAM 10",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554"}))));
        List<EC> recEcNumbers = new ArrayList<>();
        recEcNumbers.add(
                createECNumber(
                        "2.3.1.35",
                        createEvidence(Arrays.asList(new String[] {"ECO:0000303|Ref.6"}))));
        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);
        Name altFullName =
                createName(
                        "General regulatory factor 12",
                        createEvidence(Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"})));
        ProteinName altName =
                createProteinAltName(altFullName, Collections.emptyList(), Collections.emptyList());
        List<ProteinName> altNames = new ArrayList<>();
        altNames.add(altName);

        ProteinDescription proteinDescription =
                createProteinDescription(recName, altNames, null, null);

        String deLine =
                "DE   RecName: Full=14-3-3-like protein GF14 iota {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE            Short=ADAM 10 {ECO:0000269|PubMed:10433554};\n"
                        + "DE            EC=2.3.1.35 {ECO:0000303|Ref.6};\n"
                        + "DE   AltName: Full=General regulatory factor 12 {ECO:0000313|PDB:3OW2};";

        doTest(deLine, proteinDescription);
    }

    private List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }

    @Test
    void test2() {

        Name recFullName =
                createName("Granulocyte colony-stimulating factor", Collections.emptyList());
        List<Name> recShortNames = new ArrayList<>();
        recShortNames.add(createName("G-CSF", Collections.emptyList()));
        List<EC> recEcNumbers = new ArrayList<>();

        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);
        Name altFullName = createName("Pluripoietin", Collections.emptyList());
        ProteinName altName =
                createProteinAltName(altFullName, Collections.emptyList(), Collections.emptyList());

        List<ProteinName> altNames = new ArrayList<>();

        List<Name> innNames = new ArrayList<>();
        innNames.add(createName("Lenograstim", Collections.emptyList()));
        List<Name> cdAntigenNames = new ArrayList<>();
        cdAntigenNames.add(createName("CD156c", Collections.emptyList()));
        ProteinName altName2 = ProteinNameBuilder.from(altName).build();

        //	ProteinAlternativeName pAltName = factory.createProteinAlternativeName(altNames, null,
        // null, cdAntigenNames,
        //			innNames);
        altNames.add(altName2);
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        ProteinDescription proteinDescription =
                builder.recommendedName(recName)
                        .alternativeNamesSet(altNames)
                        .flag(FlagType.FRAGMENT_PRECURSOR)
                        .innNamesSet(innNames)
                        .cdAntigenNamesSet(cdAntigenNames)
                        .build();

        String deLine =
                "DE   RecName: Full=Granulocyte colony-stimulating factor;\n"
                        + "DE            Short=G-CSF;\n"
                        + "DE   AltName: Full=Pluripoietin;\n"
                        + "DE   AltName: CD_antigen=CD156c;\n"
                        + "DE   AltName: INN=Lenograstim;\n"
                        + "DE   Flags: Precursor; Fragment;";

        doTest(deLine, proteinDescription);
    }

    @Test
    void test2Evidence() {

        Name recFullName =
                createName(
                        "Granulocyte colony-stimulating factor",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000303|Ref.6", "ECO:0000313|EMBL:BAG16761.1"
                                        })));
        List<Name> recShortNames = new ArrayList<>();
        recShortNames.add(
                createName(
                        "G-CSF",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000269|PubMed:10433554",
                                            "ECO:0000313|EMBL:BAG16761.1"
                                        }))));
        List<EC> recEcNumbers = new ArrayList<>();

        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);
        Name altFullName =
                createName(
                        "Pluripoietin",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"})));
        ProteinName altName =
                createProteinAltName(altFullName, Collections.emptyList(), Collections.emptyList());
        List<ProteinName> altNames = new ArrayList<>();

        List<Name> innNames = new ArrayList<>();
        innNames.add(
                createName(
                        "Lenograstim",
                        createEvidence(Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}))));
        List<Name> cdAntigenNames = new ArrayList<>();
        cdAntigenNames.add(createName("CD156c", Collections.emptyList()));
        ProteinName altName2 = ProteinNameBuilder.from(altName).build();

        altNames.add(altName2);
        //		ProteinAlternativeName pAltName = factory.createProteinAlternativeName(altNames, null,
        // null, cdAntigenNames,
        //				innNames);

        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        ProteinDescription proteinDescription =
                builder.recommendedName(recName)
                        .alternativeNamesSet(altNames)
                        .flag(FlagType.FRAGMENT_PRECURSOR)
                        .innNamesSet(innNames)
                        .cdAntigenNamesSet(cdAntigenNames)
                        .build();

        String deLine =
                "DE   RecName: Full=Granulocyte colony-stimulating factor {ECO:0000303|Ref.6,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE            Short=G-CSF {ECO:0000269|PubMed:10433554,"
                        + " ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE   AltName: Full=Pluripoietin {ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "DE   AltName: CD_antigen=CD156c;\n"
                        + "DE   AltName: INN=Lenograstim {ECO:0000313|PDB:3OW2};\n"
                        + "DE   Flags: Precursor; Fragment;";

        doTest(deLine, proteinDescription);
    }

    @Test
    void testSubName() {

        List<ProteinSubName> submissionNames = new ArrayList<>();
        Name fullName1 = createName("Expressed protein", Collections.emptyList());
        List<EC> ecNumbers1 = new ArrayList<>();
        submissionNames.add(createProteinSubName(fullName1, ecNumbers1));
        Name fullName2 =
                createName("cDNA clone:001-021-F08, full insert sequence", Collections.emptyList());
        List<EC> ecNumbers2 = new ArrayList<>();
        ecNumbers2.add(createECNumber("2.3.1.35", createEvidence(Arrays.asList(new String[] {}))));
        submissionNames.add(createProteinSubName(fullName2, ecNumbers2));

        Name fullName3 =
                createName("cDNA clone:006-308-H01, full insert sequence", Collections.emptyList());
        List<EC> ecNumbers3 = new ArrayList<>();

        submissionNames.add(createProteinSubName(fullName3, ecNumbers3));

        ProteinDescription proteinDescription =
                createProteinDescription(null, null, submissionNames, FlagType.FRAGMENT);
        String deLine =
                "DE   SubName: Full=Expressed protein;\n"
                        + "DE   SubName: Full=cDNA clone:001-021-F08, full insert sequence;\n"
                        + "DE            EC=2.3.1.35;\n"
                        + "DE   SubName: Full=cDNA clone:006-308-H01, full insert sequence;\n"
                        + "DE   Flags: Fragment;";

        doTest(deLine, proteinDescription);
    }

    @Test
    void testSubNameWithEvidence() {
        List<ProteinSubName> submissionNames = new ArrayList<>();
        Name fullName1 =
                createName(
                        "Expressed protein",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"})));
        List<EC> ecNumbers1 = new ArrayList<>();
        submissionNames.add(createProteinSubName(fullName1, ecNumbers1));
        Name fullName2 =
                createName(
                        "cDNA clone:001-021-F08, full insert sequence",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.2"})));
        List<EC> ecNumbers2 = new ArrayList<>();
        ecNumbers2.add(
                createECNumber(
                        "2.3.1.35",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554"}))));
        submissionNames.add(createProteinSubName(fullName2, ecNumbers2));

        Name fullName3 =
                createName(
                        "cDNA clone:006-308-H01, full insert sequence",
                        createEvidence(Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"})));
        List<EC> ecNumbers3 = new ArrayList<>();

        submissionNames.add(createProteinSubName(fullName3, ecNumbers3));

        ProteinDescription proteinDescription =
                createProteinDescription(null, null, submissionNames, FlagType.FRAGMENT);
        String deLine =
                "DE   SubName: Full=Expressed protein {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE   SubName: Full=cDNA clone:001-021-F08, full insert sequence"
                        + " {ECO:0000313|EMBL:BAG16761.2};\n"
                        + "DE            EC=2.3.1.35 {ECO:0000269|PubMed:10433554};\n"
                        + "DE   SubName: Full=cDNA clone:006-308-H01, full insert sequence"
                        + " {ECO:0000313|PDB:3OW2};\n"
                        + "DE   Flags: Fragment;";

        doTest(deLine, proteinDescription);
    }

    @Test
    void testIncludeAndContain() {
        String deLine =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase;\n"
                        + "DE              EC=2.3.1.35;\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase;\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase;\n"
                        + "DE              EC=2.3.1.1;\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase;\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha"
                        + " chain;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta"
                        + " chain;";

        Name recFullName =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ", Collections.emptyList());
        List<Name> recShortNames = new ArrayList<>();
        List<EC> recEcNumbers = new ArrayList<>();
        ;
        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);

        List<ProteinSection> includes = new ArrayList<>();

        Name includeRecFullName1 =
                createName("Glutamate N-acetyltransferase", Collections.emptyList());
        List<Name> includeRecShortNames1 = new ArrayList<>();
        List<EC> includeRecEcNumbers1 = new ArrayList<>();
        ;
        includeRecEcNumbers1.add(createECNumber("2.3.1.35", Collections.emptyList()));
        ProteinName includeRecName1 =
                createProteinRecName(
                        includeRecFullName1, includeRecShortNames1, includeRecEcNumbers1);

        Name includeAltFullName =
                createName("Ornithine acetyltransferase", Collections.emptyList());
        List<Name> includAltShortNames1 = new ArrayList<>();
        includAltShortNames1.add(createName("OATase", Collections.emptyList()));
        ProteinName includeAltName =
                createProteinAltName(
                        includeAltFullName, includAltShortNames1, Collections.emptyList());
        List<ProteinName> includeAltNames = new ArrayList<>();
        includeAltNames.add(includeAltName);

        Name includeAltFullName2 = createName("Ornithine transacetylase", Collections.emptyList());
        List<Name> includAltShortNames2 = new ArrayList<>();
        // includAltShortNames2.add(createProteinName("OATase",
        // Collections.emptyList()));
        ProteinName includeAltName2 =
                createProteinAltName(
                        includeAltFullName2, includAltShortNames2, Collections.emptyList());
        includeAltNames.add(includeAltName2);

        ProteinSection include1 = createProteinNameSection(includeRecName1, includeAltNames);

        includes.add(include1);

        Name includeRecFullName11 =
                createName("Amino-acid acetyltransferase", Collections.emptyList());
        List<Name> includeRecShortNames11 = new ArrayList<>();
        List<EC> includeRecEcNumbers11 = new ArrayList<>();
        ;
        includeRecEcNumbers11.add(createECNumber("2.3.1.1", Collections.emptyList()));
        ProteinName includeRecName11 =
                createProteinRecName(
                        includeRecFullName11, includeRecShortNames11, includeRecEcNumbers11);

        Name includeAltFullName12 =
                createName("N-acetylglutamate synthase", Collections.emptyList());
        List<Name> includAltShortNames12 = new ArrayList<>();
        includAltShortNames12.add(createName("AGS", Collections.emptyList()));
        ProteinName includeAltName12 =
                createProteinAltName(
                        includeAltFullName12, includAltShortNames12, Collections.emptyList());
        List<ProteinName> includeAltNames2 = new ArrayList<>();
        includeAltNames2.add(includeAltName12);

        ProteinSection include2 = createProteinNameSection(includeRecName11, includeAltNames2);

        includes.add(include2);

        List<ProteinSection> contains = new ArrayList<>();
        Name containRecFullName1 =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ alpha chain",
                        Collections.emptyList());
        List<Name> containRecShortNames1 = new ArrayList<>();
        List<EC> containRecEcNumbers1 = new ArrayList<>();
        ;
        // includeRecEcNumbers1.add(createECNumber("2.3.1.35",
        // Collections.emptyList()));
        ProteinName containRecName1 =
                createProteinRecName(
                        containRecFullName1, containRecShortNames1, containRecEcNumbers1);
        ProteinSection contain1 = createProteinNameSection(containRecName1, null);
        contains.add(contain1);

        Name containRecFullName2 =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ beta chain",
                        Collections.emptyList());
        List<Name> containRecShortNames2 = new ArrayList<>();
        List<EC> containRecEcNumbers2 = new ArrayList<>();
        ;
        // includeRecEcNumbers1.add(createECNumber("2.3.1.35",
        // Collections.emptyList()));
        ProteinName containRecName2 =
                createProteinRecName(
                        containRecFullName2, containRecShortNames2, containRecEcNumbers2);
        ProteinSection contain2 = createProteinNameSection(containRecName2, null);
        contains.add(contain2);

        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        ProteinDescription proteinDescription =
                builder.recommendedName(recName)
                        .includesSet(includes)
                        .containsSet(contains)
                        .build();

        doTest(deLine, proteinDescription);
    }

    @Test
    void testIncludeAndContainWithEvidenceEvidence() {
        String deLine =
                "DE   RecName: Full=Arginine biosynthesis bifunctional protein argJ"
                        + " {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Glutamate N-acetyltransferase"
                        + " {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE              EC=2.3.1.35 {ECO:0000269|PubMed:10433554};\n"
                        + "DE     AltName: Full=Ornithine acetyltransferase;\n"
                        + "DE              Short=OATase;\n"
                        + "DE     AltName: Full=Ornithine transacetylase {ECO:0000303|Ref.6,"
                        + " ECO:0000313|PDB:3OW2};\n"
                        + "DE   Includes:\n"
                        + "DE     RecName: Full=Amino-acid acetyltransferase"
                        + " {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000303|Ref.6};\n"
                        + "DE              EC=2.3.1.1 {ECO:0000256|HAMAP-Rule:MF_00205};\n"
                        + "DE     AltName: Full=N-acetylglutamate synthase {ECO:0000303|Ref.6,"
                        + " ECO:0000313|PDB:3OW2};\n"
                        + "DE              Short=AGS;\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ alpha"
                        + " chain {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "DE   Contains:\n"
                        + "DE     RecName: Full=Arginine biosynthesis bifunctional protein argJ beta"
                        + " chain {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};";

        Name recFullName =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"})));
        List<Name> recShortNames = new ArrayList<>();
        List<EC> recEcNumbers = new ArrayList<>();
        ;
        ProteinName recName = createProteinRecName(recFullName, recShortNames, recEcNumbers);

        List<ProteinSection> includes = new ArrayList<>();

        Name includeRecFullName1 =
                createName(
                        "Glutamate N-acetyltransferase",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000269|PubMed:10433554",
                                            "ECO:0000313|EMBL:BAG16761.1"
                                        })));
        List<Name> includeRecShortNames1 = new ArrayList<>();
        List<EC> includeRecEcNumbers1 = new ArrayList<>();
        ;
        includeRecEcNumbers1.add(
                createECNumber(
                        "2.3.1.35",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554"}))));
        ProteinName includeRecName1 =
                createProteinRecName(
                        includeRecFullName1, includeRecShortNames1, includeRecEcNumbers1);

        Name includeAltFullName =
                createName(
                        "Ornithine acetyltransferase",
                        createEvidence(Arrays.asList(new String[] {})));
        List<Name> includAltShortNames1 = new ArrayList<>();
        includAltShortNames1.add(createName("OATase", Collections.emptyList()));
        ProteinName includeAltName =
                createProteinAltName(
                        includeAltFullName, includAltShortNames1, Collections.emptyList());
        List<ProteinName> includeAltNames = new ArrayList<>();
        includeAltNames.add(includeAltName);

        Name includeAltFullName2 =
                createName(
                        "Ornithine transacetylase",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2"
                                        })));
        List<Name> includAltShortNames2 = new ArrayList<>();
        // includAltShortNames2.add(createProteinName("OATase",
        // Collections.emptyList()));
        ProteinName includeAltName2 =
                createProteinAltName(
                        includeAltFullName2, includAltShortNames2, Collections.emptyList());
        includeAltNames.add(includeAltName2);

        ProteinSection include1 = createProteinNameSection(includeRecName1, includeAltNames);

        includes.add(include1);

        Name includeRecFullName11 =
                createName(
                        "Amino-acid acetyltransferase",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000256|HAMAP-Rule:MF_00205", "ECO:0000303|Ref.6"
                                        })));
        List<Name> includeRecShortNames11 = new ArrayList<>();
        List<EC> includeRecEcNumbers11 = new ArrayList<>();
        ;
        includeRecEcNumbers11.add(
                createECNumber(
                        "2.3.1.1",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"}))));
        ProteinName includeRecName11 =
                createProteinRecName(
                        includeRecFullName11, includeRecShortNames11, includeRecEcNumbers11);

        Name includeAltFullName12 =
                createName(
                        "N-acetylglutamate synthase",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2"
                                        })));
        List<Name> includAltShortNames12 = new ArrayList<>();
        includAltShortNames12.add(createName("AGS", Collections.emptyList()));
        ProteinName includeAltName12 =
                createProteinAltName(
                        includeAltFullName12, includAltShortNames12, Collections.emptyList());
        List<ProteinName> includeAltNames2 = new ArrayList<>();
        includeAltNames2.add(includeAltName12);

        ProteinSection include2 = createProteinNameSection(includeRecName11, includeAltNames2);

        includes.add(include2);

        List<ProteinSection> contains = new ArrayList<>();
        Name containRecFullName1 =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ alpha chain",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"})));
        List<Name> containRecShortNames1 = new ArrayList<>();
        List<EC> containRecEcNumbers1 = new ArrayList<>();
        ;
        // includeRecEcNumbers1.add(createECNumber("2.3.1.35",
        // Collections.emptyList()));
        ProteinName containRecName1 =
                createProteinRecName(
                        containRecFullName1, containRecShortNames1, containRecEcNumbers1);
        ProteinSection contain1 = createProteinNameSection(containRecName1, null);
        contains.add(contain1);

        Name containRecFullName2 =
                createName(
                        "Arginine biosynthesis bifunctional protein argJ beta chain",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000269|PubMed:10433554",
                                            "ECO:0000313|EMBL:BAG16761.1"
                                        })));
        List<Name> containRecShortNames2 = new ArrayList<>();
        List<EC> containRecEcNumbers2 = new ArrayList<>();
        ;
        // includeRecEcNumbers1.add(createECNumber("2.3.1.35",
        // Collections.emptyList()));
        ProteinName containRecName2 =
                createProteinRecName(
                        containRecFullName2, containRecShortNames2, containRecEcNumbers2);
        ProteinSection contain2 = createProteinNameSection(containRecName2, null);
        contains.add(contain2);

        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        ProteinDescription proteinDescription =
                builder.recommendedName(recName)
                        .includesSet(includes)
                        .containsSet(contains)
                        .build();

        doTest(deLine, proteinDescription);
    }

    private ProteinSection createProteinNameSection(
            ProteinName recName, List<ProteinName> altNames) {
        return new ProteinSectionBuilder()
                .recommendedName(recName)
                .alternativeNamesSet(altNames)
                .build();
    }
}
