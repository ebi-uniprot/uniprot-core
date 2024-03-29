package org.uniprot.core.feature.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.feature.Feature;
import org.uniprot.core.feature.FeatureDatabase;
import org.uniprot.core.feature.FeatureDescription;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.feature.FeatureType;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
public abstract class AbstractFeatureBuilder<
                B extends AbstractFeatureBuilder<B, T, D, F>,
                T extends Feature<D, F>,
                D extends FeatureDatabase,
                F extends FeatureType>
        implements Builder<T> {

    protected F type;
    protected FeatureLocation location;
    protected FeatureDescription description;
    protected List<CrossReference<D>> featureCrossReferences = new ArrayList<>();
    protected List<Evidence> evidences = new ArrayList<>();

    public @Nonnull B type(F type) {
        this.type = type;
        return getThis();
    }

    public @Nonnull B location(FeatureLocation location) {
        this.location = location;
        return getThis();
    }

    public @Nonnull B description(String description) {
        this.description = new FeatureDescriptionImpl(description);
        return getThis();
    }

    public @Nonnull B description(FeatureDescription description) {
        this.description = description;
        return getThis();
    }

    public @Nonnull B featureCrossReferenceAdd(CrossReference<D> featureCrossReference) {
        addOrIgnoreNull(featureCrossReference, this.featureCrossReferences);
        return getThis();
    }

    public @Nonnull B featureCrossReferenceSet(List<CrossReference<D>> featureCrossReferences) {
        this.featureCrossReferences = modifiableList(featureCrossReferences);
        return getThis();
    }

    public @Nonnull B evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return getThis();
    }

    public @Nonnull B evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return getThis();
    }

    protected abstract @Nonnull B getThis();

    protected static <
                    B extends AbstractFeatureBuilder<B, T, D, F>,
                    T extends Feature<D, F>,
                    D extends FeatureDatabase,
                    F extends FeatureType>
            B from(@Nonnull B builder, @Nonnull T instance) {
        return builder.type(instance.getType())
                .location(instance.getLocation())
                .description(instance.getDescription())
                .featureCrossReferenceSet(instance.getFeatureCrossReferences())
                .evidencesSet(instance.getEvidences());
    }
}
