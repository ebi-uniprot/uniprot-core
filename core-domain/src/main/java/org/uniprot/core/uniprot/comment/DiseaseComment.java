package org.uniprot.core.uniprot.comment;

/**
 * Description of the disease(s) associated with a deficiency of a protein
 *
 * <p>
 *
 * <p>The CC DISEASE annotation has two possible representations:
 *
 * <ul>
 *   <li>Disease comments with a characterized disease referenced from an external source
 *   <li>Disease comments with no characterized disease
 * </ul>
 *
 * <p>
 *
 * <p>Both representations are followed by a {@link DiseaseNote} that is entry specific
 *
 * <p>
 *
 * <p>Flat file example of the first type:</br> <i> CC -!- DISEASE: Deafness, autosomal recessive,
 * 12 (DFNB12) [MIM:601386]: CC A form of non-syndromic sensorineural hearing loss. Sensorineural CC
 * deafness results from damage to the neural receptors of the inner CC ear, the nerve pathways to
 * the brain, or the area of the brain CC that receives sound information. Note=The disease is
 * caused by CC mutations affecting the gene represented in this entry. </i>
 *
 * <p>Flat file example of the first type:</br> <i> CC -!- DISEASE: Note=Defects in POP1 may be the
 * cause of a severe CC skeletal dysplasia reminiscent of anauxetic dysplasia. Affected CC
 * individuals show severe growth retardation of prenatal onset, a CC bone dysplasia affecting the
 * epiphyses and metaphyses of the long CC bones particularly in the lower limbs, and abnormalities
 * of the CC spine including irregularly shaped vertebral bodies and marked CC cervical spine
 * instability. </i>
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @version 1.0
 * @see Comment
 */
public interface DiseaseComment extends Comment, HasMolecule {
    /** @return the definition of the disease */
    Disease getDisease();

    /**
     * Helper method that verifies whether the {link Disease} object has been populated with a
     * defined disease, or if it holds empty state
     *
     * @return true if the disease object has a defined disease, false otherwise
     */
    boolean hasDefinedDisease();

    /**
     * @return free text description of the manifestation of the disease on the protein containing
     *     this comment
     */
    Note getNote();

    boolean hasNote();
}
