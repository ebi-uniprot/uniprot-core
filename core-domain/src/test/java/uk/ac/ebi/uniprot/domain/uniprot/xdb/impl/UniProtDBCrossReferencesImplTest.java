package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtDBCrossReferencesImplTest {
    private UniProtDBCrossReferences uniXrefs ;
    List<UniProtDBCrossReference> xrefs;
    @Before
    public void setup(){
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String type ="Ensembl";
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        xrefs = new ArrayList<>();
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
      
     
         type ="EMBL";
         id ="DQ185029";
         description ="AAZ94714.1";
         thirdAttr= "-";
         fourthAttr = "mRNA";
         isoform = null;
        xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type ="EMBL";
        id ="AK000352";
        description ="BAA91105.1";
        thirdAttr= "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
       xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
       // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
       type ="EMBL";
       id ="AK310815";
       description ="-";
       thirdAttr= "NOT_ANNOTATED_CDS";
       fourthAttr = "mRNA";
       isoform = null;
      xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
      
   //   DR   HPA; HPA021372; -.
      type ="HPA";
      id ="HPA021372";
      description ="-";
      thirdAttr=  null;
      fourthAttr = null;
      isoform = null;
     xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
     //  DR   HPA; HPA021812; -.
     type ="HPA";
     id ="HPA021812";
     description ="-";
     thirdAttr=  null;
     fourthAttr = null;
     isoform = null;
    xrefs.add ( UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform));
    uniXrefs = new UniProtDBCrossReferencesImpl(xrefs.stream().map(val ->(UniProtDBCrossReferenceImpl)val )
    		.collect(Collectors.toList()));
    
    }
    @Test
    public void createByFactory(){
        UniProtDBCrossReferences dbXrefs = UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReferences(xrefs);
        assertEquals(uniXrefs, dbXrefs);
    }
    @Test
    public void testGetCrossReferences() {
        assertEquals(6, uniXrefs.getCrossReferences().size());
       
        
    }
    @Test 
     public void testJsonConversion() {
    	TestHelper.verifyJson(uniXrefs);
    }

    @Test
    public void testGetCrossReferencesByTypeEMBL() {
        assertEquals(3, uniXrefs.getCrossReferencesByType("EMBL").size());
    }
    @Test
    public void testGetCrossReferencesByTypeEnsembl() {
        assertEquals(1, uniXrefs.getCrossReferencesByType("Ensembl").size());
    }

    @Test
    public void testGetCrossReferencesByTypeHPA() {
        assertEquals(2, uniXrefs.getCrossReferencesByType("HPA").size());
    }
    @Test
    public void testGetCrossReferencesByTypeGO() {
        assertEquals(0, uniXrefs.getCrossReferencesByType("GO").size());
    }
}
