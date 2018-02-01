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

public final class APCommentBuilder implements CommentBuilder<AlternativeProductsComment> {
    private  List<APEvent> events;
    private  List<APIsoform> isoforms;
    private  APNote note;
    
    public static APCommentBuilder newInstance(){
        return new APCommentBuilder();
    }
    public AlternativeProductsComment build(){
        return new AlternativeProductsCommentImpl( events, 
                isoforms,  note  );
    }
    public APCommentBuilder events( List<APEvent> events){
        this.events = events;
        return this;
    }
    
    public APCommentBuilder isoforms( List<APIsoform> isoforms){
        this.isoforms = isoforms;
        return this;
    }
    public APCommentBuilder note( APNote note){
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
    public static IsoformId createIsoformId(String value) {
        return APIsoformImpl.createIsoformId(value);
    }

    public static class APIsoformBuilder {
        private IsoformName name;
        private List<IsoformSynonym> synonyms;
        private CommentNote note;
        private List<IsoformId> isoformIds;
        private List<String> sequenceIds;
        private IsoformSequenceStatus isoformSequenceStatus;

        public static APIsoformBuilder newInstance(){
            return new APIsoformBuilder();
        }
        public APIsoform build() {
            return new APIsoformImpl(name, synonyms,
                    note, isoformIds,
                    sequenceIds, isoformSequenceStatus);
        }

        public APIsoformBuilder isoformName(IsoformName name) {
            this.name = name;
            return this;
        }

        public APIsoformBuilder isoformSynonyms(List<IsoformSynonym> synonyms) {
            this.synonyms = synonyms;
            return this;
        }

        public APIsoformBuilder note(CommentNote note) {
            this.note = note;
            return this;
        }

        public APIsoformBuilder isoformIds(List<IsoformId> isoformIds) {
            this.isoformIds = isoformIds;
            return this;
        }

        public APIsoformBuilder sequenceIds(List<String> sequenceIds) {
            this.sequenceIds = sequenceIds;
            return this;
        }

        public APIsoformBuilder isoformSequenceStatus(IsoformSequenceStatus isoformSequenceStatus) {
            this.isoformSequenceStatus = isoformSequenceStatus;
            return this;
        }
    }

}
