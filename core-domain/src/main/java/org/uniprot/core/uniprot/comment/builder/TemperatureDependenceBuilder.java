package org.uniprot.core.uniprot.comment.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.uniprot.comment.TemperatureDependence;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentImpl;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public class TemperatureDependenceBuilder
        extends AbstractFreeTextBuilder<TemperatureDependenceBuilder, TemperatureDependence> {
    public TemperatureDependenceBuilder(List<EvidencedValue> evidencedValues) {
        this.evidencedValues = modifiableList(evidencedValues);
    }

    @Override
    protected @Nonnull TemperatureDependence createConcreteInstance() {
        return new BPCPCommentImpl.TemperatureDependenceImpl(evidencedValues);
    }

    public static @Nonnull TemperatureDependenceBuilder from(@Nonnull TemperatureDependence instance) {
        return new TemperatureDependenceBuilder(instance.getTexts());
    }
}
