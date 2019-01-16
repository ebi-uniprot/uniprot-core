package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.DiseaseBuilder;

import java.util.Objects;

public class DiseaseImpl implements Disease {
    private static final String DEFAULT_ACCESSION = "DI-00000";

    private String diseaseId;
    private String diseaseAccession;
    private String acronym;
    private DiseaseDescription description;
    private DBCrossReference<DiseaseReferenceType> reference;

    private DiseaseImpl() {

    }

    public DiseaseImpl(DiseaseBuilder builder) {
        this.diseaseId = builder.getDiseaseId();
        if (builder.getDiseaseAc() == null || builder.getDiseaseAc().isEmpty()) {
            this.diseaseAccession = DEFAULT_ACCESSION;
        } else {
            this.diseaseAccession = builder.getDiseaseAc();
        }
        this.acronym = builder.getAcronym();
        this.description = builder.getDescription();
        this.reference = builder.getReference();
    }

    @Override
    public String getDiseaseId() {
        return diseaseId;
    }

    @Override
    public String getDiseaseAccession() {
        return diseaseAccession;
    }


    @Override
    public String getAcronym() {
        return acronym;
    }

    @Override
    public DiseaseDescription getDescription() {
        return description;
    }

    @Override
    public DBCrossReference<DiseaseReferenceType> getReference() {
        return reference;
    }

    @Override
    public boolean hasDefinedDisease() {
        return (diseaseId != null && !diseaseId.isEmpty() && (getAcronym() != null && !getAcronym().isEmpty())
                && isValidDescription() && isValidReference());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseImpl disease = (DiseaseImpl) o;
        return Objects.equals(diseaseId, disease.diseaseId) &&
                Objects.equals(diseaseAccession, disease.diseaseAccession) &&
                Objects.equals(acronym, disease.acronym) &&
                Objects.equals(description, disease.description) &&
                Objects.equals(reference, disease.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diseaseId, diseaseAccession, acronym, description, reference);
    }

    private boolean isValidDescription() {
        return getDescription() != null && getDescription().getValue() != null
                && !getDescription().getValue().isEmpty();
    }

    private boolean isValidReference() {
        return (getReference() != null && getReference().getId() != null && !getReference().getId().isEmpty()
                && getReference().getDatabaseType() != DiseaseReferenceType.NONE);
    }
}
