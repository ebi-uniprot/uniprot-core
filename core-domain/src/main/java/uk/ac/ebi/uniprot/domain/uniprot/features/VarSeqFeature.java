package uk.ac.ebi.uniprot.domain.uniprot.features;

import java.util.List;

public interface VarSeqFeature extends Feature, HasFeatureId, HasAlternativeSequence {
	public List<VarsplicIsoform> getVarsplicIsoforms();
}
