package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineConverter;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineObject;

class SqLineConverterTest {
    private SqLineConverter converter = new SqLineConverter();

    @Test
    void test1() {

        SqLineObject obj = new SqLineObject();
        obj.crc64 = "B4840739BF7D4121";
        obj.weight = 29735;
        obj.length = 256;
        String sequence =
                "MAFSAEDVLKEYDRRRRMEALLLSLYYPNDRKLLDYKEWSPPRVQVECPKAPVEWNNPPS"
                        + "EKGLIVGHFSGIKYKGEKAQASEVDVNKMCCWVSKFKDAMRRYQGIQTCKIPGKVLSDLD"
                        + "AKIKAYNLTVEGVEGFVRYSRVTKQHVAAFLKELRHSKQYENVNLIHYILTDKRVDIQHL"
                        + "EKDLVKDFKALVESAHRMRQGHMINVKYILYQLLKKHGHGPDGPDILTVKTGSKGVLYDD"
                        + "SFRKIYTDLGWKFTPL";
        obj.sequence = sequence;

        Sequence seq = converter.convert(obj);
        assertEquals(256, seq.getLength());
        assertEquals(29735, seq.getMolWeight());
        assertEquals("B4840739BF7D4121", seq.getCrc64());
        assertEquals(sequence, seq.getValue());
    }
}
