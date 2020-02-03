package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private String molecule;
    private String name;
    private String abbr;
    private String mim;
    private String description;
    private List<EvidencedString> note = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getMim() {
        return mim;
    }

    public void setMim(String mim) {
        this.mim = mim;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EvidencedString> getNote() {
        return note;
    }

    public void setNote(List<EvidencedString> note) {
        this.note = note;
    }
}
