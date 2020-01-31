package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcDBCrossReference;
import org.uniprot.core.uniparc.UniParcDatabaseType;
import org.uniprot.core.uniparc.builder.UniParcDBCrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcDBCrossReferenceConverterTest {

    @Test
    void testNoProperty() {

        // <dbReference type="Ensembl" id="CG1106-PB" version_i="1" active="N" created="2003-04-01"
        // last="2007-11-22"/>
        UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
        builder.databaseType(UniParcDatabaseType.ENSEMBL_VERTEBRATE)
                .id("CG1106-PB")
                .versionI(1)
                .active(false)
                .created(LocalDate.of(2003, 4, 1))
                .lastUpdated(LocalDate.of(2007, 11, 22));
        UniParcDBCrossReference xref = builder.build();
        verify(xref);
    }

    private void verify(UniParcDBCrossReference xref) {
        UniParcDBCrossReferenceConverter converter = new UniParcDBCrossReferenceConverter();
        DbReferenceType xmlObj = converter.toXml(xref);
        System.out.println(
                UniParcXmlTestHelper.toXmlString(xmlObj, DbReferenceType.class, "dbReference"));
        UniParcDBCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(xref, converted);
    }

    @Test
    void testWithProperty() {
        //		<dbReference type="UniProtKB/TrEMBL" id="A0A0C4DHG2" version_i="1" active="Y"
        // version="1" created="2015-04-01" last="2019-05-08">
        //		<property type="NCBI_taxonomy_id" value="7227"/>
        //		<property type="protein_name" value="Gelsolin, isoform J"/>
        //		<property type="gene_name" value="Gel"/>
        //		</dbReference>

        UniParcDBCrossReferenceBuilder builder = new UniParcDBCrossReferenceBuilder();
        builder.databaseType(UniParcDatabaseType.TREMBL)
                .id("A0A0C4DHG2-PB")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
        properties.add(
                new Property(UniParcDBCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
        properties.add(new Property(UniParcDBCrossReference.PROPERTY_GENE_NAME, "Gel"));

        builder.propertiesSet(properties);

        UniParcDBCrossReference xref = builder.build();
        verify(xref);
    }
}
