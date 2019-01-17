package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CofactorImpl implements Cofactor {
    private String name;
    private List<Evidence> evidences;
    private DBCrossReference<CofactorReferenceType> cofactorReference;

    private CofactorImpl() {
        this.evidences = Collections.emptyList();
    }

    public CofactorImpl(CofactorBuilder builder) {
        this.name = builder.getName();
        if ((builder.getEvidences() == null) || builder.getEvidences().isEmpty()) {
            this.evidences = Collections.emptyList();
        } else {
            this.evidences = Collections.unmodifiableList(builder.getEvidences());
        }
        this.cofactorReference = builder.getCofactorReference();
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CofactorImpl cofactor = (CofactorImpl) o;
        return Objects.equals(name, cofactor.name) &&
                Objects.equals(evidences, cofactor.evidences) &&
                Objects.equals(cofactorReference, cofactor.cofactorReference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, evidences, cofactorReference);
    }
}
