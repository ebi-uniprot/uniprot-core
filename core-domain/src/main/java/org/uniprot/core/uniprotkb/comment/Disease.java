package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

/**
 * Container used to represent the definition of the disease.
 *
 * <p>A controlled vocabulary of disease descriptions is stored in the file <a
 * href="http://sp.isb-sib.ch/docs/humdisease">humdisease.txt</a>.
 *
 * <p>A disease is defined using the following:
 *
 * <ul>
 *   <li>ID = disease unique name
 *   <li>AR = disease acronym
 *   <li>DE = disease description
 *   <li>DR = cross-reference to an external source that characterizes the disease
 * </ul>
 *
 * <p>
 *
 * <p>Flat file example of a disease with a denefintion:
 *
 * <p>CC -!- DISEASE: <mark>Deafness, autosomal recessive, 12 (DFNB12) [MIM:601386]:</br> CC A form
 * of non-syndromic sensorineural hearing loss. Sensorineural</br> CC deafness results from damage
 * to the neural receptors of the inner</br> CC ear, the nerve pathways to the brain, or the area of
 * the brain</br> CC that receives sound information.</mark> Note=The disease is caused by</br> CC
 * mutations affecting the gene represented in this entry.</br>
 *
 * <p>Where:
 *
 * <ul>
 *   <ul>
 *     ID - Deafness, autosomal recessive, 12
 *   </ul>
 *   <ul>
 *     AR - DFNB12
 *   </ul>
 *   <ul>
 *     DE - A form of non-syndromic sensorineural hearing loss. Sensorineural deafness results from
 *     damage to the neural receptors of the inner ear, the nerve pathways to the brain, or the area
 *     of the brain that receives sound information
 *   </ul>
 *   <ul>
 *     DR - MIM:601386
 *   </ul>
 * </ul>
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @version 1.0
 * @see DiseaseComment
 */
public interface Disease extends HasEvidences {
    /** @return disease id (ID) */
    String getDiseaseId();

    String getDiseaseAccession();

    /** @return disease acronym (AR) */
    String getAcronym();

    String getDescription();

    CrossReference<DiseaseDatabase> getDiseaseCrossReference();

    boolean hasDefinedDisease();

    boolean hasDiseaseId();

    boolean hasDiseaseAccession();

    /** @return disease acronym (AR) */
    boolean hasAcronym();

    boolean hasDescription();

    boolean hasDiseaseCrossReference();
}
