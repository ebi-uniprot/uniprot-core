package uk.ac.ebi.uniprot.domain.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageDigestUtilTest {

    @Test
    public void testGetDigest() {
        assertEquals("900150983CD24FB0D6963F7D28E17F72", MessageDigestUtil.getDigest("abc", "MD5"));
        
        assertEquals("CFA0179DAE1A227203E07C673627B28F", MessageDigestUtil.getDigest("MSSPASTPSRRSSRRGRVTPTQSLRSEESRSSPNRRRRGE", "MD5"));
    }

    @Test
    public void testGetCrc64() {
        String seq =
                "MGQARPAARRPHSPDPGAQPAPPRRRARALALLGALLAAAAAVAAARACALLADAQAAARQESALKVLGTDGLFLFSSLDTDQDMYISPEEFKPIAEKLTGSVPVANYEEEELPHDPSEETLTIEARFQPLLMETMTKSKDGFLGVSRLALSGLRNWTTAASPSAAFAARHFRPFLPPPGQELGQPWWIIPGELSVFTGYLSNNRFYPPPPKGKEVIIHRLLSMFHPRPFVKTRFAPQGTVACLTAISDSYYTVMFRIHAEFQLSEPPDFPFWFSPGQFTGHIILSKDATHIRDFRLFVPNHRSLNVDMEWLYGASETSNMEVDIGYVPQMELEAVGPSVPSVILDEDGNMIDSRLPSGEPLQFVFEEIKWHQELSWEEAARRLEVAMYPFKKVNYLPFTEAFDRARAEKKLVHSILLWGALDDQSCUGSGRTLRETVLESPPILTLLNESFISTWSLVKELEDLQTQQENPLHRQLAGLHLEKYSFPVEMMICLPNGTVVHHINANYFLDITSMKPEDMENNNVFSFSSSFEDPSTATYMQFLREGLRRGLPLLQP";
        String seq2 =
                "MGQARPAARRPHSPDPGAQPAPPRRRARALALLGALLAAAAAVAAARACALLADAQAAARQESALKVLGTDGLFLFSSLDTDQDMYISPEEFKPIAEKLTGSVPVANYEEEELPHDPSEETLTIEARFQPLLMETMTKSKDGFLGVSRLALSGLRNWTTAASPSAAFAARHFRPFLPPPGQELGQPWWIIPGELSVFTGYLSNNRFYPPPPKGKEVIIHRLLSMFHPRPFVKTRFAPQGTVACLTAISDSYYTVMFRIHAEFQLSEPPDFPFWFSPGQFTGHIILSKDATHIRDFRLFVPNHRSLNVDMEWLYGASETSNMEVDIGYVPQMELEAVGPSVPSVILDEDGNMIDSRLPSGEPLQFVFEEIKWHQELSWEEAARRLEVAMYPFKKVNYLPFTEAFDRARAEKKLVHSILLWGALDDQSCCGSGRTLRETVLESPPILTLLNESFISTWSLVKELEDLQTQQENPLHRQLAGLHLEKYSFPVEMMICLPNGTVVHHINANYFLDITSMKPEDMENNNVFSFSSSFEDPSTATYMQFLREGLRRGLPLLQP";
        String cString1 = MessageDigestUtil.getCrc64(seq);
        String cString2 = MessageDigestUtil.getCrc64(seq2);

        assertEquals("D085928963DE8257", cString1);
        assertEquals("3E88FC97C3DE8249", cString2);
    }

}
