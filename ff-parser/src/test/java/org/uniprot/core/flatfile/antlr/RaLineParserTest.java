package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotkbLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineObject;

class RaLineParserTest {
    @Test
    void test() {
        String raLines = "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n";
        UniprotkbLineParser<RaLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createRaLineParser();
        RaLineObject obj = parser.parse(raLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Tan W.G.", "Barkman T.J.", "Gregory Chinchar V.", "Essani K."
                        }));
    }

    private void verify(RaLineObject obj, List<String> authors) {
        assertEquals(authors, obj.authors);
    }

    @Test
    void test2() {
        String raLines = "RA   Galinier A., Perriere G., Duclos B.;\n";
        UniprotkbLineParser<RaLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createRaLineParser();
        RaLineObject obj = parser.parse(raLines);
        verify(obj, Arrays.asList(new String[] {"Galinier A.", "Perriere G.", "Duclos B."}));
    }

    @Test
    void test3() {
        String raLines = "RA   Galinier A. B.;\n";
        UniprotkbLineParser<RaLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createRaLineParser();
        RaLineObject obj = parser.parse(raLines);
        verify(obj, Arrays.asList(new String[] {"Galinier A. B."}));
    }

    @Test
    void test4() {
        String raLines =
                "RA   Galinier A., Bleicher F., Nasoff M.S., Baker H.V. II, Wolf R.E. Jr.,\n"
                        + "RA   Cozzone A.J., Cortay J.-C.;\n";
        UniprotkbLineParser<RaLineObject> parser =
                new DefaultUniprotkbLineParserFactory().createRaLineParser();
        RaLineObject obj = parser.parse(raLines);
        verify(
                obj,
                Arrays.asList(
                        new String[] {
                            "Galinier A.",
                            "Bleicher F.",
                            "Nasoff M.S.",
                            "Baker H.V. II",
                            "Wolf R.E. Jr.",
                            "Cozzone A.J.",
                            "Cortay J.-C."
                        }));
    }
}
