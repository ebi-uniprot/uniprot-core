package uk.ac.ebi.uniprot.flatfile.parser.impl.de;

import uk.ac.ebi.uniprot.flatfile.parser.LineFormater;

public class DeLineFormater implements LineFormater {
	private static final String LINE_PREFIX_1 = "DE   ";
	private static final String LINE_PREFIX_2 = "DE            ";
	private static final String LINE_PREFIX_3 = "DE     ";
	private static final String LINE_PREFIX_4 = "DE              ";
	private static final String LINE_SEPARATOR = "\n";
	private static final String INCLUDE = "Includes:";
	private static final String CONTAIN = "Contains:";
	private static final String RECNAME = "RecName:";
	private static final String ALTNAME = "AltName:";
	private static final String SUBNAME = "SubName:";
	private static final String FLAGS = "Flags:";


	@Override
	public String format(String lines) {
		String[] tokens = lines.split(LINE_SEPARATOR);
		StringBuilder sb = new StringBuilder();
		boolean includeOrContains = false;
		for (String token : tokens) {
			if (token.startsWith(LINE_PREFIX_1)) {
				sb.append(token);
			} else {
				String val = token.trim();
				if (isName(val)) {
					if (includeOrContains) {
						sb.append(LINE_PREFIX_3 + val);
					} else {
						sb.append(LINE_PREFIX_1 + val);
						includeOrContains = false;
					}
				}else if(isContainIncode(val)) {
					sb.append(LINE_PREFIX_1 + val);
					includeOrContains = true;
				}else {
					if (includeOrContains) {
						sb.append(LINE_PREFIX_4 + val);
					} else {
						sb.append(LINE_PREFIX_2 + val);
					}
				}
			}
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}

	boolean isName(String token) {
		return (token.startsWith(RECNAME)) || (token.startsWith(ALTNAME)) || (token.startsWith(SUBNAME))
				|| (token.startsWith(FLAGS));
	}
	boolean isContainIncode(String token) {
		return (token.startsWith(INCLUDE)) || (token.startsWith(CONTAIN));
	}
}
