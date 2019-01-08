package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CofactorType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;



class CofactorConverterTest {
	CofactorConverter converter ;
	 @BeforeEach
	    public void setUp() {

	        EvidenceIndexMapper evidenceReferenceHandler = new EvidenceIndexMapper();

	        Evidence evidence1 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060645");
	        Evidence evidence2 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060646");
	        Evidence evidence3 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060647");
	        Evidence evidence4 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060648");
	        Map<Evidence, Integer> evIdMap = new HashMap<>();
	        evIdMap.put(evidence1, 1);
	        evIdMap.put(evidence2, 2);
	        evIdMap.put(evidence3, 3);
	        evIdMap.put(evidence4, 4);
	        evidenceReferenceHandler.reset(evIdMap);

	        this.converter = new CofactorConverter(evidenceReferenceHandler);

	    }

	    @Test
	    public void testToXml() {
	        // CC Name=Zn(2+); Xref=ChEBI:CHEBI:29105;
	        // CC Evidence={ECO:0000269|PubMed:9060645, ECO:0000269|PubMed:9060647};
	        

			DBCrossReference<CofactorReferenceType>  reference =
					UniProtFactory.INSTANCE.createDBCrossReference (CofactorReferenceType.CHEBI,
							"CHEBI:29105");

;
	        Evidence evidence1 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060645");
	        Evidence evidence2 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060647");
	        List<Evidence> evids = new ArrayList<>();
	        evids.add(evidence1);
	        evids.add(evidence2);
	        
	        Cofactor cofactor =CofactorCommentBuilder.createCofactor("Zn(2+)", reference, evids);
	        CofactorType cofactorType = converter.toXml(cofactor);
	        assertEquals("Zn(2+)", cofactorType.getName());
	        assertEquals("CHEBI:29105", cofactorType.getDbReference().getId());
	        assertEquals("ChEBI", cofactorType.getDbReference().getType());
	        List<Integer> evs = cofactorType.getEvidence();
	        assertEquals(2, evs.size());
	        assertEquals(1, evs.get(0).intValue());
	        assertEquals(3, evs.get(1).intValue());
	        System.out.println(UniProtXmlTestHelper.toXmlString(cofactorType, CofactorType.class, "cofactor"));
			
	        Cofactor converted =converter.fromXml(cofactorType);
	        assertEquals(cofactor, converted);

	    }

	    @Test
	    public void testFromXml() {
	    	ObjectFactory objectFactory = new ObjectFactory();
	        CofactorType cofactorType = objectFactory.createCofactorType();
	        cofactorType.setName("Zn(2+)");
	        DbReferenceType dbref = objectFactory.createDbReferenceType();
	        dbref.setId("CHEBI:29105");
	        dbref.setType("ChEBI");
	        cofactorType.setDbReference(dbref);
	        cofactorType.getEvidence().add(2);
	        cofactorType.getEvidence().add(3);
	        Cofactor cofactor = converter.fromXml(cofactorType);
	        assertEquals("Zn(2+)", cofactor.getName());
	        assertEquals("CHEBI:29105", cofactor.getCofactorReference().getId());
	        assertEquals(CofactorReferenceType.CHEBI, cofactor.getCofactorReference().getDatabaseType());
	        List<Evidence> evids = cofactor.getEvidences();
	        assertEquals(2, evids.size());
	        assertEquals("ECO:0000269|PubMed:9060646", evids.get(0).getValue());
	        assertEquals("ECO:0000269|PubMed:9060647", evids.get(1).getValue());
	    }
}
