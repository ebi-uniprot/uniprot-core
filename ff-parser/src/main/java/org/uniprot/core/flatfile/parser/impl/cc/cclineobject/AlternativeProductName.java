package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class AlternativeProductName {
    public enum AlternativeNameSequenceEnum {
        DISPLAYED,
        EXTERNAL,
        NOT_DESCRIBED,
        DESCRIBED,
        UNSURE
    }

    private EvidencedString name;
    private List<EvidencedString> synNames = new ArrayList<>();
    private List<String> isoId = new ArrayList<>();
    private List<String> sequenceFTId = new ArrayList<>();
    private AlternativeNameSequenceEnum sequenceEnum = null;
    private List<EvidencedString> note = new ArrayList<>(); // list of evidenced String

    public EvidencedString getName() {
        return name;
    }

    public void setName(EvidencedString name) {
        this.name = name;
    }

    public List<EvidencedString> getSynNames() {
        return synNames;
    }

    public void setSynNames(List<EvidencedString> synNames) {
        this.synNames = synNames;
    }

    public List<String> getIsoId() {
        return isoId;
    }

    public void setIsoId(List<String> isoId) {
        this.isoId = isoId;
    }

    public List<String> getSequenceFTId() {
        return sequenceFTId;
    }

    public void setSequenceFTId(List<String> sequenceFTId) {
        this.sequenceFTId = sequenceFTId;
    }

    public AlternativeNameSequenceEnum getSequenceEnum() {
        return sequenceEnum;
    }

    public void setSequenceEnum(AlternativeNameSequenceEnum sequenceEnum) {
        this.sequenceEnum = sequenceEnum;
    }

    public List<EvidencedString> getNote() {
        return note;
    }

    public void setNote(List<EvidencedString> note) {
        this.note = note;
    }
}
