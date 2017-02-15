package uk.ac.ebi.uniprot.domain.uniprot.citation;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 * Used in the flatfile either in the RA or the RG line
 * The RG line
 * The Reference Group (RG) line lists the consortium name associated with a given citation.
 * The RG line is mainly used in submission reference blocks, but can also be used in paper references,
 * if the working group is cited as an author in the paper. RG line and RA line (Reference Author) can
 * be present in the same reference block; at least one RG or RA line is mandatory per reference block.
 * An example of the use of RG lines is shown below:
 *
 * RG   The mouse genome sequencing consortium
 *
 * @link uk.ac.ebi.kraken.interfaces.common.Value;
 * @link uk.ac.ebi.kraken.interfaces.uniprot.citations.HasAuthors
 */

public interface AuthoringGroup extends Value {
}
