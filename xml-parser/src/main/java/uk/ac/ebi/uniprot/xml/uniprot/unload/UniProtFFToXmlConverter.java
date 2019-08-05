package uk.ac.ebi.uniprot.xml.uniprot.unload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.uniprot.UniProtEntry;

import uk.ac.ebi.uniprot.flatfile.parser.UniProtParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniProtParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.SupportingDataMapImpl;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.Entry;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtEntryConverter;

import java.util.function.Function;



public class UniProtFFToXmlConverter implements Function<String, Entry> {
	 private static final Logger LOGGER = LoggerFactory.getLogger(UniProtFFToXmlConverter.class);
	    private final UniProtParser  ffParser;
	    private final UniProtEntryConverter xmlConverter;
	    public UniProtFFToXmlConverter(String diseaseFile , String keywordFile) {
		      this.ffParser =new DefaultUniProtParser( new SupportingDataMapImpl(keywordFile,  diseaseFile,
		  			"", ""), true);
		      this.xmlConverter = new UniProtEntryConverter();
		    }

	    public UniProtFFToXmlConverter(UniProtParser ffParser, UniProtEntryConverter xmlConverter) {
	      this.ffParser =ffParser;
	      this.xmlConverter = xmlConverter;
	    }

	@Override
	public Entry apply(String t) {
		UniProtEntry uniprotEntry = ffParser.parse(t);
		if(uniprotEntry !=null)
			return xmlConverter.toXml(uniprotEntry);
		else
			return null;
	}

}
