package uk.ac.ebi.uniprot.parser.impl.cc;

import uk.ac.ebi.uniprot.parser.LineFormater;

public class CcLineFormater implements LineFormater {

	private static final String LINE_SEPARATOR = "\n";
	private static final String CC_LINE_PREFIX="CC       ";
	private static final String CC_LINE_FIRST_PREFIX ="CC   -!- ";

	@Override
	public String format(String lines) {
		String [] tokens= lines.split(LINE_SEPARATOR);
		StringBuilder sb = new StringBuilder();
		for(String token: tokens) {
			if(token == tokens[0]) {
				if(!token.startsWith(CC_LINE_FIRST_PREFIX)) {
					sb.append(CC_LINE_FIRST_PREFIX + token);
				}else {
					sb.append( token);
				}		
			}else {
				if(!token.startsWith(CC_LINE_PREFIX)) {
					sb.append(CC_LINE_PREFIX + token);
				}else {
					sb.append( token);
				}		
			}
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

}
