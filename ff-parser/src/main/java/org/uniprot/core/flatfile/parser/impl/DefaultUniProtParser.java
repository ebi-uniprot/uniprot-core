package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprot.UniProtEntry;

public class DefaultUniProtParser implements UniProtParser {
    private final UniprotLineParser<EntryObject> parser;
    private final EntryObjectConverter converter;

    public DefaultUniProtParser(SupportingDataMap supportingDataMap, boolean ignoreWrongDr) {
        this.parser = new DefaultUniprotLineParserFactory().createEntryParser();
        this.converter = new EntryObjectConverter(supportingDataMap, ignoreWrongDr);
    };

    @Override
    public UniProtEntry parse(String entryff) {
        EntryObject parse = parser.parse(entryff);
        return converter.convert(parse);
    }
}
