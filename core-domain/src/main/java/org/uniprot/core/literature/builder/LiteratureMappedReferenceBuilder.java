package org.uniprot.core.literature.builder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.impl.LiteratureMappedReferenceImpl;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class LiteratureMappedReferenceBuilder
        implements Builder<LiteratureMappedReferenceBuilder, LiteratureMappedReference> {

    private UniProtAccession uniprotAccession;

    private String source;

    private String sourceId;

    private List<String> sourceCategory = new ArrayList<>();

    private String annotation;

    public @Nonnull LiteratureMappedReferenceBuilder uniprotAccession(
            UniProtAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder uniprotAccession(String uniprotAccession) {
        this.uniprotAccession = new UniProtAccessionImpl(Utils.emptyOrString(uniprotAccession));
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder source(String source) {
        this.source = Utils.emptyOrString(source);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder sourceId(String sourceId) {
        this.sourceId = Utils.emptyOrString(sourceId);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder sourceCategory(List<String> sourceCategory) {
        this.sourceCategory = Utils.modifiableList(sourceCategory);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder addSourceCategory(String sourceCategory) {
        Utils.addOrIgnoreNull(sourceCategory, this.sourceCategory);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder annotation(String annotation) {
        this.annotation = Utils.emptyOrString(annotation);
        return this;
    }

    @Override
    public @Nonnull LiteratureMappedReference build() {
        return new LiteratureMappedReferenceImpl(
                uniprotAccession, source, sourceId, sourceCategory, annotation);
    }

    public static @Nonnull LiteratureMappedReferenceBuilder from(
            @Nonnull LiteratureMappedReference instance) {
        return new LiteratureMappedReferenceBuilder()
                .uniprotAccession(instance.getUniprotAccession())
                .source(instance.getSource())
                .sourceId(instance.getSourceId())
                .sourceCategory(instance.getSourceCategory())
                .annotation(instance.getAnnotation());
    }
}
