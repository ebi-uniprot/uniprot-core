package org.uniprot.core.uniprot.taxonomy.impl;

import static org.uniprot.core.util.Utils.emptyOrString;
import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.taxonomy.OrganismName;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public abstract class AbstractOrganismNameImpl implements OrganismName {

    private static final long serialVersionUID = -4054471655650420667L;
    private String scientificName;
    private String commonName;
    private List<String> synonyms;

    AbstractOrganismNameImpl() {}

    public AbstractOrganismNameImpl(
            String scientificName, String commonName, List<String> synonyms) {
        this.scientificName = emptyOrString(scientificName);
        this.commonName = emptyOrString(commonName);
        this.synonyms = unmodifiableList(synonyms);
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

    public boolean hasScientificName() {
        return Utils.notNullOrEmpty(this.scientificName);
    }

    public boolean hasCommonName() {
        return Utils.notNullOrEmpty(this.commonName);
    }

    public boolean hasSynonyms() {
        return Utils.notNullOrEmpty(this.synonyms);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractOrganismNameImpl that = (AbstractOrganismNameImpl) o;
        return Objects.equals(scientificName, that.scientificName)
                && Objects.equals(commonName, that.commonName)
                && Objects.equals(synonyms, that.synonyms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scientificName, commonName, synonyms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getScientificName());
        String commonName = getCommonName();
        if (commonName != null && !commonName.isEmpty()) {
            sb.append(" (").append(commonName).append(")");
        }
        List<String> synonyms = this.getSynonyms();
        if (!synonyms.isEmpty()) {
            sb.append(" (").append(String.join(", ", synonyms)).append(")");
        }
        return sb.toString();
    }
}
