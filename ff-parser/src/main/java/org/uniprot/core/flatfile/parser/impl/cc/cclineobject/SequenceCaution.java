package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class SequenceCaution {
    private String molecule;
    private List<SequenceCautionObject> sequenceCautionObjects = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public List<SequenceCautionObject> getSequenceCautionObjects() {
        return sequenceCautionObjects;
    }

    public void setSequenceCautionObjects(List<SequenceCautionObject> sequenceCautionObjects) {
        this.sequenceCautionObjects = sequenceCautionObjects;
    }
}
