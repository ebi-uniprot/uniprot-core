package org.uniprot.core.uniprotkb.interaction.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrix;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public class InteractionMatrixBuilder implements Builder<InteractionMatrix> {
    private UniProtKBAccession uniProtKBAccession;
    private UniProtKBId uniProtKBId;
    private Organism organism;
    private ProteinExistence proteinExistence;
    private List<Interaction> interactions = new ArrayList<>();
    private List<DiseaseComment> diseases = new ArrayList<>();
    private List<SubcellularLocationComment> subcellularLocations = new ArrayList<>();

    public @Nonnull InteractionMatrixBuilder uniProtKBAccession(
            UniProtKBAccession uniProtKBAccession) {
        this.uniProtKBAccession = uniProtKBAccession;
        return this;
    }

    public @Nonnull InteractionMatrixBuilder uniProtKBId(UniProtKBId uniProtKBId) {
        this.uniProtKBId = uniProtKBId;
        return this;
    }

    public @Nonnull InteractionMatrixBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull InteractionMatrixBuilder proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull InteractionMatrixBuilder interactionsAdd(Interaction interaction) {
        Utils.addOrIgnoreNull(interaction, this.interactions);
        return this;
    }

    public @Nonnull InteractionMatrixBuilder interactionsSet(List<Interaction> interactions) {
        this.interactions = Utils.modifiableList(interactions);
        return this;
    }

    public @Nonnull InteractionMatrixBuilder diseasesAdd(DiseaseComment disease) {
        Utils.addOrIgnoreNull(disease, this.diseases);
        return this;
    }

    public @Nonnull InteractionMatrixBuilder diseasesSet(List<DiseaseComment> diseases) {
        this.diseases = Utils.modifiableList(diseases);
        return this;
    }

    public @Nonnull InteractionMatrixBuilder subcellularLocationsAdd(
            SubcellularLocationComment subcellularLocation) {
        Utils.addOrIgnoreNull(subcellularLocation, this.subcellularLocations);
        return this;
    }

    public InteractionMatrixBuilder subcellularLocationsSet(
            List<SubcellularLocationComment> subcellularLocation) {
        this.subcellularLocations = Utils.modifiableList(subcellularLocation);
        return this;
    }

    @Nonnull
    @Override
    public InteractionMatrix build() {
        return new InteractionMatrixImpl(
                uniProtKBAccession,
                uniProtKBId,
                organism,
                proteinExistence,
                interactions,
                diseases,
                subcellularLocations);
    }

    public static @Nonnull InteractionMatrixBuilder from(@Nonnull InteractionMatrix instance) {
        return new InteractionMatrixBuilder()
                .uniProtKBAccession(instance.getPrimaryAccession())
                .uniProtKBId(instance.getUniProtKBId())
                .organism(instance.getOrganism())
                .proteinExistence(instance.getProteinExistence())
                .interactionsSet(instance.getInteractions())
                .diseasesSet(instance.getDiseases())
                .subcellularLocationsSet(instance.getSubcellularLocations());
    }
}
