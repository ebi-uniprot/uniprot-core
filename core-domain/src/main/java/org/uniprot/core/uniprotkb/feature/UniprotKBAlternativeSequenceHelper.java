package org.uniprot.core.uniprotkb.feature;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created 25/01/19
 *
 * @author Edd
 */
public class UniprotKBAlternativeSequenceHelper {

    private UniprotKBAlternativeSequenceHelper() {}

    private static final Set<UniprotKBFeatureType> ALTERNATIVE_SEQUENCE_SET =
            EnumSet.of(
                    UniprotKBFeatureType.CONFLICT,
                    UniprotKBFeatureType.MUTAGEN,
                    UniprotKBFeatureType.VARIANT,
                    UniprotKBFeatureType.VAR_SEQ);

    public static boolean hasAlternativeSequence(UniprotKBFeatureType type) {
        return ALTERNATIVE_SEQUENCE_SET.contains(type);
    }
}
