package org.uniprot.core.uniprotkb.interaction.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.util.Utils;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public class InteractionMatrixItemBuilder implements Builder<InteractionMatrixItem> {
    private UniProtKBAccession uniProtKBAccession;
    private UniProtKBId uniProtKBId;
    private Organism organism;
    private ProteinExistence proteinExistence;
    private List<Interaction> interactions = new ArrayList<>();
    private List<DiseaseComment> diseases = new ArrayList<>();
    private List<SubcellularLocationComment> subcellularLocations = new ArrayList<>();

    public @Nonnull InteractionMatrixItemBuilder uniProtKBAccession(
            UniProtKBAccession uniProtKBAccession) {
        this.uniProtKBAccession = uniProtKBAccession;
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder uniProtKBId(UniProtKBId uniProtKBId) {
        this.uniProtKBId = uniProtKBId;
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder proteinExistence(
            ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder interactionsAdd(Interaction interaction) {
        Utils.addOrIgnoreNull(interaction, this.interactions);
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder interactionsSet(List<Interaction> interactions) {
        this.interactions = Utils.modifiableList(interactions);
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder diseasesAdd(DiseaseComment disease) {
        Utils.addOrIgnoreNull(disease, this.diseases);
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder diseasesSet(List<DiseaseComment> diseases) {
        this.diseases = Utils.modifiableList(diseases);
        return this;
    }

    public @Nonnull InteractionMatrixItemBuilder subcellularLocationsAdd(
            SubcellularLocationComment subcellularLocation) {
        Utils.addOrIgnoreNull(subcellularLocation, this.subcellularLocations);
        return this;
    }

    public InteractionMatrixItemBuilder subcellularLocationsSet(
            List<SubcellularLocationComment> subcellularLocation) {
        this.subcellularLocations = Utils.modifiableList(subcellularLocation);
        return this;
    }

    @Nonnull
    @Override
    public InteractionMatrixItem build() {
        return new InteractionMatrixItemImpl(
                uniProtKBAccession,
                uniProtKBId,
                organism,
                proteinExistence,
                interactions,
                diseases,
                subcellularLocations);
    }

    public static @Nonnull InteractionMatrixItemBuilder from(
            @Nonnull InteractionMatrixItem instance) {
        return new InteractionMatrixItemBuilder()
                .uniProtKBAccession(instance.getPrimaryAccession())
                .uniProtKBId(instance.getUniProtKBId())
                .organism(instance.getOrganism())
                .proteinExistence(instance.getProteinExistence())
                .interactionsSet(instance.getInteractions())
                .diseasesSet(instance.getDiseases())
                .subcellularLocationsSet(instance.getSubcellularLocations());
    }
}
