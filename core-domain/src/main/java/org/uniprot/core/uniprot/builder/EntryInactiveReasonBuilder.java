package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprot.EntryInactiveReason;
import org.uniprot.core.uniprot.InactiveReasonType;
import org.uniprot.core.uniprot.impl.EntryInactiveReasonImpl;
import org.uniprot.core.util.Utils;

/**
 *
 * @author lgonzales
 */
public class EntryInactiveReasonBuilder implements Builder<EntryInactiveReasonBuilder, EntryInactiveReason> {
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo = new ArrayList<>();

    public EntryInactiveReasonBuilder type(InactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public EntryInactiveReasonBuilder mergeDemergeTo(List<String> mergeDemergeTo) {
        this.mergeDemergeTo = modifiableList(mergeDemergeTo);
        return this;
    }

    public EntryInactiveReasonBuilder addMergeDemergeTo(String mergeDemergeTo) {
        Utils.addOrIgnoreNull(mergeDemergeTo, this.mergeDemergeTo);
        return this;
    }

    @Override
    public EntryInactiveReason build() {
        return new EntryInactiveReasonImpl(inactiveReasonType, mergeDemergeTo);
    }

    @Override
    public EntryInactiveReasonBuilder from(EntryInactiveReason instance) {
        this.mergeDemergeTo.clear();
        this.type(instance.getInactiveReasonType());
        this.mergeDemergeTo(instance.getMergeDemergeTo());
        return this;
    }
}