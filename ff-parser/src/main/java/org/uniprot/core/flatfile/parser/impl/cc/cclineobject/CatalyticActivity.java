package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class CatalyticActivity {
    private String molecule;
    private CAReaction reaction;
    private List<CAPhysioDirection> physiologicalDirections = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public CAReaction getReaction() {
        return reaction;
    }

    public void setReaction(CAReaction reaction) {
        this.reaction = reaction;
    }

    public List<CAPhysioDirection> getPhysiologicalDirections() {
        return physiologicalDirections;
    }

    public void setPhysiologicalDirections(List<CAPhysioDirection> physiologicalDirections) {
        this.physiologicalDirections = physiologicalDirections;
    }
}
