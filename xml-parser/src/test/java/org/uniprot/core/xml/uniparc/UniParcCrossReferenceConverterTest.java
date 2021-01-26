package org.uniprot.core.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.xml.XmlReaderException;
import org.uniprot.core.xml.jaxb.uniparc.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniparc.PropertyType;

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
        builder.database(UniParcDatabase.ENSEMBL_VERTEBRATE)
                .id("CG1106-PB")
                .versionI(1)
                .active(false)
                .created(LocalDate.of(2003, 4, 1))
                .lastUpdated(LocalDate.of(2007, 11, 22));
        UniParcCrossReference xref = builder.build();
        verify(xref);
    }

    @Test
    void testInvalidPropertyThrowsException() {
        UniParcCrossReference xref =
                new UniParcCrossReferenceBuilder()
                        .database(UniParcDatabase.ENSEMBL_VERTEBRATE)
                        .id("CG1106-PB")
                        .versionI(1)
                        .active(false)
                        .created(LocalDate.of(2003, 4, 1))
                        .lastUpdated(LocalDate.of(2007, 11, 22))
                        .build();

        UniParcDBCrossReferenceConverter converter = new UniParcDBCrossReferenceConverter();
        DbReferenceType xmlObj = converter.toXml(xref);

        PropertyType invalidProperty = new PropertyType();
        invalidProperty.setType("InvalidType");
        invalidProperty.setValue("InvalidValue");
        xmlObj.getProperty().add(invalidProperty);

        assertThrows(XmlReaderException.class, () -> converter.fromXml(xmlObj));
    }

    @Test
    void testComplete() {
        //		<dbReference type="UniProtKB/TrEMBL" id="A0A0C4DHG2" version_i="1" active="Y"
        // version="1" created="2015-04-01" last="2019-05-08">
        //		<property type="NCBI_taxonomy_id" value="7227"/>
        //		<property type="protein_name" value="Gelsolin, isoform J"/>
        //		<property type="gene_name" value="Gel"/>
        //		</dbReference>

        Organism taxonomy = new OrganismBuilder().taxonId(7227).build();

        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        builder.database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2-PB")
                .versionI(1)
                .version(1)
                .active(true)
                .taxonomy(taxonomy)
                .proteinName("Gelsolin, isoform J")
                .proteomeId("proteomeValue")
                .geneName("Gel")
                .component("ComponentValue")
                .ncbiGi("ncbiGiValue")
                .chain("chainValue")
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));

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
}
