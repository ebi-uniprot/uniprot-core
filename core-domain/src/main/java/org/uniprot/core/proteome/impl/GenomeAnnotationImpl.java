package org.uniprot.core.proteome.impl;

import java.util.Objects;

import org.uniprot.core.proteome.GenomeAnnotation;

/**
 * @author lgonzales
 * @since 12/11/2020
 */
public class GenomeAnnotationImpl implements GenomeAnnotation {

    private static final long serialVersionUID = 3804726511160450959L;

    private final String source;

    private final String url;

    GenomeAnnotationImpl() {
        this(null, null);
    }

    GenomeAnnotationImpl(String source, String url) {
        this.source = source;
        this.url = url;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenomeAnnotationImpl)) return false;
        GenomeAnnotationImpl that = (GenomeAnnotationImpl) o;
        return Objects.equals(source, that.source) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, url);
    }
}
