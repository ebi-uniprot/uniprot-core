package org.uniprot.core.flatfile.writer.line;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.flatfile.parser.impl.oh.OHLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismHostBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class OHLineBuildTest {
	OHLineBuilder builder = new OHLineBuilder();
	@Test
	public void testOGHydrogenosome(){
		String ohLine ="OH   NCBI_TaxID=9606; Homo sapiens (Human).\n" +
                       "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n" +
                        "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
		List<OrganismHost> hosts = new ArrayList<>();

		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Homo sapiens (Human)"))
						.taxonId(9606L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"))
						.taxonId(77231L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"))
						.taxonId(77243L)
						.build()
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
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Homo sapiens (Human)"))
						.taxonId(9606L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Epomops franqueti (Franquet's epauleted bat)"))
						.taxonId(77231L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"))
						.taxonId(9685L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Myonycteris torquata (Little collared fruit bat)"))
						.taxonId(77243L)
						.build()
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
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Homo sapiens (Human)"))
						.taxonId(9606L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Felis catus (Cat) (Felis silvestris catus)"))
						.taxonId(9685L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Loxodonta africana (African elephant)"))
						.taxonId(9785L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Bos taurus (Bovine)"))
						.taxonId(9913L)
						.build()
				);
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Mus musculus (Mouse)"))
						.taxonId(10090L)
						.build()
				);
		
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Microtus agrestis (Short-tailed field vole)"))
						.taxonId(29092L)
						.build()
				);
		
		hosts.add(
				new OrganismHostBuilder()
						.from(OrganismNameLineParser.createFromOrganismLine("Myodes glareolus (Bank vole) (Clethrionomys glareolus)"))
						.taxonId(447135L)
						.build()
				);
		verify(ohLine, hosts);
	}
}
