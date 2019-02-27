package uk.ac.ebi.uniprot.flatfile.parser.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.flatfile.parser.UniProtParser;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.entry.EntryObjectConverter;

public class DefaultUniProtParser implements UniProtParser {
	private final UniprotLineParser<EntryObject> parser;
	private final EntryObjectConverter converter;

	private static DefaultUniProtParser instance;

	public static DefaultUniProtParser getInstance(String keywordFile, String diseaseFile,
												   String accessionGoPubmedFile, String subcellularLocationFile,  boolean ignoreWrongDr){
		if(instance == null){
			instance = new DefaultUniProtParser(keywordFile,diseaseFile,accessionGoPubmedFile,subcellularLocationFile,ignoreWrongDr);
		}
		return instance;
	}

	public DefaultUniProtParser(String keywordFile, String diseaseFile, 
			String accessionGoPubmedFile, String subcellularLocationFile,  boolean ignoreWrongDr) {
		this.parser = new DefaultUniprotLineParserFactory().createEntryParser();
		this.converter = EntryObjectConverter.getInstance(keywordFile, diseaseFile, accessionGoPubmedFile, subcellularLocationFile, ignoreWrongDr);
		
	};
	@Override
	public UniProtEntry parse(String entryff) {
		EntryObject parse = parser.parse(entryff);
		return converter.convert(parse);
	}

}
