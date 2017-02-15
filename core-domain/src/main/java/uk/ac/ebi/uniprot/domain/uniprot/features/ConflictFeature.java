package uk.ac.ebi.uniprot.domain.uniprot.features;


import java.util.List;

public interface ConflictFeature extends Feature, HasAlternativeSequence, HasFeatureDescription {

    public List<ConflictReport> getConflictReports();
}
