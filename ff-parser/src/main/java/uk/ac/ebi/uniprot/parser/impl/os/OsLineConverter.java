package uk.ac.ebi.uniprot.parser.impl.os;


import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OsLineConverter implements Converter<OsLineObject, OrganismName> {

	@Override
	public OrganismName convert(OsLineObject f) {
		String value = f.organism_species;
		return TaxonomyFactory.INSTANCE.createFromOrganismLine(value);
	}

}
