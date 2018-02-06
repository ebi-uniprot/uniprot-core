package uk.ac.ebi.uniprot.parser.impl.os;


import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.uniprot.factory.OrganismFactory;
import uk.ac.ebi.uniprot.parser.Converter;

public class OsLineConverter implements Converter<OsLineObject, Organism> {

	@Override
	public Organism convert(OsLineObject f) {
		String value = f.organism_species;
		return OrganismFactory.INSTANCE.createFromOrganismLine(value);
	}

}
