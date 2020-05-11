package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.xml.jaxb.uniprot.OrganismType;

import java.util.Arrays;

class OrganismConverterTest {

    @Test
    void test() {
        Organism organism = createOrganism();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        OrganismConverter converter = new OrganismConverter(evRefMapper);
        OrganismType xmlOrganism = converter.toXml(organism);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlOrganism, OrganismType.class, "organism"));
        Organism converted = converter.fromXml(xmlOrganism);
        assertEquals(organism, converted);
    }

    private Organism createOrganism() {
        OrganismBuilder builder = new OrganismBuilder();
        builder.scientificName("Homo sapiens")
                .commonName("Human")
                .taxonId(9606l)
                .lineagesSet(
                        Arrays.asList(
                                "Eukaryota",
                                "Metazoa",
                                "Chordata",
                                "Craniata",
                                "Vertebrata",
                                "Euteleostomi",
                                "Mammalia",
                                "Eutheria",
                                "Euarchontoglires",
                                "Primates",
                                "Haplorrhini",
                                "Catarrhini",
                                "Hominidae",
                                "Homo"))
                .evidencesSet(createEvidences());

        return builder.build();
    }
}
