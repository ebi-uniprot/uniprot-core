package org.uniprot.core.cv.ec;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.ec.impl.ECEntryImpl;

import javax.annotation.Nonnull;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ECEntryBuilder implements Builder<ECEntry> {
    private String id;
    private String label;

    public @Nonnull ECEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull ECEntryBuilder label(String label) {
        this.label = label;
        return this;
    }

    public @Nonnull ECEntry build() {
        return new ECEntryImpl(id, label);
    }

    public static @Nonnull ECEntryBuilder from(@Nonnull ECEntry instance) {
        return new ECEntryBuilder()
          .id(instance.getId())
          .label(instance.getLabel());
    }
}
