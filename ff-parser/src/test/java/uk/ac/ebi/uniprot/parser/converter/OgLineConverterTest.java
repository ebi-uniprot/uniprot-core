package uk.ac.ebi.uniprot.parser.converter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineConverter;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject;
import uk.ac.ebi.uniprot.parser.impl.og.OgLineObject.OgEnum;

public class OgLineConverterTest {
	private final OgLineConverter converter = new OgLineConverter();
	@Test
	public void test1() {
		//OG   Plasmid R6-5, Plasmid IncFII R100 (NR1), and
        //OG   Plasmid IncFII R1-19 (R1 drd-19).
		OgLineObject ogObj = new OgLineObject();
		ogObj.plasmidNames.add("R6-5");
		ogObj.plasmidNames.add("IncFII R100 (NR1)");
		ogObj.plasmidNames.add("IncFII R1-19 (R1 drd-19)");
		 List<Organelle> orgs = converter.convert(ogObj);
		 assertEquals(3, orgs.size());
		 testExist(GeneEncodingType.PLASMID, "R6-5", orgs);
		 testExist(GeneEncodingType.PLASMID, "IncFII R100 (NR1)", orgs);
		 testExist(GeneEncodingType.PLASMID, "IncFII R1-19 (R1 drd-19)", orgs);
	}
	@Test
	public void test2() {
	/*OG   Hydrogenosome.
        |OG   Mitochondrion.
        |OG   Nucleomorph.
        |OG   Plasmid R6-5.
        |OG   Plastid.
        |OG   Plastid; Apicoplast.
        |OG   Plastid; Chloroplast.
        |OG   Plastid; Organellar chromatophore.
        |OG   Plastid; Cyanelle.
        |OG   Plastid; Non-photosynthetic plastid.
        */
		OgLineObject ogObj = new OgLineObject();
		ogObj.ogs.add(OgEnum.HYDROGENOSOME);
		ogObj.ogs.add(OgEnum.MITOCHONDRION);
		ogObj.ogs.add(OgEnum.NUCLEOMORPH);
		ogObj.ogs.add(OgEnum.PLASTID);
		ogObj.ogs.add(OgEnum.PLASTID_APICOPLAST);
		ogObj.ogs.add(OgEnum.PLASTID_CHLOROPLAST);
		ogObj.ogs.add(OgEnum.PLASTID_ORGANELLAR_CHROMATOPHORE);
		ogObj.ogs.add(OgEnum.PLASTID_CYANELLE);
		ogObj.ogs.add(OgEnum.PLASTID_NON_PHOTOSYNTHETIC);
		ogObj.plasmidNames.add("R6-5");
		List<Organelle> orgs = converter.convert(ogObj);
		 assertEquals(10, orgs.size());
		 testExist(GeneEncodingType.PLASMID, "R6-5", orgs);
		 testExist(GeneEncodingType.HYDROGENOSOME, "", orgs);
		 testExist(GeneEncodingType.MITOCHONDRION, "", orgs);
		 testExist(GeneEncodingType.APICOPLAST_PLASTID, "", orgs);
		 testExist(GeneEncodingType.CHLOROPLAST_PLASTID, "", orgs);
		 testExist(GeneEncodingType.CHROMATOPHORE_PLASTID, "", orgs);
		 testExist(GeneEncodingType.CYANELLE_PLASTID, "", orgs);
		 testExist(GeneEncodingType.NON_PHOTOSYNTHETIC_PLASTID, "", orgs);
		 testExist(GeneEncodingType.NUCLEOMORPH, "", orgs);
		 
		
	}
	
	@Test
	public void testEvidence(){
		OgLineObject ogObj = new OgLineObject();
		ogObj.ogs.add(OgEnum.HYDROGENOSOME); //{EI2}
		ogObj.plasmidNames.add("R6-5");
	//	{EI1, EI2}.
	
		ogObj.plasmidNames.add("IncFII R100 (NR1)");  //{EI1}
		
		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000256|RuleBase:RU000682");
		ogObj.getEvidenceInfo().evidences.put(OgEnum.HYDROGENOSOME, evs);
		evs = new ArrayList<>();
		evs.add("ECO:0000256|RuleBase:RU000681");
		evs.add("ECO:0000256|RuleBase:RU000682");
		ogObj.getEvidenceInfo().evidences.put("R6-5", evs);
		evs = new ArrayList<>();
		evs.add("ECO:0000256|RuleBase:RU000681");		
		ogObj.getEvidenceInfo().evidences.put("IncFII R100 (NR1)", evs);
		List<Organelle> orgs = converter.convert(ogObj);
		assertEquals(3, orgs.size());
		Organelle org1 = orgs.get(0);
		Organelle org2 = orgs.get(1);
		Organelle org3 = orgs.get(2);
		for(Organelle org:orgs){
			if(org.getType() == GeneEncodingType.HYDROGENOSOME){
				org1= org;
			}else{
				if(org.getValue().equals("R6-5"))
					org2= org;
				else
					org3 =org;
			}
		}
		assertEquals(GeneEncodingType.HYDROGENOSOME, org1.getType());
		assertEquals(GeneEncodingType.PLASMID, org2.getType());
		
		assertEquals(GeneEncodingType.PLASMID, org3.getType());
		assertEquals("R6-5", org2.getValue());
		assertEquals("IncFII R100 (NR1)", org3.getValue());
		
		List<Evidence> evIDs =org1.getEvidences();
		assertEquals(1, evIDs.size());
		Evidence evId = evIDs.get(0);
		assertEquals("ECO:0000256|RuleBase:RU000682", evId.getValue());
		
		evIDs =org2.getEvidences();
		assertEquals(2, evIDs.size());
		evId = evIDs.get(0);
		Evidence evId2 = evIDs.get(1);
		assertEquals("ECO:0000256|RuleBase:RU000681", evId.getValue());
		assertEquals("ECO:0000256|RuleBase:RU000682", evId2.getValue());
		
		evIDs =org3.getEvidences();
		assertEquals(1, evIDs.size());
		evId = evIDs.get(0);
	
		assertEquals("ECO:0000256|RuleBase:RU000681", evId.getValue());
	
	}
	private void testExist(GeneEncodingType type, String value,List<Organelle> orgs ){
		boolean found =false;
		boolean plasmidValueFound =false;
		for(Organelle org:orgs){
			if(org.getType() ==type){
				found =true;
				if(type == GeneEncodingType.PLASMID){
					if(value.equals(org.getValue())){
						plasmidValueFound =true;
						break;
					}
				}else{
					plasmidValueFound=true;
					break;
				}
			
			}
		}
		assertTrue(found);
		assertTrue(plasmidValueFound);
	}
}
