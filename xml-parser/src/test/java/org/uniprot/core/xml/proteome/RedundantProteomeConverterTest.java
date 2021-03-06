package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.RedundantProteome;
import org.uniprot.core.proteome.impl.RedundantProteomeBuilder;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;
import org.uniprot.core.xml.jaxb.proteome.RedundantProteomeType;

class RedundantProteomeConverterTest {
    private ObjectFactory xmlFactory = new ObjectFactory();
    RedundantProteomeConverter converter = new RedundantProteomeConverter();

    @Test
    void testFromXml() {
        RedundantProteomeType xml = xmlFactory.createRedundantProteomeType();
        xml.setUpid("UP001231");
        xml.setSimilarity("0.91");
        RedundantProteome rProteome = converter.fromXml(xml);
        assertEquals("UP001231", rProteome.getId().getValue());
        assertEquals(0.91f, rProteome.getSimilarity().doubleValue(), 0.000000001);
    }

    @Test
    void testToXml() {
        RedundantProteome rProteome =
                new RedundantProteomeBuilder().proteomeId("UP0000123").similarity(0.95f).build();
        RedundantProteomeType xml = converter.toXml(rProteome);
        RedundantProteome converted = converter.fromXml(xml);
        assertEquals(rProteome, converted);
    }
}
