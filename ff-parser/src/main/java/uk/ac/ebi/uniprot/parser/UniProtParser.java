package uk.ac.ebi.uniprot.parser;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.ParseException;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

import java.io.*;
import java.net.URL;
import java.util.Iterator;

public final class UniProtParser {

    /**
     * Make shure this class will be nerver instanciated
     */
    private UniProtParser() {
    }

    /**
     * <em class="text">
     * Parses the String representation of a SWISS-PROT or TrEMBL flat file.
     * The {@link UniProtEntry UniProtEntry} parameter determines the parsing behaviour.
     * </em>
     * <br><br>
     *
     * @param factory     The Factory instance used for creating objects of the
     * UniProtEntry and other related interfaces.
     * @param entryText The String representation of a SWISS-PROT or TrEMBL flat file
     * @throws UniProtParserException if the entry could not be parsed
     * @see UniProtEntry UniProtEntry
     */
    private static final ThreadLocal<UniprotLineParser<EntryObject>> entryParser =
            new ThreadLocal<UniprotLineParser<EntryObject>>() {
                @Override
                protected UniprotLineParser<EntryObject> initialValue() {
                    return new DefaultUniprotLineParserFactory().createEntryParser();
                }
            };

    private static final ThreadLocal<EntryObjectConverter> entryObjectConverter =
            new ThreadLocal<EntryObjectConverter>() {
                @Override
                protected EntryObjectConverter initialValue() {
                    return new EntryObjectConverter();
                }
            };

    private static final ThreadLocal<EntryObjectConverter> entryObjectConverterIgnoreDR =
            new ThreadLocal<EntryObjectConverter>() {
                @Override
                protected EntryObjectConverter initialValue() {
                    return new EntryObjectConverter(true);
                }
            };

    public static UniProtEntry parse(String entryText) throws UniProtParserException {
        return parse(entryText, false);
    }

    public static UniProtEntry parse(String entryText, boolean ignoreWrongDR) throws UniProtParserException {
        //	 EntryObjectConverter entryObjectConverter = new EntryObjectConverter();
        //	 EntryObjectConverter entryObjectConverterIgnoreDR = new EntryObjectConverter(true);
        try {
            EntryObject parse = entryParser.get().parse(entryText);
            EntryObjectConverter entryConverter = entryObjectConverter.get();
            if (ignoreWrongDR)
                entryConverter = entryObjectConverterIgnoreDR.get();
            UniProtEntry convert = entryConverter.convert(parse);
            return convert;
        } catch (ParseException e) {
            System.err.println(entryText);
            throw new UniProtParserException(e.getDetailedMessage());
        } catch (RuntimeException ee) {
            System.err.println(entryText);
            throw ee;
        }

    }

}
