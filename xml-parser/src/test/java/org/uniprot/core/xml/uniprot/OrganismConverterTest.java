package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;
import org.uniprot.core.xml.jaxb.uniprot.OrganismType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.OrganismConverter;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

class OrganismConverterTest {

    @Test
    void test() {
        Organism organism = createOrganism();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        OrganismConverter converter = new OrganismConverter(evRefMapper);
        OrganismType xmlOrganism = converter.toXml(organism);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlOrganism, OrganismType.class, "organism"));
        Organism converted = converter.fromXml(xmlOrganism);
        assertEquals(organism, converted);

    }

    private Organism createOrganism() {
        OrganismBuilder builder = new OrganismBuilder();
        builder.scientificName("Homo sapiens")
                .commonName("Human")
                .taxonId(9606l)
                .lineage(Arrays.asList("Eukaryota", "Metazoa", "Chordata",
                                       "Craniata", "Vertebrata", "Euteleostomi",
                                       "Mammalia", "Eutheria", "Euarchontoglires", "Primates", "Haplorrhini",
                                       "Catarrhini", "Hominidae", "Homo"
                ))
                .evidences(createEvidences());

        return builder.build();
    }
}
