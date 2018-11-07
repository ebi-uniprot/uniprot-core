package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReference;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class DiseaseBuilder {
    private DiseaseId diseaseId;
    private String acronym;
    private DiseaseDescription description;
    private DiseaseReference reference;

    public static DiseaseBuilder newInstance(){
        return new DiseaseBuilder();
    }
    public Disease build() {
        return new DiseaseImpl(diseaseId, acronym, description,
                reference);
    }

    public DiseaseBuilder diseaseId(DiseaseId diseaseId) {
        this.diseaseId = diseaseId;
        return this;
    }

    public DiseaseBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public DiseaseBuilder description(DiseaseDescription description) {
        this.description = description;
        return this;
    }

    public DiseaseBuilder reference(DiseaseReference reference) {
        this.reference = reference;
        return this;
    }

    public static DiseaseId createDiseaseId(String val) {
        return DiseaseImpl.createDiseaseId(val);
    }

    public static DiseaseReference createDiseaseReference(DiseaseReferenceType referenceType, String referenceId) {
        return DiseaseImpl.createDiseaseReference(referenceType, referenceId);
    }

    public static DiseaseDescription createDiseaseDescription(String val, List<Evidence> evidences) {
        return DiseaseImpl.createDiseaseDescription(val, evidences);
    }

}
