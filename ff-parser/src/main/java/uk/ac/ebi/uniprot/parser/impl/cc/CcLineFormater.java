package uk.ac.ebi.uniprot.parser.impl.cc;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.parser.LineFormater;

public class CcLineFormater implements LineFormater {

	private static final String ABSORPTION = "Absorption:";
	private static final String KINETIC_PARAMETERS = "Kinetic parameters:";
	private static final String TEMPERATURE_DEPENDENCE = "Temperature dependence:";
	private static final String REDOX_POTENTIAL = "Redox potential:";
	private static final String PH_DEPENDENCE = "pH dependence:";
	private static final String LINE_SEPARATOR = "\n";
	private static final String CC_LINE_PREFIX="CC       ";
	private static final String CC_LINE_PREFIX_2="CC         ";
	private static final String CC_LINE_FIRST_PREFIX ="CC   -!- ";
	private static final String BIOPHYCHEMPROPERTIES = "BIOPHYSICOCHEMICAL PROPERTIES:";
	private static final String AP_COMMENT = "ALTERNATIVE PRODUCTS:";
	private static final String AP_NAME = "Name=";
	private static final String AP_EVENT = "Event=";
	private static final String AP_ISO_ID = "IsoId=";
	
	private static Set<String> BIOPHYCHEMPROPERTIES_VALUES =new HashSet<>();
	static  {
		BIOPHYCHEMPROPERTIES_VALUES.add(ABSORPTION);
		BIOPHYCHEMPROPERTIES_VALUES.add(KINETIC_PARAMETERS);
		BIOPHYCHEMPROPERTIES_VALUES.add(TEMPERATURE_DEPENDENCE);
		BIOPHYCHEMPROPERTIES_VALUES.add(REDOX_POTENTIAL);
		BIOPHYCHEMPROPERTIES_VALUES.add(PH_DEPENDENCE);
	};
	
	private static Set<String> APCOMMENT_VALUE =new HashSet<>();
	static  {
		APCOMMENT_VALUE.add("Name=");
		APCOMMENT_VALUE.add("Event=");
	};
	
	
	
	@Override
	public String format(String lines) {
		String [] tokens= lines.split(LINE_SEPARATOR);
		StringBuilder sb = new StringBuilder();
		boolean isBioPhyChem = false;
		boolean isAPComment =false;
		boolean isApName = false;
		for(String token: tokens) {
			token= token.trim();
			if(isFirstLineComment(token)){
				if(isBiophyChemProperty (token)) {
					isBioPhyChem = true;
				}else {
					isBioPhyChem = false;
				}
				if(isAPComment (token)) {
					isAPComment = true;
				}else {
					isAPComment = false;
				}
				if(!token.startsWith(CC_LINE_FIRST_PREFIX)) {					
					sb.append(CC_LINE_FIRST_PREFIX + token);					
				}else {
					sb.append( token);
				}			
			}
			else {
				
				if(!token.startsWith(CC_LINE_PREFIX)) {
					if(isBioPhyChem) {
						if(BIOPHYCHEMPROPERTIES_VALUES.contains(token)) {
							sb.append(CC_LINE_PREFIX + token);
						}else {
							String val = token.trim();
							sb.append(CC_LINE_PREFIX_2 + val);
						}
					}else if(isAPComment) {
						Map.Entry<String, Boolean > apformated = formatAPComment(token, isApName);
						sb.append(apformated.getKey());
						isApName = apformated.getValue();
					}
					else
						sb.append(CC_LINE_PREFIX + token);
				}else {
					sb.append( token);
				}		
			}
			sb.append(LINE_SEPARATOR);
		}
		return sb.toString();
	}
	
	private Map.Entry<String, Boolean> formatAPComment(String token, boolean isName ) {
		boolean isApName = isName;
		StringBuilder sb = new StringBuilder();
		if(token.startsWith(AP_NAME)) {
			sb.append(CC_LINE_PREFIX + token);
			isApName = true;
		}else if(token.startsWith(AP_EVENT)) {
			sb.append(CC_LINE_PREFIX + token);
		}else {
			if(isApName) {
				if(token.startsWith(AP_ISO_ID)){
					sb.append(CC_LINE_PREFIX_2 + token);
					isApName =false;
				}else {
					sb.append(CC_LINE_PREFIX + token);
				}
			}else {
				String val = token.trim();
				sb.append(CC_LINE_PREFIX_2 + val);
			}
		}
		return new AbstractMap.SimpleEntry<>(sb.toString(), isApName);
	}
	private boolean isFirstLineComment(String token) {
		if(token.startsWith(CC_LINE_FIRST_PREFIX))
			return true;
		for(CommentType type: CommentType.values()) {
			if(token.startsWith(type.toDisplayName() +":")) {
				return true;
			}
		}
		return false;
	}
	private boolean isBiophyChemProperty(String token) {
		if(token.startsWith(CC_LINE_FIRST_PREFIX)) {
			return token.equals(CC_LINE_FIRST_PREFIX +BIOPHYCHEMPROPERTIES );
		}else {
			return token.equals( BIOPHYCHEMPROPERTIES );
		}
	}
	private boolean isAPComment(String token) {
		if(token.startsWith(CC_LINE_FIRST_PREFIX)) {
			return token.equals(CC_LINE_FIRST_PREFIX +AP_COMMENT );
		}else {
			return token.equals( AP_COMMENT );
		}
	}
}
