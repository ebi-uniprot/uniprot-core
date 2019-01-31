package uk.ac.ebi.uniprot.domain.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.ECNumber;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

import java.util.List;

public interface Reaction extends HasEvidences {
    String getName();

    List<DBCrossReference<ReactionReferenceType>> getReactionReferences();

    ECNumber getEcNumber();
}
