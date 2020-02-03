package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class SubcullarLocation {
    private String molecule;
    private List<LocationObject> locations = new ArrayList<>();
    private List<EvidencedString> note = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public List<LocationObject> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationObject> locations) {
        this.locations = locations;
    }

    public List<EvidencedString> getNote() {
        return note;
    }

    public void setNote(List<EvidencedString> note) {
        this.note = note;
    }
}
