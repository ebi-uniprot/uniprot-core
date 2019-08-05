package uk.ac.ebi.uniprot.flatfile.parser.impl;

import org.uniprot.core.uniprot.UniProtEntry;

import uk.ac.ebi.uniprot.flatfile.parser.SupportingDataMap;
import uk.ac.ebi.uniprot.flatfile.parser.UniProtParser;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObjectConverter;

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
