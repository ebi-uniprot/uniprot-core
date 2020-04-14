package org.uniprot.core.unirule.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.util.Utils;

public class InformationImpl implements Information {
    private static final long serialVersionUID = 3599134742653971242L;
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

    InformationImpl() {
        this.uniProtIds = Collections.emptyList();
        this.names = Collections.emptyList();
        this.related = Collections.emptyList();
        this.uniProtAccessions = Collections.emptyList();
        this.duplicates = Collections.emptyList();
        this.plasmaIds = Collections.emptyList();
    }

    public InformationImpl(
            String version,
            String comment,
            String oldRuleNum,
            List<UniProtKBId> uniProtIds,
            DataClassType dataClass,
            List<String> names,
            Fusion fusion,
            List<String> related,
            List<UniProtKBAccession> uniProtAccessions,
            List<String> duplicates,
            List<String> plasmaIds,
            String internal) {
        this.version = version;
        this.comment = comment;
        this.oldRuleNum = oldRuleNum;
        this.uniProtIds = Utils.unmodifiableList(uniProtIds);
        this.dataClass = dataClass;
        this.names = Utils.unmodifiableList(names);
        this.fusion = fusion;
        this.related = Utils.unmodifiableList(related);
        this.uniProtAccessions = Utils.unmodifiableList(uniProtAccessions);
        this.duplicates = Utils.unmodifiableList(duplicates);
        this.plasmaIds = Utils.unmodifiableList(plasmaIds);
        this.internal = internal;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public String getOldRuleNum() {
        return this.oldRuleNum;
    }

    @Override
    public List<UniProtKBId> getUniProtIds() {
        return this.uniProtIds;
    }

    @Override
    public DataClassType getDataClass() {
        return this.dataClass;
    }

    @Override
    public List<String> getNames() {
        return this.names;
    }

    @Override
    public Fusion getFusion() {
        return this.fusion;
    }

    @Override
    public List<String> getRelated() {
        return this.related;
    }

    @Override
    public List<UniProtKBAccession> getUniProtAccessions() {
        return this.uniProtAccessions;
    }

    @Override
    public List<String> getDuplicates() {
        return this.duplicates;
    }

    @Override
    public List<String> getPlasmaIds() {
        return this.plasmaIds;
    }

    @Override
    public String getInternal() {
        return this.internal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InformationImpl that = (InformationImpl) o;
        return Objects.equals(version, that.version)
                && Objects.equals(comment, that.comment)
                && Objects.equals(oldRuleNum, that.oldRuleNum)
                && Objects.equals(uniProtIds, that.uniProtIds)
                && dataClass == that.dataClass
                && Objects.equals(names, that.names)
                && Objects.equals(fusion, that.fusion)
                && Objects.equals(related, that.related)
                && Objects.equals(uniProtAccessions, that.uniProtAccessions)
                && Objects.equals(duplicates, that.duplicates)
                && Objects.equals(plasmaIds, that.plasmaIds)
                && Objects.equals(internal, that.internal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
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
}
