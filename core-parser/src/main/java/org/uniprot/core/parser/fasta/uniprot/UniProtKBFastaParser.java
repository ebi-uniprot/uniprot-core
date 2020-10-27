package org.uniprot.core.parser.fasta.uniprot;

import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

public class UniProtKBFastaParser {

    private UniProtKBFastaParser() {}

    public static String toFasta(UniProtKBEntry entry) {
        return UniProtKBFastaParserWriter.parse(entry);
    }

    public static String toFasta(UniProtKBFasta entry) {
        return UniProtKBFastaParserWriter.parse(entry);
    }

    public static UniProtKBFasta fromFasta(String fastaInput) {
        return UniProtKBFastaParserReader.parse(fastaInput);
    }
}
