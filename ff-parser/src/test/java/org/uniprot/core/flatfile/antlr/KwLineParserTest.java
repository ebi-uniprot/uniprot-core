package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.kw.KwLineObject;

class KwLineParserTest {
    @Test
    void test() {
        String kwLines = "KW   Activator; Complete proteome; Reference proteome; Transcription.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(4, obj.keywords.size());
        verify(obj, 0, "Activator", null);
        verify(obj, 1, "Complete proteome", null);
        verify(obj, 2, "Reference proteome", null);
        verify(obj, 3, "Transcription", null);
    }

    private void verify(KwLineObject obj, int position, String expected, List<String> evidences) {
        assertEquals(expected, obj.keywords.get(position));
        assertEquals(
                evidences, obj.getEvidenceInfo().getEvidences().get(obj.keywords.get(position)));
    }

    @Test
    void testLineWraper() {
        String kwLines =
                "KW   Activator; Complete proteome;\n"
                        + "KW   Reference proteome; Transcription;\n"
                        + "KW   Transcription regulation.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(5, obj.keywords.size());
        verify(obj, 0, "Activator", null);
        verify(obj, 1, "Complete proteome", null);
        verify(obj, 2, "Reference proteome", null);
        verify(obj, 3, "Transcription", null);
        verify(obj, 4, "Transcription regulation", null);
    }

    @Test
    void testEvidences() {
        String kwLines =
                "KW   Activator {ECO:00000001}; Complete proteome {ECO:00000001};\n"
                        + "KW   Reference proteome; Transcription {ECO:0000006|PubMed:20858735,"
                        + " ECO:0000006};\n"
                        + "KW   Transcription regulation.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(5, obj.keywords.size());
        verify(obj, 0, "Activator", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 1, "Complete proteome", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 2, "Reference proteome", null);
        verify(
                obj,
                3,
                "Transcription",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
        verify(obj, 4, "Transcription regulation", null);
    }

    @Test
    void testEvidencesWrapper() {
        String kwLines =
                "KW   Activator {ECO:00000001}; Complete proteome {ECO:00000001};\n"
                        + "KW   Reference proteome; Transcription {ECO:0000006|PubMed:20858735,\n"
                        + "KW   ECO:0000006};\n"
                        + "KW   Transcription regulation.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(5, obj.keywords.size());
        verify(obj, 0, "Activator", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 1, "Complete proteome", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 2, "Reference proteome", null);
        verify(
                obj,
                3,
                "Transcription",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
        verify(obj, 4, "Transcription regulation", null);
    }

    @Test
    void testEvidencesWrapper3() {
        String kwLines =
                "KW   Activator {ECO:00000001}; Complete proteome {ECO:00000001};\n"
                        + "KW   Reference proteome; Transcription\n"
                        + "KW   {ECO:0000006|PubMed:20858735, ECO:0000006};\n"
                        + "KW   Transcription regulation.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(5, obj.keywords.size());
        verify(obj, 0, "Activator", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 1, "Complete proteome", Arrays.asList(new String[] {"ECO:00000001"}));
        verify(obj, 2, "Reference proteome", null);
        verify(
                obj,
                3,
                "Transcription",
                Arrays.asList(new String[] {"ECO:0000006|PubMed:20858735", "ECO:0000006"}));
        verify(obj, 4, "Transcription regulation", null);
    }

    @Test
    void testLongKw() {
        String kwLines =
                "KW   Disulfide bond {ECO:0000256|SAAS:SAAS000777_004_000331};\n"
                        + "KW   Fusion of virus membrane with host membrane\n"
                        + "KW   {ECO:0000256|SAAS:SAAS000777_004_001688};\n"
                        + "KW   Host-virus interaction {ECO:0000256|SAAS:SAAS000777_004_000688};\n"
                        + "KW   Viral attachment to host cell\n"
                        + "KW   {ECO:0000256|SAAS:SAAS000777_004_000923};\n"
                        + "KW   Viral envelope protein {ECO:0000313|EMBL:AAY20056.1};\n"
                        + "KW   Viral penetration into host cytoplasm\n"
                        + "KW   {ECO:0000256|SAAS:SAAS000777_004_001402}; Virion;\n"
                        + "KW   Virus entry into host cell {ECO:0000256|SAAS:SAAS000777_004_000842};\n"
                        + "KW   Complete proteome; Metal-binding; Repeat; Virus reference strain;\n"
                        + "KW   Zinc-finger.\n";
        UniprotKBLineParser<KwLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createKwLineParser();
        KwLineObject obj = parser.parse(kwLines);
        assertEquals(13, obj.keywords.size());
        verify(
                obj,
                0,
                "Disulfide bond",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_000331"}));
        verify(
                obj,
                1,
                "Fusion of virus membrane with host membrane",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_001688"}));
        verify(
                obj,
                2,
                "Host-virus interaction",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_000688"}));
        verify(
                obj,
                3,
                "Viral attachment to host cell",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_000923"}));
        verify(
                obj,
                4,
                "Viral envelope protein",
                Arrays.asList(new String[] {"ECO:0000313|EMBL:AAY20056.1"}));
        verify(
                obj,
                5,
                "Viral penetration into host cytoplasm",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_001402"}));
        verify(obj, 6, "Virion", null);
        verify(
                obj,
                7,
                "Virus entry into host cell",
                Arrays.asList(new String[] {"ECO:0000256|SAAS:SAAS000777_004_000842"}));
        verify(obj, 8, "Complete proteome", null);
        verify(obj, 9, "Metal-binding", null);
        verify(obj, 10, "Repeat", null);
        verify(obj, 11, "Virus reference strain", null);
        verify(obj, 12, "Zinc-finger", null);
    }
}
