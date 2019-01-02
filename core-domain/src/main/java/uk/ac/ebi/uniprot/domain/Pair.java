package uk.ac.ebi.uniprot.domain;

public interface Pair<K, V> {
    K getKey();

    V getValue();
}
