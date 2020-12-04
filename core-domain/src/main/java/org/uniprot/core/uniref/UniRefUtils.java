package org.uniprot.core.uniref;

import org.uniprot.core.uniprotkb.taxonomy.Organism;

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

    public static void addOrganism(Organism newOrganism, Set<Organism> organismTarget) {
        // Add this logic do avoid duplicated organism because
        // UniParc organisms do not contain commonName.
        Optional<Organism> foundInTarget =
                organismTarget.stream()
                        .filter(value -> value.getTaxonId() == newOrganism.getTaxonId())
                        .findFirst();
        if (!foundInTarget.isPresent()) {
            organismTarget.add(newOrganism);
        }
    }

    public static String getOrganismScientificName(String value) {
        int bracketIndex = value.indexOf('(');
        if (bracketIndex >= 0) {
            return value.substring(0, bracketIndex).trim();
        } else {
            return value;
        }
    }

    public static String getOrganismCommonName(String value) {
        String commonName = "";
        int commonNameBegin = value.indexOf('(');
        if (commonNameBegin >= 0) {
            int commonNameEnd = value.indexOf(')');
            return value.substring(commonNameBegin + 1, commonNameEnd).trim();
        }
        return commonName;
    }
}
