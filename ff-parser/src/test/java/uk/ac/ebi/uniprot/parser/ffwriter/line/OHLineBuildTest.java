package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.factory.OrganismFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.oh.OHLineBuilder;



public class OHLineBuildTest {
	OHLineBuilder builder = new OHLineBuilder();
	private final OrganismFactory factory =OrganismFactory.INSTANCE;
	@Test
	public void testOGHydrogenosome(){
		String ohLine ="OH   NCBI_TaxID=9606; Homo sapiens (Human).\n" +
                       "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n" +
                        "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
		List<OrganismHost> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganismHost(9606l, 
						factory.createFromOrganismLine("Homo sapiens (Human)"))
				);
		hosts.add(
				factory.createOrganismHost(77231l, 
						factory.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"))
				);
		hosts.add(
				factory.createOrganismHost(77243l, 
						factory.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"))
				);
		verify(ohLine, hosts);
	}
	 private void verify(String ogLine, List<OrganismHost> hosts ){
		  
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
		List<OrganismHost> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganismHost(9606l, 
						factory.createFromOrganismLine("Homo sapiens (Human)"))
				);
		hosts.add(
				factory.createOrganismHost(77231l, 
						factory.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"))
				);
		hosts.add(
				factory.createOrganismHost(9685l, 
						factory.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"))
				);
		hosts.add(
				factory.createOrganismHost(77243l, 
						factory.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"))
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
		List<OrganismHost> hosts = new ArrayList<>();

		hosts.add(
				factory.createOrganismHost(9606l, 
						factory.createFromOrganismLine("Homo sapiens (Human)"))
				);
		hosts.add(
				factory.createOrganismHost(9685l, 
						factory.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"))
				);
		hosts.add(
				factory.createOrganismHost(9785l, 
						factory.createFromOrganismLine("Loxodonta africana (African elephant)"))
				);
		hosts.add(
				factory.createOrganismHost(9913l, 
						factory.createFromOrganismLine("Bos taurus (Bovine)"))
				);
		hosts.add(
				factory.createOrganismHost(10090l, 
						factory.createFromOrganismLine("Mus musculus (Mouse)"))
				);
		
		hosts.add(
				factory.createOrganismHost(29092, 
						factory.createFromOrganismLine("Microtus agrestis (Short-tailed field vole)"))
				);
		
		hosts.add(
				factory.createOrganismHost(447135, 
						factory.createFromOrganismLine("Myodes glareolus (Bank vole) (Clethrionomys glareolus)"))
				);
		verify(ohLine, hosts);
	}
}
