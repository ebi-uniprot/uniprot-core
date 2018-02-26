package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import java.util.Optional;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;

public class WebResourceCommentImpl extends CommentImpl implements WebResourceComment {
    private final String resourceName;
    private final Optional<String> resourceUrl;
    private boolean isFtp;
    private final Optional<String> note;
    public WebResourceCommentImpl(String resourceName, String resourceUrl, boolean isFtp, String note) {
        super(CommentType.WEBRESOURCE);
        this.resourceName = resourceName;
        this.resourceUrl = ((resourceUrl ==null )|| resourceUrl.isEmpty())? Optional.empty() : Optional.of(resourceUrl);
        this.isFtp = isFtp;
        this.note = ((note ==null )|| note.isEmpty())? Optional.empty() : Optional.of(note);
    }
	@Override
	public String getResourceName() {
		return resourceName;
	}
	@Override
	public Optional<String> getNote() {
		return note;
	}
	@Override
	public Optional<String> getResourceUrl() {
		return resourceUrl;
	}
	@Override
	public boolean isFtp() {
		return isFtp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (isFtp ? 1231 : 1237);
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
		if (isFtp != other.isFtp)
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
