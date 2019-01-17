package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class CofactorBuilder implements Builder2<CofactorBuilder, Cofactor> {
    private String name;
    private List<Evidence> evidences = new ArrayList<>();
    private DBCrossReference<CofactorReferenceType> cofactorReference;

    public CofactorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CofactorBuilder evidences(List<Evidence> evidences) {
        this.evidences.addAll(evidences);
        return this;
    }

    public CofactorBuilder addEvidence(Evidence evidence) {
        this.evidences.add(evidence);
        return this;
    }

    public CofactorBuilder reference(DBCrossReference<CofactorReferenceType> cofactorReference) {
        this.cofactorReference = cofactorReference;
        return this;
    }

    @Override
    public Cofactor build() {
        return new CofactorImpl(this);
    }

    @Override
    public CofactorBuilder from(Cofactor instance) {
        return new CofactorBuilder()
                .name(instance.getName())
                .evidences(instance.getEvidences())
                .reference(instance.getCofactorReference());
    }

    public String getName() {
        return name;
    }

    public List<Evidence> getEvidences() {
        return evidences;
    }

    public DBCrossReference<CofactorReferenceType> getCofactorReference() {
        return cofactorReference;
    }
}
