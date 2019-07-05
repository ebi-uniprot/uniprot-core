package uk.ac.ebi.uniprot.domain.literature.impl;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.literature.LiteratureMappedReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;

import java.util.List;
import java.util.Objects;

/**
 * @author lgonzales
 */
public class LiteratureMappedReferenceImpl implements LiteratureMappedReference {

    private UniProtAccession uniprotAccession;
    private String source;
    private String sourceId;
    private List<String> sourceCategory;
    private String annotation;

    private LiteratureMappedReferenceImpl() {
        this(null, null, null, null, null);
    }

    public LiteratureMappedReferenceImpl(UniProtAccession uniprotAccession, String source, String sourceId, List<String> sourceCategory, String annotation) {
        this.uniprotAccession = uniprotAccession;
        this.source = Utils.nullToEmpty(source);
        this.sourceId = Utils.nullToEmpty(sourceId);
        this.sourceCategory = Utils.nonNullList(sourceCategory);
        this.annotation = Utils.nullToEmpty(annotation);
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
        return Objects.equals(getUniprotAccession(), that.getUniprotAccession()) &&
                Objects.equals(getSource(), that.getSource()) &&
                Objects.equals(getSourceId(), that.getSourceId()) &&
                Objects.equals(getSourceCategory(), that.getSourceCategory()) &&
                Objects.equals(getAnnotation(), that.getAnnotation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniprotAccession(), getSource(), getSourceId(), getSourceCategory(), getAnnotation());
    }

    @Override
    public String toString() {
        return "LiteratureMappedReferenceImpl{" +
                "uniprotAccession=" + uniprotAccession +
                ", source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", sourceCategory=" + sourceCategory +
                ", annotation='" + annotation + '\'' +
                '}';
    }

}
