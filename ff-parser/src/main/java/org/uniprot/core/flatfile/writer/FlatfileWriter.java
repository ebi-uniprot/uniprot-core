package org.uniprot.core.flatfile.writer;

public interface FlatfileWriter<T> {
    String write(T t, boolean isPublic);
}
