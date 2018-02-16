package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.gene.GeneName;
import uk.ac.ebi.uniprot.domain.gene.GeneNameSynonym;
import uk.ac.ebi.uniprot.domain.gene.ORFName;
import uk.ac.ebi.uniprot.domain.gene.OrderedLocusName;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.EvidenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.GNLineBuilder;



public class GNLineBuildTest {
	GeneFactory factory =GeneFactory.INSTANCE;
	GNLineBuilder builder = new GNLineBuilder();
	@Test
	public void test1() {
		String gnLine = "GN   Name=par-5; Synonyms=ftt-1; OrderedLocusNames=At1g22300;\n" +
				"GN   ORFNames=M117.2;";
		
		
		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("par-5", Collections.emptyList());
		List<GeneNameSynonym> synonyms =new ArrayList<>();
		synonyms.add(
		factory.createGeneNameSynonym("ftt-1", Collections.emptyList()));
		List<OrderedLocusName> olnNames = new ArrayList<>();
		olnNames.add(
		factory.createOrderedLocusName("At1g22300", Collections.emptyList()));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("M117.2", Collections.emptyList()));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		doTest(gnLine, genes);
	}
	private void doTest(String deLine, List<Gene> genes){
		FFLine ffLine = builder.buildWithEvidence(genes);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		System.out.println("\n");
	//	System.out.println(deLine);
		assertEquals(deLine, resultString);
	}

	private List<Evidence> createEvidence(List<String> evIds) {
		EvidenceFactory evFactory = UniProtFactory.INSTANCE.getEvidenceFactory();
		return evIds.stream().map(val -> evFactory.createFromEvidenceLine(val)).collect(Collectors.toList());

	}

	@Test
	public void test1Evidence() {
		String gnLine = "GN   Name=par-5 {ECO:0000313|EMBL:BAG16761.1};\n" +
						"GN   Synonyms=ftt-1 {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n" +
						"GN   OrderedLocusNames=At1g22300 {ECO:0000313|EMBL:BAG16761.1};\n" +
						"GN   ORFNames=M117.2 {ECO:0000256|HAMAP-Rule:MF_00205};";
		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("par-5",
				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"})));
		List<GeneNameSynonym> synonyms =new ArrayList<>();
		synonyms.add(
		factory.createGeneNameSynonym("ftt-1",
				createEvidence(Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554", "ECO:0000303|Ref.6"}))));
				
		List<OrderedLocusName> olnNames = new ArrayList<>();
		olnNames.add(
		factory.createOrderedLocusName("At1g22300",
				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("M117.2", 
				createEvidence(Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"}))));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		doTest(gnLine, genes);
	}
	
	@Test
	public void testTwoGenes() {
		String gnLine = "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da;\n" +
						"GN   ORFNames=At1g22300, CG7877, M117.2;\n" +
					"GN   and\n" +
					"GN   Name=Jon99Ciii; Synonyms=SER2, SER5, Ser99Db; ORFNames=CG15519;";

		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("Jon99Cii",
				createEvidence(Arrays.asList(new String[] {})));
		List<GeneNameSynonym> synonyms =new ArrayList<>();
		synonyms.add(
		factory.createGeneNameSynonym("SER1",
				createEvidence(Arrays.asList(new String[] {}))));
		synonyms.add(
				factory.createGeneNameSynonym("SER5",
						createEvidence(Arrays.asList(new String[] {}))));
		synonyms.add(
				factory.createGeneNameSynonym("Ser99Da",
						createEvidence(Arrays.asList(new String[] {}))));
				
		List<OrderedLocusName> olnNames = new ArrayList<>();
//		olnNames.add(
//		factory.createOrderedLocusName("At1g22300",
//				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("At1g22300", 
				createEvidence(Arrays.asList(new String[] {}))));
		orfNames.add(
				factory.createORFName("CG7877", 
						createEvidence(Arrays.asList(new String[] {}))));
		orfNames.add(
				factory.createORFName("M117.2", 
						createEvidence(Arrays.asList(new String[] {}))));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		
		GeneName geneName1 =factory.createGeneName("Jon99Ciii",
				createEvidence(Arrays.asList(new String[] {})));
		List<GeneNameSynonym> synonyms1 =new ArrayList<>();
		synonyms1.add(
		factory.createGeneNameSynonym("SER2",
				createEvidence(Arrays.asList(new String[] {}))));
		synonyms1.add(
				factory.createGeneNameSynonym("SER5",
						createEvidence(Arrays.asList(new String[] {}))));
		synonyms1.add(
				factory.createGeneNameSynonym("Ser99Db",
						createEvidence(Arrays.asList(new String[] {}))));
				
		List<OrderedLocusName> olnNames1 = new ArrayList<>();
//		olnNames.add(
//		factory.createOrderedLocusName("At1g22300",
//				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames1 = new ArrayList<>();
		orfNames1.add(
		factory.createORFName("CG15519", 
				createEvidence(Arrays.asList(new String[] {}))));
	
		genes.add(
		factory.createGene(geneName1, synonyms1, olnNames1, orfNames1));
		
		doTest(gnLine, genes);
	}
			

	
	@Test
	public void test2GenesWithEvidence() {
		String gnLine = "GN   Name=Jon99Cii {ECO:0000313|EMBL:BAG16761.1};\n" +
						"GN   Synonyms=SER1 {ECO:0000313|EMBL:BAG16761.1}, SER5 {ECO:0000303|Ref.6},\n" +
						"GN   Ser99Da {ECO:0000269|PubMed:10433554};\n" +
						"GN   ORFNames=At1g22300 {ECO:0000313|EMBL:BAG16761.1}, CG7877\n" +
						"GN   {ECO:0000313|EMBL:BAG16761.1}, M117.2 {ECO:0000313|PDB:3OW2};\n" +
						"GN   and\n" +
						"GN   Name=Jon99Ciii;\n"+
						"GN   Synonyms=SER2, SER5 {ECO:0000256|HAMAP-Rule:MF_00205}, Ser99Db;\n" +
						"GN   ORFNames=CG15519 {ECO:0000303|Ref.6};";
		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("Jon99Cii",
				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"})));
		List<GeneNameSynonym> synonyms =new ArrayList<>();
		synonyms.add(
		factory.createGeneNameSynonym("SER1",
				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		synonyms.add(
				factory.createGeneNameSynonym("SER5",
						createEvidence(Arrays.asList(new String[] {"ECO:0000303|Ref.6"}))));
		synonyms.add(
				factory.createGeneNameSynonym("Ser99Da",
						createEvidence(Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554"}))));
				
		List<OrderedLocusName> olnNames = new ArrayList<>();
//		olnNames.add(
//		factory.createOrderedLocusName("At1g22300",
//				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("At1g22300", 
				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		orfNames.add(
				factory.createORFName("CG7877", 
						createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		orfNames.add(
				factory.createORFName("M117.2", 
						createEvidence(Arrays.asList(new String[] {"ECO:0000313|PDB:3OW2"}))));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		
		GeneName geneName1 =factory.createGeneName("Jon99Ciii",
				createEvidence(Arrays.asList(new String[] {})));
		List<GeneNameSynonym> synonyms1 =new ArrayList<>();
		synonyms1.add(
		factory.createGeneNameSynonym("SER2",
				createEvidence(Arrays.asList(new String[] {}))));
		synonyms1.add(
				factory.createGeneNameSynonym("SER5",
						createEvidence(Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205"}))));
		synonyms1.add(
				factory.createGeneNameSynonym("Ser99Db",
						createEvidence(Arrays.asList(new String[] {}))));
				
		List<OrderedLocusName> olnNames1 = new ArrayList<>();
//		olnNames.add(
//		factory.createOrderedLocusName("At1g22300",
//				createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames1 = new ArrayList<>();
		orfNames1.add(
		factory.createORFName("CG15519", 
				createEvidence(Arrays.asList(new String[] {"ECO:0000303|Ref.6"}))));
	
		genes.add(
		factory.createGene(geneName1, synonyms1, olnNames1, orfNames1));
		
		doTest(gnLine, genes);
	}
	
	@Test
	public void test3() {
		String gnLine = "GN   Name=GF14A; OrderedLocusNames=Os08g0480800, LOC_Os08g37490;\n" +
                           "GN   ORFNames=OJ1113_A10.40, OSJNBb0092C08.10;";
		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("GF14A", Collections.emptyList());
		List<GeneNameSynonym> synonyms =new ArrayList<>();
//		synonyms.add(
//		factory.createGeneNameSynonym("ftt-1", Collections.emptyList()));
		List<OrderedLocusName> olnNames = new ArrayList<>();
		olnNames.add(
		factory.createOrderedLocusName("Os08g0480800", Collections.emptyList()));
		olnNames.add(
				factory.createOrderedLocusName("LOC_Os08g37490", Collections.emptyList()));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("OJ1113_A10.40", Collections.emptyList()));
		orfNames.add(
				factory.createORFName("OSJNBb0092C08.10", Collections.emptyList()));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		doTest(gnLine, genes);
	}
	
	@Test
	public void test3Evidence() {
		String gnLine = "GN   Name=GF14A {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1,\n"+
						"GN   ECO:0000313|PDB:3OW2};\n" +
						"GN   OrderedLocusNames=Os08g0480800 {ECO:0000269|PubMed:10433554,\n" +
						"GN   ECO:0000303|Ref.6, ECO:0000313|PDB:3OW2}, LOC_Os08g37490\n" +
						"GN   {ECO:0000313|EMBL:BAG16761.1};\n" +
                        "GN   ORFNames=OJ1113_A10.40 {ECO:0000256|HAMAP-Rule:MF_00205,\n"+
						"GN   ECO:0000313|PDB:3OW2}, OSJNBb0092C08.10;";
		List<Gene> genes =new ArrayList<>();
		GeneName geneName =factory.createGeneName("GF14A",
				createEvidence(Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554", "ECO:0000313|EMBL:BAG16761.1", "ECO:0000313|PDB:3OW2"})));
		List<GeneNameSynonym> synonyms =new ArrayList<>();
//		synonyms.add(
//		factory.createGeneNameSynonym("ftt-1", Collections.emptyList()));
		List<OrderedLocusName> olnNames = new ArrayList<>();
		olnNames.add(
		factory.createOrderedLocusName("Os08g0480800",
				createEvidence(Arrays.asList(new String[] {"ECO:0000269|PubMed:10433554", "ECO:0000303|Ref.6", "ECO:0000313|PDB:3OW2"}))));
		olnNames.add(
				factory.createOrderedLocusName("LOC_Os08g37490", 
						createEvidence(Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1"}))));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("OJ1113_A10.40", 
				createEvidence(Arrays.asList(new String[] {"ECO:0000256|HAMAP-Rule:MF_00205", "ECO:0000313|PDB:3OW2"}))));
		orfNames.add(
				factory.createORFName("OSJNBb0092C08.10", Collections.emptyList()));
		genes.add(
		factory.createGene(geneName, synonyms, olnNames, orfNames));
	
		doTest(gnLine, genes);
	}
	
	@Test
	public void testNoGeneName() {
		String gnLine = "GN   OrderedLocusNames=Os08g0480800, LOC_Os08g37490;\n" +
                           "GN   ORFNames=OJ1113_A10.40, OSJNBb0092C08.10;";
		List<Gene> genes =new ArrayList<>();

		List<OrderedLocusName> olnNames = new ArrayList<>();
		olnNames.add(
		factory.createOrderedLocusName("Os08g0480800", Collections.emptyList()));
		olnNames.add(
				factory.createOrderedLocusName("LOC_Os08g37490", Collections.emptyList()));
		List<ORFName> orfNames = new ArrayList<>();
		orfNames.add(
		factory.createORFName("OJ1113_A10.40", Collections.emptyList()));
		orfNames.add(
				factory.createORFName("OSJNBb0092C08.10", Collections.emptyList()));
		genes.add(
		factory.createGene(null, null, olnNames, orfNames));
	
		doTest(gnLine, genes);
	}

}
