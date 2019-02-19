package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.description.*;

import java.util.Collections;
import java.util.List;


public class ProteinDescriptionImpl implements ProteinDescription {

	private static final long serialVersionUID = 6554940064782470199L;
	private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames;
    private List<ProteinSubName> submissionNames;
    private List<ProteinSection> includes;  //dmain
    private List<ProteinSection> contains;  //component
    private Flag flag;

    public ProteinDescriptionImpl(ProteinRecName recommendedName,
                                  List<ProteinAltName> alternativeNames
    ) {
        this(recommendedName, alternativeNames, null);
    }

    public ProteinDescriptionImpl(ProteinRecName recommendedName,
                                  List<ProteinAltName> alternativeNames, Flag flag
    ) {
        this(recommendedName, alternativeNames, null, flag);
    }

    public ProteinDescriptionImpl(ProteinRecName recommendedName,
                                  List<ProteinAltName> alternativeNames,
                                  List<ProteinSubName> submissionNames,
                                  Flag flag
    ) {
        this(recommendedName, alternativeNames, submissionNames, flag,
              null, null);
    }

    private ProteinDescriptionImpl() {
        this.alternativeNames = Collections.emptyList();
        this.submissionNames = Collections.emptyList();
        this.includes = Collections.emptyList();
        this.contains = Collections.emptyList();
    }

    public ProteinDescriptionImpl(
    		ProteinRecName recommendedName,
            List<ProteinAltName> alternativeNames,
            List<ProteinSubName> submissionNames,
            Flag flag,
            List<ProteinSection> includes,
            List<ProteinSection> contains) {
        this.recommendedName = recommendedName;
        this.alternativeNames = Utils.nonNullUnmodifiableList(alternativeNames);
        this.submissionNames = Utils.nonNullUnmodifiableList(submissionNames);
        this.flag = flag;
        this.includes = Utils.nonNullUnmodifiableList(includes);
        this.contains = Utils.nonNullUnmodifiableList(contains);
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
		result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
		result = prime * result + ((contains == null) ? 0 : contains.hashCode());
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result + ((includes == null) ? 0 : includes.hashCode());
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
		if (alternativeNames == null) {
			if (other.alternativeNames != null)
				return false;
		} else if (!alternativeNames.equals(other.alternativeNames))
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
