package uk.ac.ebi.uniprot.parser.impl.oc;

import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.parser.Converter;


public class OcLineConverter implements Converter<OcLineObject,  List<OrganismName>> {
	@Override
	public List<OrganismName> convert(OcLineObject f) {
		return f.nodes.stream().map(val -> TaxonomyFactory.INSTANCE.createOrganismName(val))
				.collect(Collectors.toList());
		
	}
	

}
