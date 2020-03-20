package org.uniprot.core.uniprotkb.comment;

import java.util.List;

import org.uniprot.core.uniprotkb.evidence.Evidence;

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
