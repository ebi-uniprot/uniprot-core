package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.DeletedReason;
import org.uniprot.core.uniprotkb.EntryInactiveReason;
import org.uniprot.core.uniprotkb.InactiveReasonType;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class EntryInactiveReasonBuilder implements Builder<EntryInactiveReason> {
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTos = new ArrayList<>();

    private DeletedReason deletedReason;

    public @Nonnull EntryInactiveReasonBuilder type(InactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public @Nonnull EntryInactiveReasonBuilder deletedReason(DeletedReason deletedReason) {
        this.deletedReason = deletedReason;
        return this;
    }

    public @Nonnull EntryInactiveReasonBuilder mergeDemergeTosSet(List<String> mergeDemergeTo) {
        this.mergeDemergeTos = modifiableList(mergeDemergeTo);
        return this;
    }

    public @Nonnull EntryInactiveReasonBuilder mergeDemergeTosAdd(String mergeDemergeTo) {
        Utils.addOrIgnoreNull(mergeDemergeTo, this.mergeDemergeTos);
        return this;
    }

    @Override
    public @Nonnull EntryInactiveReason build() {
        return new EntryInactiveReasonImpl(inactiveReasonType, mergeDemergeTos, deletedReason);
    }

    public static @Nonnull EntryInactiveReasonBuilder from(@Nonnull EntryInactiveReason instance) {
        return new EntryInactiveReasonBuilder()
                .type(instance.getInactiveReasonType())
                .mergeDemergeTosSet(instance.getMergeDemergeTos())
                .deletedReason(instance.getDeletedReason());
    }
}
