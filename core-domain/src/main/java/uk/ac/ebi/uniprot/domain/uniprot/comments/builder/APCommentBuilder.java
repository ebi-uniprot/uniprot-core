package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APEvent;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comments.APNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentNote;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comments.IsoformSynonym;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.AlternativeProductsCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.APIsoformImpl;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;

import java.util.List;

public final class APCommentBuilder {
    private  List<APEvent> events;
    private  List<APIsoform> isoforms;
    private  APNote note;
    
    public AlternativeProductsComment build(){
        return new AlternativeProductsCommentImpl( events, 
                isoforms,  note  );
    }
    public APCommentBuilder setEvents( List<APEvent> events){
        this.events = events;
        return this;
    }
    
    public APCommentBuilder setIsoforms( List<APIsoform> isoforms){
        this.isoforms = isoforms;
        return this;
    }
    public APCommentBuilder setNote( APNote note){
        this.note = note;
        return this;
    }
    
    public static APEvent createEvent(String val) {
        return AlternativeProductsCommentImpl.createEvent(val);
    }
    public static APNote createAPNote(List<EvidencedValue> texts) {
        return AlternativeProductsCommentImpl.createAPNote(texts);
    }
    public static IsoformSynonym createIsoformSynonym(String value, List<Evidence> evidences) {
        return APIsoformImpl.createIsoformSynonym(value, evidences);
    }

    public static IsoformName createIsoformName(String value, List<Evidence> evidences) {
        return APIsoformImpl.createIsoformName(value, evidences);
    }

    public static class APIsoformBuilder {
        private IsoformName name;
        private List<IsoformSynonym> synonyms;
        private CommentNote note;
        private List<IsoformId> isoformIds;
        private List<String> sequenceIds;
        private IsoformSequenceStatus isoformSequenceStatus;

        public APIsoform build() {
            return new APIsoformImpl(name, synonyms,
                    note, isoformIds,
                    sequenceIds, isoformSequenceStatus);
        }

        public APIsoformBuilder setIsoformName(IsoformName name) {
            this.name = name;
            return this;
        }

        public APIsoformBuilder setIsoformSynonyms(List<IsoformSynonym> synonyms) {
            this.synonyms = synonyms;
            return this;
        }

        public APIsoformBuilder setNote(CommentNote note) {
            this.note = note;
            return this;
        }

        public APIsoformBuilder setIsoformIds(List<IsoformId> isoformIds) {
            this.isoformIds = isoformIds;
            return this;
        }

        public APIsoformBuilder setSequenceIds(List<String> sequenceIds) {
            this.sequenceIds = sequenceIds;
            return this;
        }

        public APIsoformBuilder setIsoformSequenceStatus(IsoformSequenceStatus isoformSequenceStatus) {
            this.isoformSequenceStatus = isoformSequenceStatus;
            return this;
        }
    }

}
