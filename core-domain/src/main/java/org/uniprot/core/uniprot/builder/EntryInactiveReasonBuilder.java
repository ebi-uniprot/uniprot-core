package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.EntryInactiveReason;
import org.uniprot.core.uniprot.InactiveReasonType;
import org.uniprot.core.uniprot.impl.EntryInactiveReasonImpl;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class EntryInactiveReasonBuilder
        implements Builder<EntryInactiveReasonBuilder, EntryInactiveReason> {
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo = new ArrayList<>();

    public @Nonnull EntryInactiveReasonBuilder type(InactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public @Nonnull EntryInactiveReasonBuilder mergeDemergeTo(List<String> mergeDemergeTo) {
        this.mergeDemergeTo = modifiableList(mergeDemergeTo);
        return this;
    }

    public @Nonnull EntryInactiveReasonBuilder addMergeDemergeTo(String mergeDemergeTo) {
        Utils.addOrIgnoreNull(mergeDemergeTo, this.mergeDemergeTo);
        return this;
    }

    @Override
    public @Nonnull EntryInactiveReason build() {
        return new EntryInactiveReasonImpl(inactiveReasonType, mergeDemergeTo);
    }

    public static @Nonnull EntryInactiveReasonBuilder from(@Nonnull EntryInactiveReason instance) {
        return new EntryInactiveReasonBuilder()
        .type(instance.getInactiveReasonType())
        .mergeDemergeTo(instance.getMergeDemergeTo());
    }
}
