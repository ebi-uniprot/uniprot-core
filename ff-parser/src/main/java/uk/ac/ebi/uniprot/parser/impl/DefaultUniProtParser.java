package uk.ac.ebi.uniprot.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.UniProtParser;
import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.parser.impl.entry.EntryObjectConverter;

public class DefaultUniProtParser implements UniProtParser {
	private final UniprotLineParser<EntryObject> parser;
	private final EntryObjectConverter converter;
	public DefaultUniProtParser(String keywordFile, String diseaseFile, 
			String accessionGoPubmedFile, boolean ignoreWrongDr) {
		this.parser = new DefaultUniprotLineParserFactory().createEntryParser();
		this.converter = new EntryObjectConverter(keywordFile, diseaseFile, accessionGoPubmedFile, ignoreWrongDr);
		
	};
	@Override
	public UniProtEntry parse(String entryff) {
		EntryObject parse = parser.parse(entryff);
		return converter.convert(parse);
	}

}
