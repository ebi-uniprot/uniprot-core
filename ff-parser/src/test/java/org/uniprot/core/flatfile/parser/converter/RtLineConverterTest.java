package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineConverter;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineObject;

class RtLineConverterTest {
    @Test
    void test() {
        RtLineObject rt = new RtLineObject();
        rt.title = "A novel adapter protein employs a phosphotyrosine binding domain";
        RtLineConverter converter = new RtLineConverter();

        String title = converter.convert(rt);
        assertEquals("A novel adapter protein employs a phosphotyrosine binding domain", title);
    }
}
