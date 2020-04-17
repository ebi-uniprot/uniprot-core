package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.GenomeAssembly;
import org.uniprot.core.proteome.GenomeAssemblyLevel;
import org.uniprot.core.proteome.GenomeAssemblySource;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
public class GenomeAssemblyImpl implements GenomeAssembly {

    private static final long serialVersionUID = 4581433599122268512L;
    private String assemblyId;
    private String genomeAssemblyUrl;
    private GenomeAssemblyLevel level;
    private GenomeAssemblySource source;

    GenomeAssemblyImpl() {}

    GenomeAssemblyImpl(
            String assemblyId,
            String genomeAssemblyUrl,
            GenomeAssemblyLevel level,
            GenomeAssemblySource source) {
        this.assemblyId = Utils.emptyOrString(assemblyId);
        this.genomeAssemblyUrl = Utils.emptyOrString(genomeAssemblyUrl);
        this.level = level;
        this.source = source;
    }

    @Override
    public String getAssemblyId() {
        return assemblyId;
    }

    @Override
    public String getGenomeAssemblyUrl() {
        return genomeAssemblyUrl;
    }

    @Override
    public GenomeAssemblyLevel getLevel() {
        return level;
    }

    @Override
    public GenomeAssemblySource getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenomeAssemblyImpl that = (GenomeAssemblyImpl) o;
        return Objects.equals(getAssemblyId(), that.getAssemblyId())
                && Objects.equals(getGenomeAssemblyUrl(), that.getGenomeAssemblyUrl())
                && getLevel() == that.getLevel()
                && getSource() == that.getSource();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAssemblyId(), getGenomeAssemblyUrl(), getLevel(), getSource());
    }
}
