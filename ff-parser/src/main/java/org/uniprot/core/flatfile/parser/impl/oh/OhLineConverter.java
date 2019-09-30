package org.uniprot.core.flatfile.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismHostBuilder;

public class OhLineConverter implements Converter<OhLineObject, List<OrganismHost>> {

    @Override
    public List<OrganismHost> convert(OhLineObject f) {
        List<OrganismHost> hosts = new ArrayList<>();
        for (OhLineObject.OhValue oh : f.hosts) {
            OrganismName organismName = OrganismNameLineParser.createFromOrganismLine(oh.hostname);
            OrganismHostBuilder organismHostBuilder =
                    new OrganismHostBuilder()
                            .taxonId(oh.tax_id)
                            .scientificName(organismName.getScientificName())
                            .commonName(organismName.getCommonName());
            if (organismName.getSynonyms() != null) {
                organismHostBuilder.synonyms(organismName.getSynonyms());
            }
            hosts.add(organismHostBuilder.build());
        }
        return hosts;
    }
}
