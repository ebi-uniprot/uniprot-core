package org.uniprot.core.literature.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.uniprotkb.UniProtkbAccession;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;
import org.uniprot.core.util.Utils;

/** @author lgonzales */
public class LiteratureMappedReferenceBuilder implements Builder<LiteratureMappedReference> {

    private UniProtkbAccession uniprotAccession;

    private String source;

    private String sourceId;

    private List<String> sourceCategories = new ArrayList<>();

    private String annotation;

    public @Nonnull LiteratureMappedReferenceBuilder uniprotAccession(
            UniProtkbAccession uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder uniprotAccession(String uniprotAccession) {
        this.uniprotAccession =
                new UniProtkbAccessionBuilder(Utils.emptyOrString(uniprotAccession)).build();
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

    public @Nonnull LiteratureMappedReferenceBuilder sourceCategoriesSet(
            List<String> sourceCategory) {
        this.sourceCategories = Utils.modifiableList(sourceCategory);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder sourceCategoriesAdd(String sourceCategory) {
        Utils.addOrIgnoreNull(sourceCategory, this.sourceCategories);
        return this;
    }

    public @Nonnull LiteratureMappedReferenceBuilder annotation(String annotation) {
        this.annotation = Utils.emptyOrString(annotation);
        return this;
    }

    @Override
    public @Nonnull LiteratureMappedReference build() {
        return new LiteratureMappedReferenceImpl(
                uniprotAccession, source, sourceId, sourceCategories, annotation);
    }

    public static @Nonnull LiteratureMappedReferenceBuilder from(
            @Nonnull LiteratureMappedReference instance) {
        return new LiteratureMappedReferenceBuilder()
                .uniprotAccession(instance.getUniprotAccession())
                .source(instance.getSource())
                .sourceId(instance.getSourceId())
                .sourceCategoriesSet(instance.getSourceCategories())
                .annotation(instance.getAnnotation());
    }
}
