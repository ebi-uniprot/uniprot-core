package org.uniprot.core.flatfile.writer.line;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.gn.GNLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.gene.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.cv.evidence.EvidenceHelper;

@Slf4j
class GNLineBuildTest {
    GNLineBuilder builder = new GNLineBuilder();

    @Test
    void test1() {
        String gnLine =
                "GN   Name=par-5; Synonyms=ftt-1; OrderedLocusNames=At1g22300;\n"
                        + "GN   ORFNames=M117.2;";

        List<Gene> genes = new ArrayList<>();
        GeneName geneName = new GeneNameBuilder("par-5", emptyList()).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        synonyms.add(new GeneNameSynonymBuilder("ftt-1", emptyList()).build());
        List<OrderedLocusName> olnNames = new ArrayList<>();
        olnNames.add(new OrderedLocusNameBuilder("At1g22300", emptyList()).build());
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(new ORFNameBuilder("M117.2", emptyList()).build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        doTest(gnLine, genes);
    }

    private void doTest(String deLine, List<Gene> genes) {
        FFLine ffLine = builder.buildWithEvidence(genes);
        String resultString = ffLine.toString();
        log.debug(resultString);
        log.debug("\n");
        //	log.debug(deLine);
        assertEquals(deLine, resultString);
    }

    private List<Evidence> createEvidence(List<String> evIds) {
        return evIds.stream().map(EvidenceHelper::parseEvidenceLine).collect(Collectors.toList());
    }

    @Test
    void test1Evidence() {
        String gnLine =
                "GN   Name=par-5 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "GN   Synonyms=ftt-1 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                        + "GN   OrderedLocusNames=At1g22300 {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "GN   ORFNames=M117.2 {ECO:0000256|HAMAP-Rule:MF_00205};";
        List<Gene> genes = new ArrayList<>();
        GeneName geneName =
                new GeneNameBuilder(
                                "par-5",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        synonyms.add(
                new GeneNameSynonymBuilder(
                                "ftt-1",
                                createEvidence(
                                        asList("ECO:0000269|PubMed:10433554", "ECO:0000303|Ref.6")))
                        .build());

        List<OrderedLocusName> olnNames = new ArrayList<>();
        olnNames.add(
                new OrderedLocusNameBuilder(
                                "At1g22300",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build());
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(
                new ORFNameBuilder(
                                "M117.2",
                                createEvidence(singletonList("ECO:0000256|HAMAP-Rule:MF_00205")))
                        .build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        doTest(gnLine, genes);
    }

    @Test
    void testTwoGenes() {
        String gnLine =
                "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da;\n"
                        + "GN   ORFNames=At1g22300, CG7877, M117.2;\n"
                        + "GN   and\n"
                        + "GN   Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;";

        List<Gene> genes = new ArrayList<>();
        GeneName geneName =
                new GeneNameBuilder("Jon99Cii", createEvidence(asList(new String[] {}))).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        synonyms.add(
                new GeneNameSynonymBuilder("SER1", createEvidence(asList(new String[] {})))
                        .build());
        synonyms.add(
                new GeneNameSynonymBuilder("SER5", createEvidence(asList(new String[] {})))
                        .build());
        synonyms.add(
                new GeneNameSynonymBuilder("Ser99Da", createEvidence(asList(new String[] {})))
                        .build());

        List<OrderedLocusName> olnNames = new ArrayList<>();
        //		olnNames.add(
        //		new OrderedLocusNameBuilder("At1g22300",
        //				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(
                new ORFNameBuilder("At1g22300", createEvidence(asList(new String[] {}))).build());
        orfNames.add(new ORFNameBuilder("CG7877", createEvidence(asList(new String[] {}))).build());
        orfNames.add(new ORFNameBuilder("M117.2", createEvidence(asList(new String[] {}))).build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        GeneName geneName1 =
                new GeneNameBuilder("Jon99Ciii", createEvidence(asList(new String[] {}))).build();
        List<GeneNameSynonym> synonyms1 = new ArrayList<>();
        synonyms1.add(
                new GeneNameSynonymBuilder("SER2", createEvidence(asList(new String[] {})))
                        .build());
        synonyms1.add(
                new GeneNameSynonymBuilder("SER5", createEvidence(asList(new String[] {})))
                        .build());
        synonyms1.add(
                new GeneNameSynonymBuilder("Ser99Db", createEvidence(asList(new String[] {})))
                        .build());

        List<OrderedLocusName> olnNames1 = new ArrayList<>();
        //		olnNames.add(
        //		new OrderedLocusNameBuilder("At1g22300",
        //				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
        List<ORFName> orfNames1 = new ArrayList<>();
        orfNames1.add(
                new ORFNameBuilder("CG15519", createEvidence(asList(new String[] {}))).build());

        genes.add(
                new GeneBuilder()
                        .geneName(geneName1)
                        .synonymsSet(synonyms1)
                        .orderedLocusNamesSet(olnNames1)
                        .orfNamesSet(orfNames1)
                        .build());

        doTest(gnLine, genes);
    }

    @Test
    void test2GenesWithEvidence() {
        String gnLine =
                "GN   Name=Jon99Cii {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "GN   Synonyms=SER1 {ECO:0000313|EMBL:BAG16761.1}, SER5"
                        + " {ECO:0000303|Ref.6},\n"
                        + "GN   Ser99Da {ECO:0000269|PubMed:10433554};\n"
                        + "GN   ORFNames=At1g22300 {ECO:0000313|EMBL:BAG16761.1}, CG7877\n"
                        + "GN   {ECO:0000313|EMBL:BAG16761.1}, M117.2 {ECO:0000313|PDB:3OW2};\n"
                        + "GN   and\n"
                        + "GN   Name=Jon99Ciii;\n"
                        + "GN   Synonyms=SER2, SER5 {ECO:0000256|HAMAP-Rule:MF_00205}, Ser99Db;\n"
                        + "GN   ORFNames=CG15519 {ECO:0000303|Ref.6};";
        List<Gene> genes = new ArrayList<>();
        GeneName geneName =
                new GeneNameBuilder(
                                "Jon99Cii",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        synonyms.add(
                new GeneNameSynonymBuilder(
                                "SER1",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build());
        synonyms.add(
                new GeneNameSynonymBuilder(
                                "SER5", createEvidence(singletonList("ECO:0000303|Ref.6")))
                        .build());
        synonyms.add(
                new GeneNameSynonymBuilder(
                                "Ser99Da",
                                createEvidence(singletonList("ECO:0000269|PubMed:10433554")))
                        .build());

        List<OrderedLocusName> olnNames = new ArrayList<>();
        //		olnNames.add(
        //		new OrderedLocusNameBuilder("At1g22300",
        //				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(
                new ORFNameBuilder(
                                "At1g22300",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build());
        orfNames.add(
                new ORFNameBuilder(
                                "CG7877",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build());
        orfNames.add(
                new ORFNameBuilder("M117.2", createEvidence(singletonList("ECO:0000313|PDB:3OW2")))
                        .build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        GeneName geneName1 =
                new GeneNameBuilder("Jon99Ciii", createEvidence(asList(new String[] {}))).build();
        List<GeneNameSynonym> synonyms1 = new ArrayList<>();
        synonyms1.add(
                new GeneNameSynonymBuilder("SER2", createEvidence(asList(new String[] {})))
                        .build());
        synonyms1.add(
                new GeneNameSynonymBuilder(
                                "SER5",
                                createEvidence(singletonList("ECO:0000256|HAMAP-Rule:MF_00205")))
                        .build());
        synonyms1.add(
                new GeneNameSynonymBuilder("Ser99Db", createEvidence(asList(new String[] {})))
                        .build());

        List<OrderedLocusName> olnNames1 = new ArrayList<>();
        //		olnNames.add(
        //		new OrderedLocusNameBuilder("At1g22300",
        //				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
        List<ORFName> orfNames1 = new ArrayList<>();
        orfNames1.add(
                new ORFNameBuilder("CG15519", createEvidence(singletonList("ECO:0000303|Ref.6")))
                        .build());

        genes.add(
                new GeneBuilder()
                        .geneName(geneName1)
                        .synonymsSet(synonyms1)
                        .orderedLocusNamesSet(olnNames1)
                        .orfNamesSet(orfNames1)
                        .build());

        doTest(gnLine, genes);
    }

    @Test
    void test3() {
        String gnLine =
                "GN   Name=GF14A; OrderedLocusNames=Os08g0480800, LOC_Os08g37490;\n"
                        + "GN   ORFNames=OJ1113_A10.40, OSJNBb0092C08.10;";
        List<Gene> genes = new ArrayList<>();
        GeneName geneName = new GeneNameBuilder("GF14A", emptyList()).build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        //		synonyms.add(
        //		new GeneNameSynonymBuilder("ftt-1",  Collections.emptyList())).build();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        olnNames.add(new OrderedLocusNameBuilder("Os08g0480800", emptyList()).build());
        olnNames.add(new OrderedLocusNameBuilder("LOC_Os08g37490", emptyList()).build());
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(new ORFNameBuilder("OJ1113_A10.40", emptyList()).build());
        orfNames.add(new ORFNameBuilder("OSJNBb0092C08.10", emptyList()).build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        doTest(gnLine, genes);
    }

    @Test
    void test3Evidence() {
        String gnLine =
                "GN   Name=GF14A {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1,\n"
                        + "GN   ECO:0000313|PDB:3OW2};\n"
                        + "GN   OrderedLocusNames=Os08g0480800 {ECO:0000269|PubMed:10433554,\n"
                        + "GN   ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}, LOC_Os08g37490\n"
                        + "GN   {ECO:0000313|EMBL:BAG16761.1};\n"
                        + "GN   ORFNames=OJ1113_A10.40 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                        + "GN   ECO:0000313|PDB:3OW2}, OSJNBb0092C08.10;";
        List<Gene> genes = new ArrayList<>();
        GeneName geneName =
                new GeneNameBuilder(
                                "GF14A",
                                createEvidence(
                                        asList(
                                                "ECO:0000269|PubMed:10433554",
                                                "ECO:0000313|EMBL:BAG16761.1",
                                                "ECO:0000313|PDB:3OW2")))
                        .build();
        List<GeneNameSynonym> synonyms = new ArrayList<>();
        //		synonyms.add(
        //		new GeneNameSynonymBuilder("ftt-1",  Collections.emptyList())).build();
        List<OrderedLocusName> olnNames = new ArrayList<>();
        olnNames.add(
                new OrderedLocusNameBuilder(
                                "Os08g0480800",
                                createEvidence(
                                        asList(
                                                "ECO:0000269|PubMed:10433554",
                                                "ECO:0000303|Ref.6",
                                                "ECO:0000313|PDB:3OW2")))
                        .build());
        olnNames.add(
                new OrderedLocusNameBuilder(
                                "LOC_Os08g37490",
                                createEvidence(singletonList("ECO:0000313|EMBL:BAG16761.1")))
                        .build());
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(
                new ORFNameBuilder(
                                "OJ1113_A10.40",
                                createEvidence(
                                        asList(
                                                "ECO:0000256|HAMAP-Rule:MF_00205",
                                                "ECO:0000313|PDB:3OW2")))
                        .build());
        orfNames.add(new ORFNameBuilder("OSJNBb0092C08.10", emptyList()).build());
        genes.add(
                new GeneBuilder()
                        .geneName(geneName)
                        .synonymsSet(synonyms)
                        .orderedLocusNamesSet(olnNames)
                        .orfNamesSet(orfNames)
                        .build());

        doTest(gnLine, genes);
    }

    @Test
    void testNoGeneName() {
        String gnLine =
                "GN   OrderedLocusNames=Os08g0480800, LOC_Os08g37490;\n"
                        + "GN   ORFNames=OJ1113_A10.40, OSJNBb0092C08.10;";
        List<Gene> genes = new ArrayList<>();

        List<OrderedLocusName> olnNames = new ArrayList<>();
        olnNames.add(new OrderedLocusNameBuilder("Os08g0480800", emptyList()).build());
        olnNames.add(new OrderedLocusNameBuilder("LOC_Os08g37490", emptyList()).build());
        List<ORFName> orfNames = new ArrayList<>();
        orfNames.add(new ORFNameBuilder("OJ1113_A10.40", emptyList()).build());
        orfNames.add(new ORFNameBuilder("OSJNBb0092C08.10", emptyList()).build());
        genes.add(new GeneBuilder().orderedLocusNamesSet(olnNames).orfNamesSet(orfNames).build());

        doTest(gnLine, genes);
    }
}
