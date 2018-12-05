package uk.ac.ebi.uniprot.domain.uniprot.comment;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

/**
 * Container used to represent the definition of the disease.
 * <p>
 * A controlled vocabulary of disease descriptions is stored in the file <a href="http://sp.isb-sib.ch/docs/humdisease">humdisease.txt</a>.
 * <p>
 * A disease is defined using the following:
 * <ul>
 *  <li>ID = disease unique name</li>
 *  <li>AR = disease acronym</li>
 *  <li>DE = disease description</li>
 *  <li>DR = cross-reference to an external source that characterizes the disease</li>
 * </ul>
 * <p>
 *
 * Flat file example of a disease with a denefintion:
 * <p>
 * CC -!- DISEASE: <mark>Deafness, autosomal recessive, 12 (DFNB12) [MIM:601386]:</br>
 * CC A form of non-syndromic sensorineural hearing loss. Sensorineural</br>
 * CC deafness results from damage to the neural receptors of the inner</br>
 * CC ear, the nerve pathways to the brain, or the area of the brain</br>
 * CC that receives sound information.</mark> Note=The disease is caused by</br>
 * CC mutations affecting the gene represented in this entry.</br>
 * <p>
 * Where:
 * <ul>
 *   <ul>ID - Deafness, autosomal recessive, 12</ul>
 *   <ul>AR - DFNB12</ul>
 *   <ul>DE - A form of non-syndromic sensorineural hearing loss. Sensorineural deafness results from damage to the
 *   neural receptors of the inner ear, the nerve pathways to the brain, or the area of the brain that receives sound
 *   information</ul>
 *   <ul>DR - MIM:601386</ul>
 * </ul>
 *
 * @author Francesco Fazzini
 * @author Ricardo Antunes
 * @see DiseaseComment
 * @version 1.0
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseImpl.class, name = "DiseaseImpl")
})
public interface Disease {
    /**
     * @return disease id (ID)
     */
     String getDiseaseId();

    /**
     * @return disease acronym (AR)
     */
     String getAcronym();

     DiseaseDescription getDescription();

     DBCrossReference<DiseaseReferenceType>  getReference();
    boolean hasDefinedDisease();

}