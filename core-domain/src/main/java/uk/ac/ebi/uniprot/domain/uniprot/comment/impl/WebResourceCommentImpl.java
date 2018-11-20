package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebResourceCommentImpl extends CommentImpl implements WebResourceComment {
	private final String resourceName;
    private final String resourceUrl;
    private final boolean ftp;
    private final String note;
    @JsonCreator
    public WebResourceCommentImpl(
    		@JsonProperty("resourceName") String resourceName, 
    		@JsonProperty("resourceUrl") String resourceUrl, 
    		@JsonProperty("ftp")  boolean ftp, 
    		@JsonProperty("note") String note) {
        super(CommentType.WEBRESOURCE);
        this.resourceName = resourceName;
        if(Strings.isNullOrEmpty(resourceUrl)) {
        	this.resourceUrl = "";
        }else {
        	this.resourceUrl =resourceUrl;
        }
    
        this.ftp = ftp;
        if(Strings.isNullOrEmpty(note)) {
        	this.note = "";
        }else {
        	this.note =note;
        }
    }
	@Override
	public String getResourceName() {
		return resourceName;
	}
	@Override
	public String getNote() {
		return note;
	}
	@Override
	public String getResourceUrl() {
		return resourceUrl;
	}
	@Override
	public boolean isFtp() {
		return ftp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (ftp ? 1231 : 1237);
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourceUrl == null) ? 0 : resourceUrl.hashCode());
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
		if (ftp != other.ftp)
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (resourceUrl == null) {
			if (other.resourceUrl != null)
				return false;
		} else if (!resourceUrl.equals(other.resourceUrl))
			return false;
		return true;
	}

}
