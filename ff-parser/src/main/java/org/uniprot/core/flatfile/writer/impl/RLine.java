package org.uniprot.core.flatfile.writer.impl;

import java.util.List;

public interface RLine<T> {
    public List<String> buildLine(T f, boolean includeFFMarkup, boolean showEvidence);
}
