package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public final class DiseaseBuilder {
    private String diseaseId;
    private String diseaseAc;
    private String acronym;

    private String description;
    private DBCrossReference<DiseaseReferenceType>  reference;
    private List<Evidence> evidences;


    public static DiseaseBuilder newInstance() {
        return new DiseaseBuilder();
    }


    public Disease build() {
        return new DiseaseImpl(diseaseId, diseaseAc, acronym, description,
                reference, evidences);

    }

    public DiseaseBuilder diseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
        return this;
    }

    public DiseaseBuilder diseaseAc(String diseaseAc) {
        this.diseaseAc = diseaseAc;
        return this;
    }

    public DiseaseBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public DiseaseBuilder description(String description) {
        this.description = description;
        return this;
    }

    public DiseaseBuilder reference(DBCrossReference<DiseaseReferenceType> reference) {
        this.reference = reference;
        return this;
    }


    public DiseaseBuilder evidences(List<Evidence> evidences) {
        this.evidences = evidences;
        return this;
    }

}
