package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class DiseaseDescriptionBuilder extends AbstractEvidencedValueBuilder<DiseaseDescriptionBuilder, DiseaseDescription> {
    @Override
    protected DiseaseDescriptionBuilder createBuilderInstance() {
        return new DiseaseDescriptionBuilder();
    }

    @Override
    protected DiseaseDescription createConcreteInstance() {
        return new DiseaseDescriptionImpl(value, evidences);
    }
}