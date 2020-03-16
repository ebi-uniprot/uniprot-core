package org.uniprot.core.flatfile.parser.impl;

import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.UniprotkbLineParser;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObjectConverter;
import org.uniprot.core.uniprotkb.UniProtkbEntry;

public class DefaultUniProtParser implements UniProtParser {
    private final UniprotkbLineParser<EntryObject> parser;
    private final EntryObjectConverter converter;

    public DefaultUniProtParser(SupportingDataMap supportingDataMap, boolean ignoreWrongDr) {
        this.parser = new DefaultUniprotkbLineParserFactory().createEntryParser();
        this.converter = new EntryObjectConverter(supportingDataMap, ignoreWrongDr);
    };

    @Override
    public UniProtkbEntry parse(String entryff) {
        EntryObject parse = parser.parse(entryff);
        return converter.convert(parse);
    }
}
