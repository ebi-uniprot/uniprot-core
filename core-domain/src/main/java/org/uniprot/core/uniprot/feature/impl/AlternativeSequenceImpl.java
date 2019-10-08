package org.uniprot.core.uniprot.feature.impl;

import java.util.Collections;
import java.util.List;

import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.util.Utils;

public class AlternativeSequenceImpl implements AlternativeSequence {
    private static final long serialVersionUID = 804707478075935286L;
    private String originalSequence;
    private List<String> alternativeSequences;

    // no arg constructor for JSON deserialization
    AlternativeSequenceImpl() {
        this.alternativeSequences = Collections.emptyList();
        this.originalSequence = "";
    }

    public AlternativeSequenceImpl(String originalSequence, List<String> alternativeSequences) {
        this.originalSequence = Utils.emptyOrString(originalSequence);
        this.alternativeSequences = Utils.unmodifiableList(alternativeSequences);
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
        result =
                prime * result
                        + ((alternativeSequences == null) ? 0 : alternativeSequences.hashCode());
        result = prime * result + ((originalSequence == null) ? 0 : originalSequence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AlternativeSequenceImpl other = (AlternativeSequenceImpl) obj;
        if (!alternativeSequences.equals(other.alternativeSequences)) return false;
        return originalSequence.equals(other.originalSequence);
    }
}
