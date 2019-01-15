package uk.ac.ebi.uniprot.domain.uniprot.comment2.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment2.impl.AlternativeProductsCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

public final class APCommentBuilder implements CommentBuilder<APCommentBuilder, AlternativeProductsComment> {
    private List<APEventType> events = new ArrayList<>();
    private List<APIsoform> isoforms = new ArrayList<>();
    private Note note;

    public AlternativeProductsComment build() {
        return new AlternativeProductsCommentImpl(this);
    }

    @Override
    public APCommentBuilder from(AlternativeProductsComment instance) {
        return new APCommentBuilder()
                .events(instance.getEvents())
                .isoforms(instance.getIsoforms())
                .note(instance.getNote());
    }

    public APCommentBuilder events(List<APEventType> events) {
        this.events.addAll(events);
        return this;
    }

    public APCommentBuilder addEvent(APEventType event) {
        this.events.add(event);
        return this;
    }

    public APCommentBuilder isoforms(List<APIsoform> isoforms) {
        this.isoforms.addAll(isoforms);
        return this;
    }

    public APCommentBuilder addIsoform(APIsoform isoform) {
        this.isoforms.add(isoform);
        return this;
    }

    public APCommentBuilder note(Note note) {
        this.note = note;
        return this;
    }

    public List<APEventType> getEvents() {
        return events;
    }

    public List<APIsoform> getIsoforms() {
        return isoforms;
    }

    public Note getNote() {
        return note;
    }

    public static class APIsoformBuilder implements Builder2<APIsoformBuilder, APIsoform> {
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

    public static class IsoformNameBuilder extends AbstractEvidencedValueBuilder<IsoformNameBuilder> {
        @Override
        protected IsoformNameBuilder createInstance() {
            return new IsoformNameBuilder();
        }
    }
}
