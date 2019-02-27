package uk.ac.ebi.uniprot.flatfile.parser.ffwriter;

public interface FlatfileWriter <T>{
	String write(T t, boolean isPublic);
}
