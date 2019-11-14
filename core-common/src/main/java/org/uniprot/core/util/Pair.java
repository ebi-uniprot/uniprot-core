package org.uniprot.core.util;

import java.io.Serializable;

public interface Pair<K, V> extends Serializable {
    K getKey();

    V getValue();
}
