package uk.ac.ebi.uniprot.parser.transformer;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;


public class FreeTextCommentTranslator implements CommentTransformer<FreeTextComment> {
    private static final String EVIDENCE_PREFIX = "{ECO";
    private static final String EVIDENCE_POSTFIX = "}";
    private static final String COLON = ":";

    @Override
    public FreeTextComment transform(String annotation) {
        int index = annotation.indexOf(COLON);
        String type = annotation.substring(0, index);
        annotation = annotation.substring(index + 1).trim();
        CommentType commentType = CommentType.typeOf(type);     
        return transform(commentType, annotation);
    }

    @Override
    public FreeTextComment transform(CommentType type, String annotation) {
        List<EvidencedValue> texts = buildFreeTexts(annotation);
        
    	 FreeTextCommentBuilder builder = FreeTextCommentBuilder.newInstance();
    	return builder.commentType(type)
    .texts(texts)
    .build();
    }

    
    private  List<EvidencedValue> buildFreeTexts(String annotation){
    	 List<EvidencedValue> texts = new ArrayList<>();
    	   int indexPre = 0;
           int indexPost = 0;
       
           do {
               indexPre = annotation.indexOf(EVIDENCE_PREFIX);
               if (indexPre == -1)
                   break;
               indexPost = annotation.indexOf(EVIDENCE_POSTFIX, indexPre);
               if (indexPost == -1) {
                   break;
               }
               String value = annotation.substring(0, indexPost + 1);
               texts.add(createCommentText(value));
               annotation = annotation.substring(indexPost + 2).trim();
           } while ((indexPost != -1) && (indexPre != -1));
           if (!annotation.isEmpty()) {
               texts.add(createCommentText(annotation));
           }
    	 
    	 return texts;
    }
    private EvidencedValue createCommentText(String value) {
        value = CommentTransformerHelper.stripTrailing(value, ".");
        List<Evidence> evidences = new ArrayList<>();
		value = CommentTransformerHelper.stripEvidences(value, evidences);
        value = CommentTransformerHelper.stripTrailing(value, ".");
       return  UniProtFactory.INSTANCE.createEvidencedValue(value, evidences);
    
    }

}
