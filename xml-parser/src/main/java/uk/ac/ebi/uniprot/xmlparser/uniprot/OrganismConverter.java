package uk.ac.ebi.uniprot.xmlparser.uniprot;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class OrganismConverter implements Converter<OrganismType, Organism> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;

	public OrganismConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public OrganismConverter(EvidenceIndexMapper evRefMapper,
			ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Organism fromXml(OrganismType xmlObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrganismType toXml(Organism uniObj) {
		// TODO Auto-generated method stub
		return null;
	}

}
