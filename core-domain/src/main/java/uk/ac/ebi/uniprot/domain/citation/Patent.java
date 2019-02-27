package uk.ac.ebi.uniprot.domain.citation;

public interface Patent extends Citation {

    String getPatentNumber();

    boolean hasPatentNumber();
}
