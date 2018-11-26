package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.oh.OHLineBuilder;



public class OHLineBuildTest {
	OHLineBuilder builder = new OHLineBuilder();
	private final TaxonomyFactory factory =TaxonomyFactory.INSTANCE;
	@Test
	public void testOGHydrogenosome(){
		String ohLine ="OH   NCBI_TaxID=9606; Homo sapiens (Human).\n" +
                       "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n" +
                        "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
		List<Organism> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Homo sapiens (Human)"), 9606l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"), 77231l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"), 77243l)
				);
		verify(ohLine, hosts);
	}
	 private void verify(String ogLine, List<Organism> hosts ){
		  
         FFLine ffLine = builder.build(hosts);
         String resultString = ffLine.toString();
         
 		System.out.println(resultString);
 		System.out.println();
 		System.out.println(ogLine);
 		assertEquals(ogLine, resultString);
   }
	
	@Test
	public void testOGHydrogenosome2(){
		String ohLine ="OH   NCBI_TaxID=9606; Homo sapiens (Human).\n" +
                       "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n" +
                       "OH   NCBI_TaxID=9685; Felis catus (Cat) (Felis silvestris catus).\n" +
                        "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
		List<Organism> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganism( 
						factory.createFromOrganismLine("Homo sapiens (Human)"), 9606l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"), 77231l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"), 9685l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"), 77243l)
				);
		verify(ohLine, hosts);
		
	}
	@Test
	public void testLongOH(){
		String ohLine ="OH   NCBI_TaxID=9606; Homo sapiens (Human).\n" +
                "OH   NCBI_TaxID=9685; Felis catus (Cat) (Felis silvestris catus).\n" +
                "OH   NCBI_TaxID=9785; Loxodonta africana (African elephant).\n" +
                "OH   NCBI_TaxID=9913; Bos taurus (Bovine).\n" +
                "OH   NCBI_TaxID=10090; Mus musculus (Mouse).\n" +
                "OH   NCBI_TaxID=29092; Microtus agrestis (Short-tailed field vole).\n" +
                "OH   NCBI_TaxID=447135; Myodes glareolus (Bank vole) (Clethrionomys glareolus).";
		List<Organism> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Homo sapiens (Human)"), 9606l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"), 9685l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Loxodonta africana (African elephant)"), 9785l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Bos taurus (Bovine)"), 9913l)
				);
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Mus musculus (Mouse)"), 10090l)
				);
		
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Microtus agrestis (Short-tailed field vole)"), 29092)
				);
		
		hosts.add(
				factory.createOrganism(
						factory.createFromOrganismLine("Myodes glareolus (Bank vole) (Clethrionomys glareolus)"), 447135)
				);
		verify(ohLine, hosts);
	}
}
