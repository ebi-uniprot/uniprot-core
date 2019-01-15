package uk.ac.ebi.uniprot.domain.uniprot.comment2.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.builder.CofactorBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CofactorImpl implements Cofactor, Serializable {
    private static final long serialVersionUID = -117992467062656339L;
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
