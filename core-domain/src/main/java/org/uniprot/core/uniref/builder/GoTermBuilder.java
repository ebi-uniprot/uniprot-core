package org.uniprot.core.uniref.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.GoTermType;
import org.uniprot.core.uniref.impl.GoTermImpl;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class GoTermBuilder implements Builder<GoTermBuilder, GoTerm> {

    private GoTermType type;
    private String id;

    @Override
    public GoTerm build() {
        return new GoTermImpl(type, id);
    }

    public GoTermBuilder type(GoTermType type) {
        this.type = type;
        return this;
    }

    public GoTermBuilder id(String id) {
        this.id = id;
        return this;
    }

    @Override
    public GoTermBuilder from(GoTerm instance) {
        return this.type(instance.getType()).id(instance.getId());
    }
}
