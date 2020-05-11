package org.uniprot.core.flatfile.writer.line.rlines;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Author;
import org.uniprot.core.citation.impl.AuthorBuilder;
import org.uniprot.core.flatfile.parser.impl.ra.RALineBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class RALineBuilderTest {
    private final RALineBuilder builder = new RALineBuilder();

    @Test
    void test1() {
        List<String> authorsS =
                new ArrayList<>(
                        Arrays.asList(
                                "Tan W.G.", "Barkman T.J.",
                                "Gregory Chinchar V.", "Essani K.",
                                "Arctander P.", "Fjeldsaa J."));
        List<Author> authors = buildAuthors(authorsS);
        List<String> lines = builder.buildLine(authors, true, true);
        assertEquals(2, lines.size());
        String expected =
                "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K., Arctander P.,";
        assertEquals(expected, lines.get(0));
    }

    @Test
    void test2() {
        List<String> authorsS =
                new ArrayList<>(
                        Arrays.asList(
                                "Tan W.G.",
                                "Barkman T.J.",
                                "Gregory Chinchar V.",
                                "Essani K.",
                                "Arctander P.",
                                "Fjeldsaa J.",
                                "Gjeldsaa J."));
        List<Author> authors = buildAuthors(authorsS);
        List<String> lines = builder.buildLine(authors, false, true);
        assertEquals(1, lines.size());
        String expected =
                "Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K., Arctander P., Fjeldsaa"
                    + " J., Gjeldsaa J.;";
        assertEquals(expected, lines.get(0));
    }

    private List<Author> buildAuthors(List<String> names) {
        List<Author> authors = new ArrayList<>();
        for (String name : names) {
            authors.add(new AuthorBuilder(name).build());
        }
        return authors;
    }
}
