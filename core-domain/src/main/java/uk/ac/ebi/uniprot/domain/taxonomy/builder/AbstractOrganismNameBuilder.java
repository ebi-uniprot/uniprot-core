package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public abstract class AbstractOrganismNameBuilder<B extends AbstractOrganismNameBuilder<B, T>, T extends OrganismName>
        implements Builder2<B, T> {

    private String scientificName = "";
    private String commonName = "";
    private List<String> synonyms = new ArrayList<>();

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
