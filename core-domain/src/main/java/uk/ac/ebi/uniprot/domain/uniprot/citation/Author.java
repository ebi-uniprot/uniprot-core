package uk.ac.ebi.uniprot.domain.uniprot.citation;

import uk.ac.ebi.uniprot.domain.common.Value;

/**
 * The RA (Reference Author) lines list the authors of the paper (or other work) cited.
 * The RA line is present in most references, but might be missing in references that cite
 * a reference group (see RG line). At least one RG or RA line is mandatory per reference block.
 *
 * All of the authors are included, and are listed in the order given in the paper.
 * The names are listed surname first followed by a blank, followed by initial(s) with periods.
 * The authors' names are separated by commas and terminated by a semicolon.
 * Author names are not split between lines. An example of the use of RA lines is shown below:
 *
 * RA   Galinier A., Bleicher F., Negre D., Perriere G., Duclos B.,
 * RA   Cozzone A.J., Cortay J.-C.;
 * As many RA lines as necessary are included in each reference.
 * All initials of the author names are indicated and hyphens between initials are kept.
 *
 * An author's initials can be followed by an abbreviation such as 'Jr' (for Junior), 'Sr' (Senior),
 * 'II', 'III' or 'IV' (2nd, 3rd and 4th). Example:
 *
 * RA   Nasoff M.S., Baker H.V. II, Wolf R.E. Jr.;
 *
 * @link uk.ac.ebi.kraken.interfaces.uniprot.citations.HasAuthors
 * @link uk.ac.ebi.kraken.interfaces.common.Value
 */


public interface Author extends Value {
}
