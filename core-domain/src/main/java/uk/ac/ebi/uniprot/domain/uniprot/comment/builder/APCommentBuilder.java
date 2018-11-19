package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APEventType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.APIsoformImpl;
import uk.ac.ebi.uniprot.domain.uniprot.comment.impl.AlternativeProductsCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

public final class APCommentBuilder implements CommentBuilder<AlternativeProductsComment> {
    private  List<APEventType> events;
    private  List<APIsoform> isoforms;
    private  Note note;
    
    public static APCommentBuilder newInstance(){
        return new APCommentBuilder();
    }
    public AlternativeProductsComment build(){
        return new AlternativeProductsCommentImpl( events, 
                isoforms,  note  );
    }
    public APCommentBuilder events( List<APEventType> events){
        this.events = events;
        return this;
    }
    
    public APCommentBuilder isoforms( List<APIsoform> isoforms){
        this.isoforms = isoforms;
        return this;
    }
    public APCommentBuilder note( Note note){
        this.note = note;
        return this;
    }
    
 
    public static IsoformName createIsoformName(String value, List<Evidence> evidences) {
        return APIsoformImpl.createIsoformName(value, evidences);
    }
    public static IsoformId createIsoformId(String value) {
        return APIsoformImpl.createIsoformId(value);
    }
    public static class APIsoformBuilder {
        private IsoformName name;
        private List<IsoformName> synonyms;
        private Note note;
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

        public APIsoformBuilder isoformSynonyms(List<IsoformName> synonyms) {
            this.synonyms = synonyms;
            return this;
        }

        public APIsoformBuilder note(Note note) {
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
