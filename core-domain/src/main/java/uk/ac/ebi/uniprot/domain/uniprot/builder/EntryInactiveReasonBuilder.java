package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.EntryInactiveReason;
import uk.ac.ebi.uniprot.domain.uniprot.InactiveReasonType;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EntryInactiveReasonImpl;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lgonzales
 */
public class EntryInactiveReasonBuilder implements Builder2<EntryInactiveReasonBuilder, EntryInactiveReason> {
    private InactiveReasonType inactiveReasonType;
    private List<String> mergeDemergeTo = new ArrayList<>();

    public EntryInactiveReasonBuilder type(InactiveReasonType inactiveReasonType) {
        this.inactiveReasonType = inactiveReasonType;
        return this;
    }

    public EntryInactiveReasonBuilder mergeDemergeTo(List<String> mergeDemergeTo) {
        Utils.nonNullAddAll(mergeDemergeTo,this.mergeDemergeTo);
        return this;
    }

    public EntryInactiveReasonBuilder addMergeDemergeTo(String mergeDemergeTo) {
        Utils.nonNullAdd(mergeDemergeTo,this.mergeDemergeTo);
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