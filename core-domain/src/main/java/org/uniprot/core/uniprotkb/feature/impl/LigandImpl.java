package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.feature.Ligand;

public class LigandImpl implements Ligand {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final String id;
    private final String label;
    private final String note;

    LigandImpl() {
        this("", null, null, null);
    }

    LigandImpl(String name, String id, String label, String note) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.note = note;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LigandImpl that = (LigandImpl) obj;
        return Objects.equals(name, that.name)
                && Objects.equals(id, that.id)
                && Objects.equals(label, that.label)
                && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, label, note);
    }
}
