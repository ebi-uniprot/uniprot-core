package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.id.IDLineBuilder;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;
import org.uniprot.core.flatfile.writer.FFLine;

class IDLineBuildTest {
    private IDLineBuilder builder = new IDLineBuilder();

    @Test
    void test1() {
        String idLine = "ID   ELNE_HUMAN              Reviewed;          45 AA.";
        IdLineObject idObj = new IdLineObject("ELNE_HUMAN", true, 45);

        FFLine ffLine = builder.build(idObj);

        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(idLine);
        assertEquals(idLine, resultString);
    }

    @Test
    void testNew() {

        String idLine = "ID   A0A000ACJ5_9GENT        Unreviewed;        45 AA.";
        IdLineObject idObj = new IdLineObject("A0A000ACJ5_9GENT", false, 45);

        FFLine ffLine = builder.build(idObj);

        String resultString = ffLine.toString();
        System.out.println(resultString);
        System.out.println(idLine);
        assertEquals(idLine, resultString);
    }
}
