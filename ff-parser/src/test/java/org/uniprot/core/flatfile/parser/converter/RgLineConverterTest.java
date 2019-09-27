package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineConverter;
import org.uniprot.core.flatfile.parser.impl.rg.RgLineObject;

class RgLineConverterTest {
    @Test
    void test() {
        // "RG   The mouse genome sequencing consortium;\n";
        RgLineObject rgline = new RgLineObject();
        rgline.reference_groups.add("The mouse genome sequencing consortium");
        RgLineConverter converter = new RgLineConverter();
        List<String> ags = converter.convert(rgline);
        assertEquals(1, ags.size());
        assertEquals("The mouse genome sequencing consortium", ags.get(0));
    }
}
