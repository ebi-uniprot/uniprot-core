package org.uniprot.core.flatfile.parser.impl.oh;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.parser.Converter;
import org.uniprot.core.flatfile.parser.impl.OrganismNameLineParser;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.taxonomy.OrganismName;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismHostBuilder;

public class OhLineConverter implements Converter<OhLineObject, List<OrganismHost>> {

    @Override
    public List<OrganismHost> convert(OhLineObject f) {
        List<OrganismHost> hosts = new ArrayList<>();
        for (OhLineObject.OhValue oh : f.getHosts()) {
            OrganismName organismName =
                    OrganismNameLineParser.createFromOrganismLine(oh.getHostname());
            OrganismHostBuilder organismHostBuilder =
                    new OrganismHostBuilder()
                            .taxonId(oh.getTax_id())
                            .scientificName(organismName.getScientificName())
                            .commonName(organismName.getCommonName());
            if (organismName.getSynonyms() != null) {
                organismHostBuilder.synonymsSet(organismName.getSynonyms());
            }
            hosts.add(organismHostBuilder.build());
        }
        return hosts;
    }
}
