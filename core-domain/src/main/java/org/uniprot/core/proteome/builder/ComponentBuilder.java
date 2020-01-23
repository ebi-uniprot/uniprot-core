package org.uniprot.core.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.ProteomeXReferenceType;
import org.uniprot.core.proteome.impl.ComponentImpl;
import org.uniprot.core.util.Utils;

public class ComponentBuilder implements Builder<ComponentBuilder, Component> {
    private String name;
    private String description;
    private int proteinCount;
    private ComponentType type;
    private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences = new ArrayList<>();

    public static @Nonnull ComponentBuilder newInstance() {
        return new ComponentBuilder();
    }

    public @Nonnull ComponentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ComponentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public @Nonnull ComponentBuilder dbXReferences(
            List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
        this.dbXReferences = Utils.modifiableList(dbXReferences);
        return this;
    }

    public @Nonnull ComponentBuilder addDbXReference(
            DBCrossReference<ProteomeXReferenceType> dbXReference) {
        Utils.addOrIgnoreNull(dbXReference, dbXReferences);
        return this;
    }

    public @Nonnull ComponentBuilder proteinCount(int proteinCount) {
        this.proteinCount = proteinCount;
        return this;
    }

    public @Nonnull ComponentBuilder type(ComponentType type) {
        this.type = type;
        return this;
    }

    @Override
    public @Nonnull Component build() {
        return new ComponentImpl(name, description, proteinCount, type, dbXReferences);
    }

    public static @Nonnull ComponentBuilder from(@Nonnull Component instance) {
        return new ComponentBuilder()
        .name(instance.getName())
        .description(instance.getDescription())
        .proteinCount(instance.getProteinCount())
        .type(instance.getType())
        .dbXReferences(instance.getDbXReferences());
    }
}
