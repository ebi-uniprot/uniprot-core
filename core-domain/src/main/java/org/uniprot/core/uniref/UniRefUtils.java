package org.uniprot.core.uniref;

import java.util.Optional;
import java.util.Set;

/**
 * @author lgonzales
 * @since 08/07/2020
 */
public class UniRefUtils {

    private UniRefUtils() {}

    public static UniRefMemberIdType getUniProtKBIdType(String memberId, String accession) {
        UniRefMemberIdType type = UniRefMemberIdType.UNIPROTKB_SWISSPROT;
        if (memberId.startsWith(accession + "_")) {
            type = UniRefMemberIdType.UNIPROTKB_TREMBL;
        }

        return type;
    }

    public static void addOrganism(String organismValue, Set<String> organismTarget) {
        // Add this logic do avoid duplicated organism names because
        // UniParc organisms do not contains common name in brackets.
        Optional<String> found =
                organismTarget.stream()
                        .map(UniRefUtils::getOrganismWithoutCommonName)
                        .filter(
                                value ->
                                        value.equalsIgnoreCase(
                                                getOrganismWithoutCommonName(organismValue)))
                        .findFirst();
        if (!found.isPresent()) {
            organismTarget.add(organismValue);
        }
    }

    public static String getOrganismWithoutCommonName(String value) {
        int bracketIndex = value.indexOf('(');
        if (bracketIndex >= 0) {
            return value.substring(0, bracketIndex).trim();
        } else {
            return value;
        }
    }
}
