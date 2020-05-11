package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.id.IdLineConverter;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;

import java.util.Map;

class IdLineConverterTest {
    private IdLineConverter converter = new IdLineConverter();

    @Test
    void testConverter() {
        // ID   001R_FRG3G              Reviewed;         256 AA
        IdLineObject idObj = new IdLineObject();
        idObj.setReviewed(true);
        idObj.setEntryName("001R_FRG3G");
        idObj.setSequenceLength(256);
        Map.Entry<UniProtKBId, UniProtKBEntryType> uniObj = converter.convert(idObj);

        assertEquals("001R_FRG3G", uniObj.getKey().getValue());
        assertEquals(UniProtKBEntryType.SWISSPROT, uniObj.getValue());
    }

    @Test
    void testConverter2() {
        // ID   001R_FRG3G              Reviewed;         256 AA
        IdLineObject idObj = new IdLineObject();
        idObj.setReviewed(false);
        idObj.setEntryName("001R_FRG3G");
        idObj.setSequenceLength(256);

        Map.Entry<UniProtKBId, UniProtKBEntryType> uniObj = converter.convert(idObj);

        assertEquals("001R_FRG3G", uniObj.getKey().getValue());
        assertEquals(UniProtKBEntryType.TREMBL, uniObj.getValue());
    }
}
