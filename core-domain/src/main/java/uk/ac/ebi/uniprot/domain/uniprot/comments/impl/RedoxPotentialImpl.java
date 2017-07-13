package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.RedoxPotential;
import uk.ac.ebi.uniprot.domain.uniprot.interfaces.EvidencedValue;

import java.util.List;

public class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public RedoxPotentialImpl() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return super.equals(o);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public List<EvidencedValue> getTexts() {
        // TODO Auto-generated method stub
        return null;
    }

}