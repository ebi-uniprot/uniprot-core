package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtIsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtDBCrossReferenceFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtIsoformIdImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DatabaseType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.DbXRefAttribute;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniProtDBCrossReferenceImplTest {

    @Test
    public void testCreateDbXRefAttribute() {
        String val = "Some val";
       DbXRefAttribute attr = UniProtDBCrossReferenceImpl.createDbXRefAttribute(val);
       assertEquals(val, attr.getValue());
    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl12() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description);
      
        verify(xref, val, type, id, description, null, null, null);
        
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl12ByFactory() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        UniProtDBCrossReference xref =UniProtDBCrossReferenceFactory.INSTANCE
                .createUniProtDBCrossReference(type, id, description);
      
        verify(xref, val, type, id, description, null, null, null);
        
    }
    
    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl32ByFactory() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        String thirdAttr= null;
        String fourthAttr = null;
        String isoform = null;
        UniProtDBCrossReference xref =UniProtDBCrossReferenceFactory.INSTANCE
                .createUniProtDBCrossReference(type, id, description, thirdAttr);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl32() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        String thirdAttr= null;
        String fourthAttr = null;
        String isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }
   
    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl33() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        DatabaseType type =DatabaseType.GO;
        String id ="GO:0005814";
        String description ="C:centriole";
        String thirdAttr= "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl42() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        String thirdAttr= null;
        String fourthAttr = null;
        String isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl43() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        DatabaseType type =DatabaseType.GO;
        String id ="GO:0005814";
        String description ="C:centriole";
        String thirdAttr= "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }
    
    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl44() {
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
        String val = "EMBL; DQ185029; AAZ94714.1; -; mRNA.";
        DatabaseType type =DatabaseType.EMBL;
        String id ="DQ185029";
        String description ="AAZ94714.1";
        String thirdAttr= "-";
        String fourthAttr = "mRNA";
        String isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
        
    }

    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl4Iso() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String val = "Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]";
        DatabaseType type =DatabaseType.ENSEMBL;
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }
    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl4IsoByFactory() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String val = "Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]";
        DatabaseType type =DatabaseType.ENSEMBL;
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr= "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";
        UniProtDBCrossReference xref =UniProtDBCrossReferenceFactory.INSTANCE
                .createUniProtDBCrossReference(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }
    
   
    private void verify(UniProtDBCrossReference xref, String drVal, DatabaseType type, String id, String description,
            String thirdAttr, String fourAttr, String isoformId
            ){
        assertEquals(drVal, xref.toString());
        assertEquals(type , xref.getDatabase());
        assertEquals(id, xref.getId());
        assertEquals(description, xref.getDescription());
        if((isoformId !=null) && !isoformId.isEmpty()){
            assertTrue(xref.getIsoformId().isPresent());
            assertEquals(isoformId, xref.getIsoformId().get().getValue());
        }else{
            assertFalse(xref.getIsoformId().isPresent());
        }
        if(type.getNumberOfAttribute()>=3){
            assertTrue(xref.getThirdAttribute().isPresent());
            assertEquals(thirdAttr, xref.getThirdAttribute().get().getValue());
        }else{
            assertFalse(xref.getThirdAttribute().isPresent());
        }
        if(type.getNumberOfAttribute()>3){
            assertTrue(xref.getFourthAttribute().isPresent());
            assertEquals(fourAttr, xref.getFourthAttribute().get().getValue());
        }else{
            assertFalse(xref.getFourthAttribute().isPresent());
        }
       
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl402() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        DatabaseType type =DatabaseType.GENEDB;
        String id ="H25N7.01:pep";
        String description ="-";
        DbXRefAttribute thirdAttr= null;
        DbXRefAttribute fourthAttr = null;
        UniProtIsoformId isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, null, null, null);
        
    }
    @Test
    public void testUniProtDatabaseCrossReferenceImpl403() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        DatabaseType type =DatabaseType.GO;
        String id ="GO:0005814";
        String description ="C:centriole";
        String thirdAttr0= "IEA:Ensembl";
        DbXRefAttribute thirdAttr= UniProtDBCrossReferenceImpl.createDbXRefAttribute(thirdAttr0);
        DbXRefAttribute fourthAttr = null;
        UniProtIsoformId isoform = null;
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr0, null, null);
        
    }
    
    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl404() {
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
        String val = "EMBL; DQ185029; AAZ94714.1; -; mRNA.";
        DatabaseType type =DatabaseType.EMBL;
        String id ="DQ185029";
        String description ="AAZ94714.1";
        String thirdAttr0= "-";
        String fourthAttr0 = "mRNA";
        String isoform0 = null;
        DbXRefAttribute thirdAttr= UniProtDBCrossReferenceImpl.createDbXRefAttribute(thirdAttr0);
        DbXRefAttribute fourthAttr= UniProtDBCrossReferenceImpl.createDbXRefAttribute(fourthAttr0);

        UniProtIsoformId isoform = null;
        
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr0, fourthAttr0, isoform0);
        
    }

    
    @Test
    public void testUniProtDatabaseCrossReferenceImpl40Iso() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String val = "Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]";
        DatabaseType type =DatabaseType.ENSEMBL;
        String id ="ENST00000393119";
        String description ="ENSP00000376827";
        String thirdAttr0= "ENSG00000011143";
        String fourthAttr0 = null;
        String isoform0 = "Q9NXB0-1";
        
        DbXRefAttribute thirdAttr= UniProtDBCrossReferenceImpl.createDbXRefAttribute(thirdAttr0);
        DbXRefAttribute fourthAttr= null;

        UniProtIsoformId isoform = new UniProtIsoformIdImpl(isoform0);
        
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceImpl(type, id, description, thirdAttr, fourthAttr, isoform);
        verify(xref, val, type, id, description, thirdAttr0, fourthAttr0, isoform0);
    }
    
   
}
