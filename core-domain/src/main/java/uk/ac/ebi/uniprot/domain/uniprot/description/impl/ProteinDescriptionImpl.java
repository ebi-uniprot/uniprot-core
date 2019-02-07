package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.domain.uniprot.description.*;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;


public class ProteinDescriptionImpl implements ProteinDescription {

    private ProteinName recommendedName;
    private List<ProteinName> alternativeNames;
    private List<ProteinName> submissionNames;

    private Name allergenName;
    private Name biotechName;
    private List<Name> cdAntigenNames;
    private List<Name> innNames;
    private List<ProteinSection> includes;  //dmain
    private List<ProteinSection> contains;  //component
    private Flag flag;

    public ProteinDescriptionImpl(ProteinName recommendedName,
                                  List<ProteinName> alternativeNames
    ) {
        this(recommendedName, alternativeNames, null);
    }

    public ProteinDescriptionImpl(ProteinName recommendedName,
                                  List<ProteinName> alternativeNames, Flag flag
    ) {
        this(recommendedName, alternativeNames, null, flag);
    }

    public ProteinDescriptionImpl(ProteinName recommendedName,
                                  List<ProteinName> alternativeNames,
                                  List<ProteinName> submissionNames,
                                  Flag flag
    ) {
        this(recommendedName, alternativeNames, submissionNames, flag,
             null, null, null, null, null, null);
    }

    private ProteinDescriptionImpl() {
        this.alternativeNames = Collections.emptyList();
        this.submissionNames = Collections.emptyList();
        this.cdAntigenNames = Collections.emptyList();
        this.innNames = Collections.emptyList();
        this.includes = Collections.emptyList();
        this.contains = Collections.emptyList();
    }

    public ProteinDescriptionImpl(
            ProteinName recommendedName,
            List<ProteinName> alternativeNames,
            List<ProteinName> submissionNames,
            Flag flag,
            Name allergenName,
            Name biotechName,
            List<Name> cdAntigenNames,
            List<Name> innNames,
            List<ProteinSection> includes,
            List<ProteinSection> contains) {
        this.recommendedName = recommendedName;
        this.alternativeNames = Utils.nonNullUnmodifiableList(alternativeNames);
        this.submissionNames = Utils.nonNullUnmodifiableList(submissionNames);
        this.flag = flag;
        this.allergenName = allergenName;
        this.biotechName = biotechName;
        this.cdAntigenNames = Utils.nonNullUnmodifiableList(cdAntigenNames);
        this.innNames = Utils.nonNullUnmodifiableList(innNames);
        this.includes = Utils.nonNullUnmodifiableList(includes);
        this.contains = Utils.nonNullUnmodifiableList(contains);
    }

    @Override
    public ProteinName getRecommendedName() {
        return recommendedName;
    }

    @Override
    public List<ProteinName> getSubmissionNames() {
        return submissionNames;
    }


    @Override
    public List<ProteinName> getAlternativeNames() {
        return alternativeNames;
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
        return cdAntigenNames;
    }

    @Override
    public List<Name> getInnNames() {
        return innNames;
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
        return this.recommendedName != null;
    }

    @Override
    public boolean hasAlternativeNames() {
        return Utils.notEmpty(this.alternativeNames);
    }

    @Override
    public boolean hasSubmissionNames() {
        return Utils.notEmpty(this.submissionNames);
    }

    @Override
    public boolean hasAllergenName() {
        return this.allergenName != null;
    }

    @Override
    public boolean hasBiotechName() {
        return this.biotechName != null;
    }

    @Override
    public boolean hasCdAntigenNames() {
        return Utils.notEmpty(this.cdAntigenNames);
    }

    @Override
    public boolean hasInnNames() {
        return Utils.notEmpty(this.innNames);
    }

    @Override
    public boolean hasIncludes() {
        return Utils.notEmpty(this.includes);
    }

    @Override
    public boolean hasContains() {
        return Utils.notEmpty(this.contains);
    }

    @Override
    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
		this.flag= flag;
	}
    @Override
    public boolean isValid() {
        if (getRecommendedName() != null) {
            return getRecommendedName().isValid();
        } else {
            return getSubmissionNames().stream().anyMatch(val -> val.isValid());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((allergenName == null) ? 0 : allergenName.hashCode());
        result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
        result = prime * result + ((biotechName == null) ? 0 : biotechName.hashCode());
        result = prime * result + ((cdAntigenNames == null) ? 0 : cdAntigenNames.hashCode());
        result = prime * result + ((contains == null) ? 0 : contains.hashCode());
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((includes == null) ? 0 : includes.hashCode());
        result = prime * result + ((innNames == null) ? 0 : innNames.hashCode());
        result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
        result = prime * result + ((submissionNames == null) ? 0 : submissionNames.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProteinDescriptionImpl other = (ProteinDescriptionImpl) obj;
        if (allergenName == null) {
            if (other.allergenName != null)
                return false;
        } else if (!allergenName.equals(other.allergenName))
            return false;
        if (alternativeNames == null) {
            if (other.alternativeNames != null)
                return false;
        } else if (!alternativeNames.equals(other.alternativeNames))
            return false;
        if (biotechName == null) {
            if (other.biotechName != null)
                return false;
        } else if (!biotechName.equals(other.biotechName))
            return false;
        if (cdAntigenNames == null) {
            if (other.cdAntigenNames != null)
                return false;
        } else if (!cdAntigenNames.equals(other.cdAntigenNames))
            return false;
        if (contains == null) {
            if (other.contains != null)
                return false;
        } else if (!contains.equals(other.contains))
            return false;
        if (flag == null) {
            if (other.flag != null)
                return false;
        } else if (!flag.equals(other.flag))
            return false;
        if (includes == null) {
            if (other.includes != null)
                return false;
        } else if (!includes.equals(other.includes))
            return false;
        if (innNames == null) {
            if (other.innNames != null)
                return false;
        } else if (!innNames.equals(other.innNames))
            return false;
        if (recommendedName == null) {
            if (other.recommendedName != null)
                return false;
        } else if (!recommendedName.equals(other.recommendedName))
            return false;
        if (submissionNames == null) {
            if (other.submissionNames != null)
                return false;
        } else if (!submissionNames.equals(other.submissionNames))
            return false;
        return true;
    }


}
