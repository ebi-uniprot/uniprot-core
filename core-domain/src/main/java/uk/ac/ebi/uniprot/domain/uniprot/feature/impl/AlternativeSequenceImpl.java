package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;


import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;

import java.util.Collections;
import java.util.List;


public class AlternativeSequenceImpl implements AlternativeSequence {
    private static final long serialVersionUID = 804707478075935286L;
    private String originalSequence;
    private List<String> alternativeSequences;

    private AlternativeSequenceImpl() {
        this.alternativeSequences = Collections.emptyList();
    }

    public AlternativeSequenceImpl(String originalSequence,
                                   List<String> alternativeSequences
    ) {
        this.originalSequence = Utils.nullToEmpty(originalSequence);
        this.alternativeSequences = Utils.nonNullUnmodifiableList(alternativeSequences);

    }

    @Override
    public String getOriginalSequence() {
        return this.originalSequence;
    }


    @Override
    public List<String> getAlternativeSequences() {
        return this.alternativeSequences;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((alternativeSequences == null) ? 0 : alternativeSequences.hashCode());
        result = prime * result + ((originalSequence == null) ? 0 : originalSequence.hashCode());
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
        AlternativeSequenceImpl other = (AlternativeSequenceImpl) obj;
        if (alternativeSequences == null) {
            if (other.alternativeSequences != null)
                return false;
        } else if (!alternativeSequences.equals(other.alternativeSequences))
            return false;
        if (originalSequence == null) {
            if (other.originalSequence != null)
                return false;
        } else if (!originalSequence.equals(other.originalSequence))
            return false;
        return true;
    }
}
