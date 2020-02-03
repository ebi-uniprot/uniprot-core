package org.uniprot.core.literature.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStoreEntry;
import org.uniprot.core.literature.impl.LiteratureStoreEntryImpl;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-12-05
 */
public class LiteratureStoreEntryBuilder implements Builder<LiteratureStoreEntry> {

    private LiteratureEntry literatureEntry;
    private List<LiteratureMappedReference> literatureMappedReferences = new ArrayList<>();

    public @Nonnull LiteratureStoreEntryBuilder literatureEntry(LiteratureEntry literatureEntry) {
        this.literatureEntry = literatureEntry;
        return this;
    }

    public @Nonnull LiteratureStoreEntryBuilder literatureMappedReference(
            List<LiteratureMappedReference> literatureMappedReference) {
        this.literatureMappedReferences = Utils.modifiableList(literatureMappedReference);
        return this;
    }

    public @Nonnull LiteratureStoreEntryBuilder addLiteratureMappedReference(
            LiteratureMappedReference literatureMappedReference) {
        Utils.addOrIgnoreNull(literatureMappedReference, this.literatureMappedReferences);
        return this;
    }

    @Nonnull
    @Override
    public LiteratureStoreEntry build() {
        return new LiteratureStoreEntryImpl(literatureEntry, literatureMappedReferences);
    }

    public static @Nonnull LiteratureStoreEntryBuilder from(
            @Nonnull LiteratureStoreEntry instance) {
        return new LiteratureStoreEntryBuilder()
                .literatureEntry(instance.getLiteratureEntry())
                .literatureMappedReference(instance.getLiteratureMappedReferences());
    }
}
