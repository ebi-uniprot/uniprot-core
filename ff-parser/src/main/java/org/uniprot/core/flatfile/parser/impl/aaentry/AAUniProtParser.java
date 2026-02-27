package org.uniprot.core.flatfile.parser.impl.aaentry;

import org.uniprot.core.flatfile.parser.SupportingDataMap;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

/**
 *
 * @author jluo
 * @date: 30 Jan 2026
 *
*/

public class AAUniProtParser implements UniProtParser {

	private static final long serialVersionUID = 1L;
	private final UniprotKBLineParser<AAEntryObject> parser;
    private final AAEntryObjectConverter converter;

    public AAUniProtParser(SupportingDataMap supportingDataMap, boolean ignoreWrong) {
        this.parser = new DefaultAAUniProtKBLineParserFactory().createEntryParser();
        this.converter = new AAEntryObjectConverter(supportingDataMap, ignoreWrong);
    }

    @Override
    public UniProtKBEntry parse(String entryff) {
        AAEntryObject parse = parser.parse(entryff);
        return converter.convert(parse);
    }
}

