package uk.ac.ebi.uniprot.flatfile.parser.impl.os;



import org.uniprot.core.uniprot.taxonomy.OrganismName;

import uk.ac.ebi.uniprot.flatfile.parser.Converter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.OrganismNameLineParser;

public class OsLineConverter implements Converter<OsLineObject, OrganismName> {

	@Override
	public OrganismName convert(OsLineObject f) {
		String value = f.organism_species;
		return OrganismNameLineParser.createFromOrganismLine(value);
	}

}
