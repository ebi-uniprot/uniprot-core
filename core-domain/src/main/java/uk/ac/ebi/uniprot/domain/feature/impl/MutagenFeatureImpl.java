package uk.ac.ebi.uniprot.domain.feature.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.FeatureSequence;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.feature.SequenceReport;

public class MutagenFeatureImpl extends FeatureWithAlternativeSequenceImpl implements MutagenFeature {

    public MutagenFeatureImpl( FeatureLocation location,
        String orginalSequence, List<String> alternativeSequences, List<String> report) {
        super(FeatureType.MUTAGEN, location, orginalSequence, alternativeSequences, report);

    }

    public MutagenFeatureImpl( FeatureLocation location,
            FeatureSequence orginalSequence, List<FeatureSequence> alternativeSequences, SequenceReport report) {
            super(FeatureType.MUTAGEN, location, orginalSequence, alternativeSequences, report);
        }



}
