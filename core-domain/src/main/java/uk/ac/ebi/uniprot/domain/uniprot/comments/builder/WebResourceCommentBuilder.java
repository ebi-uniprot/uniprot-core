package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder implements CommentBuilder<WebResourceComment>{
    private  String resourceName;
    private  String resourceUrl;
    	private boolean isFtp =false;
    private  String note;
    
    public static WebResourceCommentBuilder newInstance(){
        return new WebResourceCommentBuilder();
    }
    public WebResourceComment build(){
        return new  WebResourceCommentImpl( resourceName,  resourceUrl,  isFtp,  note);
    }
    public WebResourceCommentBuilder resourceName(String resourceName){
        this.resourceName = resourceName;
        return this;
    }
    
    public WebResourceCommentBuilder resourceUrl(String resourceUrl){
        this.resourceUrl = resourceUrl;
        return this;
    }
    public WebResourceCommentBuilder isFtp(boolean isFtp){
        this.isFtp = isFtp;
        return this;
    }
    
    public WebResourceCommentBuilder note(String note){
        this.note = note;
        return this;
    }
}
