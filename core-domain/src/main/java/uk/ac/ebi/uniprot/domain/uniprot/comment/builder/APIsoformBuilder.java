package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;

import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    public APIsoformBuilder isoformName(IsoformName name) {
        this.name = name;
        return this;
    }

    public APIsoformBuilder isoformSynonyms(List<IsoformName> synonyms) {
        this.synonyms.addAll(synonyms);
        return this;
    }

    public APIsoformBuilder addIsoformSynonym(IsoformName synonym) {
        this.synonyms.add(synonym);
        return this;
    }

    public APIsoformBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public APIsoformBuilder isoformIds(List<String> isoformIds) {
        isoformIds.stream().map(APIsoformImpl.IsoformIdImpl::new).forEach(this.isoformIds::add);
        return this;
    }

    public APIsoformBuilder addIsoformId(String isoformId) {
        this.isoformIds.add(new APIsoformImpl.IsoformIdImpl(isoformId));
        return this;
    }

    public APIsoformBuilder sequenceIds(List<String> sequenceIds) {
        this.sequenceIds.addAll(sequenceIds);
        return this;
    }

    public APIsoformBuilder addSequenceId(String sequenceId) {
        this.sequenceIds.add(sequenceId);
        return this;
    }

    public APIsoformBuilder isoformSequenceStatus(IsoformSequenceStatus isoformSequenceStatus) {
        this.isoformSequenceStatus = isoformSequenceStatus;
        return this;
    }

    public IsoformName getName() {
        return name;
    }

    public List<IsoformName> getSynonyms() {
        return synonyms;
    }

    public List<IsoformId> getIsoformIds() {
        return isoformIds;
    }

    public List<String> getSequenceIds() {
        return sequenceIds;
    }

    public Note getNote() {
        return note;
    }

    public IsoformSequenceStatus getIsoformSequenceStatus() {
        return isoformSequenceStatus;
    }
}