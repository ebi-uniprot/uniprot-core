package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.builder.UniParcCrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class UniParcCrossReferenceConverterTest {

    @Test
    void testNoProperty() {

        // <dbReference type="Ensembl" id="CG1106-PB" version_i="1" active="N" created="2003-04-01"
        // last="2007-11-22"/>
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.databaseType(UniParcDatabase.ENSEMBL_VERTEBRATE)
                .id("CG1106-PB")
                .versionI(1)
                .active(false)
                .created(LocalDate.of(2003, 4, 1))
                .lastUpdated(LocalDate.of(2007, 11, 22));
        UniParcCrossReference xref = builder.build();
        verify(xref);
    }

    private void verify(UniParcCrossReference xref) {
        UniParcDBCrossReferenceConverter converter = new UniParcDBCrossReferenceConverter();
        DbReferenceType xmlObj = converter.toXml(xref);
        System.out.println(
                UniParcXmlTestHelper.toXmlString(xmlObj, DbReferenceType.class, "dbReference"));
        UniParcCrossReference converted = converter.fromXml(xmlObj);
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

        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.databaseType(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2-PB")
                .versionI(1)
                .version(1)
                .active(true)
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));
        List<Property> properties = new ArrayList<>();
        properties.add(new Property(UniParcCrossReference.PROPERTY_NCBI_TAXONOMY_ID, "7227"));
        properties.add(
                new Property(UniParcCrossReference.PROPERTY_PROTEIN_NAME, "Gelsolin, isoform J"));
        properties.add(new Property(UniParcCrossReference.PROPERTY_GENE_NAME, "Gel"));

        builder.propertiesSet(properties);

        UniParcCrossReference xref = builder.build();
        verify(xref);
    }
}
