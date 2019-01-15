package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comment2.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidencedValueImpl;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class DiseaseDescriptionImpl extends EvidencedValueImpl implements DiseaseDescription, Serializable {
    private static final long serialVersionUID = -3723332407175262069L;

    private DiseaseDescriptionImpl() {
        super(null, Collections.emptyList());
    }

    public DiseaseDescriptionImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }
}