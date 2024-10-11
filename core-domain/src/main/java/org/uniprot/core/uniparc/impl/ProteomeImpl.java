package org.uniprot.core.uniparc.impl;

import org.uniprot.core.uniparc.Proteome;

import java.util.Objects;

public class ProteomeImpl implements Proteome {

    private static final long serialVersionUID = -7947022870498125809L;

    private String id;
    private String component;

    ProteomeImpl() {
    }

    ProteomeImpl(String id, String component) {
        this.id = id;
        this.component = component;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getComponent() {
        return component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProteomeImpl proteome = (ProteomeImpl) o;
        return Objects.equals(id, proteome.id) && Objects.equals(component, proteome.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, component);
    }
}
