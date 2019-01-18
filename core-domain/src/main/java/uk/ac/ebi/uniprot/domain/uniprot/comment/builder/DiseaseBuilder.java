package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.DiseaseImpl;

import static java.util.Collections.emptyList;

public final class DiseaseBuilder implements Builder2<DiseaseBuilder, Disease> {
    private String diseaseId;
    private String diseaseAc;
    private String acronym;
    private DiseaseDescription description;
    private DBCrossReference<DiseaseReferenceType> reference;

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

    public DiseaseBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public DiseaseBuilder description(DiseaseDescription description) {
        this.description = description;
        return this;
    }

    public DiseaseBuilder description(String description) {
        this.description = new DiseaseDescriptionBuilder(description, emptyList()).build();
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

    public DiseaseDescription getDescription() {
        return description;
    }

    public DBCrossReference<DiseaseReferenceType> getReference() {
        return reference;
    }
}
