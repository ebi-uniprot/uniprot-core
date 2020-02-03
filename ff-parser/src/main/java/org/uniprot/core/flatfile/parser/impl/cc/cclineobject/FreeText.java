package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class FreeText {
    private String molecule;
    private List<EvidencedString> texts = new ArrayList<>();

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public List<EvidencedString> getTexts() {
        return texts;
    }

    public void setTexts(List<EvidencedString> texts) {
        this.texts = texts;
    }
}
