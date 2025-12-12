package org.uniprot.core.uniparc.impl;

import org.uniprot.core.uniparc.ProteomeIdComponent;

import java.util.Objects;

public class ProteomeIdComponentImpl implements ProteomeIdComponent {
    private static final long serialVersionUID = 2650285931942827368L;
    private final String proteomeId;
    private final String component;

    //for the deserializer
    public ProteomeIdComponentImpl() {
        this(null, null);
    }

    ProteomeIdComponentImpl(String proteomeId, String component) {
        this.proteomeId = proteomeId;
        this.component = component;
    }

    @Override
    public String getProteomeId() {
        return proteomeId;
    }

    @Override
    public String getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProteomeIdComponentImpl that = (ProteomeIdComponentImpl) o;
        return Objects.equals(proteomeId, that.proteomeId) && Objects.equals(component, that.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proteomeId, component);
    }
}
