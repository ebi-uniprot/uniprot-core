package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;

public class WebResourceCommentImpl extends CommentImpl implements WebResourceComment {
    private final String databaseName;
    private final String databaseUrl;
    private final String databaseFtp;
    private final String note;
    public WebResourceCommentImpl(String databaseName, String databaseUrl, String databaseFtp, String note) {
        super(CommentType.WEBRESOURCE);
        this.databaseName = databaseName;
        this.databaseUrl =databaseUrl;
        this.databaseFtp = databaseFtp;
        this.note = note;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public boolean hasDatabaseName() {
        return hasValue(databaseName);
    }

    private boolean hasValue(String val){
        return ((val!=null) && !val.isEmpty());
    }
    @Override
    public String getDatabaseNote() {
       return note;
    }

    @Override
    public boolean hasDatabaseNote() {
        return hasValue(note);
    }

    @Override
    public String getDatabaseURL() {
        return databaseUrl;
    }

    @Override
    public boolean hasDatabaseURL() {
        return hasValue(databaseUrl);
    }

    @Override
    public String getDatabaseFTP() {
        return databaseFtp;
    }

    @Override
    public boolean hasDatabaseFTP() {
        return hasValue(databaseFtp);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((databaseFtp == null) ? 0 : databaseFtp.hashCode());
        result = prime * result + ((databaseName == null) ? 0 : databaseName.hashCode());
        result = prime * result + ((databaseUrl == null) ? 0 : databaseUrl.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        WebResourceCommentImpl other = (WebResourceCommentImpl) obj;
        if (databaseFtp == null) {
            if (other.databaseFtp != null)
                return false;
        } else if (!databaseFtp.equals(other.databaseFtp))
            return false;
        if (databaseName == null) {
            if (other.databaseName != null)
                return false;
        } else if (!databaseName.equals(other.databaseName))
            return false;
        if (databaseUrl == null) {
            if (other.databaseUrl != null)
                return false;
        } else if (!databaseUrl.equals(other.databaseUrl))
            return false;
        if (note == null) {
            if (other.note != null)
                return false;
        } else if (!note.equals(other.note))
            return false;
        return true;
    }

}
