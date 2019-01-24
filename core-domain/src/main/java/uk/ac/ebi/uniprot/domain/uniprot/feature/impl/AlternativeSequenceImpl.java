package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;


import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;



public class AlternativeSequenceImpl implements AlternativeSequence {

    private static final Set<FeatureType> ALTERNATIVE_SEQUENCE_SET = EnumSet.of(FeatureType.CONFLICT,
                                                                                FeatureType.MUTAGEN, FeatureType.VARIANT, FeatureType.VAR_SEQ);
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


    public static boolean hasAlternativeSequence(FeatureType type) {
        return ALTERNATIVE_SEQUENCE_SET.contains(type);
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
