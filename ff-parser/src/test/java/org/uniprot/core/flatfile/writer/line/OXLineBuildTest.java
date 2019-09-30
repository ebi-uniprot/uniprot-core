package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ox.OXLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;

class OXLineBuildTest {
    private OXLineBuilder builder = new OXLineBuilder();

    @Test
    void test() {
        String oxLine = "OX   NCBI_TaxID=9606;";

        Organism taxId = new OrganismBuilder().taxonId(9606).build();

        FFLine ffLine = builder.build(taxId);

        String resultString = ffLine.toString();
        // System.out.println(text.getText());
        System.out.println(resultString);
        assertEquals(oxLine, resultString);
    }
}
