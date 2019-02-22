package uk.ac.ebi.uniprot.domain.citation.impl;

import uk.ac.ebi.uniprot.domain.citation.Journal;

public class JournalImpl implements Journal {
    private static final long serialVersionUID = 5877235340272317134L;
    private String name;

    private JournalImpl() {

    }

    public JournalImpl(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JournalImpl other = (JournalImpl) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

}
