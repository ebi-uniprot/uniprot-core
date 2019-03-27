package uk.ac.ebi.uniprot.xml.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismBuilder;
import uk.ac.ebi.uniprot.xml.Converter;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;

public class OrganismConverter implements Converter<OrganismType, Organism> {
	private final EvidenceIndexMapper evRefMapper;
	private final ObjectFactory xmlUniprotFactory;

	public OrganismConverter(EvidenceIndexMapper evRefMapper) {
		this(evRefMapper, new ObjectFactory());
	}

	public OrganismConverter(EvidenceIndexMapper evRefMapper, ObjectFactory xmlUniprotFactory) {
		this.evRefMapper = evRefMapper;
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public Organism fromXml(OrganismType xmlObj) {
		OrganismBuilder builder = new OrganismBuilder();
		builder.taxonId(Long.parseLong(xmlObj.getDbReference().get(0).getId()));
		OrganismConverterUtil.updateOrganismNameFromXml(xmlObj.getName(), builder);
		builder.lineage(xmlObj.getLineage().getTaxon());
		builder.evidences(evRefMapper.parseEvidenceIds(xmlObj.getEvidence()));
		return builder.build();
	}

	@Override
	public OrganismType toXml(Organism organism) {
		OrganismType xmlOrganism = xmlUniprotFactory.createOrganismType();
		xmlOrganism.getDbReference()
				.add(OrganismConverterUtil.taxonIdToXmlDbRef(xmlUniprotFactory, organism.getTaxonId()));
		xmlOrganism.getName().addAll(OrganismConverterUtil.organismNameToXml(xmlUniprotFactory, organism));

		OrganismType.Lineage lineage = xmlUniprotFactory.createOrganismTypeLineage();
		organism.getLineage().forEach(val -> lineage.getTaxon().add(val));
		xmlOrganism.setLineage(lineage);
		if (!organism.getEvidences().isEmpty()) {
			List<Integer> ev = evRefMapper.writeEvidences(organism.getEvidences());
			if (!ev.isEmpty())
				xmlOrganism.getEvidence().addAll(ev);
		}
		return xmlOrganism;
	}

}
