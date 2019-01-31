package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAdd;
import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullList;

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
        this.evidences = nonNullList(evidences);
        return this;
    }

    public CofactorBuilder addEvidence(Evidence evidence) {
        nonNullAdd(evidence, this.evidences);
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
        evidences.clear();
        return this
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
