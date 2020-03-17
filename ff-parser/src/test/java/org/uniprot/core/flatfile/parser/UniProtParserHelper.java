package org.uniprot.core.flatfile.parser;

import org.uniprot.core.flatfile.parser.impl.DefaultUniProtEntryIterator;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

public final class UniProtParserHelper {

    /** Make shure this class will be nerver instanciated */
    private UniProtParserHelper() {}

    /**
     * <em class="text"> Parses the String representation of a SWISS-PROT or TrEMBL flat file. The
     * {@link UniProtKBEntry UniProtKBEntry} parameter determines the parsing behaviour. </em> <br>
     * <br>
     *
     * @param factory The Factory instance used for creating objects of the UniProtKBEntry and other
     *     related interfaces.
     * @param entryText The String representation of a SWISS-PROT or TrEMBL flat file
     * @throws org.uniprot.core.flatfile.parser.UniProtParserException if the entry could not be
     *     parsed
     * @see UniProtKBEntry UniProtKBEntry
     */
    private static final ThreadLocal<UniprotKBLineParser<EntryObject>> entryParser =
            new ThreadLocal<UniprotKBLineParser<EntryObject>>() {
                @Override
                protected UniprotKBLineParser<EntryObject> initialValue() {
                    return new DefaultUniprotKBLineParserFactory().createEntryParser();
                }
            };

    private static final ThreadLocal<EntryObjectConverter> entryObjectConverter =
            new ThreadLocal<EntryObjectConverter>() {
                @Override
                protected EntryObjectConverter initialValue() {
                    return new EntryObjectConverter(new SupportingDataMapImpl(), true);
                }
            };

    private static final ThreadLocal<EntryObjectConverter> entryObjectConverterIgnoreDR =
            new ThreadLocal<EntryObjectConverter>() {
                @Override
                protected EntryObjectConverter initialValue() {
                    return new EntryObjectConverter(new SupportingDataMapImpl(), true);
                }
            };

    public static UniProtKBEntry parse(String entryText) throws UniProtParserException {
        return parse(entryText, false);
    }

    public static UniProtKBEntry parse(String entryText, boolean ignoreWrongDR) {
        try {
            EntryObject parse = entryParser.get().parse(entryText);
            EntryObjectConverter entryConverter = entryObjectConverter.get();
            if (ignoreWrongDR) entryConverter = entryObjectConverterIgnoreDR.get();
            UniProtKBEntry convert = entryConverter.convert(parse);
            return convert;
        } catch (ParseException e) {
            throw new UniProtParserException(e.getDetailedMessage());
        } catch (RuntimeException ee) {
            throw ee;
        }
    }

    public static UniProtEntryIterator parseFile(
            String filename,
            String keywordFile,
            String diseaseFile,
            String accessionGoPubmedFile,
            String subcellularLocationFile) {
        DefaultUniProtEntryIterator iterator = new DefaultUniProtEntryIterator();
        iterator.setIgnoreWrong(true);
        iterator.setInput(
                filename, keywordFile, diseaseFile, accessionGoPubmedFile, subcellularLocationFile);
        return iterator;
    }
}
