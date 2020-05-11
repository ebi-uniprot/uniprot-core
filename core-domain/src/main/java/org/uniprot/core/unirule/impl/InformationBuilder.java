package org.uniprot.core.unirule.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class InformationBuilder implements Builder<Information> {
    private String version;
    private String comment;
    private String oldRuleNum;
    private List<String> uniProtIds = new ArrayList<>();
    private DataClassType dataClass;
    private List<String> names = new ArrayList<>();
    private Fusion fusion;
    private List<String> related = new ArrayList<>();
    private List<UniProtKBAccession> uniProtAccessions = new ArrayList<>();
    private List<String> duplicates = new ArrayList<>();
    private List<String> plasmaIds = new ArrayList<>();
    private String internal;

    public InformationBuilder(String version) {
        this.version = version;
    }

    public @Nonnull InformationBuilder version(String version) {
        this.version = version;
        return this;
    }

    public @Nonnull InformationBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public @Nonnull InformationBuilder oldRuleNum(String oldRuleNum) {
        this.oldRuleNum = oldRuleNum;
        return this;
    }

    public @Nonnull InformationBuilder uniProtIdsAdd(String uniProtId) {
        addOrIgnoreNull(uniProtId, this.uniProtIds);
        return this;
    }

    public @Nonnull InformationBuilder uniProtIdsSet(List<String> uniProtIds) {
        this.uniProtIds = modifiableList(uniProtIds);
        return this;
    }

    public @Nonnull InformationBuilder dataClass(DataClassType dataClass) {
        this.dataClass = dataClass;
        return this;
    }

    public @Nonnull InformationBuilder namesAdd(String name) {
        addOrIgnoreNull(name, this.names);
        return this;
    }

    public @Nonnull InformationBuilder namesSet(List<String> names) {
        this.names = modifiableList(names);
        return this;
    }

    public @Nonnull InformationBuilder fusion(Fusion fusion) {
        this.fusion = fusion;
        return this;
    }

    public @Nonnull InformationBuilder relatedAdd(String related) {
        addOrIgnoreNull(related, this.related);
        return this;
    }

    public @Nonnull InformationBuilder relatedSet(List<String> related) {
        this.related = modifiableList(related);
        return this;
    }

    public @Nonnull InformationBuilder uniProtAccessionsAdd(UniProtKBAccession uniProtAccession) {
        addOrIgnoreNull(uniProtAccession, this.uniProtAccessions);
        return this;
    }

    public @Nonnull InformationBuilder uniProtAccessionsSet(
            List<UniProtKBAccession> uniProtAccessions) {
        this.uniProtAccessions = modifiableList(uniProtAccessions);
        return this;
    }

    public @Nonnull InformationBuilder duplicatesAdd(String duplicate) {
        addOrIgnoreNull(duplicate, this.duplicates);
        return this;
    }

    public @Nonnull InformationBuilder duplicatesSet(List<String> duplicates) {
        this.duplicates = modifiableList(duplicates);
        return this;
    }

    public @Nonnull InformationBuilder plasmaIdsAdd(String plasmaId) {
        addOrIgnoreNull(plasmaId, this.plasmaIds);
        return this;
    }

    public @Nonnull InformationBuilder plasmaIdsSet(List<String> plasmaIds) {
        this.plasmaIds = modifiableList(plasmaIds);
        return this;
    }

    public @Nonnull InformationBuilder internal(String internal) {
        this.internal = internal;
        return this;
    }

    @Nonnull
    @Override
    public Information build() {
        return new InformationImpl(
                version,
                comment,
                oldRuleNum,
                uniProtIds,
                dataClass,
                names,
                fusion,
                related,
                uniProtAccessions,
                duplicates,
                plasmaIds,
                internal);
    }

    public static @Nonnull InformationBuilder from(@Nonnull Information instance) {
        InformationBuilder builder = new InformationBuilder(instance.getVersion());
        builder.comment(instance.getComment())
                .oldRuleNum(instance.getOldRuleNum())
                .uniProtIdsSet(instance.getUniProtIds())
                .dataClass(instance.getDataClass())
                .namesSet(instance.getNames())
                .fusion(instance.getFusion())
                .relatedSet(instance.getRelated())
                .uniProtAccessionsSet(instance.getUniProtAccessions())
                .duplicatesSet(instance.getDuplicates())
                .plasmaIdsSet(instance.getPlasmaIds())
                .internal(instance.getInternal());
        return builder;
    }
}
