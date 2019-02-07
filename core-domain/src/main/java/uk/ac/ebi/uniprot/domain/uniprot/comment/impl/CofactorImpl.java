package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;

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

    public CofactorImpl(String name,
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
