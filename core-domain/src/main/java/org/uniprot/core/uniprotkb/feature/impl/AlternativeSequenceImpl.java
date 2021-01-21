package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.util.Utils;

public class AlternativeSequenceImpl implements AlternativeSequence {
    private static final long serialVersionUID = 804707478075935286L;
    private final String originalSequence;
    private final List<String> alternativeSequences;

    // no arg constructor for JSON deserialization
    AlternativeSequenceImpl() {
        this.alternativeSequences = Collections.emptyList();
        this.originalSequence = "";
    }

    AlternativeSequenceImpl(String originalSequence, List<String> alternativeSequences) {
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
    	return Objects.hash( originalSequence, alternativeSequences );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AlternativeSequenceImpl that = (AlternativeSequenceImpl) obj;
        return  Objects.equals(originalSequence, that.originalSequence)
                && Objects.equals(alternativeSequences, that.alternativeSequences);
        
    }
}
