package org.uniprot.core.uniprot.comment;

import java.util.List;

import org.uniprot.core.DBCrossReference;
import org.uniprot.core.ECNumber;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface Reaction extends HasEvidences {
    String getName();

    List<DBCrossReference<ReactionReferenceType>> getReactionReferences();

    ECNumber getEcNumber();

    boolean hasName();

    boolean hasReactionReferences();

    boolean hasEcNumber();
}
