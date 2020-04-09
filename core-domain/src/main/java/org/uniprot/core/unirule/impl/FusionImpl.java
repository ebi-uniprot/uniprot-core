package org.uniprot.core.unirule.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.unirule.Fusion;

public class FusionImpl implements Fusion {

    private static final long serialVersionUID = -3391845343665369638L;
    private List<String> cters;
    private List<String> nters;

    public FusionImpl(List<String> cters, List<String> nters) {
        this.cters = cters;
        this.nters = nters;
    }

    @Override
    public List<String> getCters() {
        return cters;
    }

    @Override
    public List<String> getNters() {
        return nters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FusionImpl fusion = (FusionImpl) o;
        return Objects.equals(cters, fusion.cters) && Objects.equals(nters, fusion.nters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cters, nters);
    }
}
