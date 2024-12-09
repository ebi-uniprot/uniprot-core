package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcEntryLight;

import static org.uniprot.core.parser.fasta.FastaUtils.*;
import static org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder.HAS_ACTIVE_CROSS_REF;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcFastaParser {
    private UniParcFastaParser(){}


    public static String toFasta(UniParcEntry entry) {
        String status = "active";
        boolean isActive = entry.getUniParcCrossReferences()
                .stream()
                .anyMatch(UniParcCrossReference::isActive);
        if (!isActive) {
            status = "inactive";
        }
        return getFastaString(entry.getUniParcId().getValue(), status, entry.getSequence());
    }

    public static String toFasta(UniParcEntryLight entry) {
        String status = "active";
        if(entry.getExtraAttributes().containsKey(HAS_ACTIVE_CROSS_REF) &&
                entry.getExtraAttributes().get(HAS_ACTIVE_CROSS_REF).equals(false)){
            status = "inactive";
        }
        return getFastaString(entry.getUniParcId(), status, entry.getSequence());
    }

    private static String getFastaString(String entry, String status, Sequence sequence) {
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(entry).append(" ");
        sb.append("status=").append(status);
        sb.append("\n");
        sb.append(parseSequence(sequence.getValue()));
        return sb.toString();
    }
}
