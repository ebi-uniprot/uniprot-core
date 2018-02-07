package uk.ac.ebi.uniprot.ffwriter.line;

public interface FFLineConstant {
	final public  String UNIPROT_SWISSPROT = "UniProtKB/Swiss-Prot";
	final public  String UNIPROT_TREMBL = "UniProtKB/TrEMBL";
	final public  String UNIPROT = "UniProtKB";
	final public  String STOP = ".";
	final public  String COMA = ",";
	public static  String SEMI_COMA = ";";
	public static  String SPACE = " ";
	public static  String EQUAL_SIGN = "=";
	final public  String SEPARATOR = SPACE;
	final public  String DASH = "-";
	final public  String SEPARATOR_SEMICOMA ="; ";
	final public  String SEPARATOR_COMA =", ";
	final public  String SEPARATOR_AND =" and ";
	final public  String SEPARATOR__CAP_AND =" AND ";
	final public  String COLON =":";
	
	public final  int LINE_LENGTH = 75;
	public final  String DEFAUT_LINESPACE = "   ";
	public final  String FEATURE_SPACE = "                                ";
	public final  String[] SEPS =new String[] {SEPARATOR, DASH};
}
