package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.flatfile.parser.impl.os.OSLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;

class OSLineBuildTest {
    private OSLineBuilder builder = new OSLineBuilder();

    @Test
    void test2() {
        String osLine = "OS   Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB).";
        OrganismName organism =
                OrganismNameLineParser.createFromOrganismLine(
                        "Rous sarcoma virus (strain Schmidt-Ruppin B) (RSV-SRB)");

        FFLine ffLine = builder.build(new OrganismBuilder().from(organism).build());

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(osLine, resultString);
    }

    @Test
    void test41() {

        String osLine =
                "OS   Methylobacterium extorquens (Methylobacterium dichloromethanicum)\n"
                        + "OS   (Protomonas extorquens).";
        OrganismName organism =
                OrganismNameLineParser.createFromOrganismLine(
                        "Methylobacterium extorquens (Methylobacterium dichloromethanicum) (Protomonas extorquens)");

        FFLine ffLine = builder.build(new OrganismBuilder().from(organism).build());

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(osLine, resultString);
    }
}
