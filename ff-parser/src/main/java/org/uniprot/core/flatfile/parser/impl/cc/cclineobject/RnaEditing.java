package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class RnaEditing {
    private String molecule;
    private CcLineObject.RnaEditingLocationEnum locationEnum;
    private List<Integer> locations = new ArrayList<>();
    private List<EvidencedString> note = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public CcLineObject.RnaEditingLocationEnum getLocationEnum() {
        return locationEnum;
    }

    public void setLocationEnum(CcLineObject.RnaEditingLocationEnum locationEnum) {
        this.locationEnum = locationEnum;
    }

    public List<Integer> getLocations() {
        return locations;
    }

    public void setLocations(List<Integer> locations) {
        this.locations = locations;
    }

    public List<EvidencedString> getNote() {
        return note;
    }

    public void setNote(List<EvidencedString> note) {
        this.note = note;
    }
}
