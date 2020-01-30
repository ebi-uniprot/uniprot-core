package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

public class SequenceCautionObject {
    private String molecule;
    private String sequence;
    private SequenceCautionType type;
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

    public SequenceCautionType getType() {
        return type;
    }

    public void setType(SequenceCautionType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public enum SequenceCautionType {
        FRAMESHIFT,
        ERRONEOUS_INITIATION,
        ERRONEOUS_TERMINATION,
        ERRONEOUS_GENE_MODEL_PREDICTION,
        ERRONEOUS_TRANSLATION,
        MISCELLANEOUS_DISCREPANCY;

        public static SequenceCautionType fromSting(String s) {
            String replace = s.replace(' ', '_');
            return SequenceCautionType.valueOf(replace.toUpperCase());
        }
    }
}
