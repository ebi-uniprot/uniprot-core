package org.uniprot.core.xml.dbreference;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.impl.UniParcCrossReferenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.util.Pair;
import org.uniprot.core.util.PairImpl;
import org.uniprot.core.xml.jaxb.dbreference.DbReference;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.CrossReferenceConverterUtils.PROPERTY_SOURCES;
import static org.uniprot.core.xml.CrossReferenceConverterUtils.PROPERTY_UNIPROTKB_ACCESSION;


class UniParcCrossReferenceConverterTest {
    @Test
    void testComplete() {
        Organism taxonomy = new OrganismBuilder().taxonId(7227).build();
        UniParcCrossReferenceBuilder builder = new UniParcCrossReferenceBuilder();
        Pair<String, String> pair = new PairImpl<>("proteomeValue", "ComponentValue");
        builder.database(UniParcDatabase.TREMBL)
                .id("A0A0C4DHG2-PB")
                .versionI(1)
                .version(1)
                .active(true)
                .organism(taxonomy)
                .proteinName("Gelsolin, isoform J")
                .geneName("Gel")
                .ncbiGi("ncbiGiValue")
                .chain("chainValue")
                .proteomeIdComponentPairsAdd(pair)
                .propertiesAdd(PROPERTY_UNIPROTKB_ACCESSION, "P21802")
                .created(LocalDate.of(2015, 4, 1))
                .lastUpdated(LocalDate.of(2019, 5, 8));

        UniParcCrossReference xref = builder.build();
        UniParcCrossReferenceConverter converter = new UniParcCrossReferenceConverter();
        DbReference xmlObj = converter.toXml(xref);
        UniParcCrossReference converted = converter.fromXml(xmlObj);
        assertEquals(xref, converted);

    }
}
