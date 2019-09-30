package org.uniprot.core.flatfile.writer;

public interface FFLineBuilder<F> {
    FFLine build(F f);

    FFLine buildWithEvidence(F f);

    String buildString(F f);

    String buildStringWithEvidence(F f);
}
