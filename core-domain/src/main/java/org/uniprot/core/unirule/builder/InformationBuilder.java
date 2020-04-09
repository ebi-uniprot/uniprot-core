package org.uniprot.core.unirule.builder;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.impl.InformationImpl;

public class InformationBuilder implements Builder<Information> {
    private String version;
    private String comment;
    private String oldRuleNum;
    private List<UniProtKBId> uniProtIds;
    private DataClassType dataClass;
    private List<String> names;
    private Fusion fusion;
    private List<String> related;
    private List<UniProtKBAccession> uniProtAccessions;
    private List<String> duplicates;
    private List<String> plasmaIds;
    private String internal;

    public InformationBuilder version(String version) {
        this.version = version;
        return this;
    }

    public InformationBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public InformationBuilder oldRuleNum(String oldRuleNum) {
        this.oldRuleNum = oldRuleNum;
        return this;
    }

    public InformationBuilder uniProtIds(List<UniProtKBId> uniProtIds) {
        this.uniProtIds = uniProtIds;
        return this;
    }

    public InformationBuilder dataClass(DataClassType dataClass) {
        this.dataClass = dataClass;
        return this;
    }

    public InformationBuilder namesAdd(String name) {
        addOrIgnoreNull(name, this.names);
        return this;
    }

    public InformationBuilder namesSet(List<String> names) {
        this.names = modifiableList(names);
        return this;
    }

    public InformationBuilder fusion(Fusion fusion) {
        this.fusion = fusion;
        return this;
    }

    public InformationBuilder relatedAdd(String related) {
        addOrIgnoreNull(related, this.related);
        return this;
    }

    public InformationBuilder relatedSet(List<String> related) {
        this.related = modifiableList(related);
        return this;
    }

    public InformationBuilder uniProtAccessionsAdd(UniProtKBAccession uniProtAccession) {
        addOrIgnoreNull(uniProtAccession, this.uniProtAccessions);
        return this;
    }

    public InformationBuilder uniProtAccessionsSet(List<UniProtKBAccession> uniProtAccessions) {
        this.uniProtAccessions = modifiableList(uniProtAccessions);
        return this;
    }

    public InformationBuilder duplicatesAdd(String duplicate) {
        addOrIgnoreNull(duplicate, this.duplicates);
        return this;
    }

    public InformationBuilder duplicatesSet(List<String> duplicates) {
        this.duplicates = modifiableList(duplicates);
        return this;
    }

    public InformationBuilder plasmaIdsAdd(String plasmaId) {
        addOrIgnoreNull(plasmaId, this.plasmaIds);
        return this;
    }

    public InformationBuilder plasmaIdsSet(List<String> plasmaIds) {
        this.plasmaIds = modifiableList(plasmaIds);
        return this;
    }

    public InformationBuilder internal(String internal) {
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
        InformationBuilder builder = new InformationBuilder();
        builder.version(instance.getVersion())
                .comment(instance.getComment())
                .oldRuleNum(instance.getOldRuleNum())
                .uniProtIds(instance.getUniProtIds())
                .dataClass(instance.getDataClass())
                .namesSet(instance.getNames())
                .fusion(instance.getFusion())
                .uniProtAccessionsSet(instance.getUniProtAccessions())
                .duplicatesSet(instance.getDuplicates())
                .plasmaIdsSet(instance.getPlasmaIds())
                .internal(instance.getInternal());
        return builder;
    }
}
