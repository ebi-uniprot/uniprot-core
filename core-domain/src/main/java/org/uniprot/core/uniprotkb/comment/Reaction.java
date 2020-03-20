package org.uniprot.core.uniprotkb.comment;

import java.util.List;

import org.uniprot.core.CrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface Reaction extends HasEvidences {
    String getName();

    List<CrossReference<ReactionDatabase>> getReactionCrossReferences();

    ECNumber getEcNumber();

    boolean hasName();

    boolean hasReactionCrossReferences();

    boolean hasEcNumber();
}
