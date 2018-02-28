package uk.ac.ebi.uniprot.parser.impl.ft;

import java.util.regex.Pattern;

public class ftLineConverterUtil {
	private static String CARBOHYD_DESC ="([NOSC]-linked) ([^;]+)((; )(.+))?" ;
	public final static Pattern CARBOHYD_DESC_PATTERN = Pattern.compile(CARBOHYD_DESC);
	
	private static String VAR_SEQ_DESC ="(Missing|(([A-Z ]+)(( )?-\\>( )?)([A-Z ]+)))(( \\(in )(.+)(\\)))";
	public final static Pattern VAR_SEQ_DESC_PATTERN = Pattern.compile(VAR_SEQ_DESC);
	
	
	private static String VARIANT_DESC ="(Missing|(([A-Z]+)( -\\> )([A-Z]+)))(( \\()(.+)?(\\)))?";
	public final static Pattern VAIANT_DESC_PATTERN = Pattern.compile(VARIANT_DESC);
	
	
	private static String MUTAGEN_DESC ="(Missing|(([A-Z ]+)(-\\>)([ A-Z\\,]+)))((: )(.+))";
	public final static Pattern MUTAGEN_DESC_PATTERN = Pattern.compile(MUTAGEN_DESC);
	
	private static String CONFLICT_DESC ="(Missing|(([A-Z]+)(( )?-\\>( )?)([A-Z]+)))(( \\(in Ref\\. )(.+)(\\)))";
	public final static Pattern CONFLICT_DESC_PATTERN = Pattern.compile(CONFLICT_DESC);
}
