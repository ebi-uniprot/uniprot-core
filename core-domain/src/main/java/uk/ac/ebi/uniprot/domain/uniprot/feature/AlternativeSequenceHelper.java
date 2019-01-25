package uk.ac.ebi.uniprot.domain.uniprot.feature;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class AlternativeSequenceHelper {
    private static final Set<FeatureType> ALTERNATIVE_SEQUENCE_SET =
            EnumSet.of(FeatureType.CONFLICT, FeatureType.MUTAGEN, FeatureType.VARIANT, FeatureType.VAR_SEQ);

    public static boolean hasAlternativeSequence(FeatureType type) {
        return ALTERNATIVE_SEQUENCE_SET.contains(type);
    }
}
