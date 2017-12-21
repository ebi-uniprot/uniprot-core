package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.impl.WebResourceCommentImpl;

public final class WebResourceCommentBuilder {
    private  String databaseName;
    private  String databaseUrl;
    private  String databaseFtp;
    private  String note;
    
    public WebResourceComment build(){
        return new  WebResourceCommentImpl( databaseName,  databaseUrl,  databaseFtp,  note);
    }
    public WebResourceCommentBuilder setDatabaseName(String databaseName){
        this.databaseName = databaseName;
        return this;
    }
    
    public WebResourceCommentBuilder setDatabaseUrl(String databaseUrl){
        this.databaseUrl = databaseUrl;
        return this;
    }
    public WebResourceCommentBuilder setDatabaseFtp(String databaseFtp){
        this.databaseFtp = databaseFtp;
        return this;
    }
    
    public WebResourceCommentBuilder seNote(String note){
        this.note = note;
        return this;
    }
}
