package org.uniprot.core.fasta.impl;

import java.util.Objects;

import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.ProteinFasta;

/**
 * @author lgonzales
 * @since 21/10/2020
 */
public class ProteinFastaImpl implements ProteinFasta {
    private static final long serialVersionUID = -2516700155820985775L;

    private final String id;
    private final Sequence sequence;

    public ProteinFastaImpl(String id, Sequence sequence) {
        this.id = id;
        this.sequence = sequence;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProteinFastaImpl)) return false;
        ProteinFastaImpl that = (ProteinFastaImpl) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getSequence(), that.getSequence());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSequence());
    }
}
