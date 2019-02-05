package uk.ac.ebi.uniprot.parser.gff.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

/**
 * Created 05/02/19
 *
 * @author Edd
 */
class UniProtGffParserTest {
    private static final String TEST_FILE_PATH = "/uniprotkb/entry/";

    @Test
    void validateOutputForQ8NDV7() {
        String entryPath = TEST_FILE_PATH + "Q8NDV7.dat";
        String gffPath = TEST_FILE_PATH + "Q8NDV7.gff";
        UniProtEntry entry = readEntry(entryPath);
        String entryGff = UniProtGffParser.convert(entry);

        String gffAsString = readEntryFromFile(gffPath)
                .orElseThrow(() -> new IllegalStateException("Could not read file: " + gffPath));
        System.out.println(entryGff);
        assertThat(entryGff, is(gffAsString));
    }

    @Test
    void validateOutputForP21802() {
        String entryPath = TEST_FILE_PATH + "P21802.dat";
        String gffPath = TEST_FILE_PATH + "P21802.gff";
        UniProtEntry entry = readEntry(entryPath);
        String entryGff = UniProtGffParser.convert(entry);

        String gffAsString = readEntryFromFile(gffPath)
                .orElseThrow(() -> new IllegalStateException("Could not read file: " + gffPath));
        System.out.println(entryGff);
        assertThat(entryGff, is(gffAsString));
    }

    private UniProtEntry readEntry(String path) {
        UniprotLineParser<EntryObject> entryParser = new DefaultUniprotLineParserFactory().createEntryParser();
        String entryAsString = readEntryFromFile(path)
                .orElseThrow(() -> new IllegalStateException("Could not read file: " + path));
        EntryObject parse = entryParser.parse(entryAsString);
        assertNotNull(parse);
        EntryObjectConverter entryObjectConverter = new EntryObjectConverter("", "", "", true);
        return entryObjectConverter.convert(parse);
    }

    private Optional<String> readEntryFromFile(String fileName) {
        URL resource = getClass().getResource(fileName);
        try {
            Path path = Paths.get(resource.toURI());
            return Optional.of(Files.lines(path).collect(Collectors.joining("\n")));
        } catch (URISyntaxException | IOException ex) {
            ex.printStackTrace();//handle exception here
        }
        return Optional.empty();
    }
}