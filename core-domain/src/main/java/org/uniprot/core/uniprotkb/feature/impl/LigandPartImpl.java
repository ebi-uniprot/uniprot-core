package org.uniprot.core.uniprotkb.feature.impl;

import java.util.Objects;

import org.uniprot.core.uniprotkb.feature.LigandPart;

public class LigandPartImpl implements LigandPart {

    private static final long serialVersionUID = 1L;
    private final String name;
    private final String id;
    private final String note;
    private final String label;

    LigandPartImpl() {
        this("", null, null, null);
    }

    LigandPartImpl(String name, String id, String label, String note) {
        this.name = name;
        this.id = id;
        this.note = note;
        this.label = label;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id, label, note);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LigandPartImpl that = (LigandPartImpl) obj;
        return Objects.equals(name, that.name)
                && Objects.equals(id, that.id)
                && Objects.equals(note, that.note)
                && Objects.equals(label, that.label);
    }
}
