package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GenomeAssembly;
import org.uniprot.core.proteome.GenomeAssemblyLevel;
import org.uniprot.core.proteome.GenomeAssemblySource;
import org.uniprot.core.proteome.impl.GenomeAssemblyBuilder;
import org.uniprot.core.xml.jaxb.proteome.GenomeAssemblyType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

/**
 * @author lgonzales
 * @since 16/04/2020
 */
class GenomeAssemblyConverterTest {
    private final ObjectFactory xmlFactory = new ObjectFactory();
    private final GenomeAssemblyConverter converter = new GenomeAssemblyConverter();

    @Test
    void testFromXmlNull() {
        GenomeAssembly result = converter.fromXml(null);
        assertNull(result);
    }

    @Test
    void testFromXml() {
        GenomeAssemblyType genomeAssemblyType = getGenomeAssemblyType(xmlFactory);

        GenomeAssembly result = converter.fromXml(genomeAssemblyType);

        assertNotNull(result);
        assertEquals("Id value", result.getAssemblyId());
        assertEquals("Url Value", result.getGenomeAssemblyUrl());
        assertEquals(GenomeAssemblySource.REFSEQ, result.getSource());
        assertEquals(GenomeAssemblyLevel.FULL, result.getLevel());
    }

    @Test
    void testToXml() {
        GenomeAssembly genomeAssembly = createGenomeAssembly();
        GenomeAssemblyType result = converter.toXml(genomeAssembly);

        assertNotNull(result);
        assertEquals("id value", result.getGenomeAssembly());
        assertEquals("url value", result.getGenomeAssemblyUrl());
        assertEquals("Ensembl", result.getGenomeAssemblySource());
        assertEquals("partial", result.getGenomeRepresentation());
    }

    static GenomeAssemblyType getGenomeAssemblyType(ObjectFactory xmlFactory) {
        GenomeAssemblyType genomeAssemblyType = xmlFactory.createGenomeAssemblyType();
        genomeAssemblyType.setGenomeRepresentation("full");
        genomeAssemblyType.setGenomeAssemblySource("Refseq");
        genomeAssemblyType.setGenomeAssemblyUrl("Url Value");
        genomeAssemblyType.setGenomeAssembly("Id value");
        return genomeAssemblyType;
    }

    static GenomeAssembly createGenomeAssembly() {
        return new GenomeAssemblyBuilder()
                .assemblyId("id value")
                .genomeAssemblyUrl("url value")
                .level(GenomeAssemblyLevel.PARTIAL)
                .source(GenomeAssemblySource.ENSEMBL)
                .build();
    }
}
