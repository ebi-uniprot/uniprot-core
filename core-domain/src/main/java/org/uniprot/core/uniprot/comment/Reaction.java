package org.uniprot.core.uniprot.comment;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Reaction extends HasEvidences {
    String getName();

    List<CrossReference<ReactionDatabase>> getReactionReferences();

    ECNumber getEcNumber();

    boolean hasName();

    boolean hasReactionReferences();

    boolean hasEcNumber();
}
