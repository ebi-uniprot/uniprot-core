package org.uniprot.core.uniprotkb.comment.impl;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.Disease;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;

public class DiseaseImpl implements Disease {
    private static final String DEFAULT_ACCESSION = "DI-00000";
    private static final long serialVersionUID = 6827893775979818439L;

    private String diseaseId;
    private String diseaseAccession;
    private String acronym;
    private String description;
    private CrossReference<DiseaseDatabase> diseaseCrossReference;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    DiseaseImpl() {
        this(null, null, null, null, null, null);
    }

    DiseaseImpl(
            String diseaseId,
            String diseaseAccession,
            String acronym,
            String description,
            CrossReference<DiseaseDatabase> diseaseCrossReference,
            List<Evidence> evidences) {
        this.diseaseId = diseaseId;
        if (diseaseAccession == null || diseaseAccession.isEmpty()) {
            this.diseaseAccession = DEFAULT_ACCESSION;
        } else {
            this.diseaseAccession = diseaseAccession;
        }
        this.acronym = acronym;
        this.description = description;
        this.diseaseCrossReference = diseaseCrossReference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
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
    public String getDescription() {
        return description;
    }

    @Override
    public CrossReference<DiseaseDatabase> getDiseaseCrossReference() {
        return diseaseCrossReference;
    }

    @Override
    public boolean hasDefinedDisease() {
        return (diseaseId != null
                && !diseaseId.isEmpty()
                && (getAcronym() != null && !getAcronym().isEmpty())
                && isValidDescription()
                && isValidReference());
    }

    @Override
    public boolean hasDiseaseId() {
        return Utils.notNullNotEmpty(this.diseaseId);
    }

    @Override
    public boolean hasDiseaseAccession() {
        return Utils.notNullNotEmpty(this.diseaseAccession);
    }

    @Override
    public boolean hasAcronym() {
        return Utils.notNullNotEmpty(this.acronym);
    }

    @Override
    public boolean hasDescription() {
        return Utils.notNullNotEmpty(this.description);
    }

    @Override
    public boolean hasDiseaseCrossReference() {
        return this.diseaseCrossReference != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseImpl disease = (DiseaseImpl) o;
        return Objects.equals(diseaseId, disease.diseaseId)
                && Objects.equals(diseaseAccession, disease.diseaseAccession)
                && Objects.equals(acronym, disease.acronym)
                && Objects.equals(description, disease.description)
                && Objects.equals(diseaseCrossReference, disease.diseaseCrossReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                diseaseId, diseaseAccession, acronym, description, diseaseCrossReference);
    }

    private boolean isValidDescription() {
        return description != null && !description.isEmpty();
    }

    private boolean isValidReference() {
        return (getDiseaseCrossReference() != null
                && getDiseaseCrossReference().getId() != null
                && !getDiseaseCrossReference().getId().isEmpty()
                && getDiseaseCrossReference().getDatabase() != DiseaseDatabase.NONE);
    }
}
