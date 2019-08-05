package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.CofactorImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import static org.uniprot.core.common.Utils.nonNullAdd;
import static org.uniprot.core.common.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class CofactorBuilder implements Builder<CofactorBuilder, Cofactor> {
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
        return new CofactorImpl(name, cofactorReference, evidences);
    }

    @Override
    public CofactorBuilder from(Cofactor instance) {
        evidences.clear();
        return this
                .name(instance.getName())
                .evidences(instance.getEvidences())
                .reference(instance.getCofactorReference());
    }
}
