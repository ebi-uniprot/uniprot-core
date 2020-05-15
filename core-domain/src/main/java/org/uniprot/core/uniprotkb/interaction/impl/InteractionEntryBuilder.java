package org.uniprot.core.uniprotkb.interaction.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.interaction.InteractionEntry;
import org.uniprot.core.uniprotkb.interaction.InteractionMatrixItem;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created 11/05/2020
 *
 * @author Edd
 */
public class InteractionEntryBuilder implements Builder<InteractionEntry> {
    private List<InteractionMatrixItem> interactionMatrix = new ArrayList<>();

    public @Nonnull InteractionEntryBuilder interactionsAdd(InteractionMatrixItem interactionMatrixItem) {
        Utils.addOrIgnoreNull(interactionMatrixItem, this.interactionMatrix);
        return this;
    }

    public @Nonnull InteractionEntryBuilder interactionsSet(List<InteractionMatrixItem> interactions) {
        this.interactionMatrix = Utils.modifiableList(interactions);
        return this;
    }

    @Nonnull
    @Override
    public InteractionEntry build() {
        return new InteractionEntryImpl(interactionMatrix);
    }

    public static @Nonnull InteractionEntryBuilder from(@Nonnull InteractionEntry instance) {
        return new InteractionEntryBuilder().interactionsSet(instance.getInteractionMatrix());
    }
}
