package uk.ac.ebi.uniprot.domain.uniprot.xdb.impl;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.Property;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtXDbType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniProtDBCrossReferenceImplTest {


    @Test
    public void testUniProtDatabaseCrossReferenceImpl12() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .build();

        verify(xref, val, type, id, description, null, null, null);
    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl32ByFactory() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;
        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .build();
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);

    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl32() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }


    @Test
    public void testUniProtDatabaseCrossReferenceImpl33() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        String type = "GO";
        String id = "GO:0005814";
        String description = "C:centriole";
        String thirdAttr = "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl42() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .isoformId(isoform)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);

    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl43() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        String type = "GO";
        String id = "GO:0005814";
        String description = "C:centriole";
        String thirdAttr = "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .isoformId(isoform)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }


    @Test
    public void testUniProtDatabaseCrossReferenceImpl44() {
        //DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
        String val = "EMBL; DQ185029; AAZ94714.1; -; mRNA.";
        String type = "EMBL";
        String id = "DQ185029";
        String description = "AAZ94714.1";
        String thirdAttr = "-";
        String fourthAttr = "mRNA";
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .addProperty(dbType.getAttribute(2), fourthAttr)
                .isoformId(isoform)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }


    @Test
    public void testUniProtDatabaseCrossReferenceImpl4Iso() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String val = "Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]";
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .addProperty(dbType.getAttribute(2), fourthAttr)
                .isoformId(isoform)
                .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    public void testUniProtDatabaseCrossReferenceImpl402() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtXDbType dbType = new UniProtXDbType(type);
        UniProtDBCrossReference xref = new UniProtDBCrossReferenceBuilder()
                .databaseType(dbType)
                .id(id)
                .addProperty(dbType.getAttribute(0), description)
                .addProperty(dbType.getAttribute(1), thirdAttr)
                .addProperty(dbType.getAttribute(2), fourthAttr)
                .isoformId(isoform)
                .build();

        verify(xref, val, type, id, description, null, null, null);

    }

    private void verify(UniProtDBCrossReference xref, String drVal, String type, String id, String description,
                        String thirdAttr, String fourAttr, String isoformId) {
        assertEquals(drVal, xref.toString());
        assertEquals(type, xref.getDatabaseType().getName());
        assertEquals(id, xref.getId());
        assertEquals(description, getValue(xref, 0));
        assertEquals(isoformId, xref.getIsoformId());
        assertEquals(thirdAttr, getValue(xref, 1));
        assertEquals(fourAttr, getValue(xref, 2));
        TestHelper.verifyJson(xref);
    }

    private String getValue(UniProtDBCrossReference xref, int number) {
        List<Property> properties = xref.getProperties();
        if (properties.size() < number + 1) {
            return null;
        } else
            return properties.get(number).getValue();
    }
}
