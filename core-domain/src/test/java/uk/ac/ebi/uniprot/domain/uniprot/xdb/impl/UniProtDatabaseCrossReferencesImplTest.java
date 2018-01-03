package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtDatabaseCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDatabaseCrossReference;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtDatabaseCrossReferencesImplTest {
    private UniProtDatabaseCrossReferences uniXrefs ;
    List<UniProtDatabaseCrossReference> xrefs;
    @Before
    public void setup(){
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        DatabaseType type =DatabaseType.ENSEMBL;
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        xrefs = new ArrayList<>();
        xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
        
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
      
     
         type =DatabaseType.EMBL;
         id ="DQ185029";
         description ="AAZ94714.1";
         thirdAttr= "-";
         fourthAttr = "mRNA";
         isoform = null;
        xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
        // DR   EMBL; AK000352; BAA91105.1; ALT_INIT; mRNA.
        type =DatabaseType.EMBL;
        id ="AK000352";
        description ="BAA91105.1";
        thirdAttr= "ALT_INIT";
        fourthAttr = "mRNA";
        isoform = null;
       xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
       // DR   EMBL; AK310815; -; NOT_ANNOTATED_CDS; mRNA.
       type =DatabaseType.EMBL;
       id ="AK310815";
       description ="-";
       thirdAttr= "NOT_ANNOTATED_CDS";
       fourthAttr = "mRNA";
       isoform = null;
      xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
      
   //   DR   HPA; HPA021372; -.
      type =DatabaseType.HPA;
      id ="HPA021372";
      description ="-";
      thirdAttr=  null;
      fourthAttr = null;
      isoform = null;
     xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
     //  DR   HPA; HPA021812; -.
     type =DatabaseType.HPA;
     id ="HPA021812";
     description ="-";
     thirdAttr=  null;
     fourthAttr = null;
     isoform = null;
    xrefs.add (new UniProtDatabaseCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform));
    uniXrefs = new UniProtDatabaseCrossReferencesImpl(xrefs);
    
    }
    @Test
    public void createByFactory(){
        UniProtDatabaseCrossReferences dbXrefs = UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDatabaseCrossReferences(xrefs);
        assertEquals(uniXrefs, dbXrefs);
    }
    @Test
    public void testGetCrossReferences() {
        assertEquals(6, uniXrefs.getCrossReferences().size());
    }

    @Test
    public void testGetCrossReferencesByTypeEMBL() {
        assertEquals(3, uniXrefs.getCrossReferencesByType(DatabaseType.EMBL).size());
    }
    @Test
    public void testGetCrossReferencesByTypeEnsembl() {
        assertEquals(1, uniXrefs.getCrossReferencesByType(DatabaseType.ENSEMBL).size());
    }

    @Test
    public void testGetCrossReferencesByTypeHPA() {
        assertEquals(2, uniXrefs.getCrossReferencesByType(DatabaseType.HPA).size());
    }
    @Test
    public void testGetCrossReferencesByTypeGO() {
        assertEquals(0, uniXrefs.getCrossReferencesByType(DatabaseType.GO).size());
    }
}
