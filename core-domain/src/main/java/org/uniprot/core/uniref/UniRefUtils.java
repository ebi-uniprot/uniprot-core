package org.uniprot.core.uniref;

import java.util.Optional;
import java.util.Set;

/**
 * @author lgonzales
 * @since 08/07/2020
 */
public class UniRefUtils {

    private UniRefUtils() {}

    /**
     * memberId (UniProtKB id field) has the following format TREMBL: "$accession_$organism"
     * SWISSPROT: "$gene_$organism" This method uses the logic above to identify if memberId is
     * TREMBL or SWISSPROT
     *
     * @param memberId UniProtKB id field
     * @param accession UniProtKB accession
     * @return UniRefMemberIdType for the memberId and accession provided
     */
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
