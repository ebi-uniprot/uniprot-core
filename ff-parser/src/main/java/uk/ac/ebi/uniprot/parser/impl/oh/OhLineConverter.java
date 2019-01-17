package uk.ac.ebi.uniprot.parser.impl.oh;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.parser.Converter;
import uk.ac.ebi.uniprot.parser.impl.OrganismNameLineParser;

import java.util.ArrayList;
import java.util.List;


public class OhLineConverter implements Converter<OhLineObject, List<OrganismHost> > {

	@Override
	public List<OrganismHost> convert(OhLineObject f) {
		List<OrganismHost> hosts =new ArrayList<>();
		for(OhLineObject.OhValue oh: f.hosts){
			OrganismName organismName = OrganismNameLineParser.createFromOrganismLine(oh.hostname);
			OrganismHostBuilder organismHostBuilder = new OrganismHostBuilder()
					.taxonId(oh.tax_id)
					.scientificName(organismName.getScientificName())
					.commonName(organismName.getCommonName());
			if(organismName.getSynonyms() != null){
				organismHostBuilder.synonyms(organismName.getSynonyms());
			}
			hosts.add(organismHostBuilder.build());
		}
		return hosts;
	}

}
