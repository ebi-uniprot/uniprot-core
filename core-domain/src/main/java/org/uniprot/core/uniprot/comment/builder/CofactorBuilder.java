package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.comment.impl.CofactorImpl;
import org.uniprot.core.uniprot.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class CofactorBuilder implements Builder<CofactorBuilder, Cofactor> {
    private String name;
    private List<Evidence> evidences = new ArrayList<>();
    private DBCrossReference<CofactorReferenceType> cofactorReference;

    public @Nonnull CofactorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull CofactorBuilder evidences(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull CofactorBuilder addEvidence(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull CofactorBuilder reference(
            DBCrossReference<CofactorReferenceType> cofactorReference) {
        this.cofactorReference = cofactorReference;
        return this;
    }

    @Override
    public @Nonnull Cofactor build() {
        return new CofactorImpl(name, cofactorReference, evidences);
    }

    public static @Nonnull CofactorBuilder from(@Nonnull Cofactor instance) {
        return new CofactorBuilder().name(instance.getName())
                .evidences(instance.getEvidences())
                .reference(instance.getCofactorReference());
    }
}
