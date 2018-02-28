package uk.ac.ebi.uniprot.ffwriter.line;

public interface FlatfileWriter <T>{
	String write(T t, boolean isPublic);
}
