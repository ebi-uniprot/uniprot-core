package org.uniprot.core.parser.fasta;

public class FastaUtils {

    public static String parseSequence(String sequence) {
        StringBuilder sb = new StringBuilder();
        int columnCounter = 0;
        for (char c : sequence.toCharArray()) {
            if (columnCounter % 60 == 0 && columnCounter > 0) {
                sb.append("\n");
            }
            sb.append(c);
            columnCounter++;
        }
        return sb.toString();
    }
}
