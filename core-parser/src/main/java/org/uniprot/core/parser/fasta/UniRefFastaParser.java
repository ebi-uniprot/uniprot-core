package org.uniprot.core.parser.fasta;

import org.uniprot.core.uniref.UniRefEntry;

/**
 *
 * @author jluo
 * @date: 22 Aug 2019
 *
*/

public class UniRefFastaParser {
	private static String NAME_PREFIX="Cluster: ";
	public static String toFasta(UniRefEntry entry) {
		  StringBuilder sb = new StringBuilder();
	      sb.append(getHeader(entry))
	        .append("\n");
	        int columnCounter = 0;
	        String sequence = entry.getRepresentativeMember().getSequence().getValue();
	        for (char c : sequence.toCharArray()) {
	            if (columnCounter % 60 == 0 && columnCounter > 0) {
	                sb.append("\n");
	            }
	            sb.append(c);
	            columnCounter++;
	        }
	        return sb.toString();
	}
	
	private static String getHeader(UniRefEntry entry) {
		StringBuilder sb = new StringBuilder();
		sb.append(">")
		.append(entry.getId().getValue())
		.append(" ")
		.append(entry.getName().substring(NAME_PREFIX.length()))
		.append(" n=")
		.append(entry.getMembers().size()+1)
		;
		
		if(entry.getCommonTaxonId() !=1l) {
			sb.append(" Tax=")
			.append(entry.getCommonTaxonName())
			.append(" TaxID=")
			.append(entry.getCommonTaxonId());
		}
		sb.append(" RepID=")
		.append(entry.getRepresentativeMember().getMemberId());
		return sb.toString();
	}
}

