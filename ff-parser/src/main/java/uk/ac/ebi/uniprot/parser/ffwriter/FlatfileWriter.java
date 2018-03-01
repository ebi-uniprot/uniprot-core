package uk.ac.ebi.uniprot.parser.ffwriter;

public interface FlatfileWriter <T>{
	String write(T t, boolean isPublic);
}
