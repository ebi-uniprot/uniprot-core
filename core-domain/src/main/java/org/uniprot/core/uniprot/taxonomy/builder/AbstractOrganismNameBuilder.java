package org.uniprot.core.uniprot.taxonomy.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.taxonomy.OrganismName;

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
        this.synonyms = modifiableList(synonyms);
        return getThis();
    }

    public B addSynonyms(String synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
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
        this.synonyms = modifiableList(organismName.getSynonyms());
        return getThis();
    }
}
