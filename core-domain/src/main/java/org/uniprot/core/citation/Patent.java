package org.uniprot.core.citation;

public interface Patent extends Citation {

    String getPatentNumber();

    boolean hasPatentNumber();
}
