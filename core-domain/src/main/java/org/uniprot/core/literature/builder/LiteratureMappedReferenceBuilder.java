package org.uniprot.core.literature.builder;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.impl.LiteratureMappedReferenceImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 */
public class LiteratureMappedReferenceBuilder implements Builder<LiteratureMappedReferenceBuilder, LiteratureMappedReference> {

    private UniProtAccession uniprotAccession;

    private String source;

    private String sourceId;

    private List<String> sourceCategory = new ArrayList<>();

    private String annotation;

    public LiteratureMappedReferenceBuilder uniprotAccession(UniProtAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

    public LiteratureMappedReferenceBuilder uniprotAccession(String uniprotAccession) {
        this.uniprotAccession = new UniProtAccessionImpl(Utils.nullToEmpty(uniprotAccession));
        return this;
    }

    public LiteratureMappedReferenceBuilder source(String source) {
        this.source = Utils.nullToEmpty(source);
        return this;
    }

    public LiteratureMappedReferenceBuilder sourceId(String sourceId) {
        this.sourceId = Utils.nullToEmpty(sourceId);
        return this;
    }

    public LiteratureMappedReferenceBuilder sourceCategory(List<String> sourceCategory) {
        this.sourceCategory = Utils.modifiableList(sourceCategory);
        return this;
    }

    public LiteratureMappedReferenceBuilder addSourceCategory(String sourceCategory) {
        Utils.addOrIgnoreNull(sourceCategory, this.sourceCategory);
        return this;
    }

    public LiteratureMappedReferenceBuilder annotation(String annotation) {
        this.annotation = Utils.nullToEmpty(annotation);
        return this;
    }

    @Override
    public LiteratureMappedReference build() {
        return new LiteratureMappedReferenceImpl(uniprotAccession, source, sourceId, sourceCategory, annotation);
    }

    @Override
    public LiteratureMappedReferenceBuilder from(LiteratureMappedReference instance) {
        return new LiteratureMappedReferenceBuilder()
                .uniprotAccession(instance.getUniprotAccession())
                .source(instance.getSource())
                .sourceId(instance.getSourceId())
                .sourceCategory(instance.getSourceCategory())
                .annotation(instance.getAnnotation());
    }
}
