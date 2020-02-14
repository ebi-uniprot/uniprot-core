package org.uniprot.core.flatfile.parser.impl.os;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.uniprot.taxonomy.OrganismName;

public class OsLineConverter implements Converter<OsLineObject, OrganismName> {

    @Override
    public OrganismName convert(OsLineObject f) {
        String value = f.getOrganismSpecies();
        return OrganismNameLineParser.createFromOrganismLine(value);
    }
}
