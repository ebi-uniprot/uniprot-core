package org.uniprot.core.uniprotkb.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.description.ProteinSection;
import org.uniprot.core.util.Utils;

public class ProteinSectionImpl implements ProteinSection {

    private static final long serialVersionUID = -8215290595530427991L;
    private ProteinName recommendedName;
    private List<ProteinName> alternativeNames;
    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames;
    private List<Name> innNames;

    // no arg constructor for JSON deserialization
    ProteinSectionImpl() {
        this.alternativeNames = Collections.emptyList();
        this.cdAntigenNames = Collections.emptyList();
        this.innNames = Collections.emptyList();
    }

    ProteinSectionImpl(
            ProteinName recommendedName,
            List<ProteinName> alternativeNames,
            Name allergenName,
            Name biotechName,
            List<Name> cdAntigenNames,
            List<Name> innNames) {

        this.recommendedName = recommendedName;
        this.alternativeNames = Utils.unmodifiableList(alternativeNames);
        this.allergenName = allergenName;
        this.biotechName = biotechName;
        this.cdAntigenNames = Utils.unmodifiableList(cdAntigenNames);
        this.innNames = Utils.unmodifiableList(innNames);
    }

    @Override
    public ProteinName getRecommendedName() {
        return recommendedName;
    }

    @Override
    public List<ProteinName> getAlternativeNames() {
        return alternativeNames;
    }

    @Override
    public boolean hasRecommendedName() {
        return this.recommendedName != null;
    }

    @Override
    public boolean hasAlternativeNames() {
        return Utils.notNullNotEmpty(this.alternativeNames);
    }

    @Override
    public boolean hasAllergenName() {
        return this.allergenName != null && this.allergenName.hasValue();
    }

    @Override
    public boolean hasBiotechName() {
        return this.biotechName != null && this.biotechName.hasValue();
    }

    @Override
    public boolean hasCdAntigenNames() {
        return Utils.notNullNotEmpty(this.cdAntigenNames);
    }

    @Override
    public boolean hasInnNames() {
        return Utils.notNullNotEmpty(this.innNames);
    }

    @Override
    public Name getAllergenName() {
        return allergenName;
    }

    @Override
    public Name getBiotechName() {
        return biotechName;
    }

    @Override
    public List<Name> getCdAntigenNames() {
        return this.cdAntigenNames;
    }

    @Override
    public List<Name> getInnNames() {
        return this.innNames;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
        result = prime * result + alternativeNames.hashCode();
        result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
        result = prime * result + cdAntigenNames.hashCode();
        result = prime * result + innNames.hashCode();
        result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinSectionImpl other = (ProteinSectionImpl) obj;
        if (allergenName == null) {
            if (other.allergenName != null) return false;
        } else if (!allergenName.equals(other.allergenName)) return false;
        if (!alternativeNames.equals(other.alternativeNames)) return false;
        if (biotechName == null) {
            if (other.biotechName != null) return false;
        } else if (!biotechName.equals(other.biotechName)) return false;
        if (!cdAntigenNames.equals(other.cdAntigenNames)) return false;
        if (!innNames.equals(other.innNames)) return false;
        if (recommendedName == null) {
            return other.recommendedName == null;
        } else return recommendedName.equals(other.recommendedName);
    }
}
