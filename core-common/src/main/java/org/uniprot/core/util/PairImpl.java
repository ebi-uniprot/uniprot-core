package org.uniprot.core.util;

public class PairImpl<K, V> implements Pair<K, V> {
    private static final long serialVersionUID = -2316868501519520544L;
    private final K key;
    private final V value;

    PairImpl(){ // do not use, just to make Jackson serializer happy
        this(null, null);
    }

    public PairImpl(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PairImpl other = (PairImpl) obj;
        if (key == null) {
            if (other.key != null) return false;
        } else if (!key.equals(other.key)) return false;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }
}
