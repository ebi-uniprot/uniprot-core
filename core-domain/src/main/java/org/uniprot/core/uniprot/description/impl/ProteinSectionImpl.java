package org.uniprot.core.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.util.Utils;

public class ProteinSectionImpl implements ProteinSection {

    private static final long serialVersionUID = -8215290595530427991L;
    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames;
    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames;
    private List<Name> innNames;

    private ProteinSectionImpl() {
        this.alternativeNames = Collections.emptyList();
        this.cdAntigenNames = Collections.emptyList();
        this.innNames = Collections.emptyList();
    }

    public ProteinSectionImpl(
            ProteinRecName recommendedName,
            List<ProteinAltName> alternativeNames,
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
    public ProteinRecName getRecommendedName() {
        return recommendedName;
    }

    @Override
    public List<ProteinAltName> getAlternativeNames() {
        return alternativeNames;
    }

    @Override
    public boolean hasRecommendedName() {
        return this.recommendedName != null;
    }

    @Override
    public boolean hasAlternativeNames() {
        return Utils.notNullOrEmpty(this.alternativeNames);
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
        return Utils.notNullOrEmpty(this.cdAntigenNames);
    }

    @Override
    public boolean hasInnNames() {
        return Utils.notNullOrEmpty(this.innNames);
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
        result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
        result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
        result = prime * result + ((cdAntigenNames == null) ? 0 : cdAntigenNames.hashCode());
        result = prime * result + ((innNames == null) ? 0 : innNames.hashCode());
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
        if (alternativeNames == null) {
            if (other.alternativeNames != null) return false;
        } else if (!alternativeNames.equals(other.alternativeNames)) return false;
        if (biotechName == null) {
            if (other.biotechName != null) return false;
        } else if (!biotechName.equals(other.biotechName)) return false;
        if (cdAntigenNames == null) {
            if (other.cdAntigenNames != null) return false;
        } else if (!cdAntigenNames.equals(other.cdAntigenNames)) return false;
        if (innNames == null) {
            if (other.innNames != null) return false;
        } else if (!innNames.equals(other.innNames)) return false;
        if (recommendedName == null) {
            if (other.recommendedName != null) return false;
        } else if (!recommendedName.equals(other.recommendedName)) return false;
        return true;
    }
}
