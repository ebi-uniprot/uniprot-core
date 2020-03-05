package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorDatabase;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class CofactorImpl implements Cofactor {
    private static final long serialVersionUID = -1446627008409869566L;
    private String name;
    private List<Evidence> evidences;
    private CrossReference<CofactorDatabase> cofactorReference;

    // no arg constructor for JSON deserialization
    CofactorImpl() {
        this.evidences = Collections.emptyList();
    }

    public CofactorImpl(
            String name,
            CrossReference<CofactorDatabase> cofactorReference,
            List<Evidence> evidences) {
        this.name = name;
        if ((evidences == null) || evidences.isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(evidences);
        }
        this.cofactorReference = cofactorReference;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CrossReference<CofactorDatabase> getCofactorReference() {
        return cofactorReference;
    }

    @Override
    public boolean hasName() {
        return Utils.notNullNotEmpty(this.name);
    }

    @Override
    public boolean hasCofactorReference() {
        return this.cofactorReference != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CofactorImpl cofactor = (CofactorImpl) o;
        return Objects.equals(name, cofactor.name)
                && Objects.equals(evidences, cofactor.evidences)
                && Objects.equals(cofactorReference, cofactor.cofactorReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, evidences, cofactorReference);
    }
}
