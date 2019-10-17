package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;
import org.uniprot.core.uniprot.comment.impl.DiseaseImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

import javax.annotation.Nonnull;

public final class DiseaseBuilder implements Builder<DiseaseBuilder, Disease> {
    private String diseaseId;
    private String diseaseAc;
    private String acronym;
    private String description;
    private DBCrossReference<DiseaseReferenceType> reference;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull Disease build() {
        return new DiseaseImpl(diseaseId, diseaseAc, acronym, description, reference, evidences);
    }

    @Override
    public @Nonnull DiseaseBuilder from(@Nonnull Disease instance) {
        return this.acronym(instance.getAcronym())
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
        this.evidences = modifiableList(evidences);
        return this;
    }

    public DiseaseBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
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
}
