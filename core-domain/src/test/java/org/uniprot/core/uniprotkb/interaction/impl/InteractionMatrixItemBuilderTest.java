package org.uniprot.core.uniprotkb.interaction.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.comment.DiseaseComment;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.InteractionBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created 12/05/2020
 *
 * @author Edd
 */
class InteractionMatrixItemBuilderTest {

    private InteractionMatrixItemBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new InteractionMatrixItemBuilder();
    }

    @Test
    void canSetAccession() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("value").build();
        assertThat(
                builder.uniProtKBAccession(accession).build().getPrimaryAccession(), is(accession));
    }

    @Test
    void canSetId() {
        UniProtKBId id = new UniProtKBIdBuilder("value").build();
        assertThat(builder.uniProtKBId(id).build().getUniProtKBId(), is(id));
    }

    @Test
    void canSetProteinExistence() {
        ProteinExistence proteinExistence = ProteinExistence.HOMOLOGY;
        assertThat(
                builder.proteinExistence(proteinExistence).build().getProteinExistence(),
                is(proteinExistence));
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().build();
        assertThat(builder.organism(organism).build().getOrganism(), is(organism));
    }

    @Test
    void canAddInteraction() {
        Interaction interaction = new InteractionBuilder().build();
        assertThat(
                builder.interactionsAdd(interaction).build().getInteractions(),
                is(singletonList(interaction)));
    }

    @Test
    void canSetInteractions() {
        List<Interaction> interactions = singletonList(new InteractionBuilder().build());
        assertThat(
                builder.interactionsSet(interactions).build().getInteractions(), is(interactions));
    }

    @Test
    void canAddDisease() {
        DiseaseComment diseaseComment = new DiseaseCommentBuilder().build();
        assertThat(
                builder.diseasesAdd(diseaseComment).build().getDiseases(),
                is(singletonList(diseaseComment)));
    }

    @Test
    void canSetDiseases() {
        List<DiseaseComment> diseaseComments = singletonList(new DiseaseCommentBuilder().build());
        assertThat(builder.diseasesSet(diseaseComments).build().getDiseases(), is(diseaseComments));
    }

    @Test
    void canAddSubcellularLocation() {
        SubcellularLocationComment subcellularLocation =
                new SubcellularLocationCommentBuilder().build();
        assertThat(
                builder.subcellularLocationsAdd(subcellularLocation)
                        .build()
                        .getSubcellularLocations(),
                is(singletonList(subcellularLocation)));
    }

    @Test
    void canSetSubcellularLocations() {
        List<SubcellularLocationComment> subcellularLocations =
                singletonList(new SubcellularLocationCommentBuilder().build());
        assertThat(
                builder.subcellularLocationsSet(subcellularLocations)
                        .build()
                        .getSubcellularLocations(),
                is(subcellularLocations));
    }

    @Test
    void fromWorksAsExpected() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("value").build();
        InteractionMatrixItem matrixItem = builder.uniProtKBAccession(accession).build();

        InteractionMatrixItem fromMatrixItem = InteractionMatrixItemBuilder.from(matrixItem).build();

        assertThat(fromMatrixItem, is(matrixItem));
    }
}
