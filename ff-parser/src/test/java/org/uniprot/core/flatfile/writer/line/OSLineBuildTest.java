package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.flatfile.parser.impl.os.OSLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

class OSLineBuildTest {
    private OSLineBuilder builder = new OSLineBuilder();

    @Test
    void test2() {
        String osLine = "OS   Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB).";

        FFLine ffLine =
                builder.build(
                        createOrganism("Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB)"));

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(osLine, resultString);
    }

    private Organism createOrganism(String name) {
        OrganismName organismName = OrganismNameLineParser.createFromOrganismLine(name);
        OrganismBuilder builder = new OrganismBuilder();
        builder.scientificName(organismName.getScientificName())
                .synonymsSet(organismName.getSynonyms())
                .commonName(organismName.getCommonName());
        return builder.build();
    }

    @Test
    void test41() {

        String osLine =
                "OS   Methylobacterium extorquens (Methylobacterium dichloromethanicum)\n"
                        + "OS   (Protomonas extorquens).";

        FFLine ffLine =
                builder.build(
                        createOrganism(
                                "Methylobacterium extorquens (Methylobacterium dichloromethanicum)"
                                        + " (Protomonas extorquens)"));

        String resultString = ffLine.toString();
        System.out.println(osLine);
        System.out.println(resultString);
        assertEquals(osLine, resultString);
    }
}
