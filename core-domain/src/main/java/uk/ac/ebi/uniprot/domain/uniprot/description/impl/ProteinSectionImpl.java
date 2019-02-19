package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import java.util.Collections;
import java.util.List;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;

public class ProteinSectionImpl implements ProteinSection {

	private static final long serialVersionUID = -8215290595530427991L;
	private ProteinRecName recommendedName;
    private List<ProteinAltName> alternativeNames;

    private ProteinSectionImpl() {
        this.alternativeNames = Collections.emptyList();
    }

    public ProteinSectionImpl(ProteinRecName recommendedName, List<ProteinAltName> alternativeNames) {

        this.recommendedName = recommendedName;
        if ((alternativeNames == null) || alternativeNames.isEmpty()) {
            this.alternativeNames = Collections.emptyList();
        } else {
            this.alternativeNames = Collections.unmodifiableList(alternativeNames);
        }

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
        return Utils.notEmpty(this.alternativeNames);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
        result = prime * result + ((recommendedName == null) ? 0 : recommendedName.hashCode());
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
        ProteinSectionImpl other = (ProteinSectionImpl) obj;
        if (alternativeNames == null) {
            if (other.alternativeNames != null)
                return false;
        } else if (!alternativeNames.equals(other.alternativeNames))
            return false;
        if (recommendedName == null) {
            if (other.recommendedName != null)
                return false;
        } else if (!recommendedName.equals(other.recommendedName))
            return false;
        return true;
    }

}
