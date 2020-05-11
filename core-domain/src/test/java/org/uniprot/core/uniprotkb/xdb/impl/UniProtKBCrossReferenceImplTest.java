package org.uniprot.core.uniprotkb.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.UniProtKBDatabaseMock;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.UniProtKBDatabase;

class UniProtKBCrossReferenceImplTest {

    @Test
    void testUniProtDatabaseCrossReferenceImpl12() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .build();

        verify(xref, val, type, id, description, null, null, null);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl32ByFactory() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;
        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .build();
        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl32() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl33() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        String type = "GO";
        String id = "GO:0005814";
        String description = "C:centriole";
        String thirdAttr = "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl42() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .isoformId(isoform)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl43() {
        // DR   GO; GO:0005814; C:centriole; IEA:Ensembl.
        String val = "GO; GO:0005814; C:centriole; IEA:Ensembl.";
        String type = "GO";
        String id = "GO:0005814";
        String description = "C:centriole";
        String thirdAttr = "IEA:Ensembl";
        String fourthAttr = null;
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .isoformId(isoform)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl44() {
        // DR   EMBL; DQ185029; AAZ94714.1; -; mRNA.
        String val = "EMBL; DQ185029; AAZ94714.1; -; mRNA.";
        String type = "EMBL";
        String id = "DQ185029";
        String description = "AAZ94714.1";
        String thirdAttr = "-";
        String fourthAttr = "mRNA";
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(2), fourthAttr)
                        .isoformId(isoform)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl4Iso() {
        // DR   Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]
        String val = "Ensembl; ENST00000393119; ENSP00000376827; ENSG00000011143. [Q9NXB0-1]";
        String type = "Ensembl";
        String id = "ENST00000393119";
        String description = "ENSP00000376827";
        String thirdAttr = "ENSG00000011143";
        String fourthAttr = null;
        String isoform = "Q9NXB0-1";

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(2), fourthAttr)
                        .isoformId(isoform)
                        .build();

        verify(xref, val, type, id, description, thirdAttr, fourthAttr, isoform);
    }

    @Test
    void testUniProtDatabaseCrossReferenceImpl402() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        String thirdAttr = null;
        String fourthAttr = null;
        String isoform = null;

        UniProtKBDatabase dbType = new UniProtKBDatabaseMock(type);
        UniProtKBCrossReference xref =
                new UniProtCrossReferenceBuilder()
                        .database(dbType)
                        .id(id)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(0), description)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(1), thirdAttr)
                        .propertiesAdd(dbType.getUniProtDatabaseAttribute(2), fourthAttr)
                        .isoformId(isoform)
                        .build();

        verify(xref, val, type, id, description, null, null, null);
    }

    @Test
    void canCheckIsformIsNotPresent() {
        UniProtKBCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertFalse(xref.hasIsoformId());
    }

    @Test
    void canCheckEvidencesNotPresent() {
        UniProtKBCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertFalse(xref.hasEvidences());
        assertNotNull(xref.getEvidences());
        assertTrue(xref.getEvidences().isEmpty());
    }

    @Test
    void toStringWillHave_DASH_propertiesNull() {
        UniProtKBCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertEquals("; null; -.", xref.toString());
    }

    @Test
    void twoDifferentEmptyObjectFromBuilder_areEqual() {
        UniProtKBCrossReference xref = new UniProtCrossReferenceBuilder().build();
        UniProtKBCrossReference xref2 = new UniProtCrossReferenceBuilder().build();
        assertTrue(xref.equals(xref2) && xref2.equals(xref));
        assertEquals(xref.hashCode(), xref2.hashCode());
    }

    @Test
    void defaultConstructor_jsonDeSerialization() {
        UniProtKBCrossReference xref = new UniProtKBCrossReferenceImpl();
        assertNull(xref.getDatabase());
        assertEquals("", xref.getId());
        assertTrue(xref.getEvidences().isEmpty());
    }

    private void verify(
            UniProtKBCrossReference xref,
            String drVal,
            String type,
            String id,
            String description,
            String thirdAttr,
            String fourAttr,
            String isoformId) {
        assertEquals(drVal, xref.toString());
        assertEquals(type, xref.getDatabase().getName());
        assertEquals(id, xref.getId());
        assertEquals(description, getValue(xref, 0));
        assertEquals(isoformId, xref.getIsoformId());
        assertEquals(thirdAttr, getValue(xref, 1));
        assertEquals(fourAttr, getValue(xref, 2));
    }

    private String getValue(UniProtKBCrossReference xref, int number) {
        List<Property> properties = xref.getProperties();
        if (properties.size() < number + 1) {
            return null;
        } else return properties.get(number).getValue();
    }
}
