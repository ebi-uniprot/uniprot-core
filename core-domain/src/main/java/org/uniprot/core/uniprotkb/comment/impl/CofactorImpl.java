package org.uniprot.core.uniprotkb.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class CofactorImpl implements Cofactor {
    private static final long serialVersionUID = -1446627008409869566L;
    private String name;
    private List<Evidence> evidences;
    private CrossReference<CofactorDatabase> cofactorCrossReference;

    // no arg constructor for JSON deserialization
    CofactorImpl() {
        this.evidences = Collections.emptyList();
    }

    CofactorImpl(
            String name,
            CrossReference<CofactorDatabase> cofactorCrossReference,
            List<Evidence> evidences) {
        this.name = name;
        this.evidences = Utils.unmodifiableList(evidences);
        this.cofactorCrossReference = cofactorCrossReference;
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
    public CrossReference<CofactorDatabase> getCofactorCrossReference() {
        return cofactorCrossReference;
    }

    @Override
    public boolean hasName() {
        return Utils.notNullNotEmpty(this.name);
    }

    @Override
    public boolean hasCofactorCrossReference() {
        return this.cofactorCrossReference != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CofactorImpl cofactor = (CofactorImpl) o;
        return Objects.equals(name, cofactor.name)
                && Objects.equals(evidences, cofactor.evidences)
                && Objects.equals(cofactorCrossReference, cofactor.cofactorCrossReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, evidences, cofactorCrossReference);
    }
}
