package uk.ac.ebi.uniprot.flatfile.parser;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniProtEntryIterator;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObjectConverterFactory;

public final class UniProtParserHelper {

    /**
     * Make shure this class will be nerver instanciated
     */
    private UniProtParserHelper() {
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

    private static final ThreadLocal<EntryObjectConverterFactory.EntryObjectConverter> entryObjectConverter =
            new ThreadLocal<EntryObjectConverterFactory.EntryObjectConverter>() {
                @Override
                protected EntryObjectConverterFactory.EntryObjectConverter initialValue() {
                    return new EntryObjectConverterFactory().createEntryObjectConverter("", "", "", "", true);
                }
            };

    private static final ThreadLocal<EntryObjectConverterFactory.EntryObjectConverter> entryObjectConverterIgnoreDR =
            new ThreadLocal<EntryObjectConverterFactory.EntryObjectConverter>() {
                @Override
                protected EntryObjectConverterFactory.EntryObjectConverter initialValue() {
                    return new EntryObjectConverterFactory().createEntryObjectConverter("", "", "", "",  true);
                }
            };

    public static UniProtEntry parse(String entryText) throws UniProtParserException {
        return parse(entryText, false);
    }

    public static UniProtEntry parse(String entryText, boolean ignoreWrongDR) {
        try {
            EntryObject parse = entryParser.get().parse(entryText);
            EntryObjectConverterFactory.EntryObjectConverter entryConverter = entryObjectConverter.get();
            if (ignoreWrongDR)
                entryConverter = entryObjectConverterIgnoreDR.get();
            UniProtEntry convert = entryConverter.convert(parse);
            return convert;
        } catch (ParseException e) {
            throw new UniProtParserException(e.getDetailedMessage());
        } catch (RuntimeException ee) {
            throw ee;
        }

    }
   
    public static UniProtEntryIterator parseFile(String filename, String keywordFile, String diseaseFile, 
    		String accessionGoPubmedFile, String subcellularLocationFile) {
            DefaultUniProtEntryIterator iterator =new DefaultUniProtEntryIterator();
            iterator.setIgnoreWrong(true);
    		iterator.setInput(filename, keywordFile, diseaseFile, accessionGoPubmedFile, subcellularLocationFile);
    		return iterator;
    }
}
