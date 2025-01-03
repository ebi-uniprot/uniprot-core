package org.uniprot.core.parser.fasta;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.util.Utils;

import static org.uniprot.core.parser.fasta.FastaParserUtils.getSequence;
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
        return getFastaString(entry.getUniParcId().getValue(), status, entry.getSequence(), null);
    }

    public static String toFasta(UniParcEntryLight entry) {
       return toFasta(entry, null);
    }

    public static String toFasta(UniParcEntryLight entry, String sequenceRange) {
        String status = "active";
        if(entry.getExtraAttributes().containsKey(HAS_ACTIVE_CROSS_REF) &&
                entry.getExtraAttributes().get(HAS_ACTIVE_CROSS_REF).equals(false)){
            status = "inactive";
        }
        Sequence sequence = getSequence(entry.getSequence(), sequenceRange);
        return getFastaString(entry.getUniParcId(), status, sequence, sequenceRange);
    }
    private static String getFastaString(String entry, String status, Sequence sequence, String sequenceRange) {
        StringBuilder sb = new StringBuilder();
        sb.append(">").append(entry);
        if(Utils.notNullNotEmpty(sequenceRange)){
            sb.append("|").append(sequenceRange);
        }
        sb.append(" ");
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
