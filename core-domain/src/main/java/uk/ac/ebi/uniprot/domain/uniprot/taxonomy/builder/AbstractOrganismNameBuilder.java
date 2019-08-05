package uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.OrganismName;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOrganismNameBuilder<B extends AbstractOrganismNameBuilder<B, T>, T extends OrganismName>
        implements Builder<B, T> {
    protected String scientificName = "";
    protected String commonName = "";
    protected List<String> synonyms = new ArrayList<>();

    protected abstract B getThis();

    public B scientificName(String scientificName) {
        this.scientificName = scientificName;
        return getThis();
    }

    public B commonName(String commonName) {
        this.commonName = commonName;
        return getThis();
    }

    public B synonyms(List<String> synonyms) {
        this.synonyms = nonNullList(synonyms);
        return getThis();
    }

    public B addSynonyms(String synonym) {
        nonNullAdd(synonym, this.synonyms);
        return getThis();
    }

    public String getScientificName() {
        return scientificName;
    }

    public String getCommonName() {
        return commonName;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public B from(OrganismName organismName) {
        this.scientificName = organismName.getScientificName();
        this.commonName = organismName.getCommonName();
        this.synonyms = nonNullList(organismName.getSynonyms());
        return getThis();
    }
}
