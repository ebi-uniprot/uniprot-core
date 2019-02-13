package uk.ac.ebi.uniprot.flatfile.parser.impl;

import uk.ac.ebi.uniprot.flatfile.parser.LineFormater;

public class DefaultLineFormater implements LineFormater {
	private final String linePrefix ;
	private static final String LINE_SEPARATOR = "\n";
	public DefaultLineFormater(String linePrefix) {
		this.linePrefix = linePrefix;
	}
	@Override
	public String format(String lines) {
		String [] tokens= lines.split(LINE_SEPARATOR);
		StringBuilder sb = new StringBuilder();
		for(String token: tokens) {
			if(!token.startsWith(linePrefix)) {					
				sb.append(linePrefix + token);					
			}else {
				sb.append( token);
			}	
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

}
