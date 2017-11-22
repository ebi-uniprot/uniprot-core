package uk.ac.ebi.uniprot.domain.feature.impl;

import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;
import java.util.List;

public class ConflictFeatureImpl extends FeatureWithAlternativeSequenceImpl implements ConflictFeature {
    public ConflictFeatureImpl( FeatureLocation location,
        String orginalSequence, List<String> alternativeSequences,
        List<String> report) {
        super(FeatureType.CONFLICT, location, orginalSequence, alternativeSequences, report);
      
    }

    public ConflictFeatureImpl( FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences,
            SequenceReport report) {
            super(FeatureType.CONFLICT, location, orginalSequence, alternativeSequences, report);
          
        }
   
}
