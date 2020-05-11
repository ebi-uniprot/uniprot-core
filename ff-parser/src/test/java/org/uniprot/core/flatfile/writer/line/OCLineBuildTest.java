package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.oc.OCLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;

import java.util.ArrayList;
import java.util.List;

class OCLineBuildTest {
    private OCLineBuilder builder = new OCLineBuilder();

    @Test
    void testHuman() {

        String ocLine =
                "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia;\n"
                        + "OC   Eutheria; Euarchontoglires; Primates; Catarrhini; Hominidae; Homo.";
        List<String> taxonNames = new ArrayList<>();
        taxonNames.add("Eukaryota");
        taxonNames.add("Metazoa");
        taxonNames.add("Chordata");
        taxonNames.add("Craniata");
        taxonNames.add("Vertebrata");
        taxonNames.add("Euteleostomi");
        taxonNames.add("Mammalia");
        taxonNames.add("Eutheria");
        taxonNames.add("Euarchontoglires");
        taxonNames.add("Primates");
        taxonNames.add("Catarrhini");
        taxonNames.add("Hominidae");
        taxonNames.add("Homo");
        FFLine ffLine = builder.build(taxonNames);
        String resultString = ffLine.toString();
        assertEquals(ocLine, resultString);
    }
}
