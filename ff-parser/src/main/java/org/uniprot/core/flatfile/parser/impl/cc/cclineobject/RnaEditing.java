package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class RnaEditing {
    private String molecule;
    private RnaEditingLocationEnum locationEnum;
    private List<Integer> locations = new ArrayList<>();
    private List<EvidencedString> note = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public RnaEditingLocationEnum getLocationEnum() {
        return locationEnum;
    }

    public void setLocationEnum(RnaEditingLocationEnum locationEnum) {
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

    public enum RnaEditingLocationEnum {
        UNDETERMINED,
        NOT_APPLICABLE
    }
}
