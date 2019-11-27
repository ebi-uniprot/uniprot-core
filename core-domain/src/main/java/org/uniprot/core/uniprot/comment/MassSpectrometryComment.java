package org.uniprot.core.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;

public interface MassSpectrometryComment extends Comment, HasMolecule {

    public Float getMolWeight();

    public Float getMolWeightError();

    public String getNote();

    public MassSpectrometryMethod getMethod();

    public List<Evidence> getEvidences();

    boolean hasMolWeight();

    boolean hasMolWeightError();

    boolean hasNote();

    boolean hasMethod();

    boolean hasEvidences();
}
