package org.uniprot.core.xml;

public interface Converter<F, T> {
    T fromXml(F xmlObj);

    F toXml(T uniObj);
}
