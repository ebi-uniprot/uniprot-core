package org.uniprot.core.uniprotkb.interaction.impl;

import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.util.List;
import java.util.Objects;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public class InteractionMatrixItemImpl implements InteractionMatrixItem {
    private static final long serialVersionUID = -1742579668649931897L;
    private final UniProtKBAccession uniProtKBAccession;
    private final UniProtKBId uniProtKBId;
    private final Organism organism;
    private final ProteinExistence proteinExistence;
    private final List<Interaction> interactions;
    private final List<DiseaseComment> diseases;
    private final List<SubcellularLocationComment> subcellularLocations;

    // no arg constructor for JSON deserialization
    InteractionMatrixItemImpl() {
        this(null, null, null, null, null, null, null);
    }

    InteractionMatrixItemImpl(
            UniProtKBAccession uniProtKBAccession,
            UniProtKBId uniProtKBId,
            Organism organism,
            ProteinExistence proteinExistence,
            List<Interaction> interactions,
            List<DiseaseComment> diseases,
            List<SubcellularLocationComment> subcellularLocations) {
        this.uniProtKBAccession = uniProtKBAccession;
        this.uniProtKBId = uniProtKBId;
        this.organism = organism;
        this.proteinExistence = proteinExistence;
        this.interactions = interactions;
        this.diseases = diseases;
        this.subcellularLocations = subcellularLocations;
    }

    @Override
    public UniProtKBAccession getPrimaryAccession() {
        return uniProtKBAccession;
    }

    @Override
    public UniProtKBId getUniProtKBId() {
        return uniProtKBId;
    }

    @Override
    public Organism getOrganism() {
        return organism;
    }

    @Override
    public ProteinExistence getProteinExistence() {
        return proteinExistence;
    }

    @Override
    public List<Interaction> getInteractions() {
        return interactions;
    }

    @Override
    public List<DiseaseComment> getDiseases() {
        return diseases;
    }

    @Override
    public List<SubcellularLocationComment> getSubcellularLocations() {
        return subcellularLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InteractionMatrixItemImpl that = (InteractionMatrixItemImpl) o;
        return Objects.equals(uniProtKBAccession, that.uniProtKBAccession)
                && Objects.equals(uniProtKBId, that.uniProtKBId)
                && Objects.equals(organism, that.organism)
                && proteinExistence == that.proteinExistence
                && Objects.equals(interactions, that.interactions)
                && Objects.equals(diseases, that.diseases)
                && Objects.equals(subcellularLocations, that.subcellularLocations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniProtKBAccession,
                uniProtKBId,
                organism,
                proteinExistence,
                interactions,
                diseases,
                subcellularLocations);
    }
}
