package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.comment.Disease;
import org.uniprot.core.uniprot.comment.DiseaseDatabase;
import org.uniprot.core.uniprot.comment.impl.DiseaseImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

public final class DiseaseBuilder implements Builder<Disease> {
    private String diseaseId;
    private String diseaseAc;
    private String acronym;
    private String description;
    private CrossReference<DiseaseDatabase> reference;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull Disease build() {
        return new DiseaseImpl(diseaseId, diseaseAc, acronym, description, reference, evidences);
    }

    public static @Nonnull DiseaseBuilder from(@Nonnull Disease instance) {
        return new DiseaseBuilder()
                .acronym(instance.getAcronym())
                .description(instance.getDescription())
                .diseaseAc(instance.getDiseaseAccession())
                .diseaseId(instance.getDiseaseId())
                .evidencesSet(instance.getEvidences())
                .reference(instance.getReference());
    }

    public @Nonnull DiseaseBuilder diseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
        return this;
    }

    public @Nonnull DiseaseBuilder diseaseAc(String diseaseAc) {
        this.diseaseAc = diseaseAc;
        return this;
    }

    public @Nonnull DiseaseBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull DiseaseBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull DiseaseBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public @Nonnull DiseaseBuilder description(String description) {
        this.description = description;
        return this;
    }

    public @Nonnull DiseaseBuilder reference(CrossReference<DiseaseDatabase> reference) {
        this.reference = reference;
        return this;
    }
}
