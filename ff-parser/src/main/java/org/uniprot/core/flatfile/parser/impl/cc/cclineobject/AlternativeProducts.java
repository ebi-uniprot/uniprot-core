package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class AlternativeProducts {
    private List<String> events = new ArrayList<>();
    private String namedIsoforms;
    private List<EvidencedString> comment = new ArrayList<>(); // list of evidenced String

    private List<AlternativeProductName> names = new ArrayList<>();

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getNamedIsoforms() {
        return namedIsoforms;
    }

    public void setNamedIsoforms(String namedIsoforms) {
        this.namedIsoforms = namedIsoforms;
    }

    public List<EvidencedString> getComment() {
        return comment;
    }

    public void setComment(List<EvidencedString> comment) {
        this.comment = comment;
    }

    public List<AlternativeProductName> getNames() {
        return names;
    }

    public void setNames(List<AlternativeProductName> names) {
        this.names = names;
    }
}
