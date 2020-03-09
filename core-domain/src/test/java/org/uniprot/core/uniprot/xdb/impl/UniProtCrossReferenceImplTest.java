package org.uniprot.core.uniprot.xdb.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.cv.xdb.UniProtDatabaseAttribute;
import org.uniprot.core.cv.xdb.UniProtDatabaseDetail;
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtDatabase;

class UniProtCrossReferenceImplTest {

    @Test
    void testUniProtDatabaseCrossReferenceImpl12() {
        // DR GeneDB; H25N7.01:pep; -.
        String val = "GeneDB; H25N7.01:pep; -.";
        String type = "GeneDB";
        String id = "H25N7.01:pep";
        String description = "-";
        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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
        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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

        UniProtDatabase dbType = new UniProtDatabaseMock(type);
        UniProtCrossReference xref =
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
        UniProtCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertFalse(xref.hasIsoformId());
    }

    @Test
    void canCheckEvidencesNotPresent() {
        UniProtCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertFalse(xref.hasEvidences());
        assertNotNull(xref.getEvidences());
        assertTrue(xref.getEvidences().isEmpty());
    }

    @Test
    void toStringWillHave_DASH_propertiesNull() {
        UniProtCrossReference xref = new UniProtCrossReferenceBuilder().build();
        assertEquals("; null; -.", xref.toString());
    }

    @Test
    void twoDifferentEmptyObjectFromBuilder_areEqual() {
        UniProtCrossReference xref = new UniProtCrossReferenceBuilder().build();
        UniProtCrossReference xref2 = new UniProtCrossReferenceBuilder().build();
        assertTrue(xref.equals(xref2) && xref2.equals(xref));
        assertEquals(xref.hashCode(), xref2.hashCode());
    }

    @Test
    void defaultConstructor_jsonDeSerialization() {
        UniProtCrossReference xref = new UniProtCrossReferenceImpl();
        assertNull(xref.getDatabase());
        assertEquals("", xref.getId());
        assertTrue(xref.getEvidences().isEmpty());
    }

    private void verify(
            UniProtCrossReference xref,
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

    private String getValue(UniProtCrossReference xref, int number) {
        List<Property> properties = xref.getProperties();
        if (properties.size() < number + 1) {
            return null;
        } else return properties.get(number).getValue();
    }

    private static class UniProtDatabaseMock implements UniProtDatabase {
        String name;

        UniProtDatabaseMock(String name) {
            this.name = name;
        }

        public @Nonnull UniProtDatabaseDetail getUniProtDatabaseDetail() {
            throw new RuntimeException("No implementation defined in mock class");
        }

        public @Nullable UniProtDatabaseAttribute getUniProtDatabaseAttribute(int position) {
            return new UniProtDatabaseAttribute(name + position, null, null);
        }

        public String getName() {
            return name;
        }
    }
}
