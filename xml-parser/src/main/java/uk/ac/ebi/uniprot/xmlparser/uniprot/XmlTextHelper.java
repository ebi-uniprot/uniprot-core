package uk.ac.ebi.uniprot.xmlparser.uniprot;

import com.google.common.base.Strings;

/**
 *Utility methods for comments for the text information value.
 *
 *<text>...</text>  
 *
 **/
final class XmlTextHelper{

	public static String addIfNoPostfix(String text, String postfix){
		if(Strings.isNullOrEmpty(text))
			return text;
		if(!text.endsWith(postfix))
			return text + postfix;
		else
			return text;
	}
	
	public static String removeIfPostfix(String xmlText, String postfix){
		if(Strings.isNullOrEmpty(xmlText))
			return xmlText;
		return xmlText.endsWith(postfix) ?  xmlText.substring(0, xmlText.length() -postfix.length() ) : xmlText;
	}
	public static String uppercaseFirstLetter(String val) {
		
		if(Strings.isNullOrEmpty(val))
			return val;
		return val.substring(0, 1).toUpperCase() + val.substring(1);
	}
	
	public static String lowercaseFirstLetter(String val) {
		if(Strings.isNullOrEmpty(val))
			return val;
		return val.substring(0, 1).toLowerCase() + val.substring(1);
	}
	
}
