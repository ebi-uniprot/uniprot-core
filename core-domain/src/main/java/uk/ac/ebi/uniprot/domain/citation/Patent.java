package uk.ac.ebi.uniprot.domain.citation;

public interface Patent extends Citation {

    public String getPatentNumber();

    boolean hasPatentNumber();
}
