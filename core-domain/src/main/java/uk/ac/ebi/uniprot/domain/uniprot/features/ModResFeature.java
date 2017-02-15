package uk.ac.ebi.uniprot.domain.uniprot.features;

/**
 * MOD_RES - Posttranslational modification of a residue.

   1. Modification Description
   2. ACETYLATION N-terminal of some residues and side chain of lysine
   3. AMIDATION Generally at the C-terminal of a mature active peptide after oxidative cleavage of last glycine
   4. BLOCKED Unidentified N- or C-terminal blocking group
   5. FORMYLATION Generally of the N-terminal methionine
   6. GAMMA-CARBOXYGLUTAMIC ACID Of glutamate
   7. HYDROXYLATION Generally of asparagine, aspartate, proline or lysine

 */
public interface ModResFeature extends Feature, HasFeatureDescription {
}
