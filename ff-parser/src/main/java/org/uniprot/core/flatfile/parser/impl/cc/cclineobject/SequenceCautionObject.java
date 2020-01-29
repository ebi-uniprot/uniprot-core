package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class SequenceCautionObject {
    private String molecule;
    private String sequence;
    private CcLineObject.SequenceCautionType type;
    //   private List<Integer> positions = new ArrayList<>();
    //   private String positionValue;
    private String note;

    public String getMolecule() {
        return molecule;
    }

    public void setMolecule(String molecule) {
        this.molecule = molecule;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public CcLineObject.SequenceCautionType getType() {
        return type;
    }

    public void setType(CcLineObject.SequenceCautionType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
