package org.uniprot.core.citation.impl;

import org.uniprot.core.citation.Journal;

import java.util.Objects;

public class JournalImpl implements Journal {
    private static final long serialVersionUID = 5877235340272317134L;
    private String name;

    // no arg constructor for JSON deserialization
    JournalImpl() {}

    JournalImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JournalImpl journal = (JournalImpl) o;
        return Objects.equals(getName(), journal.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "JournalImpl{" + "name='" + name + '\'' + '}';
    }
}
