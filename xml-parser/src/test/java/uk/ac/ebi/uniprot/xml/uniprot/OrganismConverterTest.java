package uk.ac.ebi.uniprot.xml.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.EvidenceHelper.createEvidences;

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
