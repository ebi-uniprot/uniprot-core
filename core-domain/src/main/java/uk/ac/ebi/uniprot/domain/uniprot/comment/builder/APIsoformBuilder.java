package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.Value;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 15/01/19
 *
 * @author Edd
 */
public class APIsoformBuilder implements Builder2<APIsoformBuilder, APIsoform> {
    private IsoformName name;
    private List<IsoformName> synonyms = new ArrayList<>();
    private List<IsoformId> isoformIds = new ArrayList<>();
    private List<String> sequenceIds = new ArrayList<>();
    private Note note;
    private IsoformSequenceStatus isoformSequenceStatus;

    public APIsoform build() {
        return new APIsoformImpl(this);
    }

    @Override
    public APIsoformBuilder from(APIsoform instance) {
        synonyms.clear();
        isoformIds.clear();
        sequenceIds.clear();
        return this
                .sequenceIds(instance.getSequenceIds())
                .ids(instance.getIsoformIds().stream().map(Value::getValue).collect(Collectors.toList()))
                .note(instance.getNote())
                .synonyms(instance.getSynonyms())
                .sequenceStatus(instance.getIsoformSequenceStatus())
                .name(instance.getName());
    }

    public APIsoformBuilder name(IsoformName name) {
        this.name = name;
        return this;
    }

    public APIsoformBuilder synonyms(List<IsoformName> synonyms) {
        nonNullAddAll(synonyms, this.synonyms);
        return this;
    }

    public APIsoformBuilder addSynonym(IsoformName synonym) {
        this.synonyms.add(synonym);
        return this;
    }

    public APIsoformBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public APIsoformBuilder ids(List<String> isoformIds) {
        isoformIds.stream().map(APIsoformImpl.IsoformIdImpl::new).forEach(this.isoformIds::add);
        return this;
    }

    public APIsoformBuilder addId(String isoformId) {
        this.isoformIds.add(new APIsoformImpl.IsoformIdImpl(isoformId));
        return this;
    }

    public APIsoformBuilder sequenceIds(List<String> sequenceIds) {
        nonNullAddAll(sequenceIds, this.sequenceIds);
        return this;
    }

    public APIsoformBuilder addSequenceId(String sequenceId) {
        this.sequenceIds.add(sequenceId);
        return this;
    }

    public APIsoformBuilder sequenceStatus(IsoformSequenceStatus isoformSequenceStatus) {
        this.isoformSequenceStatus = isoformSequenceStatus;
        return this;
    }

    public IsoformName getName() {
        return name;
    }

    public List<IsoformName> getSynonyms() {
        return synonyms;
    }

    public List<IsoformId> getIds() {
        return isoformIds;
    }

    public List<String> getSequenceIds() {
        return sequenceIds;
    }

    public Note getNote() {
        return note;
    }

    public IsoformSequenceStatus getSequenceStatus() {
        return isoformSequenceStatus;
    }
}