package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class CitationConverter implements Converter<CitationType, Citation> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	public static final String GENENAME_XMLTAG = "primary";
	public static final String SYNONYM_XMLTAG = "synonym";
	public static final String ORF_XMLTAG = "ORF";
	public static final String OLN_XMLTAG = "ordered locus";

	public CitationConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public CitationConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Citation fromXml(CitationType xmlObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CitationType toXml(Citation uniObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
