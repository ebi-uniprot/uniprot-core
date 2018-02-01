package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder implements CommentBuilder<WebResourceComment>{
    private  String databaseName;
    private  String databaseUrl;
    private  String databaseFtp;
    private  String note;
    
    public static WebResourceCommentBuilder newInstance(){
        return new WebResourceCommentBuilder();
    }
    public WebResourceComment build(){
        return new  WebResourceCommentImpl( databaseName,  databaseUrl,  databaseFtp,  note);
    }
    public WebResourceCommentBuilder databaseName(String databaseName){
        this.databaseName = databaseName;
        return this;
    }
    
    public WebResourceCommentBuilder databaseUrl(String databaseUrl){
        this.databaseUrl = databaseUrl;
        return this;
    }
    public WebResourceCommentBuilder databaseFtp(String databaseFtp){
        this.databaseFtp = databaseFtp;
        return this;
    }
    
    public WebResourceCommentBuilder note(String note){
        this.note = note;
        return this;
    }
}
