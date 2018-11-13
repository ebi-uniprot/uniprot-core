package uk.ac.ebi.uniprot.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.factory.OrganismFactory;
import uk.ac.ebi.uniprot.parser.Converter;


public class OhLineConverter implements Converter<OhLineObject, List<OrganismHost> > {
	//private DefaultUniProtFactory factory =DefaultUniProtFactory.getInstance();
	@Override
	public List<OrganismHost> convert(OhLineObject f) {
		List<OrganismHost> hosts =new ArrayList<>();
		for(OhLineObject.OhValue oh: f.hosts){
			OrganismName organism = OrganismFactory.INSTANCE.createFromOrganismLine(oh.hostname);
			OrganismHost organismHost = OrganismFactory.INSTANCE.createOrganismHost(oh.tax_id, organism);
			hosts.add(organismHost);
		}
		return hosts;
	}

}
