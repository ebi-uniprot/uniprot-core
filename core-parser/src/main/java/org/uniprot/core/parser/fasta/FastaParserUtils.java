package org.uniprot.core.parser.fasta;

import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.util.Utils;

public class FastaParserUtils {
    private FastaParserUtils(){}
    public static Sequence getSequence(Sequence sequence, String sequenceRange) {
        if(Utils.notNullNotEmpty(sequenceRange) && sequenceRange.contains("-")) {
            String[] rangeTokens = sequenceRange.split("-");
            int start = Integer.parseInt(rangeTokens[0]);
            int end = Integer.parseInt(rangeTokens[1]);
            String sequenceString = sequence.getValue()
                    .substring(
                            Math.min(start - 1, sequence.getLength()),
                            Math.min(end, sequence.getLength()));
            return new SequenceBuilder(sequenceString).build();
        }
        return sequence;
    }
}
