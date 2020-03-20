package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

public class DefaultUniProtParser implements UniProtParser {
    private final UniprotKBLineParser<EntryObject> parser;
    private final EntryObjectConverter converter;

    public DefaultUniProtParser(SupportingDataMap supportingDataMap, boolean ignoreWrongDr) {
        this.parser = new DefaultUniprotKBLineParserFactory().createEntryParser();
        this.converter = new EntryObjectConverter(supportingDataMap, ignoreWrongDr);
    };

    @Override
    public UniProtKBEntry parse(String entryff) {
        EntryObject parse = parser.parse(entryff);
        return converter.convert(parse);
    }
}
