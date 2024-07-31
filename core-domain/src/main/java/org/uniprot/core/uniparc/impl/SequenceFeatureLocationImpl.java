package org.uniprot.core.uniparc.impl;

import org.uniprot.core.Location;
import org.uniprot.core.uniparc.SequenceFeatureLocation;

import java.util.Objects;

public class SequenceFeatureLocationImpl extends Location implements SequenceFeatureLocation{

    private static final long serialVersionUID = -4804406936471873484L;
    private String alignment;

    SequenceFeatureLocationImpl(){
        super(0, 0);
    }

    SequenceFeatureLocationImpl(int start, int end, String alignment) {
        super(start, end);
        this.alignment = alignment;
    }

    @Override
    public String getAlignment() {
        return alignment;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SequenceFeatureLocationImpl that = (SequenceFeatureLocationImpl) o;
        return Objects.equals(alignment, that.alignment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), alignment);
    }
}
