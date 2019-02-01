package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

public final class DiseaseBuilder implements Builder<DiseaseBuilder, Disease> {
    private String diseaseId;
    private String diseaseAc;
    private String acronym;
    private String description;
    private DBCrossReference<DiseaseReferenceType> reference;
    private List<Evidence> evidences = new ArrayList<>();

    public Disease build() {
        return new DiseaseImpl(this);
    }

    @Override
    public DiseaseBuilder from(Disease instance) {
        return this
                .acronym(instance.getAcronym())
                .description(instance.getDescription())
                .diseaseAc(instance.getDiseaseAccession())
                .diseaseId(instance.getDiseaseId())
                .evidences(instance.getEvidences())
                .reference(instance.getReference());
    }

    public DiseaseBuilder diseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
        return this;
    }

    public DiseaseBuilder diseaseAc(String diseaseAc) {
        this.diseaseAc = diseaseAc;
        return this;
    }

    public DiseaseBuilder evidences(List<Evidence> evidences) {
        this.evidences = nonNullList(evidences);
        return this;
    }

    public DiseaseBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
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

    public String getDiseaseId() {
        return diseaseId;
    }

    public String getDiseaseAc() {
        return diseaseAc;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getDescription() {
        return description;
    }

    public DBCrossReference<DiseaseReferenceType> getReference() {
        return reference;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }
}
