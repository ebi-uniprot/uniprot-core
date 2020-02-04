package org.uniprot.core.uniprot.comment;

import java.util.List;

import org.uniprot.core.uniprot.evidence.Evidence;

public interface MassSpectrometryComment extends Comment, HasMolecule {

    Float getMolWeight();

    Float getMolWeightError();

    String getNote();

    MassSpectrometryMethod getMethod();

    List<Evidence> getEvidences();

    boolean hasMolWeight();

    boolean hasMolWeightError();

    boolean hasNote();

    boolean hasMethod();

    boolean hasEvidences();
}
