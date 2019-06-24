package uk.ac.ebi.uniprot.parser.fasta;

import uk.ac.ebi.uniprot.domain.uniparc.UniParcEntry;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
*/

public class UniParcFastaParser {
	public static String toFasta(UniParcEntry entry) {
		  StringBuilder sb = new StringBuilder();
	        String status = "active";
	       boolean isActive= entry.getDbXReferences().stream().anyMatch(val -> val.isActive());
	        if(!isActive){
	            status = "inactive";
	        }
	        sb.append(">").append(entry.getUniParcId().getValue()).append(" ");
	        sb.append("status=").append(status);
	        sb.append("\n");
	        int columnCounter = 0;
	        String sequence = entry.getSequence().getValue();
	        for (char c : sequence.toCharArray()) {
	            sb.append(c);
	            if ((++columnCounter % 60 == 0) && (columnCounter < sequence.length())) {
	                sb.append("\n");
	            }
	        }
	        
	
	        return sb.toString();
	}
}

