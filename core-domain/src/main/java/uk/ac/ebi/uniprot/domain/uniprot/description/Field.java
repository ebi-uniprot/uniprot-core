package uk.ac.ebi.uniprot.domain.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;

/**
 * Stores the sub category type of a specific category as well as the respective value.
 * As examples of sub category types we have: Full, Short, EC, etc.
 * <br>
 * For a list of the allowable sub categories see
 * {@link uk.ac.ebi.uniprot.domain.uniprot.description.FieldType FieldType}
 */
public interface Field extends EvidencedValue {

    /**
     * Returns the type of field
     *
     * @return valid field type (see
     * {@link uk.ac.ebi.uniprot.domain.uniprot.description.FieldType FieldType})
     */
     FieldType getType();
}


