package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;

import java.util.List;
import java.util.stream.Collectors;

public class OrganismImpl extends TaxonImpl implements Organism {

    public OrganismImpl(long taxonId, String scientificName, String commonName, List<String> synonyms) {
        super(taxonId, scientificName, commonName, synonyms);
       
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getScientificName());
        String commonName = getCommonName();
        if ((commonName != null) && !commonName.isEmpty()) {
            sb.append(" (")
                    .append(commonName).append(")");
        }
        List<String> synonyms = this.getSynonyms();
        if (!synonyms.isEmpty()) {
            sb.append(" (")
                    .append(synonyms.stream().collect(Collectors.joining(", ")))
                    .append(")");
        }
        return sb.toString();
    }

}
