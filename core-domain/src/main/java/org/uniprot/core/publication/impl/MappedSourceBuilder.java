package org.uniprot.core.publication.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.publication.MappedSource;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableSet;

/**
 * Created 03/12/2020
 *
 * @author Edd
 */
public class MappedSourceBuilder implements Builder<MappedSource> {
    private String source;
    private String sourceId;

    public MappedSourceBuilder source(String source) {
        this.source = source;
        return this;
    }

    public MappedSourceBuilder sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    @Nonnull
    @Override
    public MappedSource build() {
        return new MappedSourceImpl(source, sourceId);
    }

    public static MappedSourceBuilder from(@Nonnull MappedSource instance) {
        return new MappedSourceBuilder()
                .source(instance.getSource())
                .sourceId(instance.getSourceId());
    }
}
