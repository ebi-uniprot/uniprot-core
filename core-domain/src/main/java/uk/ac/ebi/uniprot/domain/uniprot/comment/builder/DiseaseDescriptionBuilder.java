package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseDescriptionImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class DiseaseDescriptionBuilder extends AbstractEvidencedValueBuilder<DiseaseDescriptionBuilder, DiseaseDescription> {
    private DiseaseDescriptionBuilder() {}

    public DiseaseDescriptionBuilder(String name, List<Evidence> evidences) {
        this.value = name;
        this.evidences = evidences;
    }

   @Override
    protected DiseaseDescription createConcreteInstance() {
        return new DiseaseDescriptionImpl(value, evidences);
    }

    @Override
    protected DiseaseDescriptionBuilder getThis() {
        return this;
    }
}