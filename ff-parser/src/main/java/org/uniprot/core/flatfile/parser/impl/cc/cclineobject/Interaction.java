package org.uniprot.core.flatfile.parser.impl.cc.cclineobject;

import java.util.ArrayList;
import java.util.List;

public class Interaction {
    private List<InteractionObject> interactions = new ArrayList<>();

    public List<InteractionObject> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<InteractionObject> interactions) {
        this.interactions = interactions;
    }
}
