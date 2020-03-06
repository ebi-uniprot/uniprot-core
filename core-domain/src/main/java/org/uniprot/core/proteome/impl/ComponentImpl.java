package org.uniprot.core.proteome.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.util.Utils;

public class ComponentImpl implements Component {
    private static final long serialVersionUID = -5592878122341180241L;
    private String name;
    private String description;
    private int proteinCount;
    private ComponentType type;

    private List<CrossReference<ProteomeDatabase>> proteomeCrossReferences;

    protected ComponentImpl() {
        proteomeCrossReferences = Collections.emptyList();
    }

    public ComponentImpl(
            String name,
            String description,
            int proteinCount,
            ComponentType type,
            List<CrossReference<ProteomeDatabase>> proteomeCrossReferences) {
        this.name = name;
        this.description = description;
        this.proteinCount = proteinCount;
        this.type = type;
        this.proteomeCrossReferences = Utils.unmodifiableList(proteomeCrossReferences);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<CrossReference<ProteomeDatabase>> getProteomeCrossReferences() {
        return proteomeCrossReferences;
    }

    @Override
    public int getProteinCount() {
        return proteinCount;
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, proteinCount, proteomeCrossReferences, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ComponentImpl other = (ComponentImpl) obj;
        return Objects.equals(name, other.name)
                && Objects.equals(description, other.description)
                && Objects.equals(proteinCount, other.proteinCount)
                && Objects.equals(type, other.type)
                && Objects.equals(proteomeCrossReferences, other.proteomeCrossReferences);
    }
}
