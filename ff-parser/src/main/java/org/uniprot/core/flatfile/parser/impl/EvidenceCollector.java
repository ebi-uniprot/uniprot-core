package org.uniprot.core.flatfile.parser.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.uniprot.core.flatfile.parser.HasEvidence;
import org.uniprot.core.uniprot.evidence.Evidence;

public class EvidenceCollector implements HasEvidence {
    private Collection<Evidence> evidences = new HashSet<>();

    public Collection<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public void clear() {
        this.evidences.clear();
    }

    @Override
    public void add(Collection<Evidence> evidences) {
        this.evidences.addAll(evidences);
    }

    @Override
    public void addAll(Collection<List<Evidence>> evidences) {
        for (List<Evidence> ids : evidences) {
            add(ids);
        }
    }
}
