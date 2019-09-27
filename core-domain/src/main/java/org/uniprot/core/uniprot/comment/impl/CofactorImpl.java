package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class CofactorImpl implements Cofactor {
    private static final long serialVersionUID = -1446627008409869566L;
    private String name;
    private List<Evidence> evidences;
    private DBCrossReference<CofactorReferenceType> cofactorReference;

    private CofactorImpl() {
        this.evidences = Collections.emptyList();
    }

    public CofactorImpl(
            String name,
            DBCrossReference<CofactorReferenceType> cofactorReference,
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
        return Utils.notEmpty(this.evidences);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DBCrossReference<CofactorReferenceType> getCofactorReference() {
        return cofactorReference;
    }

    @Override
    public boolean hasName() {
        return Utils.notEmpty(this.name);
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
