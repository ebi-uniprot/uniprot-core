package org.uniprot.core.flatfile.parser;

import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface HasEvidence extends Serializable {
    void clear();

    Collection<Evidence> getEvidences();

    void add(Collection<Evidence> ids);

    void addAll(Collection<List<Evidence>> idss);
}
