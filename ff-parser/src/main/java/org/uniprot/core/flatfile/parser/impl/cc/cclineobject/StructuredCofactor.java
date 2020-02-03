package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class StructuredCofactor {
    private String molecule;
    private List<EvidencedString> note = new ArrayList<>();
    private List<CofactorItem> cofactors = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public List<EvidencedString> getNote() {
        return note;
    }

    public void setNote(List<EvidencedString> note) {
        this.note = note;
    }

    public List<CofactorItem> getCofactors() {
        return cofactors;
    }

    public void setCofactors(List<CofactorItem> cofactors) {
        this.cofactors = cofactors;
    }
}
