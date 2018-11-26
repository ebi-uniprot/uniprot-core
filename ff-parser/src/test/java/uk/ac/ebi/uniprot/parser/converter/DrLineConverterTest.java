package uk.ac.ebi.uniprot.parser.converter;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.parser.DatabaseTypeNotExistException;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineConverter;
import uk.ac.ebi.uniprot.parser.impl.dr.DrLineObject;
import uk.ac.ebi.uniprot.parser.impl.dr.UniProtDrObjects;

public class DrLineConverterTest {
	final private DrLineConverter converter = new DrLineConverter();
	
	@Test
	public void testEmbl(){
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL", "AY548484", "AAT09660.1", "someValue", "Genomic_DNA"  ));
		UniProtDrObjects drObjects= converter.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(1, xrefs.size());
		validate( xrefs.get(0), "EMBL",
				"AY548484", "AAT09660.1", "someValue", "Genomic_DNA");
	}
	
	@Test
	public void test(){
		/**
		 *  val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                 |DR   RefSeq; YP_031579.1; NC_005946.1.
                 |DR   ProteinModelPortal; Q6GZX4; -.
                 |DR   GeneID; 2947773; -.
                 |DR   ProtClustDB; CLSP2511514; -.
                 |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                 |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                 |DR   InterPro; IPR007031; Poxvirus_VLTF3.
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null ));	
		obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null ));
		obj.drObjects.add(creatDrObject("ProtClustDB", "CLSP2511514", "-", null, null ));	
		obj.drObjects.add(creatDrObject("GO", "GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null ));
		obj.drObjects.add(creatDrObject("GO", "GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null ));
		obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null ));
		UniProtDrObjects drObjects= converter.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(7, xrefs.size());
		validate( xrefs.get(0), "EMBL",
				"AY548484", "AAT09660.1", "-", "Genomic_DNA");
		validate( xrefs.get(1), "REFSEQ",
				"YP_031579.1", "NC_005946.1", null, null);
		validate( xrefs.get(2), "GENEID",
				"2947773", "-", null, null);
		validate( xrefs.get(3), "PROTCLUSTDB",
				"CLSP2511514", "-", null, null);
		validate( xrefs.get(4), "GO",
				"GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null);
		validate( xrefs.get(5), "GO",
				"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
		validate( xrefs.get(6), "INTERPRO",
				"IPR007031", "Poxvirus_VLTF3", null, null );
	}
	
	@Test (expected = DatabaseTypeNotExistException.class) 
	public void testWrongDr()throws Exception{
		/**
		 *  val drLine = """DR   EMBL2; AY548484; AAT09660.1; -; Genomic_DNA.
             
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		
		UniProtDrObjects drObjects= converter.convert(obj);
		assertNull(drObjects);
	}
	
	@Test 
	public void testIgnoreWrongDr()throws Exception{
		/**
		 *  val drLine = """DR   EMBL2; AY548484; AAT09660.1; -; Genomic_DNA.
             
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		DrLineConverter converter2 = new DrLineConverter(true);
		UniProtDrObjects drObjects= converter2.convert(obj);
		assertNotNull(drObjects);
	}
	
	@Test
	public void testIgnoreWrongDR(){
		/**
		 *  val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                 |DR   RefSeq; YP_031579.1; NC_005946.1.
                 |DR   ProteinModelPortal; Q6GZX4; -.
                 |DR   GeneID; 2947773; -.
                 |DR   ProtClustDB; CLSP2511514; -.
                 |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                 |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                 |DR   InterPro; IPR007031; Poxvirus_VLTF3.
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null ));	
		obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null ));
		obj.drObjects.add(creatDrObject("ProtClustDB", "CLSP2511514", "-", null, null ));	
		obj.drObjects.add(creatDrObject("GO", "GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null ));
		obj.drObjects.add(creatDrObject("GO", "GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null ));
		obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null ));
		DrLineConverter converter2 = new DrLineConverter(true);
		UniProtDrObjects drObjects= converter2.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(6, xrefs.size());
	//	validate( xrefs.get(0), "EMBL,
	//			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

		validate( xrefs.get(0), "REFSEQ",
				"YP_031579.1", "NC_005946.1", null, null);
		validate( xrefs.get(1), "GENEID",
				"2947773", "-", null, null);
		validate( xrefs.get(2), "PROTCLUSTDB",
				"CLSP2511514", "-", null, null);
		validate( xrefs.get(3), "GO",
				"GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null);
		validate( xrefs.get(4), "GO",
				"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
		validate( xrefs.get(5), "INTERPRO",
				"IPR007031", "Poxvirus_VLTF3", null, null );
	}
	
	@Test
	public void testIgnoreWrongDR3(){
		/**
		 *  val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                 |DR   RefSeq; YP_031579.1; NC_005946.1.
                 |DR   ProteinModelPortal; Q6GZX4; -.
                 |DR   GeneID; 2947773; -.
                 |DR   ProtClustDB; CLSP2511514; -.
                 |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                 |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                 |DR   InterPro; IPR007031; Poxvirus_VLTF3.
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", null, null ));	
		obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null ));
		obj.drObjects.add(creatDrObject("ProtClustDB", "CLSP2511514", "-", null, null ));	
		obj.drObjects.add(creatDrObject("GO", "GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null ));
		obj.drObjects.add(creatDrObject("GO", "GO:0046782", "P:regulation of viral transcription", null, null ));
		obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null ));
		DrLineConverter converter2 = new DrLineConverter(true);
		UniProtDrObjects drObjects= converter2.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(5, xrefs.size());
	//	validate( xrefs.get(0), "EMBL,
	//			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

		validate( xrefs.get(0), "REFSEQ",
				"YP_031579.1", "NC_005946.1", null, null);
		validate( xrefs.get(1), "GENEID",
				"2947773", "-", null, null);
		validate( xrefs.get(2), "PROTCLUSTDB",
				"CLSP2511514", "-", null, null);
		validate( xrefs.get(3), "GO",
				"GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null);
	//	validate( xrefs.get(4), "GO,
	//			"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
		validate( xrefs.get(4), "INTERPRO",
				"IPR007031", "Poxvirus_VLTF3", null, null );
	}
	
	
	@Test
	public void testIgnoreWrongDR2(){
		/**
		 *  val drLine = """DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.
                 |DR   RefSeq; YP_031579.1; NC_005946.1.
                 |DR   ProteinModelPortal; Q6GZX4; -.
                 |DR   GeneID; 2947773; -.
                 |DR   ProtClustDB; CLSP2511514; -.
                 |DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.
                 |DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.
                 |DR   InterPro; IPR007031; Poxvirus_VLTF3.
		 */
		DrLineObject obj = new DrLineObject();
		obj.drObjects.add(creatDrObject("EMBL2", "AY548484", "AAT09660.1", "-", "Genomic_DNA"  ));
		obj.drObjects.add(creatDrObject("RefSeq", "YP_031579.1", "NC_005946.1", "notdata", null ));	
		obj.drObjects.add(creatDrObject("GeneID", "2947773", "-", null, null ));
		obj.drObjects.add(creatDrObject("ProtClustDB", "CLSP2511514", "-", null, null ));	
		obj.drObjects.add(creatDrObject("GO", "GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null ));
		obj.drObjects.add(creatDrObject("GO", "GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null ));
		obj.drObjects.add(creatDrObject("InterPro", "IPR007031", "Poxvirus_VLTF3", null, null ));
		DrLineConverter converter2 = new DrLineConverter(true);
		UniProtDrObjects drObjects= converter2.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(6, xrefs.size());
	//	validate( xrefs.get(0), "EMBL,
	//			"AY548484", "AAT09660.1", "-", "Genomic_DNA");

		validate( xrefs.get(0), "REFSEQ",
				"YP_031579.1", "NC_005946.1", null, null);
		validate( xrefs.get(1), "GENEID",
				"2947773", "-", null, null);
		validate( xrefs.get(2), "PROTCLUSTDB",
				"CLSP2511514", "-", null, null);
		validate( xrefs.get(3), "GO",
				"GO:0006355", "P:regulation of transcription, DNA-dependent", "IEA:UniProtKB-KW", null);
		validate( xrefs.get(4), "GO",
				"GO:0046782", "P:regulation of viral transcription", "IEA:InterPro", null );
		validate( xrefs.get(5), "INTERPRO",
				"IPR007031", "Poxvirus_VLTF3", null, null );
	}
	
	
	
	@Test
	public void testEvidence(){
		//"DR   EMBL; CP001509; ACT41999.1; -; Genomic_DNA.{EI3}
        //DR   EMBL; AM946981; CAQ30614.1; -; Genomic_DNA.{EI4}
		//DR   GeneID; 2947773; -.
		
		DrLineObject obj = new DrLineObject();
		DrLineObject.DrObject obj1 =creatDrObject("EMBL", "CP001509", "ACT41999.1", "-", "Genomic_DNA"  );
		DrLineObject.DrObject obj2 =creatDrObject("EMBL", "AM946981", "CAQ30614.1", "-", "Genomic_DNA"  );
		DrLineObject.DrObject obj3 =creatDrObject("GeneID", "2947773", "-", null, null );
		obj.drObjects.add(obj1);
		obj.drObjects.add(obj2);
		obj.drObjects.add(obj3);
		List<String> evIds = new ArrayList<String>();
		evIds.add("ECO:0000269|PubMed:26711273");
		obj.evidenceInfo.evidences.put(obj1, evIds);
		evIds = new ArrayList<String>();
		evIds.add("ECO:0000269|PubMed:26711274");
		obj.evidenceInfo.evidences.put(obj2, evIds);
		UniProtDrObjects drObjects= converter.convert(obj);
		List<UniProtDBCrossReference> xrefs = drObjects.drObjects;
		assertEquals(3, xrefs.size());
		UniProtDBCrossReference xref1 =xrefs.get(0);
		UniProtDBCrossReference xref2=xrefs.get(1);
		UniProtDBCrossReference xref3=xrefs.get(2);
		validate(xref1 , "EMBL",
				"CP001509", "ACT41999.1", "-", "Genomic_DNA");
		validate(xref2 , "EMBL",
				"AM946981", "CAQ30614.1", "-", "Genomic_DNA");
		validate( xref3, "GENEID",
				"2947773", "-", null, null);
		List<Evidence> eviIds1 = xref1.getEvidences();
		List<Evidence> eviIds2 = xref2.getEvidences();
		List<Evidence> eviIds3 = xref3.getEvidences();
		assertEquals(1, eviIds1.size());
		assertEquals(1, eviIds2.size());
		assertEquals(0, eviIds3.size());
		assertEquals("ECO:0000269|PubMed:26711273", eviIds1.get(0).getValue());
		assertEquals("ECO:0000269|PubMed:26711274", eviIds2.get(0).getValue());
		
	}
	private void validate(UniProtDBCrossReference xref, String type,
			String first, String second,
			String third, String fourth){
		assertEquals(first, xref.getId());
		assertEquals(second, xref.getProperties().get(0).getValue());
		if(third !=null){
			assertTrue(xref.getProperties().size()>=2);
			assertEquals(third, xref.getProperties().get(1).getValue());
		}else {
			assertFalse(xref.getProperties().size()>=2);
		}
		if(fourth !=null){
			assertTrue(xref.getProperties().size()>=3);
			assertEquals(fourth, xref.getProperties().get(2).getValue());
		}else {
			assertFalse(xref.getProperties().size()>=3);
		}
	}
	private DrLineObject.DrObject creatDrObject(String dbname, String first, String second,
			String third, String fourth){
		DrLineObject.DrObject dr1 =new DrLineObject.DrObject();
		dr1.DbName =dbname;
		dr1.attributes.add(first);
		dr1.attributes.add(second);
		if(third !=null)
		dr1.attributes.add(third);
		if(fourth !=null)
		dr1.attributes.add(fourth);
		return dr1;
	}
}
