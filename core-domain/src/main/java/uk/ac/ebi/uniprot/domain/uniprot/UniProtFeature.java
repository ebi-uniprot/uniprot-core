package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.feature.Feature;

public interface UniProtFeature <T extends Feature> extends HasEvidences {
    T getFeature();
}
