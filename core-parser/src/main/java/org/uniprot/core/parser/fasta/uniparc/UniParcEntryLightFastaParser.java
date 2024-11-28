package org.uniprot.core.parser.fasta.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.UniParcEntryLight;

import static org.uniprot.core.uniparc.impl.UniParcEntryLightBuilder.HAS_ACTIVE_CROSS_REF;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
public class UniParcEntryLightFastaParser {
    private UniParcEntryLightFastaParser(){}

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
        int columnCounter = 0;
        for (char c : sequence.getValue().toCharArray()) {
            if (columnCounter % 60 == 0 && columnCounter > 0) {
                sb.append("\n");
            }
            sb.append(c);
            columnCounter++;
        }
        return sb.toString();
    }
}
