package org.uniprot.core.uniprot.comment.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class DiseaseImpl implements Disease {
    private static final String DEFAULT_ACCESSION = "DI-00000";
    private static final long serialVersionUID = 6827893775979818439L;

    private String diseaseId;
    private String diseaseAccession;
    private String acronym;
    private String description;
    private DBCrossReference<DiseaseReferenceType> reference;
    private List<Evidence> evidences;

    private DiseaseImpl() {
        this(null, null, null, null, null, null);
    }

    public DiseaseImpl(String diseaseId, String diseaseAccession, String acronym, String description,
                       DBCrossReference<DiseaseReferenceType> reference, List<Evidence> evidences) {
        this.diseaseId = diseaseId;
        if (diseaseAccession == null || diseaseAccession.isEmpty()) {
            this.diseaseAccession = DEFAULT_ACCESSION;
        } else {
            this.diseaseAccession = diseaseAccession;
        }
        this.acronym = acronym;
        this.description = description;
        this.reference = reference;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
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
    public DBCrossReference<DiseaseReferenceType> getReference() {
        return reference;
    }

    @Override
    public boolean hasDefinedDisease() {
        return (diseaseId != null && !diseaseId.isEmpty() && (getAcronym() != null && !getAcronym().isEmpty())
                && isValidDescription() && isValidReference());
    }

    @Override
    public boolean hasDiseaseId() {
        return Utils.notNullOrEmpty(this.diseaseId);
    }

    @Override
    public boolean hasDiseaseAccession() {
        return Utils.notNullOrEmpty(this.diseaseAccession);
    }

    @Override
    public boolean hasAcronym() {
        return Utils.notNullOrEmpty(this.acronym);
    }

    @Override
    public boolean hasDescription() {
        return Utils.notNullOrEmpty(this.description);
    }

    @Override
    public boolean hasReference() {
        return this.reference != null;
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
        return description != null && !description.isEmpty();
    }

    private boolean isValidReference() {
        return (getReference() != null && getReference().getId() != null && !getReference().getId().isEmpty()
                && getReference().getDatabaseType() != DiseaseReferenceType.NONE);
    }
}
