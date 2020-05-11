package org.uniprot.core.flatfile.parser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.uniprot.core.uniprotkb.evidence.Evidence;

public interface HasEvidence extends Serializable {
    void clear();

    Collection<Evidence> getEvidences();

    void add(Collection<Evidence> ids);

    void addAll(Collection<List<Evidence>> idss);
}
