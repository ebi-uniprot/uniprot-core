package uk.ac.ebi.uniprot.xmlparser.uniprot;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;
import uk.ac.ebi.uniprot.xmlparser.Converter;

public class OrganismHostConverter  implements Converter<OrganismType, OrganismHost> {
	private final ObjectFactory xmlUniprotFactory;

	public OrganismHostConverter() {
		this( new ObjectFactory());
	}

	public OrganismHostConverter( ObjectFactory xmlUniprotFactory) {
		this.xmlUniprotFactory = xmlUniprotFactory;
	}

	@Override
	public OrganismHost fromXml(OrganismType xmlObj) {
		OrganismHostBuilder builder = new OrganismHostBuilder();
		builder.taxonId(Long.parseLong(xmlObj.getDbReference().get(0).getId()));
		OrganismConverterUtil.updateOrganismNameFromXml(xmlObj.getName(), builder);
		return builder.build();
	}

	@Override
	public OrganismType toXml(OrganismHost organism) {
		OrganismType xmlOrganism = xmlUniprotFactory.createOrganismType();
		xmlOrganism.getDbReference()
				.add(OrganismConverterUtil.taxonIdToXmlDbRef(xmlUniprotFactory, organism.getTaxonId()));
		xmlOrganism.getName().addAll(OrganismConverterUtil.organismNameToXml(xmlUniprotFactory, organism));		
		return xmlOrganism;
	}

}
