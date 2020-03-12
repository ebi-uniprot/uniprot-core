package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.flatfile.parser.impl.oh.OHLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;

class OHLineBuildTest {
    private OHLineBuilder builder = new OHLineBuilder();

    @Test
    void testOGHydrogenosome() {
        String ohLine =
                "OH   NCBI_TaxID=9606; Homo sapiens (Human).\n"
                        + "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n"
                        + "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
        List<OrganismHost> hosts = new ArrayList<>();

        hosts.add(createHost("Homo sapiens (Human)", 9606l));
        hosts.add(createHost("Epomops franqueti (Franquet's epauleted bat)", 77231L));
        hosts.add(createHost("Myonycteris torquata (Little collared fruit bat)", 77243L));
        verify(ohLine, hosts);
    }

    private OrganismHost createHost(String name, long taxid) {
        OrganismName organismName = OrganismNameLineParser.createFromOrganismLine(name);
        OrganismHostBuilder builder = new OrganismHostBuilder();
        builder.scientificName(organismName.getScientificName())
                .synonymsSet(organismName.getSynonyms())
                .commonName(organismName.getCommonName())
                .taxonId(taxid);
        return builder.build();
    }

    private void verify(String ogLine, List<OrganismHost> hosts) {

        FFLine ffLine = builder.build(hosts);
        String resultString = ffLine.toString();

        System.out.println(resultString);
        System.out.println();
        System.out.println(ogLine);
        assertEquals(ogLine, resultString);
    }

    @Test
    void testOGHydrogenosome2() {
        String ohLine =
                "OH   NCBI_TaxID=9606; Homo sapiens (Human).\n"
                        + "OH   NCBI_TaxID=77231; Epomops franqueti (Franquet's epauleted bat).\n"
                        + "OH   NCBI_TaxID=9685; Felis catus (Cat) (Felis silvestris catus).\n"
                        + "OH   NCBI_TaxID=77243; Myonycteris torquata (Little collared fruit bat).";
        List<OrganismHost> hosts = new ArrayList<>();
        hosts.add(createHost("Homo sapiens (Human)", 9606l));
        hosts.add(createHost("Epomops franqueti (Franquet's epauleted bat)", 77231L));
        hosts.add(createHost("Felis catus (Cat) (Felis silvestris catus)", 9685L));
        hosts.add(createHost("Myonycteris torquata (Little collared fruit bat)", 77243L));
        verify(ohLine, hosts);
    }

    @Test
    void testLongOH() {
        String ohLine =
                "OH   NCBI_TaxID=9606; Homo sapiens (Human).\n"
                        + "OH   NCBI_TaxID=9685; Felis catus (Cat) (Felis silvestris catus).\n"
                        + "OH   NCBI_TaxID=9785; Loxodonta africana (African elephant).\n"
                        + "OH   NCBI_TaxID=9913; Bos taurus (Bovine).\n"
                        + "OH   NCBI_TaxID=10090; Mus musculus (Mouse).\n"
                        + "OH   NCBI_TaxID=29092; Microtus agrestis (Short-tailed field vole).\n"
                        + "OH   NCBI_TaxID=447135; Myodes glareolus (Bank vole) (Clethrionomys glareolus).";
        List<OrganismHost> hosts = new ArrayList<>();

        hosts.add(createHost("Homo sapiens (Human)", 9606l));
        hosts.add(createHost("Felis catus (Cat) (Felis silvestris catus)", 9685L));
        hosts.add(createHost("Loxodonta africana (African elephant)", 9785L));
        hosts.add(createHost("Bos taurus (Bovine)", 9913L));
        hosts.add(createHost("Mus musculus (Mouse)", 10090L));
        hosts.add(createHost("Microtus agrestis (Short-tailed field vole)", 29092L));
        hosts.add(createHost("Myodes glareolus (Bank vole) (Clethrionomys glareolus)", 447135L));

        verify(ohLine, hosts);
    }
}
