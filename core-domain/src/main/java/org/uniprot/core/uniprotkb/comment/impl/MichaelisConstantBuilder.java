package org.uniprot.core.uniprotkb.comment.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.comment.MichaelisConstant;
import org.uniprot.core.uniprotkb.comment.MichaelisConstantUnit;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public final class MichaelisConstantBuilder implements Builder<MichaelisConstant> {
    private double constant;
    private MichaelisConstantUnit unit;
    private String substrate;
    private List<Evidence> evidences = new ArrayList<>();

    public @Nonnull MichaelisConstantBuilder constant(double constant) {
        this.constant = constant;
        return this;
    }

    public @Nonnull MichaelisConstantBuilder unit(MichaelisConstantUnit unit) {
        this.unit = unit;
        return this;
    }

    public @Nonnull MichaelisConstantBuilder substrate(String substrate) {
        this.substrate = substrate;
        return this;
    }

    public @Nonnull MichaelisConstantBuilder evidencesSet(List<Evidence> evidences) {
        this.evidences = modifiableList(evidences);
        return this;
    }

    public @Nonnull MichaelisConstantBuilder evidencesAdd(Evidence evidence) {
        addOrIgnoreNull(evidence, this.evidences);
        return this;
    }

    @Override
    public @Nonnull MichaelisConstant build() {
        return new MichaelisConstantImpl(constant, unit, substrate, evidences);
    }

    public static @Nonnull MichaelisConstantBuilder from(@Nonnull MichaelisConstant instance) {
        return new MichaelisConstantBuilder()
                .evidencesSet(instance.getEvidences())
                .constant(instance.getConstant())
                .substrate(instance.getSubstrate())
                .unit(instance.getUnit());
    }
}
