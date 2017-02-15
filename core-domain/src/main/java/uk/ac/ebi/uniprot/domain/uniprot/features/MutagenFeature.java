package uk.ac.ebi.uniprot.domain.uniprot.features;


/**
 * MUTAGEN - Site which has been experimentally altered.
 */
public interface MutagenFeature extends Feature, HasAlternativeSequence {

	public MutagenReport getMutagenReport();
}
