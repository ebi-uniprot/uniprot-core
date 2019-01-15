package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;

public class ReferenceConverter  implements Converter<ReferenceType, UniProtReference> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;
	public static final String GENENAME_XMLTAG = "primary";
	public static final String SYNONYM_XMLTAG = "synonym";
	public static final String ORF_XMLTAG = "ORF";
	public static final String OLN_XMLTAG = "ordered locus";

	public ReferenceConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public ReferenceConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public UniProtReference fromXml(ReferenceType xmlObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReferenceType toXml(UniProtReference uniObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
