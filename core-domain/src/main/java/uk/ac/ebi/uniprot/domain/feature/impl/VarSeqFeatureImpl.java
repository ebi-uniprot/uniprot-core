package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;
import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;

import java.util.List;
import java.util.regex.Pattern;

public class VarSeqFeatureImpl extends FeatureWithAlternativeSequenceImpl implements VarSeqFeature {

    private final FeatureId featureId;
    private static final Pattern FEATURE_ID_PATTERN = Pattern.compile("VSP_(\\d+)");
    public VarSeqFeatureImpl( FeatureLocation location,
        String orginalSequence, List<String> alternativeSequences, List<String> report,
        FeatureId featureId) {
        super(FeatureType.VAR_SEQ, location, orginalSequence, alternativeSequences, report);
        this.featureId = featureId;

    }

    public VarSeqFeatureImpl( FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences, SequenceReport report,
            FeatureId featureId) {
            super(FeatureType.VAR_SEQ, location, orginalSequence, alternativeSequences, report);
            this.featureId =featureId;
        }

    @Override
    public FeatureId getFeatureId() {
       return featureId;
    }

    @Override
    public boolean isValidFeatureId() {
        if (featureId == null)
            return false;
        String val = this.featureId.getValue();
        if (val == null)
            return false;
        return FEATURE_ID_PATTERN.matcher(val).matches();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((featureId == null) ? 0 : featureId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        VarSeqFeatureImpl other = (VarSeqFeatureImpl) obj;
        if (featureId == null) {
            if (other.featureId != null)
                return false;
        } else if (!featureId.equals(other.featureId))
            return false;
        return true;
    }
    
    
}
