package org.uniprot.core.xml.uniprot.unload;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.flatfile.parser.UniProtParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.xml.jaxb.uniprot.Entry;
import org.uniprot.core.xml.uniprot.UniProtEntryConverter;

public class UniProtFFToXmlConverter implements Function<String, Entry> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UniProtFFToXmlConverter.class);
    private final UniProtParser ffParser;
    private final UniProtEntryConverter xmlConverter;

    public UniProtFFToXmlConverter(String diseaseFile, String keywordFile) {
        this.ffParser =
                new DefaultUniProtParser(
                        new SupportingDataMapImpl(keywordFile, diseaseFile, "", ""), true);
        this.xmlConverter = new UniProtEntryConverter();
    }

    public UniProtFFToXmlConverter(UniProtParser ffParser, UniProtEntryConverter xmlConverter) {
        this.ffParser = ffParser;
        this.xmlConverter = xmlConverter;
    }

    @Override
    public Entry apply(String t) {
        UniProtKBEntry uniprotEntry = ffParser.parse(t);
        if (uniprotEntry != null) return xmlConverter.toXml(uniprotEntry);
        else return null;
    }
}
