package uk.ac.ebi.uniprot.domain.proteome;

import java.io.Serializable;

public interface RedundantProteome extends Serializable {
	ProteomeId getId();
	Float getSimilarity();
}
