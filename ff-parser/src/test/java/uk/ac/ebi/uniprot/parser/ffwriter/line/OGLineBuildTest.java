package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.impl.og.OGLineBuilder;



public class OGLineBuildTest {
	private final UniProtFactory factory = UniProtFactory.INSTANCE;
	OGLineBuilder builder = new OGLineBuilder();
	@Test
    public void testOGHydrogenosome() {
        String ogLine ="OG   Hydrogenosome.";       
        GeneEncodingType type = GeneEncodingType.HYDROGENOSOME;
        
        
        List<Organelle> organelles = new ArrayList<>();
        organelles.add(factory.createOrganelle(type, "", Collections.emptyList()));
    
        doTestOrganelle(ogLine, organelles);
    }
	 private void doTestOrganelle(String ogLine, List<Organelle> organelles ){
		  
         FFLine ffLine = builder.buildWithEvidence(organelles);
         String resultString = ffLine.toString();
         
 		System.out.println(resultString);
 		System.out.println();
 		System.out.println(ogLine);
 		assertEquals(ogLine, resultString);
   }

	@Test
    public void testOGMitochondrion() {      
        String ogLine = "OG   Mitochondrion.";
        GeneEncodingType type = GeneEncodingType.MITOCHONDRION;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }
	@Test
    public void testOGNucleomorph() {    
        String ogLine = "OG   Nucleomorph.";
        GeneEncodingType type = GeneEncodingType.NUCLEOMORPH;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }
	
	@Test
    public void testOGPlastid() {
        String ogLine = "OG   Plastid.";
        GeneEncodingType type = GeneEncodingType.PLASTID;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }

    @Test
    public void testOGPlastidApicoplast() {
        String ogLine = "OG   Plastid; Apicoplast.";
        GeneEncodingType type = GeneEncodingType.APICOPLAST_PLASTID;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }

    @Test
    public void testOGPlastidChloroplast() {
    	 String ogLine = "OG   Plastid; Chloroplast.";
         GeneEncodingType type = GeneEncodingType.CHLOROPLAST_PLASTID;
         List<Organelle> organelles = new ArrayList<>();
         List<String> evs =new ArrayList<>();
         Organelle org = createOrganelle( type, evs);
         organelles.add(org);
         
         doTestOrganelle(ogLine, organelles);
    }
    @Test
    public void testOGPlastidCyanelle() {
        String ogLine = "OG   Plastid; Cyanelle.";
        GeneEncodingType type = GeneEncodingType.CYANELLE_PLASTID;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }
    @Test
    public void testOGPlastidNonPhotosyntheticPlastid() {
        String ogLine = "OG   Plastid; Non-photosynthetic plastid.";
        GeneEncodingType type = GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID;
        List<Organelle> organelles = new ArrayList<>();
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        
        doTestOrganelle(ogLine, organelles);
    }
   
    @Test
    public void testOGPlasmidname() {
    	 String ogLine ="OG   Plasmid name.";
    	 List<Organelle> organelles = new ArrayList<>();
    	 List<String> evs = new ArrayList<>();
         Organelle og = createPlasmid("name", evs);
         organelles.add(og);
         doTestOrganelle(ogLine, organelles);
    }
  
    @Test
    public void testOrganPlasmids() {
        String ogLine =
        		"OG   Plastid; Chloroplast.\n" +
        		"OG   Plasmid IncFII R100 (NR1), Plasmid IncW R388, and Plasmid pLMO20.";
        List<Organelle> organelles = new ArrayList<>();
        GeneEncodingType type = GeneEncodingType.CHLOROPLAST_PLASTID;
        
        List<String> evs =new ArrayList<>();
        Organelle org = createOrganelle( type, evs);
        organelles.add(org);
        List<String> evs1 =new ArrayList<>();
        organelles.add(createPlasmid("IncFII R100 (NR1)", evs1));
        List<String> evs2 =new ArrayList<>();
        organelles.add(createPlasmid("IncW R388", evs2));
        List<String> evs3 =new ArrayList<>();
        organelles.add(createPlasmid("pLMO20", evs3));
       
        doTestOrganelle(ogLine, organelles);
     
    }
    
  
    @Test
    public void testOGPlasmids() {
    	 String ogLine = "OG   Plasmid R1 (R7268), Plasmid IncF::IncFIA::IncFIB::IncI1-ly,\n" +
       					"OG   Plasmid p013.1IncR, Plasmid pUD16, and Plasmid IncF::IncL/M.";
    	  List<Organelle> organelles = new ArrayList<>();
    	  List<String> evs1 =new ArrayList<>();
          organelles.add(createPlasmid("R1 (R7268)", evs1));
          List<String> evs2 =new ArrayList<>();
          organelles.add(createPlasmid("IncF::IncFIA::IncFIB::IncI1-ly", evs2));
          List<String> evs3 =new ArrayList<>();
          organelles.add(createPlasmid("p013.1IncR", evs3));
          List<String> evs4 =new ArrayList<>();
          organelles.add(createPlasmid("pUD16", evs4));
          List<String> evs5 =new ArrayList<>();
          organelles.add(createPlasmid("IncF::IncL/M", evs5));
          
          doTestOrganelle(ogLine, organelles);
    }

  
    @Test
    public void testOGPlastidChloroplastEvidence() {
    	 String ogLine = "OG   Plastid; Chloroplast {ECO:0000256|HAMAP-Rule:MF_00205,\n"+
    	 				 "OG   ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6,\n"+
    	 				 "OG   ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}.";
         GeneEncodingType type = GeneEncodingType.CHLOROPLAST_PLASTID;
 		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
 		String ev2= "ECO:0000269|PubMed:10433554";
 		String ev3 ="ECO:0000303|Ref.6";
 		String ev4 ="ECO:0000313|PDB:3OW2";
 		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
         List<Organelle> organelles = new ArrayList<>();
         List<String> evs =new ArrayList<>();
         evs.add(ev1);        
         evs.add(ev2);
         evs.add(ev3);
         evs.add(ev4);
         evs.add(ev5);
         Organelle org = createOrganelle( type, evs);
         organelles.add(org);
         
         doTestOrganelle(ogLine, organelles);
    }
    
    @Test
    public void testOrganPlasmidsWithEvidence() {
        String ogLine =
        		"OG   Plastid; Chloroplast {ECO:0000256|HAMAP-Rule:MF_00205,\n"+
   	 			"OG   ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6,\n"+
   	 			"OG   ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}.\n"+
        		"OG   Plasmid IncFII R100 (NR1) {ECO:0000269|PubMed:10433554,\n" +
   	 			"OG   ECO:0000313|EMBL:BAG16761.1}, Plasmid IncW R388 {ECO:0000303|Ref.6},\n" +
        		"OG   and Plasmid pLMO20 {ECO:0000256|HAMAP-Rule:MF_00205,\n"+
   	 			"OG   ECO:0000313|EMBL:BAG16761.1}.";
        
        String ev1 ="ECO:0000313|EMBL:BAG16761.1";
 		String ev2= "ECO:0000269|PubMed:10433554";
 		String ev3 ="ECO:0000303|Ref.6";
 		String ev4 ="ECO:0000313|PDB:3OW2";
 		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
        List<Organelle> organelles = new ArrayList<>();
        GeneEncodingType type = GeneEncodingType.CHLOROPLAST_PLASTID;
        
        List<String> evs =new ArrayList<>();
        evs.add(ev1);        
        evs.add(ev2);
        evs.add(ev3);
        evs.add(ev4);
        evs.add(ev5);
        Organelle org = createOrganelle( type, evs);
     
        organelles.add(org);
        List<String> evs1 =new ArrayList<>();
        evs1.add(ev1);        
        evs1.add(ev2);
        organelles.add(createPlasmid("IncFII R100 (NR1)", evs1));
        List<String> evs2 =new ArrayList<>();
        evs2.add(ev3);
        organelles.add(createPlasmid("IncW R388", evs2));
        List<String> evs3 =new ArrayList<>();
        evs3.add(ev1);        
        evs3.add(ev5);
        organelles.add(createPlasmid("pLMO20", evs3));
       
        doTestOrganelle(ogLine, organelles);
     
    }
    
    @Test
    public void testOGPlasmidsWithEvidence() {
    	 String ogLine = 
    			 "OG   Plasmid R1 (R7268) {ECO:0000313|EMBL:BAG16761.1},\n"+
    			 "OG   Plasmid IncF::IncFIA::IncFIB::IncI1-ly {ECO:0000269|PubMed:10433554,\n"+
    			 "OG   ECO:0000313|EMBL:BAG16761.1}, Plasmid p013.1IncR {ECO:0000303|Ref.6,\n" +
       			 "OG   ECO:0000313|PDB:3OW2}, Plasmid pUD16 {ECO:0000313|PDB:3OW2}, and\n" +
    			 "OG   Plasmid IncF::IncL/M {ECO:0000256|HAMAP-Rule:MF_00205}.";
    	  String ev1 ="ECO:0000313|EMBL:BAG16761.1";
   		String ev2= "ECO:0000269|PubMed:10433554";
   		String ev3 ="ECO:0000303|Ref.6";
   		String ev4 ="ECO:0000313|PDB:3OW2";
   		String ev5 ="ECO:0000256|HAMAP-Rule:MF_00205";
    	  List<Organelle> organelles = new ArrayList<>();
    	  List<String> evs1 =new ArrayList<>();
    	  evs1.add(ev1);
          organelles.add(createPlasmid("R1 (R7268)", evs1));
          List<String> evs2 =new ArrayList<>();
          evs2.add(ev1);
          evs2.add(ev2);
          organelles.add(createPlasmid("IncF::IncFIA::IncFIB::IncI1-ly", evs2));
          List<String> evs3 =new ArrayList<>();
          evs3.add(ev3);
          evs3.add(ev4);
          organelles.add(createPlasmid("p013.1IncR", evs3));
          List<String> evs4 =new ArrayList<>();
          evs4.add(ev4);
          organelles.add(createPlasmid("pUD16", evs4));
          List<String> evs5 =new ArrayList<>();
          evs5.add(ev5);
          organelles.add(createPlasmid("IncF::IncL/M", evs5));
          
          doTestOrganelle(ogLine, organelles);
    }
    
    private Organelle createPlasmid(String value, List<String> evs){
    	  return factory.createOrganelle( GeneEncodingType.PLASMID, value, createEvidence(evs));
    
    }
    private Organelle createOrganelle( GeneEncodingType type, List<String> evs){
    	 return factory.createOrganelle( type, "", createEvidence(evs));

    }
    private List<Evidence> createEvidence(List<String> evIds) {
		return evIds.stream().map(val -> UniProtFactory.INSTANCE.createEvidence(val)).collect(Collectors.toList());

	}
   
}
