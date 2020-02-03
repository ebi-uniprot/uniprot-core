package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Value;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.APIsoformImpl;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class APIsoformBuilder implements Builder<APIsoform> {
    private IsoformName name;
    private List<IsoformName> synonyms = new ArrayList<>();
    private List<IsoformId> isoformIds = new ArrayList<>();
    private List<String> sequenceIds = new ArrayList<>();
    private Note note;
    private IsoformSequenceStatus isoformSequenceStatus;

    public @Nonnull APIsoform build() {
        return new APIsoformImpl(
                name, synonyms, note, isoformIds, sequenceIds, isoformSequenceStatus);
    }

    public static @Nonnull APIsoformBuilder from(@Nonnull APIsoform instance) {
        return new APIsoformBuilder()
                .sequenceIds(instance.getSequenceIds())
                .ids(
                        instance.getIsoformIds().stream()
                                .map(Value::getValue)
                                .collect(Collectors.toList()))
                .note(instance.getNote())
                .synonyms(instance.getSynonyms())
                .sequenceStatus(instance.getIsoformSequenceStatus())
                .name(instance.getName());
    }

    public @Nonnull APIsoformBuilder name(IsoformName name) {
        this.name = name;
        return this;
    }

    public @Nonnull APIsoformBuilder synonyms(List<IsoformName> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return this;
    }

    public @Nonnull APIsoformBuilder addSynonym(IsoformName synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull APIsoformBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public @Nonnull APIsoformBuilder ids(List<String> isoformIds) {
        isoformIds.stream().map(APIsoformImpl.IsoformIdImpl::new).forEach(this.isoformIds::add);
        return this;
    }

    public @Nonnull APIsoformBuilder addId(String isoformId) {
        if (isoformId != null) this.isoformIds.add(new APIsoformImpl.IsoformIdImpl(isoformId));
        return this;
    }

    public @Nonnull APIsoformBuilder sequenceIds(List<String> sequenceIds) {
        this.sequenceIds = modifiableList(sequenceIds);
        return this;
    }

    public @Nonnull APIsoformBuilder addSequenceId(String sequenceId) {
        addOrIgnoreNull(sequenceId, this.sequenceIds);
        return this;
    }

    public @Nonnull APIsoformBuilder sequenceStatus(IsoformSequenceStatus isoformSequenceStatus) {
        this.isoformSequenceStatus = isoformSequenceStatus;
        return this;
    }
}
