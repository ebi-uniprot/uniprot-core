package uk.ac.ebi.uniprot.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;


public class OhLineConverter implements Converter<OhLineObject, List<Organism> > {
	//private DefaultUniProtFactory factory =DefaultUniProtFactory.getInstance();
	@Override
	public List<Organism> convert(OhLineObject f) {
		List<Organism> hosts =new ArrayList<>();
		for(OhLineObject.OhValue oh: f.hosts){
			OrganismName organismName = TaxonomyFactory.INSTANCE.createFromOrganismLine(oh.hostname);
			Organism organismHost = TaxonomyFactory.INSTANCE.createOrganism(organismName, oh.tax_id);
			hosts.add(organismHost);
		}
		return hosts;
	}

}
