package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.flatfile.parser.impl.kw.KWLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.builder.KeywordBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.impl.EvidenceHelper;

class KWLineBuildTest {
    KWLineBuilder builder = new KWLineBuilder();

    @Test
    void test1() {
        String kwLine =
                "KW   Complete proteome; Metal-binding; Repeat; Virus reference strain;\n"
                        + "KW   Zinc-finger.";

        List<Keyword> keywords = new ArrayList<>();
        keywords.add(createKeyword("KW-0181", "Complete proteome", Collections.emptyList()));
        keywords.add(createKeyword("KW-0479", "Metal-binding", Collections.emptyList()));
        keywords.add(createKeyword("KW-0677", "Repeat", Collections.emptyList()));
        keywords.add(createKeyword("", "Virus reference strain", Collections.emptyList()));
        keywords.add(createKeyword("KW-0863", "Zinc-finger", Collections.emptyList()));
        doTest(kwLine, keywords);
    }

    @Test
    void test1Evidence() {
        String kwLine =
                "KW   Complete proteome {ECO:0000269|PubMed:10433554,\n"
                        + "KW   ECO:0000313|EMBL:BAG16761.1}; Metal-binding;\n"
                        + "KW   Repeat {ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1};\n"
                        + "KW   Virus reference strain {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "KW   ECO:0000303|Ref.6};\n"
                        + "KW   Zinc-finger {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}.";
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(
                createKeyword(
                        "",
                        "Complete proteome",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000269|PubMed:10433554",
                                            "ECO:0000313|EMBL:BAG16761.1"
                                        }))));
        keywords.add(
                createKeyword("", "Metal-binding", createEvidence(Arrays.asList(new String[] {}))));
        keywords.add(
                createKeyword(
                        "",
                        "Repeat",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000303|Ref.6", "ECO:0000313|EMBL:BAG16761.1"
                                        }))));
        keywords.add(
                createKeyword(
                        "",
                        "Virus reference strain",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000256|HAMAP-Rule:MF_00205", "ECO:0000303|Ref.6"
                                        }))));
        keywords.add(
                createKeyword(
                        "",
                        "Zinc-finger",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000256|HAMAP-Rule:MF_00205",
                                            "ECO:0000313|PDB:3OW2"
                                        }))));
        doTest(kwLine, keywords);
    }

    @Test
    void test3() {
        String kwLine =
                "KW   Cell membrane; Complete proteome; Glycoprotein; GPI-anchor; Lipoprotein;\n"
                        + "KW   Membrane; Repeat; Signal; Sporozoite.";

        List<Keyword> keywords = new ArrayList<>();
        keywords.add(createKeyword("", "Cell membrane", Collections.emptyList()));
        keywords.add(createKeyword("", "Complete proteome", Collections.emptyList()));
        keywords.add(createKeyword("", "Glycoprotein", Collections.emptyList()));
        keywords.add(createKeyword("", "GPI-anchor", Collections.emptyList()));
        keywords.add(createKeyword("", "Lipoprotein", Collections.emptyList()));
        keywords.add(createKeyword("", "Membrane", Collections.emptyList()));
        keywords.add(createKeyword("", "Repeat", Collections.emptyList()));
        keywords.add(createKeyword("", "Signal", Collections.emptyList()));
        keywords.add(createKeyword("", "Sporozoite", Collections.emptyList()));
        doTest(kwLine, keywords);
    }

    @Test
    void testEvidence() {
        String kwLine =
                "KW   3D-structure {ECO:0000313, ECO:0000313|EMBL:EAW66463.1};\n"
                        + "KW   Allergen {ECO:0000313|EMBL:EAW66464.1};\n"
                        + "KW   Direct protein sequencing {ECO:0000313|EMBL:EAW66463.1,\n"
                        + "KW   ECO:0000313|Ensembl:ENSP0012134};\n"
                        + "KW   Disulfide bond {ECO:0000313|EMBL:EAW66463.1,\n"
                        + "KW   ECO:0000313|Ensembl:ENSP0012134}; Polymorphism;\n"
                        + "KW   Pyrrolidone carboxylic acid {ECO:0000313|EMBL:EAW66463.1,\n"
                        + "KW   ECO:0000313|Ensembl:ENSP0012134}; Seed storage protein; Signal;\n"
                        + "KW   Storage protein.";
        List<Keyword> keywords = new ArrayList<>();
        keywords.add(
                createKeyword(
                        "",
                        "3D-structure",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000313", "ECO:0000313|EMBL:EAW66463.1"
                                        }))));
        keywords.add(
                createKeyword(
                        "",
                        "Allergen",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:EAW66464.1"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Direct protein sequencing",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000313|EMBL:EAW66463.1",
                                            "ECO:0000313|Ensembl:ENSP0012134"
                                        }))));
        keywords.add(
                createKeyword(
                        "",
                        "Disulfide bond",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000313|EMBL:EAW66463.1",
                                            "ECO:0000313|Ensembl:ENSP0012134"
                                        }))));
        keywords.add(
                createKeyword("", "Polymorphism", createEvidence(Arrays.asList(new String[] {}))));
        keywords.add(
                createKeyword(
                        "",
                        "Pyrrolidone carboxylic acid",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {
                                            "ECO:0000313|EMBL:EAW66463.1",
                                            "ECO:0000313|Ensembl:ENSP0012134"
                                        }))));
        keywords.add(
                createKeyword(
                        "",
                        "Seed storage protein",
                        createEvidence(Arrays.asList(new String[] {}))));
        keywords.add(createKeyword("", "Signal", createEvidence(Arrays.asList(new String[] {}))));
        keywords.add(
                createKeyword(
                        "", "Storage protein", createEvidence(Arrays.asList(new String[] {}))));
        doTest(kwLine, keywords);
    }

    @Test
    void testEvidence2() {
        String kwLine =
                "KW   Disulfide bond {ECO:0000256|SAAS:SAAS000777_004_000331};\n"
                        + "KW   Fusion of virus membrane with host membrane\n"
                        + "KW   {ECO:0000256|SAAS:SAAS000777_004_001688};\n"
                        + "KW   Host-virus interaction {ECO:0000256|SAAS:SAAS000777_004_000688};\n"
                        + "KW   Viral attachment to host cell {ECO:0000256|SAAS:SAAS000777_004_000923};\n"
                        + "KW   Viral envelope protein {ECO:0000313|EMBL:AAY20056.1};\n"
                        + "KW   Viral penetration into host cytoplasm\n"
                        + "KW   {ECO:0000256|SAAS:SAAS000777_004_001402}; Virion;\n"
                        + "KW   Virus entry into host cell {ECO:0000256|SAAS:SAAS000777_004_000842}.";

        List<Keyword> keywords = new ArrayList<>();
        keywords.add(
                createKeyword(
                        "",
                        "Disulfide bond",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_000331"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Fusion of virus membrane with host membrane",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_001688"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Host-virus interaction",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_000688"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Viral attachment to host cell",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_000923"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Viral envelope protein",
                        createEvidence(
                                Arrays.asList(new String[] {"ECO:0000313|EMBL:AAY20056.1"}))));
        keywords.add(
                createKeyword(
                        "",
                        "Viral penetration into host cytoplasm",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_001402"}))));
        keywords.add(createKeyword("", "Virion", createEvidence(Arrays.asList(new String[] {}))));
        keywords.add(
                createKeyword(
                        "",
                        "Virus entry into host cell",
                        createEvidence(
                                Arrays.asList(
                                        new String[] {"ECO:0000256|SAAS:SAAS000777_004_000842"}))));
        doTest(kwLine, keywords);
    }

    private Keyword createKeyword(String id, String kw, List<Evidence> evidences) {
        return new KeywordBuilder()
                .id(id)
                .value(kw)
                .category(KeywordCategory.DOMAIN)
                .evidencesSet(evidences)
                .build();
    }

    private void doTest(String deLine, List<Keyword> genes) {
        FFLine ffLine = builder.buildWithEvidence(genes);
        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println("\n");
        assertEquals(deLine, resultString);
    }

    private List<Evidence> createEvidence(List<String> evIds) {

        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }
}
