package org.uniprot.core.proteome.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.proteome.Component;
import org.uniprot.core.proteome.GenomeAnnotation;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.util.Utils;

public class ComponentImpl implements Component {
    private static final long serialVersionUID = -5592878122341180241L;
    private final String name;
    private final String description;
    private final Integer proteinCount;
    private final GenomeAnnotation genomeAnnotation;
    private final List<CrossReference<ProteomeDatabase>> proteomeCrossReferences;

    ComponentImpl() {
        this(null, null,  null, null, null);
    }

    public ComponentImpl(
            String name,
            String description,
            Integer proteinCount,
            GenomeAnnotation genomeAnnotation,
            List<CrossReference<ProteomeDatabase>> proteomeCrossReferences) {
        this.name = name;
        this.description = description;
        this.proteinCount = proteinCount;
        this.genomeAnnotation = genomeAnnotation;
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
    public Integer getProteinCount() {
        return proteinCount;
    }

    @Override
    public GenomeAnnotation getGenomeAnnotation() {
        return genomeAnnotation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, proteinCount, proteomeCrossReferences, genomeAnnotation);
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
                && Objects.equals(genomeAnnotation, other.genomeAnnotation)
                && Objects.equals(proteomeCrossReferences, other.proteomeCrossReferences);
    }
}
