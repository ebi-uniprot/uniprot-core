package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.uniprotkb.comment.Cofactor;
import org.uniprot.core.uniprotkb.comment.CofactorDatabase;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class CofactorBuilder implements Builder<Cofactor> {
    private String name;
    private List<Evidence> evidences = new ArrayList<>();
    private CrossReference<CofactorDatabase> cofactorCrossReference;

    public @Nonnull CofactorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull CofactorBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull CofactorBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    public @Nonnull CofactorBuilder cofactorCrossReference(
            CrossReference<CofactorDatabase> cofactorCrossReference) {
        this.cofactorCrossReference = cofactorCrossReference;
        return this;
    }

    @Override
    public @Nonnull Cofactor build() {
        return new CofactorImpl(name, cofactorCrossReference, evidences);
    }

    public static @Nonnull CofactorBuilder from(@Nonnull Cofactor instance) {
        return new CofactorBuilder()
                .name(instance.getName())
                .evidencesSet(instance.getEvidences())
                .cofactorCrossReference(instance.getCofactorCrossReference());
    }
}
