package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;
import java.util.Objects;

public class DiseaseImpl implements Disease {
    private static final String DEFAULT_ACCESSION = "DI-00000";

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
        this.evidences = Utils.nonNullUnmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
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
