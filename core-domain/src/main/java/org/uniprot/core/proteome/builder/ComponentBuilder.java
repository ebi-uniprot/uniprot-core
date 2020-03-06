package org.uniprot.core.proteome.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.CrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.ComponentType;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.impl.ComponentImpl;
import org.uniprot.core.util.Utils;

public class ComponentBuilder implements Builder<Component> {
    private String name;
    private String description;
    private int proteinCount;
    private ComponentType type;
    private List<CrossReference<ProteomeDatabase>> proteomeCrossReferences = new ArrayList<>();

    public @Nonnull ComponentBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ComponentBuilder description(String description) {
        this.description = description;
        return this;
    }

    public @Nonnull ComponentBuilder proteomeCrossReferencesSet(
            List<CrossReference<ProteomeDatabase>> proteomeCrossReferences) {
        this.proteomeCrossReferences = Utils.modifiableList(proteomeCrossReferences);
        return this;
    }

    public @Nonnull ComponentBuilder proteomeCrossReferencesAdd(
            CrossReference<ProteomeDatabase> proteomeCrossReference) {
        Utils.addOrIgnoreNull(proteomeCrossReference, proteomeCrossReferences);
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
        return new ComponentImpl(name, description, proteinCount, type, proteomeCrossReferences);
    }

    public static @Nonnull ComponentBuilder from(@Nonnull Component instance) {
        return new ComponentBuilder()
                .name(instance.getName())
                .description(instance.getDescription())
                .proteinCount(instance.getProteinCount())
                .type(instance.getType())
                .proteomeCrossReferencesSet(instance.getProteomeCrossReferences());
    }
}
