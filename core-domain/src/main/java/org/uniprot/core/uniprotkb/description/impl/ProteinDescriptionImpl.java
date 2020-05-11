package org.uniprot.core.uniprotkb.description.impl;

import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;

public class ProteinDescriptionImpl implements ProteinDescription {

    private static final long serialVersionUID = 6554940064782470199L;
    private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames;
    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames;
    private List<Name> innNames;
    private List<ProteinSubName> submissionNames;
    private List<ProteinSection> includes; // dmain
    private List<ProteinSection> contains; // component
    private Flag flag;

    // no arg constructor for JSON deserialization
    ProteinDescriptionImpl() {
        this.alternativeNames = Collections.emptyList();
        this.submissionNames = Collections.emptyList();
        this.cdAntigenNames = Collections.emptyList();
        this.innNames = Collections.emptyList();
        this.includes = Collections.emptyList();
        this.contains = Collections.emptyList();
    }

    ProteinDescriptionImpl(
            ProteinRecName recommendedName,
            List<ProteinAltName> alternativeNames,
            Name allergenName,
            Name biotechName,
            List<Name> cdAntigenNames,
            List<Name> innNames,
            List<ProteinSubName> submissionNames,
            Flag flag,
            List<ProteinSection> includes,
            List<ProteinSection> contains) {
        this.recommendedName = recommendedName;
        this.alternativeNames = Utils.unmodifiableList(alternativeNames);
        this.submissionNames = Utils.unmodifiableList(submissionNames);
        this.allergenName = allergenName;
        this.biotechName = biotechName;
        this.cdAntigenNames = Utils.unmodifiableList(cdAntigenNames);
        this.innNames = Utils.unmodifiableList(innNames);
        this.flag = flag;
        this.includes = Utils.unmodifiableList(includes);
        this.contains = Utils.unmodifiableList(contains);
    }

    @Override
    public ProteinRecName getRecommendedName() {
        return recommendedName;
    }

    @Override
    public List<ProteinSubName> getSubmissionNames() {
        return submissionNames;
    }

    @Override
    public List<ProteinAltName> getAlternativeNames() {
        return alternativeNames;
    }

    @Override
    public List<ProteinSection> getIncludes() {
        return includes;
    }

    @Override
    public List<ProteinSection> getContains() {
        return contains;
    }

    @Override
    public boolean hasRecommendedName() {
        return (this.recommendedName != null) && this.recommendedName.isValid();
    }

    @Override
    public boolean hasAlternativeNames() {
        return Utils.notNullNotEmpty(this.alternativeNames);
    }

    @Override
    public boolean hasSubmissionNames() {
        return Utils.notNullNotEmpty(this.submissionNames);
    }

    @Override
    public boolean hasIncludes() {
        return Utils.notNullNotEmpty(this.includes);
    }

    @Override
    public boolean hasContains() {
        return Utils.notNullNotEmpty(this.contains);
    }

    @Override
    public boolean hasFlag() {
        return this.flag != null;
    }

    @Override
    public Flag getFlag() {
        return flag;
    }

    @Override
    public Name getAllergenName() {
        return this.allergenName;
    }

    @Override
    public Name getBiotechName() {
        return this.biotechName;
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
    public boolean hasAllergenName() {
        return (allergenName != null) && allergenName.isValid();
    }

    @Override
    public boolean hasBiotechName() {
        return (biotechName != null) && biotechName.isValid();
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
        result = prime * result + alternativeNames.hashCode();
        result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
        result = prime * result + cdAntigenNames.hashCode();
        result = prime * result + contains.hashCode();
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + includes.hashCode();
        result = prime * result + innNames.hashCode();
        result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
        result = prime * result + submissionNames.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteinDescriptionImpl other = (ProteinDescriptionImpl) obj;
        if (allergenName == null) {
            if (other.allergenName != null) return false;
        } else if (!allergenName.equals(other.allergenName)) return false;
        if (!alternativeNames.equals(other.alternativeNames)) return false;
        if (biotechName == null) {
            if (other.biotechName != null) return false;
        } else if (!biotechName.equals(other.biotechName)) return false;
        if (!cdAntigenNames.equals(other.cdAntigenNames)) return false;
        if (!contains.equals(other.contains)) return false;
        if (flag == null) {
            if (other.flag != null) return false;
        } else if (!flag.equals(other.flag)) return false;
        if (!includes.equals(other.includes)) return false;
        if (!innNames.equals(other.innNames)) return false;
        if (recommendedName == null) {
            if (other.recommendedName != null) return false;
        } else if (!recommendedName.equals(other.recommendedName)) return false;
        return submissionNames.equals(other.submissionNames);
    }
}
