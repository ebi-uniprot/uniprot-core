package org.uniprot.core.flatfile.parser.impl.cc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CcLineUtils {
	private static final String FREETEXT_REGEX 
		="((\\[(.+?)\\])(: ))?(.+)";
	public static final Pattern FREETEXT_PATTERN = Pattern.compile(FREETEXT_REGEX);
	public static List<String> parseFreeText(String s){
		Matcher matcher =FREETEXT_PATTERN.matcher(s);
		
		if(matcher.matches()) {	
			List<String> result = new ArrayList<>();
			String g3 =matcher.group(3);
			String g5= matcher.group(5);
			result.add(g3==null?"":g3);
			result.add(g5==null?"":g5);
			return result;
			
		}else {
			return Arrays.asList("",  s);
		}
	}
}
