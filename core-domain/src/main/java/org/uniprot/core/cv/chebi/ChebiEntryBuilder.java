package org.uniprot.core.cv.chebi;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.chebi.impl.ChebiEntryImpl;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ChebiEntryBuilder implements Builder<ChebiEntry> {
    private String id;
    private String name;
    private String inchiKey;

    public @Nonnull ChebiEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull ChebiEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ChebiEntryBuilder inchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
        return this;
    }

    public @Nonnull ChebiEntry build() {
        return new ChebiEntryImpl(id, name, inchiKey);
    }

    public static @Nonnull ChebiEntryBuilder from(@Nonnull ChebiEntry instance) {
        return new ChebiEntryBuilder()
          .id(instance.getId())
          .name(instance.getName())
          .inchiKey(instance.getInchiKey());
    }
}
