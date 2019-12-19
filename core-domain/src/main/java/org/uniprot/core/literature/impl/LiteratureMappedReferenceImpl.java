package org.uniprot.core.literature.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class LiteratureMappedReferenceImpl implements LiteratureMappedReference {

    private static final long serialVersionUID = -1925700851366460682L;

    private UniProtAccession uniprotAccession;
    private String source;
    private String sourceId;
    private List<String> sourceCategory;
    private String annotation;

    // no arg constructor for JSON deserialization
    LiteratureMappedReferenceImpl() {
        this(null, null, null, null, null);
    }

    public LiteratureMappedReferenceImpl(
            UniProtAccession uniprotAccession,
            String source,
            String sourceId,
            List<String> sourceCategory,
            String annotation) {
        this.uniprotAccession = uniprotAccession;
        this.source = Utils.emptyOrString(source);
        this.sourceId = Utils.emptyOrString(sourceId);
        this.sourceCategory = Utils.unmodifiableList(sourceCategory);
        this.annotation = Utils.emptyOrString(annotation);
    }

    @Override
    public UniProtAccession getUniprotAccession() {
        return uniprotAccession;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public List<String> getSourceCategory() {
        return sourceCategory;
    }

    @Override
    public String getAnnotation() {
        return annotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteratureMappedReferenceImpl that = (LiteratureMappedReferenceImpl) o;
        return Objects.equals(getUniprotAccession(), that.getUniprotAccession())
                && Objects.equals(getSource(), that.getSource())
                && Objects.equals(getSourceId(), that.getSourceId())
                && Objects.equals(getSourceCategory(), that.getSourceCategory())
                && Objects.equals(getAnnotation(), that.getAnnotation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getUniprotAccession(),
                getSource(),
                getSourceId(),
                getSourceCategory(),
                getAnnotation());
    }

    @Override
    public String toString() {
        return "LiteratureMappedReferenceImpl{"
                + "uniprotAccession="
                + uniprotAccession
                + ", source='"
                + source
                + '\''
                + ", sourceId='"
                + sourceId
                + '\''
                + ", sourceCategory="
                + sourceCategory
                + ", annotation='"
                + annotation
                + '\''
                + '}';
    }
}
