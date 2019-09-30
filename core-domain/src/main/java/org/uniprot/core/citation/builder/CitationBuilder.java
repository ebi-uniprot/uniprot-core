package org.uniprot.core.citation.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;

public interface CitationBuilder<B extends CitationBuilder<B, T>, T extends Citation>
        extends Builder<B, T> {}
