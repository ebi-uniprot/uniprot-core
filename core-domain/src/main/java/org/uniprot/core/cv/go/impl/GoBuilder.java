package org.uniprot.core.cv.go.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.go.Go;
import org.uniprot.core.cv.go.GoAspect;
import org.uniprot.core.cv.go.GoEvidenceType;

/**
 * @author jluo
 * @date: 8 Apr 2021
 */
public class GoBuilder implements Builder<Go> {
    private String id;
    private String name;
    private GoAspect aspect;
    private GoEvidenceType goEvidenceType;
    private String goEvidenceSource;

    public @Nonnull GoBuilder id(String goId) {
        this.id = goId;
        return this;
    }

    public @Nonnull GoBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull GoBuilder aspect(GoAspect aspect) {
        this.aspect = aspect;
        return this;
    }

    public @Nonnull GoBuilder goEvidenceType(GoEvidenceType goEvidenceType) {
        this.goEvidenceType = goEvidenceType;
        return this;
    }

    public @Nonnull GoBuilder goEvidenceSource(String goEvidenceSource) {
        this.goEvidenceSource = goEvidenceSource;
        return this;
    }

    @Override
    public Go build() {
        return new GoImpl(id, name, aspect, goEvidenceType, goEvidenceSource);
    }

    public static @Nonnull GoBuilder from(@Nonnull Go instance) {
        return new GoBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .aspect(instance.getAspect())
                .goEvidenceType(instance.getGoEvidenceType())
                .goEvidenceSource(instance.getGoEvidenceSource());
    }
}
